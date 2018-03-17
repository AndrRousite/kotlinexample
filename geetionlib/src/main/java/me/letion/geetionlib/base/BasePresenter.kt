package me.letion.geetionlib.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by liu-feng on 2017/12/19.
 */
open class BasePresenter<M : IModel, V : IView> : IPresenter<M, V> {
    var mView: V? = null
        protected set
    var mModel: M? = null
        protected set

    override fun attach(mView: V) {
        this.mView = mView
    }

    override fun attach(mView: V, mModel: M) {
        this.mView = mView
        this.mModel = mModel
    }

    override fun detach() {
        mView = null
        mModel = null
        //保证activity结束时取消所有正在执行的订阅
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    private var compositeDisposable = CompositeDisposable()


    private val isViewAttached: Boolean
        get() = mView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")
}