package com.liufeng.kotlinexample.api

import com.liufeng.kotlinexample.KotlinApplication
import com.liufeng.kotlinexample.util.NetworkUtils
import com.liufeng.kotlinexample.util.SPUtils
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by liu-feng on 2018/1/17.
 */
object RetrofitFactory {
    private var client: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    private var token: String by SPUtils("token", "")
    val service: ApiService by lazy { getRetrofit()!!.create(ApiService::class.java)}

    private fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            synchronized(RetrofitFactory::class.java) {
                // 50M缓存大小
                val cache = Cache(File(KotlinApplication.context.cacheDir, "cache"), 50 * 1024 * 1024)

                // 获取HttpClient实例
                client = OkHttpClient.Builder()
                        .addInterceptor(addQueryParameterInterceptor())
                        .addInterceptor(addHeaderParameterInterceptor())
                        .addInterceptor(addChaceInterceptor())
                        .addInterceptor(addHttpLoggingInterceptor())
                        .cache(cache)
                        .connectTimeout(60L, TimeUnit.SECONDS)
                        .readTimeout(60L, TimeUnit.SECONDS)
                        .writeTimeout(60L, TimeUnit.SECONDS)
                        .build()
                // 获取Retrofit实例
                retrofit = Retrofit.Builder()
                        .baseUrl(API.HOST)
                        .client(client!!)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
        }
        return retrofit
    }

    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                    .addQueryParameter("phoneSystem", "")
                    .addQueryParameter("phoneModel", "")
                    .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    /**
     * 设置请求头
     */
    private fun addHeaderParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    .header("token", token)
                    .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    /**
     * 设置日志打印级别
     */
    private fun addHttpLoggingInterceptor(): Interceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    private fun addChaceInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtils.isNetworkAvailable(KotlinApplication.context)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            val response = chain.proceed(request)
            if (NetworkUtils.isNetworkAvailable(KotlinApplication.context)) {
                val maxAge = 0
                response.newBuilder().header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build()
            } else {
                // 无网络
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("nyn")
                        .build()
            }
            response
        }
    }
}