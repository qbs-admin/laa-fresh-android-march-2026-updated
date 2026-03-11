package com.qbs.laafresh.ui.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qbs.laafresh.R
import com.qbs.laafresh.data.dbhelper.LaaFreshDAO
import com.qbs.laafresh.data.network.api.reponse.MyOrderListItem
import com.qbs.laafresh.databinding.AdapterMyOrderListBinding
import org.json.JSONArray


class OderHistoryAdapter() :
    RecyclerView.Adapter<OderHistoryAdapter.ViewHolder>() {
    var mMyOrderListItem = ArrayList<MyOrderListItem>()
    var db: LaaFreshDAO? = null

    inner class ViewHolder(var v: AdapterMyOrderListBinding) : RecyclerView.ViewHolder(v.root) {
        fun bindUi(position: Int) {
            v.apply {
                orderNo.text = mMyOrderListItem[position].sale_id
                orderDate.text = mMyOrderListItem[position].sale_datetime
                val paymentJO =
                    JSONArray(mMyOrderListItem[position].payment_status).getJSONObject(0)
                val deliveryJO =
                    JSONArray(mMyOrderListItem[position].delivery_status).getJSONObject(0)
                paymentStatus.text = paymentJO.getString("status")
                deliveryStatus.text = deliveryJO.getString("status")
                orderAmount.text =
                    root.context.getString(R.string.Rs) + " " + mMyOrderListItem[position].grand_total
            }

        }

        private fun abbreviate(s: String): String? {
            return if (s.length <= 10) s else s.substring(0, 13) + ".."
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OderHistoryAdapter.ViewHolder {
        return ViewHolder(
            AdapterMyOrderListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = mMyOrderListItem.size

    override fun onBindViewHolder(holder: OderHistoryAdapter.ViewHolder, position: Int) {
        holder.bindUi(position)
    }

    fun addHistoryItem(myOrderListItem: ArrayList<MyOrderListItem>) {
        mMyOrderListItem.clear()
        mMyOrderListItem = myOrderListItem
        notifyDataSetChanged()

    }

    class ItemCaterogyAdapterDecoration(var sp: Int) : RecyclerView.ItemDecoration() {
        private var space = 0

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            this.space = sp
            outRect.left = space
            outRect.right = space
            outRect.bottom = space
            outRect.top = space
        }
    }
}