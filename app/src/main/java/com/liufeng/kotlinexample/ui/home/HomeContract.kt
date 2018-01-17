package com.liufeng.kotlinexample.ui.home

import com.liufeng.kotlinexample.base.IModel
import com.liufeng.kotlinexample.base.IView
import com.liufeng.kotlinexample.bean.HomeEntity
import io.reactivex.Observable

/**
 * Created by liu-feng on 2018/1/17.
 */
interface HomeContract {
    interface View : IView {
        fun setFirstData(data: HomeEntity)
        fun setMoreData(data: ArrayList<HomeEntity.Issue.Item>)
        fun showError(msg: String, code: Int)
    }

    interface Model : IModel {
        fun loadFirstData(num: Int): Observable<HomeEntity>
        fun loadMoreData(url :String): Observable<HomeEntity>
    }
}