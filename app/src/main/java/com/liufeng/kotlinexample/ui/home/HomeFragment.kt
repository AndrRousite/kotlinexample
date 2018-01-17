package com.liufeng.kotlinexample.ui.home

import com.liufeng.kotlinexample.base.BaseFragment
import com.liufeng.kotlinexample.bean.HomeEntity

/**
 * Created by liu-feng on 2018/1/17.
 */
class HomeFragment : BaseFragment(), HomeContract.View {
    override fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lazyLoadData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setFirstData(data: HomeEntity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setMoreData(data: ArrayList<HomeEntity.Issue.Item>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(msg: String, code: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}