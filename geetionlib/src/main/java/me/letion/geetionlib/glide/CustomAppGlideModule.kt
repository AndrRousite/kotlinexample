package me.letion.geetionlib.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

/**
 * Created by Administrator on 2018/3/17.
 */
@GlideModule
class CustomAppGlideModule : AppGlideModule() {
    /**
     * 通过GlideBuilder配置(Engine,BitmapPool ,ArrayPool,MemoryCache等等)
     */
    override fun applyOptions(context: Context?, builder: GlideBuilder?) {
        super.applyOptions(context, builder)
        // 设置最大缓存
        builder!!.setMemoryCache(LruResourceCache(10 * 1024 * 1024))
    }

    /**
     * 是否解析manifest文件配置glide，默认是开启
     * 设置false通过代码配置，避免添加module两次
     */
    override fun isManifestParsingEnabled(): Boolean {
        return !super.isManifestParsingEnabled()
    }

    /**
     * 为APP注册自定义的String类型的BaseGlideUrlLoader
     */
    override fun registerComponents(context: Context?, glide: Glide?, registry: Registry?) {
        super.registerComponents(context, glide, registry)
        registry!!.append(String::class.java, InputStream::class.java, GlideImageLoader.GlideImageLoaderFactory())
    }
}