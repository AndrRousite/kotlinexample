package com.liufeng.kotlinexample.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.liufeng.kotlinexample.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_content.text = "Hello Kotlin Example"
    }

}
