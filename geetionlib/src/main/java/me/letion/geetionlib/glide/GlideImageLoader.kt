package me.letion.geetionlib.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import java.io.InputStream
import java.util.regex.Pattern

/**
 * Created by Administrator on 2018/3/17.
 */
class GlideImageLoader(modelLoader: ModelLoader<GlideUrl, InputStream>, modelCache: ModelCache<String, GlideUrl>) : BaseGlideUrlLoader<String>(modelLoader, modelCache) {
    /**
     * 控制图片大小
     */
    override fun getUrl(model: String, width: Int, height: Int, options: Options?): String {
        val m = PATTERN.matcher(model)
        var bestBucket: Int
        if (m.find()) {
            val found = m.group(1).split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (bucketStr in found) {
                bestBucket = Integer.parseInt(bucketStr)
                if (bestBucket >= width)
                    break
            }
        }
        return model
    }

    override fun handles(model: String?): Boolean {
        return true
    }


    companion object {
        private val PATTERN = Pattern.compile("__w-((?:-?\\d+)+)__")
        private val urlCACHE = ModelCache<String, GlideUrl>(150)
    }

    class GlideImageLoaderFactory : ModelLoaderFactory<String, InputStream> {
        override fun teardown() {

        }

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> {
            return GlideImageLoader(multiFactory.build(GlideUrl::class.java, InputStream::class.java), urlCACHE)
        }

    }

}