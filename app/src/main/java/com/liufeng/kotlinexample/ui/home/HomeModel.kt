package com.liufeng.kotlinexample.ui.home

import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import com.liufeng.kotlinexample.api.RetrofitFactory
import com.liufeng.kotlinexample.bean.HomeEntity
import io.reactivex.Observable

/**
 * Created by liu-feng on 2018/1/17.
 */
class HomeModel : HomeContract.Model {
    override fun loadFirstData(num: Int): Observable<HomeEntity> {
        return RetrofitFactory.service.getFirstHome(num).compose(SchedulerUtils.ioToMain())
    }

    override fun loadMoreData(url: String): Observable<HomeEntity> {
        return RetrofitFactory.service.getMoreHome(url).compose(SchedulerUtils.ioToMain())
    }
}