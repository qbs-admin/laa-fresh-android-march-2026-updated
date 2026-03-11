package com.qbs.laafresh.ui.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qbs.laafresh.R
import com.qbs.laafresh.data.dbhelper.table.SubCategoriesItemEntity
import com.qbs.laafresh.databinding.ItemCategoryItemBinding

typealias onClickCategory = (categoryItemId: String) -> Unit

class DashboardCategoryAdapter(var categoryItemId: onClickCategory) :
    RecyclerView.Adapter<DashboardCategoryAdapter.ViewHolder>() {
    var dashBoardSubCategory = ArrayList<SubCategoriesItemEntity>()

    inner class ViewHolder(var v: ItemCategoryItemBinding) : RecyclerView.ViewHolder(v.root) {
        fun bindUi(position: Int) {
            v.apply {
                tvItemName.text = dashBoardSubCategory[position].subCategoryName
                // tvItemPrice.text=categoryProductItem[position].currency_code+" "+categoryProductItem[position].price
                Glide.with(root.context)
                    .load(dashBoardSubCategory[position].banner)
                    .placeholder(R.drawable.product_placeholder)
                    .into(ivItem)
                root.setOnClickListener {
                    categoryItemId.invoke(dashBoardSubCategory[position].subCategoryId.toString())
                }

            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardCategoryAdapter.ViewHolder {
        return ViewHolder(
            ItemCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = dashBoardSubCategory.size

    override fun onBindViewHolder(holder: DashboardCategoryAdapter.ViewHolder, position: Int) {
        holder.bindUi(position)
    }

    fun addDashBoardCategory(subCategory: List<SubCategoriesItemEntity>) {
        dashBoardSubCategory.clear()
        dashBoardSubCategory = subCategory as ArrayList<SubCategoriesItemEntity>
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