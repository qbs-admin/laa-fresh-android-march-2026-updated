package com.qbs.laafresh.ui.fragments.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.qbs.laafresh.R
import com.qbs.laafresh.data.dbhelper.LaaFreshDAO
import com.qbs.laafresh.data.dbhelper.LaaFreshDB
import com.qbs.laafresh.data.dbhelper.table.MyCartEntity
import com.qbs.laafresh.data.dbhelper.table.ProductEntity
import com.qbs.laafresh.data.network.api.reponse.WishListProductItem
import com.qbs.laafresh.data.preference.PreferenceManager
import com.qbs.laafresh.data.viewmodel.VMFavorite
import com.qbs.laafresh.databinding.FragmentFavoriteBinding
import com.qbs.laafresh.ui.adapter.FavoriteProductAdapter
import com.qbs.laafresh.ui.extension.toast

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    var db: LaaFreshDAO? = null
    var getProductsList = ArrayList<ProductEntity>()
    var getIndividualProductsList = ArrayList<ProductEntity>()
    val vmFavorite: VMFavorite by lazy {
        this.let {
            ViewModelProvider(it, VMFavorite.Factory(requireContext()))
                .get(VMFavorite::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = LaaFreshDB.getInstance(context = requireContext())?.laaFreshDAO()
        getFavoriteList()
    }

    private fun getFavoriteList() {
        if (PreferenceManager(requireContext()).getIsLoggedIn()) {
            showLoading(true)
            vmFavorite.getFavorite(onSuccess = {
                if (it.products!!.isNotEmpty()) {
                    showLoading(false)
                    binding.tvEmptyWishList.visibility = View.GONE
                    binding.rvFavoriteProduct.visibility = View.VISIBLE
                    setProductAdapter(it.products)
                } else {
                    showLoading(false)
                    binding.tvEmptyWishList.visibility = View.VISIBLE
                    binding.rvFavoriteProduct.visibility = View.GONE
                }

            }, onError = {
                showLoading(true)

            })
        }
    }

    private fun setProductAdapter(productItem: List<WishListProductItem>) {
        binding.rvFavoriteProduct.apply {
            binding.rvFavoriteProduct.adapter =
                FavoriteProductAdapter { productId, moveToCart, isDelete ->
                    if (moveToCart) {
                        addToCart(productId)
                        findNavController().navigate(
                            R.id.action_favoriteFragment_to_addingToCartFragment,
                            bundleOf(
                                "productId" to productId
                            )
                        )
                    } else {
                        if (isDelete) {
                            removeFavorite(productId)
                        } else {
                            /*findNavController().navigate(
                                R.id.action_favoriteFragment_to_productDetailsFragment,
                                bundleOf(
                                    "productId" to productId
                                )
                            )*/
                        }
                    }

                }
        }
        (binding.rvFavoriteProduct.adapter as FavoriteProductAdapter).addProduct(productItem)
    }

    private fun removeFavorite(productId: String) {
        vmFavorite.deleteWishList(productId, onSuccess = {
            if (it.success == ("1")) {
                getFavoriteList()
            } else {
                activity?.toast(it.message.toString())
            }
        }, onError = {
            activity?.toast(it.toString())
        }
        )
    }

    private fun showLoading(isLoading: Boolean) {
        if (binding.pbFavoriteProduct != null) {
            if (isLoading)
                binding.pbFavoriteProduct.visibility = View.VISIBLE
            else
                binding.pbFavoriteProduct.visibility = View.GONE
        }
    }

    fun addToCart(productId: String) {
        getIndividualProductsList.clear()
        try {
            getIndividualProductsList.clear()
            getIndividualProductsList =
                db?.getProductItemsDetails(productId) as ArrayList<ProductEntity>

            var cartEntity = db?.getQtyAndQtyValue(productId)
            var cartCount = db?.getCartCount(productId)
            if (cartCount == 0) {
                var myCard = MyCartEntity(
                    productCode = getIndividualProductsList[0].productId,
                    subCategoryCode = getIndividualProductsList[0].subCategory,
                    categoryCode = getIndividualProductsList[0].category,
                    productName = getIndividualProductsList[0].title,
                    orderQty = 1,
                    orderValue = getIndividualProductsList[0].salePrice.toDouble(),
                    productWight = "",
                    productURI = getIndividualProductsList[0].image,
                    priceId = "",
                    user_id = "",
                    discountAmount = ParseDouble(getIndividualProductsList[0].discount),
                    tax = ParseDouble(getIndividualProductsList[0].tax),
                    subtotal = ParseDouble(getIndividualProductsList[0].salePrice) - ParseDouble(
                        getIndividualProductsList[0].discount
                    ) + ParseDouble(getIndividualProductsList[0].tax) + ParseDouble(
                        getIndividualProductsList[0].shipping_cost
                    ),
                    shipping_cost = ParseDouble(getIndividualProductsList[0].shipping_cost),
                    defaultShippingCost = ParseDouble(getIndividualProductsList[0].shipping_cost),
                    defaultDiscountAmount = ParseDouble(getIndividualProductsList[0].discount),
                    defaultTax = ParseDouble(getIndividualProductsList[0].tax),
                    defaultProductPrice = ParseDouble(getIndividualProductsList[0].salePrice)
                )
                db?.insertMyCard(myCard)
            } else {
                Log.e("cartdata>>>", (cartEntity?.orderQty!! + 1).toString())
                Log.e(
                    "cartdata>>>",
                    (getIndividualProductsList[0].salePrice.toDouble() * (cartEntity.orderQty + 1)).toString()
                )
                var myCard = MyCartEntity(
                    productCode = getIndividualProductsList[0].productId,
                    subCategoryCode = getIndividualProductsList[0].subCategory,
                    categoryCode = getIndividualProductsList[0].category,
                    productName = getIndividualProductsList[0].title,
                    orderQty = cartEntity.orderQty + 1,
                    orderValue = (getIndividualProductsList[0].salePrice.toDouble() * (cartEntity.orderQty + 1)) + getIndividualProductsList[0].tax.toDouble(),
                    productWight = "",
                    productURI = getIndividualProductsList[0].image,
                    priceId = "",
                    user_id = "",
                    subtotal = (ParseDouble(getIndividualProductsList[0].salePrice) * (cartEntity.orderQty + 1)) - (ParseDouble(
                        getIndividualProductsList[0].discount
                    ) * (cartEntity.orderQty + 1)) + (ParseDouble(getIndividualProductsList[0].tax) * (cartEntity.orderQty + 1)) + (ParseDouble(
                        getIndividualProductsList[0].shipping_cost
                    ) * (cartEntity.orderQty + 1)),
                    discountAmount = (ParseDouble(getIndividualProductsList[0].discount) * (cartEntity.orderQty + 1)),
                    tax = (ParseDouble(getIndividualProductsList[0].tax) * (cartEntity.orderQty + 1)),
                    shipping_cost = (ParseDouble(getIndividualProductsList[0].shipping_cost) * (cartEntity.orderQty + 1)),
                    defaultShippingCost = ParseDouble(getIndividualProductsList[0].shipping_cost),
                    defaultDiscountAmount = ParseDouble(getIndividualProductsList[0].discount),
                    defaultTax = ParseDouble(getIndividualProductsList[0].tax),
                    defaultProductPrice = ParseDouble(getIndividualProductsList[0].salePrice)
                )
                db?.insertMyCard(myCard)


            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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


}