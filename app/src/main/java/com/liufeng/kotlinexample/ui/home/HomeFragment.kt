package com.liufeng.kotlinexample.ui.home

import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat.getColor
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.liufeng.kotlinexample.R
import me.letion.geetionlib.base.BaseFragment
import com.liufeng.kotlinexample.bean.HomeEntity
import me.letion.geetionlib.util.TStatusBar
import com.scwang.smartrefresh.header.MaterialHeader
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by liu-feng on 2018/1/17.
 */
class HomeFragment : BaseFragment(), HomeContract.View {
    private var mRefreshing = false
    private var mLoadMoreing = false
    private var mMaterialHeader: MaterialHeader? = null
    private var mHomeAdapter: HomeAdapter? = null
    private val mPresenter by lazy { HomePresenter() }
    private val linearLayoutManager by lazy { LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false) }
    private val simpleDateFormat by lazy { SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH) }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        mPresenter.attach(this, HomeModel())
        mLayoutStatusView = statusView
        refreshView.setEnableFooterTranslationContent(true)
        refreshView.setOnRefreshListener {
            mRefreshing = true
            mPresenter.loadFirstData(1)
        }
        mMaterialHeader = refreshView.refreshHeader as MaterialHeader?
        mMaterialHeader?.setShowBezierWave(true)
//        refreshView.setPrimaryColors(R.color.color_light_black, R.color.color_title_bg)

        // 状态栏透明和间距处理
        TStatusBar.darkMode(activity)
        TStatusBar.setPaddingSmart(activity, toolbar)

        iv_search.setOnClickListener {
            //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, iv_search, iv_search.transitionName)
//                startActivity(Intent(activity, SearchActivity::class.java), options.toBundle())
//            } else {
//                startActivity(Intent(activity, SearchActivity::class.java))
//            }
        }

        // recycler view 滚动监听
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = recyclerView.childCount
                    val itemCount = recyclerView.layoutManager.itemCount
                    val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        if (!mLoadMoreing) {
                            mLoadMoreing = true
                            mPresenter.loadMoreData()
                        }
                    }
                }
            }

            //RecyclerView滚动的时候调用
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    //背景设置为透明
                    toolbar.setBackgroundColor(getColor(R.color.color_translucent))
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_title.text = ""
                } else {
                    if (mHomeAdapter?.mData!!.size > 1) {
                        toolbar.setBackgroundColor(getColor(R.color.color_title_bg))
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList = mHomeAdapter!!.mData
                        val item = itemList[currentVisibleItemPosition + mHomeAdapter!!.bannerItemSize - 1]
                        if (item.type == "textHeader") {
                            tv_title.text = item.data?.text
                        } else {
                            tv_title.text = simpleDateFormat.format(item.data?.date)
                        }
                    }
                }

            }
        })
    }

    override fun initData() {

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