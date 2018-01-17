package com.liufeng.kotlinexample.base

/**
 * Created by liu-feng on 2017/12/19.
 */
interface IPresenter<in M : IModel, in V : IView> {
    fun attach(mView: V)

    fun attach(mView: V, mModel: M)

    fun detach()
}