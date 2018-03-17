package me.letion.geetionlib

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import kotlin.properties.Delegates

/**
 * Created by liu-feng on 2017/12/19.
 */
class KotlinApplication : Application() {

    private var refWatcher: RefWatcher? = null

    companion object {
        var context: Context by Delegates.notNull()
            private set

        fun getRefWatcher(context: Context): RefWatcher? {
            val application = context.applicationContext as KotlinApplication
            return application.refWatcher
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        refWatcher = if (LeakCanary.isInAnalyzerProcess(this)) RefWatcher.DISABLED else LeakCanary.install(this)

        // 初始化日志信息
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 打印线程信息，默认不打印
                .methodCount(0)   // 决定打印多少行（每一行代表一个方法）默认：2
                .methodOffset(7)     // (Optional) Hides internal method calls up to offset. Default 5
                .tag("Letion")
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        // 注册activity的生命周期
        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(p0: Activity?) {
            }

            override fun onActivityResumed(p0: Activity?) {
            }

            override fun onActivityStarted(p0: Activity?) {
            }

            override fun onActivityDestroyed(p0: Activity) {
                Logger.d("Activity Destroyed:" + p0.componentName.className)
            }

            override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
            }

            override fun onActivityStopped(p0: Activity?) {
            }

            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                Logger.d("Activity Created:" + p0.componentName.className)
            }

        })
    }
}