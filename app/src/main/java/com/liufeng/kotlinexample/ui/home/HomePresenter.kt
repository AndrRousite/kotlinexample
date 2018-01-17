package com.liufeng.kotlinexample.ui.home

import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.liufeng.kotlinexample.base.BasePresenter
import com.liufeng.kotlinexample.bean.HomeEntity

/**
 * Created by liu-feng on 2018/1/17.
 */
class HomePresenter : BasePresenter<HomeContract.Model, HomeContract.View>() {
    private var url: String? = null
    private var homeEntity: HomeEntity? = null

    fun loadFirstData(num: Int) {
        // 检测是否绑定 View
        checkViewAttached()
        mView?.showLoading()
        val disposable = mModel!!.loadFirstData(num).flatMap({ bean ->

            //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
            val bannerItemList = bean.issueList[0].itemList

            bannerItemList.filter { item ->
                item.type == "banner2" || item.type == "horizontalScrollCard"
            }.forEach { item ->
                //移除 item
                bannerItemList.remove(item)
            }

            homeEntity = bean //记录第一页是当做 banner 数据


            //根据 nextPageUrl 请求下一页数据
            mModel!!.loadMoreData(bean.nextPageUrl)
        }).subscribe({ bean ->
            mView?.apply {
                hideLoading()

                url = bean.nextPageUrl
                //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                val newBannerItemList = bean.issueList[0].itemList

                newBannerItemList.filter { item ->
                    item.type == "banner2" || item.type == "horizontalScrollCard"
                }.forEach { item ->
                    //移除 item
                    newBannerItemList.remove(item)
                }
                // 重新赋值 Banner 长度
                homeEntity!!.issueList[0].count = homeEntity!!.issueList[0].itemList.size

                //赋值过滤后的数据 + banner 数据
                homeEntity?.issueList!![0].itemList.addAll(newBannerItemList)

                setFirstData(homeEntity!!)
            }
        }, { t ->
            mView?.apply {
                hideLoading()
                showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
            }
        })
        addSubscription(disposable)
    }

    fun loadMoreData() {
        val disposable = url?.let {
            mModel!!.loadMoreData(it).subscribe({ bean ->
                mView?.apply {
                    //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                    val newItemList = bean.issueList[0].itemList

                    newItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        //移除 item
                        newItemList.remove(item)
                    }

                    url = bean.nextPageUrl
                    setMoreData(newItemList)
                }
            }, { t ->
                mView?.apply {
                    showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }
            })
        }
        if (disposable != null) {
            addSubscription(disposable)
        }
    }
}