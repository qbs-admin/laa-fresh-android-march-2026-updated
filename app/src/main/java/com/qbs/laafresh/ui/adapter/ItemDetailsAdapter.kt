package com.qbs.laafresh.ui.adapter

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qbs.laafresh.R
import com.qbs.laafresh.data.network.api.reponse.OfferProductsItem
import com.qbs.laafresh.databinding.ItemProductBundleListBinding

typealias onClickOffersProduct = (ProductId: String, move: Boolean) -> Unit

class ItemDetailsAdapter(val onClickProductItem: onClickOffersProduct, val context: Context) :
    RecyclerView.Adapter<ItemDetailsAdapter.ViewHolder>() {
    var categoryProductItem = ArrayList<OfferProductsItem>()

    inner class ViewHolder(var v: ItemProductBundleListBinding) : RecyclerView.ViewHolder(v.root) {
        fun buildUi(position: Int) {
            v.apply {
                tvBundleCategoryName.text = categoryProductItem[position].title
                tvBundleProductPrice.text =
                    context.getString(R.string.Rs) + " " + categoryProductItem[position].salePrice
                Glide.with(root.context).load(categoryProductItem[position].image)
                    .placeholder(R.drawable.product_placeholder).into(ivBundleProductImage)
                if (categoryProductItem[position].currentStock == "0") {
                    tvBundleAddToCart.visibility = View.GONE
                    linearBundleProductQty.visibility = View.GONE
                    tvBundleNoStock.visibility = View.VISIBLE
                }
                tvBundleAddToCart.setOnClickListener {
                    onClickProductItem.invoke(
                        categoryProductItem[position].productId.toString(),
                        true
                    )
                }
                root.setOnClickListener {
                    onClickProductItem.invoke(
                        categoryProductItem[position].productId.toString(),
                        false
                    )
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductBundleListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = categoryProductItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.buildUi(position)
    }

    class ItemCaterogyAdapterDecoration(space: Int) : RecyclerView.ItemDecoration() {
        private val halfSpace: Int = space / 2
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            if (parent.paddingLeft != halfSpace) {
                parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace)
                parent.clipToPadding = false
            }
            outRect.top = halfSpace
            outRect.bottom = halfSpace
            outRect.left = halfSpace
            outRect.right = halfSpace
        }
    }

    fun addDashBoardProduct(categoryProductItemList: List<OfferProductsItem>) {
        categoryProductItem.clear()
        categoryProductItem = categoryProductItemList as ArrayList<OfferProductsItem>
        notifyDataSetChanged()
    }
}