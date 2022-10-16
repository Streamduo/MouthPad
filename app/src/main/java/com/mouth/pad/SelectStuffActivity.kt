package com.mouth.pad

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.jeremyliao.liveeventbus.LiveEventBus
import com.mouth.pad.adapter.SelectStuffListAdapter
import com.mouth.pad.base.BaseActivity
import com.mouth.pad.bean.event.SelectStuffEvent
import com.mouth.pad.utils.Const
import kotlinx.android.synthetic.main.activity_select_stuff.*

class SelectStuffActivity : BaseActivity() {

    var currentIndex = 1
    private val pageSize = Const.PAGE_SIZE
    private val allNetViewModel by lazy {
        AllNetViewModel()
    }

    private val selectStuffListAdapter by lazy {
        SelectStuffListAdapter(R.layout.item_select_stuff_list)
    }

    override fun layoutId(): Int {
        return R.layout.activity_select_stuff
    }

    override fun initData() {
    }

    override fun initView() {
        iv_back.setOnSingleClickListener {
            finish()
        }
        rv_refresh_list.apply {
            layoutManager = LinearLayoutManager(this@SelectStuffActivity)
            adapter = selectStuffListAdapter
        }
        srl_refresh?.apply {
            //是否启用下拉刷新功能
            setEnableRefresh(true)
            //是否启用上拉加载功能
            setEnableLoadMore(true)
            //是否启用列表惯性滑动到底部时自动加载更多
            setEnableAutoLoadMore(true)
            //是否在列表不满一页时候开启上拉加载功能
            setEnableLoadMoreWhenContentNotFull(true)
            setOnRefreshListener {
                currentIndex = 1
                getQueryDate()
            }
            setOnLoadMoreListener {
                currentIndex++
                getQueryDate()
            }
        }
        selectStuffListAdapter.setOnItemClickListener { _, _, position ->
            val item = selectStuffListAdapter.getItem(position)
            LiveEventBus.get("SelectStuffEvent").post(SelectStuffEvent(item))
            finish()
        }
        te_search.setOnSingleClickListener {
            currentIndex = 1
            getQueryDate()
            closeKeyBord(ed_search,this)
        }
    }

    private fun getQueryDate() {
        val searchKey = ed_search.text.toString().trim()
        getSelectMaterialPage(searchKey)
    }

    private fun getSelectMaterialPage(invName: String) {
        allNetViewModel.getSelectMaterialPage(invName, currentIndex, pageSize).observe(this, {
            if (it.isOk()) {
                if (!it.data?.rows.isNullOrEmpty()) {
                    if (currentIndex == 1) {
                        selectStuffListAdapter.setNewInstance(it.data?.rows)
                    } else {
                        selectStuffListAdapter.addData(it.data?.rows!!)
                    }
                } else {
                    if (currentIndex == 1) {
                        showToast("暂无相关材料")
                        selectStuffListAdapter.data.clear()
                        selectStuffListAdapter.notifyDataSetChanged()
                    } else {
                        showToast("没有更多了")
                    }
                }
            } else {
                it.msg?.let { msg ->
                    showToast(msg)
                }
            }
            if (currentIndex == 1) {
                srl_refresh.finishRefresh()
            } else {
                srl_refresh.finishLoadMore()
            }
        })
    }

    override fun start() {
    }

    override fun isNeedLogin(): Boolean {
        return true
    }

    override fun isUseBlackFontWithStatusBar(): Boolean {
        return false
    }

    override fun getStatusBarColor(): Int {
        return R.color.color_1876FF
    }

    companion object {

        fun launchSelectStuffActivity(context: Context?) {
            context?.startActivity(Intent(context, SelectStuffActivity::class.java).apply {
            })
        }

    }

}