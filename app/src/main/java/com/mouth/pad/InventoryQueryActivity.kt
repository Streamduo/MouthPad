package com.mouth.pad

import android.Manifest
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bmncc.pis.ylct.utils.setOnSingleClickListener
import com.mouth.pad.adapter.ConsumptionQueryListAdapter
import com.mouth.pad.adapter.InventoryQueryListAdapter
import com.mouth.pad.base.BaseActivity
import com.mouth.pad.utils.Const
import com.permissionx.guolindev.PermissionX
import com.xys.libzxing.zxing.activity.CaptureActivity
import kotlinx.android.synthetic.main.activity_inventory_query.*
import kotlinx.android.synthetic.main.layout_title.*

//库存查询
class InventoryQueryActivity : BaseActivity() {

    var currentIndex = 1
    private val pageSize = Const.PAGE_SIZE
    private val allNetViewModel by lazy {
        AllNetViewModel()
    }
    private var selectMaterialCode: String? = ""
    private val inventoryQueryListAdapter by lazy {
        InventoryQueryListAdapter(R.layout.item_inventory_list)
    }
    private var selectStorehouseCode = ""
    private var selectStorehouse = ""

    override fun layoutId(): Int {
        return R.layout.activity_inventory_query
    }

    override fun initData() {
    }

    override fun initView() {
        tv_title.text = "库存查询"
        title_back.setOnSingleClickListener {
            finish()
        }
        rv_refresh_list.apply {
            layoutManager = LinearLayoutManager(this@InventoryQueryActivity)
            adapter = inventoryQueryListAdapter
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
        val storehouseCode = resources.getStringArray(R.array.storehouseCodeAll)
        val storehouse = resources.getStringArray(R.array.storehouseAll)
        sp_storehouse.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectStorehouseCode = storehouseCode[p2]
                selectStorehouse = storehouse[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        //扫码
        te_sCode.setOnSingleClickListener {
            getPermissions()
        }
        //重置
        te_reset.setOnSingleClickListener {
            te_search_result.visibility = View.GONE
            inventoryQueryListAdapter.data.clear()
            inventoryQueryListAdapter.notifyDataSetChanged()
            sp_storehouse.setSelection(0)
            te_stuff_name.text = ""
        }
        te_query.setOnSingleClickListener {
            currentIndex = 1
            getQueryDate()
        }
    }

    private fun getQueryDate() {
        getAllStuffPageStorehouse(selectMaterialCode, selectStorehouseCode, currentIndex, pageSize)
    }

    private fun getAllStuffPageStorehouse(
        materialCode: String?,
        warehouseCode: String,
        pageNum: Int,
        pageSize: Int
    ) {
        allNetViewModel.getAllStuffPageStorehouse(materialCode, warehouseCode, pageNum, pageSize)
            .observe(this, {
                if (it.isOk()) {
                    if (!it.data?.rows.isNullOrEmpty()) {
                        te_search_result.visibility = View.VISIBLE
                        if (currentIndex == 1) {
                            inventoryQueryListAdapter.setNewInstance(it.data?.rows)
                        } else {
                            inventoryQueryListAdapter.addData(it.data?.rows!!)
                        }
                    } else {
                        if (currentIndex == 1) {
                            te_search_result.visibility = View.GONE
                            showToast("暂无相关记录")
                            inventoryQueryListAdapter.data.clear()
                            inventoryQueryListAdapter.notifyDataSetChanged()
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


    private fun getPermissions() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA
            )
            .request { allGranted, _, _ ->
                //所有权限都允许
                if (allGranted) {
                    goCapture()
                }
            }
    }

    private fun goCapture() {
        startActivityForResult(Intent(this, CaptureActivity::class.java), 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == RESULT_OK) {
            val bundle = data?.extras
            if (bundle != null) {
                val result = bundle.getString("result")
                selectByMaterialCode(result)
            }
        }
    }

    ////根据材料编号查询-材料基本信息
    private fun selectByMaterialCode(materialCode: String?) {
        allNetViewModel.selectByMaterialCode(materialCode).observe(this, {
            if (it.isOk()) {
                it.data?.apply {
                    te_stuff_name.text = invName
                    selectMaterialCode = materialCode
                }
            } else {
                it.msg?.let { msg ->
                    showToast(msg)
                }
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

        fun launchInventoryQueryActivity(context: Context?) {
            context?.startActivity(Intent(context, InventoryQueryActivity::class.java).apply {
            })
        }

    }

}