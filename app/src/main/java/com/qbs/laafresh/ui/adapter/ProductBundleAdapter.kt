package com.qbs.laafresh.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qbs.laafresh.R
import com.qbs.laafresh.data.network.api.reponse.CategoriesItem
import com.qbs.laafresh.data.network.api.reponse.OfferProductsItem
import com.qbs.laafresh.databinding.ItemOfferListBinding

typealias onSenBundleToHome = (ProductId: String, move: Boolean) -> Unit

class ProductBundleAdapter(var onSenBundleToHome: onSenBundleToHome) :
    RecyclerView.Adapter<ProductBundleAdapter.ViewHolder>() {
    var mProductItem = ArrayList<CategoriesItem>()

    inner class ViewHolder(var v: ItemOfferListBinding) : RecyclerView.ViewHolder(v.root) {
        fun bindUi(position: Int) {
            v.apply {
                tvCategoryHeader.text = mProductItem[position].name
                rvIndividualItems.apply {
                    adapter = ItemDetailsAdapter(onClickProductItem = { it, moveToCart ->
                        onSenBundleToHome.invoke(it, moveToCart)
                    }, context = context)
                    val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing_small)
                    addItemDecoration(
                        ItemDetailsAdapter.ItemCaterogyAdapterDecoration(
                            spacingInPixels
                        )
                    )
                }
                (rvIndividualItems.adapter as ItemDetailsAdapter).addDashBoardProduct(
                    mProductItem[position].products as List<OfferProductsItem>
                )
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductBundleAdapter.ViewHolder {
        return ViewHolder(
            ItemOfferListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = mProductItem.size

    override fun onBindViewHolder(holder: ProductBundleAdapter.ViewHolder, position: Int) {
        holder.bindUi(position)
    }

    fun addProduct(productItem: List<CategoriesItem>) {
        mProductItem.clear()
        mProductItem = productItem as ArrayList<CategoriesItem>
        notifyDataSetChanged()
    }

}