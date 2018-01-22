package com.liufeng.kotlinexample.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.liufeng.kotlinexample.KotlinApplication
import com.liufeng.multiplestatusview.MultipleStatusView

/**
 * Created by liu-feng on 2017/12/19.
 */
abstract class BaseFragment : Fragment() {

    private var prepareView = false
    private var prepareData = false

    protected var mLayoutStatusView: MultipleStatusView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(getLayoutId(), null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareView = true
        initView()
        initData()

        lazyLoadDataIfPrepared()

        // 重试 ，加载数据
        mLayoutStatusView?.setOnRetryClickListener(mRetryClickListener)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initData()

    // 加载数据
    abstract fun lazyLoadData()

    /**
     * 懒加载
     */
    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && prepareView && !prepareData) {
            lazyLoadData()
            prepareData = true
        }
    }

    /**
     * 重试加载数据
     */
    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoadData()
    }

    fun showToast(content: String) {
        val toast = Toast.makeText(context, content, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        KotlinApplication.getRefWatcher(context)?.watch(context)
    }
}