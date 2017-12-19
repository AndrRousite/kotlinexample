package com.liufeng.kotlinexample.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.liufeng.kotlinexample.KotlinApplication

/**
 * Created by liu-feng on 2017/12/19.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
        initData()
    }


    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initData()

    fun toggleSoftKeyboard(view: View, close: Boolean) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (close) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } else {
            imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN)
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }

    fun showToast(content: String) {
        val toast = Toast.makeText(this, content, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        KotlinApplication.getRefWatcher(this)?.watch(this)
    }
}