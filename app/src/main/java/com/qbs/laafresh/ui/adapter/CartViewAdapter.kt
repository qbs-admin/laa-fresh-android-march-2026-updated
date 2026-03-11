package com.qbs.laafresh.ui.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qbs.laafresh.R
import com.qbs.laafresh.data.dbhelper.LaaFreshDAO
import com.qbs.laafresh.data.dbhelper.LaaFreshDB
import com.qbs.laafresh.data.dbhelper.table.MyCartEntity
import com.qbs.laafresh.databinding.ItemCardViewBinding

typealias onCart = (ProductQty: Int, ProductPrice: Double, ProductId: String, delete: Boolean, SubTotal: Double, Discount: Double, Tax: Double, ShippingCost: Double) -> Unit

class CartViewAdapter(var onCart: onCart) :
    RecyclerView.Adapter<CartViewAdapter.ViewHolder>() {
    var mProductItem = ArrayList<MyCartEntity>()
    var db: LaaFreshDAO? = null
    var myCartEntity: MyCartEntity? = null

    inner class ViewHolder(var binding: ItemCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindUi(position: Int) {
            binding.apply {
                db = LaaFreshDB.getInstance(context = root.context)?.laaFreshDAO()
                pbCartProduct.visibility = View.GONE
                Glide.with(root.context)
                    .load(mProductItem[position].productURI)
                    .placeholder(R.drawable.product_placeholder)
                    .into(ivCartProductImage)
                tvCartCategoryName.text = mProductItem[position].productName
                tvCartCategoryPrice.text =
                    "${mProductItem[position].defaultProductPrice} x ${mProductItem[position].orderQty}"
                tvCartCategoryTotalPrice.text =
                    (mProductItem[position].defaultProductPrice * mProductItem[position].orderQty).toString()
                tvProductQty.text = mProductItem[position].orderQty.toString()
                tvAddProduct.setOnClickListener {
                    myCartEntity = db?.checkItemAlreadyExist(mProductItem[position].productCode)
                    if (myCartEntity!!.orderQty >= 1) {
                        onCart.invoke(
                            mProductItem[position].orderQty + 1,
                            mProductItem[position].defaultProductPrice * (mProductItem[position].orderQty + 1),
                            mProductItem[position].productCode,
                            false,
                            (ParseDouble(mProductItem[position].defaultProductPrice.toString()) * (mProductItem[position].orderQty + 1))
                                    - (ParseDouble(mProductItem[position].defaultDiscountAmount.toString()) * (mProductItem[position].orderQty + 1)) +
                                    (ParseDouble(mProductItem[position].defaultTax.toString()) * (mProductItem[position].orderQty + 1)) +
                                    (ParseDouble(mProductItem[position].defaultShippingCost.toString()) * (mProductItem[position].orderQty + 1)),

                            (ParseDouble(mProductItem[position].defaultDiscountAmount.toString()) * (mProductItem[position].orderQty + 1)),
                            (ParseDouble(mProductItem[position].defaultTax.toString()) * (mProductItem[position].orderQty + 1)),
                            (ParseDouble(mProductItem[position].defaultShippingCost.toString()) * (mProductItem[position].orderQty + 1))
                        )
                    }
                    tvProductQty.text = (mProductItem[position].orderQty + 1).toString()
                    tvCartCategoryPrice.text =
                        "${mProductItem[position].defaultProductPrice} x ${mProductItem[position].orderQty + 1}"
                    tvCartCategoryTotalPrice.text =
                        (mProductItem[position].defaultProductPrice * (mProductItem[position].orderQty + 1)).toString()

                }
                tvReduceProduct.setOnClickListener {
                    myCartEntity = db?.checkItemAlreadyExist(mProductItem[position].productCode)
                    if (myCartEntity!!.orderQty == 1) {
                        onCart.invoke(
                            0,
                            0.0,
                            mProductItem[position].productCode,
                            true,
                            0.0,
                            0.0,
                            0.0,
                            0.0
                        )
                    } else {
                        onCart.invoke(
                            mProductItem[position].orderQty - 1,
                            mProductItem[position].defaultProductPrice * (mProductItem[position].orderQty - 1),
                            mProductItem[position].productCode, false,
                            (ParseDouble(mProductItem[position].defaultProductPrice.toString()) * (mProductItem[position].orderQty - 1))
                                    - (ParseDouble(mProductItem[position].defaultDiscountAmount.toString()) * (mProductItem[position].orderQty - 1)) +
                                    (ParseDouble(mProductItem[position].defaultTax.toString()) * (mProductItem[position].orderQty - 1)) +
                                    (ParseDouble(mProductItem[position].defaultShippingCost.toString()) * (mProductItem[position].orderQty - 1)),
                            (ParseDouble(mProductItem[position].defaultDiscountAmount.toString()) * (mProductItem[position].orderQty - 1)),
                            (ParseDouble(mProductItem[position].defaultTax.toString()) * (mProductItem[position].orderQty - 1)),
                            (ParseDouble(mProductItem[position].defaultShippingCost.toString()) * (mProductItem[position].orderQty - 1))
                        )
                        tvProductQty.text = (myCartEntity!!.orderQty - 1).toString()
                        tvCartCategoryPrice.text =
                            "${mProductItem[position].defaultProductPrice} x ${mProductItem[position].orderQty - 1}"
                        tvCartCategoryTotalPrice.text =
                            (mProductItem[position].defaultProductPrice * (mProductItem[position].orderQty - 1)).toString()
                    }
                }

                ivCartDelete.setOnClickListener {
                    myCartEntity = db?.checkItemAlreadyExist(mProductItem[position].productCode)
                    if (myCartEntity!!.orderQty >= 1) {
                        onCart.invoke(
                            0,
                            0.0,
                            mProductItem[position].productCode,
                            true,
                            0.0,
                            0.0,
                            0.0,
                            0.0
                        )
                    }

                }
                ivCartProductImage.setOnClickListener {

                }

            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewAdapter.ViewHolder {
        return ViewHolder(
            ItemCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = mProductItem.size

    override fun onBindViewHolder(holder: CartViewAdapter.ViewHolder, position: Int) {
        holder.bindUi(position)
    }

    fun ParseDouble(value: String?): Double {
        return if (value != null && value.isNotEmpty()) {
            try {
                value.toDouble()
            } catch (e: java.lang.Exception) {
                (-1).toDouble()
            }
        } else 0.0
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