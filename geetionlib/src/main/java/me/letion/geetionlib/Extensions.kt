package me.letion.geetionlib

import android.app.Fragment
import android.content.Context
import android.widget.Toast

/**
 * Created by Administrator on 2018/3/17.
 */

fun Context.showToast(content: String): Toast {
    val toast = Toast.makeText(this.applicationContext, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun Fragment.showToast(content: String): Toast {
    val toast = Toast.makeText(this.activity.applicationContext, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}