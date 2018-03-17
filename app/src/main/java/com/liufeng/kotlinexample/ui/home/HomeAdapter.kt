package com.liufeng.kotlinexample.ui.home

import android.content.Context
import com.liufeng.kotlinexample.bean.HomeEntity

/**
 * Created by liu-feng on 2018/1/22.
 */
class HomeAdapter(context: Context,data:ArrayList<HomeEntity.Issue.Item>){
    // item type
    companion object {
        private val ITEM_BANNER = 1
        private val ITEM_TITLE = 2
        private val ITEM_CONTENT = 3
    }
}