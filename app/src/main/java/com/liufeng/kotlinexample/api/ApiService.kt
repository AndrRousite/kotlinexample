package com.liufeng.kotlinexample.api

import com.liufeng.kotlinexample.bean.HomeEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by liu-feng on 2018/1/17.
 */
interface ApiService {
    // 首页精选
    @GET("/v2/feed")
    fun getFirstHome(@Query("num") num: Int): Observable<HomeEntity>

    @GET
    fun getMoreHome(@Url url: String): Observable<HomeEntity>
}