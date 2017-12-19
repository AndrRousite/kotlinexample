package com.liufeng.kotlinexample.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.liufeng.kotlinexample.KotlinApplication
import com.liufeng.kotlinexample.R
import com.liufeng.kotlinexample.base.BaseActivity
import com.liufeng.kotlinexample.ui.MainActivity
import com.liufeng.kotlinexample.util.AppUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_splash.*
import me.weyye.hipermission.HiPermission
import me.weyye.hipermission.PermissionCallback
import me.weyye.hipermission.PermissionItem

/**
 * Created by liu-feng on 2017/12/19.
 */
class SplashActivity : BaseActivity() {

    private var textTypeface: Typeface? = null

    private var descTypeFace: Typeface? = null

    private var alphaAnimation: AlphaAnimation? = null

    init {
        textTypeface = Typeface.createFromAsset(KotlinApplication.context.assets, "fonts/Lobster-1.4.otf")
        descTypeFace = Typeface.createFromAsset(KotlinApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        tv_app_name.typeface = textTypeface
        tv_splash_desc.typeface = descTypeFace
        tv_version_name.text = "v${AppUtils.getVerName(KotlinApplication.context)}"

        //渐变展示启动屏
        alphaAnimation = AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 5000
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(arg0: Animation) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationStart(animation: Animation) {}

        })
    }

    override fun initData() {
        val permissionItems = ArrayList<PermissionItem>()
        permissionItems.add(PermissionItem(Manifest.permission.READ_PHONE_STATE, "手机状态", R.drawable.permission_ic_phone))
        permissionItems.add(PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储空间", R.drawable.permission_ic_storage))
        HiPermission.create(this)
                .title("亲爱的上帝")
                .msg("为了能够正常使用，请开启这些权限吧！")
                .permissions(permissionItems)
                .style(R.style.PermissionDefaultBlueStyle)
                .animStyle(R.style.PermissionAnimScale)
                .checkMutiPermission(object : PermissionCallback {
                    override fun onClose() {
                        Logger.i("permission_onClose")
                        showToast("用户关闭了权限")
                    }

                    override fun onFinish() {
                        layout_splash.startAnimation(alphaAnimation)
                    }

                    override fun onDeny(permission: String, position: Int) {
                        Logger.i("permission_onDeny")
                    }

                    override fun onGuarantee(permission: String, position: Int) {
                        Logger.i("permission_onGuarantee")
                    }
                })
    }

}