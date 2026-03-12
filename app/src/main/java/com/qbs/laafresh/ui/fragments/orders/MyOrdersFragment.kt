package com.qbs.laafresh.ui.fragments.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.qbs.laafresh.data.network.api.reponse.MyOrderListItem
import com.qbs.laafresh.data.viewmodel.VMCheckOut
import com.qbs.laafresh.databinding.FragmentMyOrdersBinding
import com.qbs.laafresh.ui.adapter.OderHistoryAdapter
import com.qbs.laafresh.ui.extension.isConnected
import com.qbs.laafresh.ui.extension.toast
import java.util.*

class MyOrdersFragment : Fragment() {
    private lateinit var binding: FragmentMyOrdersBinding
    private val vmCheckOut: VMCheckOut by lazy {
        this.let {
            ViewModelProvider(it, VMCheckOut.Factory(requireContext()))
                .get(VMCheckOut::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyOrdersBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.tbMyOrders) { v, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            v.setPadding(0, statusBar, 0, 0)
            insets
        }

        showLoading(true)
        if (isConnected(requireActivity())) {
            getOrderHistory()
        } else {
            showLoading(false)
            activity?.toast("Please Check the Internet")
        }
    }

    private fun getOrderHistory() {
        vmCheckOut.getMyOrderList(onSuccess = { myOrderListResponse ->
            showLoading(false)
            if (myOrderListResponse?.orders!!.size > 0) {
                binding.tvEmptyMenuOrder.visibility = View.GONE
                binding.rcHistoryList.visibility = View.VISIBLE
                setOderHistoryAdapter(myOrderListResponse.orders)
            } else {
                binding.rcHistoryList.visibility = View.GONE
                binding.tvEmptyMenuOrder.visibility = View.VISIBLE
            }
        }, onError = {
            showLoading(false)
            activity?.toast(it.toString())
        })
    }

    private fun setOderHistoryAdapter(orders: ArrayList<MyOrderListItem>) {
        binding.rcHistoryList.apply {
            binding.rcHistoryList.adapter = OderHistoryAdapter()
        }
        (binding.rcHistoryList.adapter as OderHistoryAdapter).addHistoryItem(orders)
    }

    private fun showLoading(isLoading: Boolean) {
        if (binding.pbMyOrder != null) {
            if (isLoading)
                binding.pbMyOrder.visibility = View.VISIBLE
            else
                binding.pbMyOrder.visibility = View.GONE
        }
    }
}


