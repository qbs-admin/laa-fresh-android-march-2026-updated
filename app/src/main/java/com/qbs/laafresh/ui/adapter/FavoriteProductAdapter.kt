package com.qbs.laafresh.ui.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qbs.laafresh.R
import com.qbs.laafresh.data.network.api.reponse.WishListProductItem
import com.qbs.laafresh.databinding.ItemFavoriteProductsBinding

typealias onClickFavorite = (ProductId: String, move: Boolean, isDelete: Boolean) -> Unit

class FavoriteProductAdapter(var addCart: onClickFavorite) :
    RecyclerView.Adapter<FavoriteProductAdapter.ViewHolder>() {
    var mProductItem = ArrayList<WishListProductItem>()

    inner class ViewHolder(var v: ItemFavoriteProductsBinding) : RecyclerView.ViewHolder(v.root) {
        fun bindUi(position: Int) {
            v.apply {
                pbFProductItem.visibility = View.GONE
                Glide.with(root.context)
                    .load(mProductItem[position].image)
                    .placeholder(R.drawable.product_placeholder)
                    .into(ivFProductImage)
                tvFCategoryName.text = mProductItem[position].title
                tvFavAddToCart.setOnClickListener {
                    addCart.invoke(mProductItem[position].productId, true, false)
                }
                ivCartDelete.setOnClickListener {
                    addCart.invoke(mProductItem[position].productId, false, true)
                }
                //  tvFProductPrice.text= context.getString(R.string.Rs)+" "+mProductItem[position]
                /*tvAddToCart.setOnClickListener {
                    tvAddToCart.visibility = View.GONE
                    linearProductQty.visibility = View.VISIBLE
                }*/
                root.setOnClickListener {
                    addCart.invoke(mProductItem[position].productId, false, false)
                }

            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteProductAdapter.ViewHolder {
        return ViewHolder(
            ItemFavoriteProductsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = mProductItem.size

    override fun onBindViewHolder(holder: FavoriteProductAdapter.ViewHolder, position: Int) {
        holder.bindUi(position)
    }

    fun addProduct(productItem: List<WishListProductItem>) {
        mProductItem.clear()
        mProductItem = productItem as ArrayList<WishListProductItem>
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