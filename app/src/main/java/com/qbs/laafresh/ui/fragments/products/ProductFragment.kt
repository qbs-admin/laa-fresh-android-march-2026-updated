package com.qbs.laafresh.ui.fragments.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.qbs.laafresh.R
import com.qbs.laafresh.data.dbhelper.LaaFreshDAO
import com.qbs.laafresh.data.dbhelper.LaaFreshDB
import com.qbs.laafresh.data.dbhelper.table.MyCartEntity
import com.qbs.laafresh.data.dbhelper.table.ProductEntity
import com.qbs.laafresh.data.network.api.reponse.ProductsItem
import com.qbs.laafresh.data.viewmodel.VMProduct
import com.qbs.laafresh.databinding.FragmentProductBinding
import com.qbs.laafresh.ui.adapter.ProductAdapter
import com.qbs.laafresh.ui.extension.isConnected
import com.qbs.laafresh.ui.extension.toast

class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    var db: LaaFreshDAO? = null
    var getProductsList = ArrayList<ProductEntity>()
    var getIndividualProductsList = ArrayList<ProductEntity>()
    var productId = "";
    val vmProduct: VMProduct by lazy {
        this.let {
            ViewModelProvider(it, VMProduct.Factory(requireContext()))
                .get(VMProduct::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = LaaFreshDB.getInstance(context = requireContext())?.laaFreshDAO()
        val bundle = this.arguments
        if (bundle != null)
            productId = bundle.getString("productId")!!
        binding.tbProducts.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        loadProductList()
        if (getProductsList.isNotEmpty()) {
            binding.tvEmptyProductList.visibility = View.GONE
            setProductAdapter(getProductsList as List<ProductEntity>)
        } else {
            showLoading(false)
            binding.tvEmptyProductList.visibility = View.VISIBLE
        }
        if (getProductsList.size == 0)
            showLoading(true)
        if (isConnected(requireActivity())) {
            getProducts()
        } else {
            showLoading(false)
            if (getProductsList.size == 0)
                activity?.toast(getString(R.string.no_internet))


        }

        var count = db?.getAllCartCount()
        if (count!! > 0) {
            binding.contentProductCartView.root.visibility = View.VISIBLE
            binding.contentProductCartView.tvCartCount.text = "Items- ${count}"
            binding.contentProductCartView.root.setOnClickListener {
                findNavController().navigate(R.id.action_productFragment_to_mycartFragment, null,
                    navOptions {
                        popUpTo(R.id.homeFragment) {
                            inclusive = true
                        }
                    })
            }
        }


    }

    fun getProducts() {
        vmProduct.getProductDetails(productId, onSuccess = {
            showLoading(false)
            val productList = ArrayList<ProductEntity>()

            for (productsItem: ProductsItem in it.products!!) {
                productList.add(
                    ProductEntity(
                        productsItem.productId.toString(),
                        productsItem.rating_num.toString(),
                        productsItem.rating_total.toString(),
                        productsItem.title.toString(),
                        productsItem.category.toString(),
                        productsItem.description.toString(),
                        productsItem.sub_category.toString(),
                        productsItem.salePrice.toString(),
                        productsItem.purchase_price.toString(),
                        productsItem.currentStock.toString(),
                        productsItem.discount.toString(),
                        productsItem.discount_type.toString(),
                        productsItem.tax.toString(),
                        productsItem.tax_type.toString(),
                        productsItem.logo.toString(),
                        productsItem.image.toString(),
                        productsItem.deal.toString(),
                        productsItem.featured.toString(),
                        productsItem.net_weight.toString(),
                        productsItem.shipping_cost.toString()
                    )
                )
            }

            if(!it.products.isNullOrEmpty()) {
                db?.deleteAllProduct()
            }

            db?.insertProductItems(productList)

            if (getProductsList.isNullOrEmpty()) {
                loadProductList()
                binding.tvEmptyProductList.visibility = View.GONE
                setProductAdapter(getProductsList as List<ProductEntity>)
            }
        }, onError = {
            showLoading(false)
        })
    }

    private fun loadProductList() {
        //new line added by udhaya on 10-03-2026
        getProductsList.clear()
        getProductsList = ArrayList(db?.getProductItems(productId) ?: emptyList())
    }

    private fun setProductAdapter(productItem: List<ProductEntity>) {
        binding.rvProduct.apply {
            binding.rvProduct.adapter = ProductAdapter { productId, moveToCart ->
                if (moveToCart) {
                    addToCart(productId)
                    findNavController().navigate(
                        R.id.action_productFragment_to_addingToCartFragment,
                        bundleOf(
                            "productId" to productId
                        )
                    )
                } else {
                    findNavController().navigate(
                        R.id.action_productFragment_to_productDetailsFragment,
                        bundleOf(
                            "productId" to productId
                        )
                    )
                }

            };
        }
        (binding.rvProduct.adapter as ProductAdapter).addProduct(productItem)
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


    private fun showLoading(isLoading: Boolean) {
        if (binding.pbProduct != null) {
            if (isLoading)
                binding.pbProduct.visibility = View.VISIBLE
            else
                binding.pbProduct.visibility = View.GONE
        }
    }
}