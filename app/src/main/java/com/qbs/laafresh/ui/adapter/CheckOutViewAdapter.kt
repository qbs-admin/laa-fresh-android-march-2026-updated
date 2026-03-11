package com.qbs.laafresh.ui.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qbs.laafresh.data.dbhelper.LaaFreshDAO
import com.qbs.laafresh.data.dbhelper.table.MyCartEntity
import com.qbs.laafresh.databinding.ItemCardDetailListBinding


class CheckOutViewAdapter() :
    RecyclerView.Adapter<CheckOutViewAdapter.ViewHolder>() {
    var mProductItem = ArrayList<MyCartEntity>()
    var db: LaaFreshDAO? = null
    var myCartEntity: MyCartEntity? = null

    inner class ViewHolder(var v: ItemCardDetailListBinding) : RecyclerView.ViewHolder(v.root) {
        fun bindUi(position: Int) {
            v.apply {
                //tvItemListName.text = "${abbreviate(mProductItem[position].productName)} 1 x ${mProductItem[position].orderQty.toString()}"
                tvItemListName.text =
                    "${mProductItem[position].productName} ${mProductItem[position].defaultProductPrice} x ${mProductItem[position].orderQty}"
                tvItemListPrice.text =
                    ((mProductItem[position].defaultProductPrice * (mProductItem[position].orderQty)).toString())

            }

        }

        private fun abbreviate(s: String): String? {
            return if (s.length <= 10) s else s.substring(0, 13) + ".."
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckOutViewAdapter.ViewHolder {
        return ViewHolder(
            ItemCardDetailListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = mProductItem.size

    override fun onBindViewHolder(holder: CheckOutViewAdapter.ViewHolder, position: Int) {
        holder.bindUi(position)
    }


    fun addCartItem(cartItems: List<MyCartEntity>) {
        mProductItem.clear()
        mProductItem = cartItems as ArrayList<MyCartEntity>
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