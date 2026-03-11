package com.qbs.laafresh.ui.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qbs.laafresh.R
import com.qbs.laafresh.data.dbhelper.table.ProductEntity
import com.qbs.laafresh.databinding.ItemProductListBinding

typealias onClickProduct = (ProductId: String, move: Boolean) -> Unit

class ProductAdapter(var ProductId: onClickProduct) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    var mProductItem = ArrayList<ProductEntity>()

    inner class ViewHolder(var v: ItemProductListBinding) : RecyclerView.ViewHolder(v.root) {
        fun bindUi(position: Int) {
            v.apply {
                pbProductItem.visibility = View.GONE
                Glide.with(root.context)
                    .load(mProductItem[position].image)
                    .placeholder(R.drawable.product_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(ivProductImage)
                tvCategoryName.text = mProductItem[position].title
                tvProductPrice.text =
                    root.context.getString(R.string.Rs) + " " + mProductItem[position].salePrice
                if (mProductItem[position].currentStock == "0") {
                    tvAddToCart.visibility = View.GONE
                    linearProductQty.visibility = View.GONE
                    tvNoStock.visibility = View.VISIBLE
                }
                tvAddToCart.setOnClickListener {
                    ProductId.invoke(mProductItem[position].productId, true)
                }
                root.setOnClickListener {
                    ProductId.invoke(mProductItem[position].productId.toString(), false)
                }

            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ViewHolder {
        return ViewHolder(
            ItemProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = mProductItem.size

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.bindUi(position)
    }

    fun addProduct(productItem: List<ProductEntity>) {
        mProductItem.clear()
        mProductItem = productItem as ArrayList<ProductEntity>
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