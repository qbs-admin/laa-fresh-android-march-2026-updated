package com.qbs.laafresh.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.AppBarLayout
import com.qbs.laafresh.R
import com.qbs.laafresh.data.dbhelper.LaaFreshDAO
import com.qbs.laafresh.data.dbhelper.LaaFreshDB
import com.qbs.laafresh.data.dbhelper.table.MyCartEntity
import com.qbs.laafresh.data.dbhelper.table.ProductEntity
import com.qbs.laafresh.data.dbhelper.table.SubCategoriesItemEntity
import com.qbs.laafresh.data.network.api.reponse.*
import com.qbs.laafresh.data.viewmodel.VMUser
import com.qbs.laafresh.databinding.FragmentHomeBinding
import com.qbs.laafresh.ui.DashBoardActivity
import com.qbs.laafresh.ui.adapter.DashboardCategoryAdapter
import com.qbs.laafresh.ui.adapter.ProductBundleAdapter
import com.qbs.laafresh.ui.adapter.SliderAdapter
import com.qbs.laafresh.ui.extension.isConnected
import com.qbs.laafresh.ui.extension.toast
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {
    private lateinit var binding: FragmentHomeBinding
    var db: LaaFreshDAO? = null
    var getSubCategoriesList = ArrayList<SubCategoriesItemEntity>()
    var getIndividualProductsList = ArrayList<ProductEntity>()
    val vmUser: VMUser by lazy {
        this.let {
            ViewModelProvider(it, VMUser.Factory(requireContext()))
                .get(VMUser::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
//            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
//            v.setPadding(0, statusBar, 0, 0)
//            insets
//        }

        db = LaaFreshDB.getInstance(context = requireContext())?.laaFreshDAO()

        binding.HmCollapsingToolbar.rootView.visibility = View.VISIBLE
        binding.HmCollapsingToolbar.title = " "
        binding.HmAppBarLayout.addOnOffsetChangedListener(this)
        if (isConnected(requireActivity())) {
            getAllProduct()
            getSlides()
            getBundleProducts()
            getMiniOrder()
            getFooterImage()
        } else {
            activity?.toast(getString(R.string.no_internet))
        }
        loadSubCategoryList()
        if (getSubCategoriesList.isNotEmpty()) {
            setCategoryAdapter(getSubCategoriesList as List<SubCategoriesItemEntity>)
        }
        if (isConnected(requireActivity())) {
            getSubCategories()
        } else {
            showLoading(false)
            if (getSubCategoriesList.size == 0)
                activity?.toast(getString(R.string.no_internet))
        }
        var count = db?.getAllCartCount()
        if (count!! >= 0) {
            setCartCount(count)
        }


    }

    private fun getFooterImage() {
        vmUser.getFooterBannerResponse(onSuccess = {
            Glide.with(this)
                .load(it.footerImage?.get(0)?.images)
                .placeholder(R.drawable.referral_logo)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(binding.imReferral)
        }, onError = {

        })
    }

    private fun getAllProduct() {
        vmUser.getProduct(onSuccess = {
            if (it.success == 1) {
//                Log.e("TAG", it.products?.size.toString()+" -------z")
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
//                Log.e("TAG", "----- delete -----")
                    db?.deleteAllProduct()
                }

                db?.insertProductItems(productList)

               db?.removeDeletedProduct()

            }

        }, onError = {

        })
    }

    private fun getMiniOrder() {
        vmUser.getMiniOrder(onSuccess = {

        }, onError = {

        })
    }

    private fun getBundleProducts() {
        vmUser.getProductBundle(onSuccess = {
            if (it.success == 1) {
                setBundleProductsAdapter(it.categories as List<CategoriesItem>)
                binding.cvReferral.visibility = View.VISIBLE
            }
        }, onError = {

        })
    }


    private fun getSlides() {
        vmUser.getSlides(onSuccess = {
            if (it.success == 1) {
                binding.cardImageSlider.visibility = View.VISIBLE
                setSlidesAdapter((it.slides as List<SlidesItem>?)!!)
            }
        }, onError = {

        })
    }


    fun setCartCount(cartCount: Int) {
        (activity as DashBoardActivity).setCartCount(cartCount)
    }

    private fun getSubCategories() {
        if (getSubCategoriesList.size == 0)
            showLoading(true)
        vmUser.getSubCategories(
            onSuccess = {
                showLoading(false)
                val subCategoriesList = ArrayList<SubCategoriesItemEntity>()
                for (subCategoriesItem: SubCategoriesItem in it.subCategories!!) {
                    subCategoriesList.add(
                        SubCategoriesItemEntity(
                            subCategoriesItem.digital.toString(),
                            subCategoriesItem.subCategoryId.toString(),
                            subCategoriesItem.subCategoryName.toString(),
                            subCategoriesItem.description.toString(),
                            subCategoriesItem.banner.toString(),
                            subCategoriesItem.category.toString(),
                            subCategoriesItem.brand.toString()
                        )
                    )
                }
                db?.insertSubCategoriesItem(subCategoriesList)
                if (getSubCategoriesList.isNullOrEmpty()) {
                    loadSubCategoryList()
                    setCategoryAdapter(getSubCategoriesList as List<SubCategoriesItemEntity>)
                }
            },
            onError = {
                showLoading(false)

            }
        )
    }

    private fun loadSubCategoryList() {
        getSubCategoriesList = db?.getSubCategoriesItem() as ArrayList<SubCategoriesItemEntity>
    }

    private fun setSlidesAdapter(slides: List<SlidesItem>) {
        binding.slider.apply {
            val adapter = SliderAdapter()
            adapter.addSlides(slides)
            binding.slider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
            binding.slider.setSliderAdapter(adapter)
            binding.slider.scrollTimeInSec = 3
            binding.slider.isAutoCycle = true
            binding.slider.startAutoCycle()
        }


    }


    private fun setBundleProductsAdapter(categories: List<CategoriesItem>) {
        binding.rvWhatNew.apply {
            adapter = ProductBundleAdapter { productId, moveToCart ->
                if (moveToCart) {
                    addToCart(productId)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_addingToCartFragment,
                        bundleOf(
                            "productId" to productId
                        )
                    )
                } else {
                    findNavController().navigate(
                        R.id.action_homeFragment_to_productDetailsFragment,
                        bundleOf(
                            "productId" to productId
                        )
                    )
                }
            }
            if (onFlingListener == null)
                PagerSnapHelper().attachToRecyclerView(this)
        }
        (binding.rvWhatNew.adapter as ProductBundleAdapter).addProduct(
            categories
        )
    }

    private fun setCategoryAdapter(subCategory: List<SubCategoriesItemEntity>) {
        binding.rvSubcategories.apply {
            adapter = DashboardCategoryAdapter {
                findNavController().navigate(
                    R.id.action_homeFragment_to_productFragment,
                    bundleOf(
                        "productId" to it
                    )
                )
            }
            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.margin_4)
            addItemDecoration(
                DashboardCategoryAdapter.ItemCaterogyAdapterDecoration(
                    spacingInPixels
                )
            )
        }
        (binding.rvSubcategories.adapter as DashboardCategoryAdapter).addDashBoardCategory(
            subCategory
        )

    }

    private fun showLoading(isLoading: Boolean) {
        if (binding.pbHome != null) {
            if (isLoading)
                binding.pbHome.visibility = View.VISIBLE
            else
                binding.pbHome.visibility = View.GONE
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        activity?.runOnUiThread {
            var scrollRange = -1
            if (scrollRange == -1) {
                scrollRange = appBarLayout?.totalScrollRange!!
            }

            if (scrollRange + verticalOffset == 0) {
                binding.tvHmCollpasing.text = ""
                binding.tvHmToolbar.text = getString(R.string.app_name)

            } else {
                binding.tvHmCollpasing.text = ""
                binding.tvHmToolbar.text = ""
            }
        }
    }

    private fun addToCart(productId: String) {
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
                    subtotal = ParseDouble(getIndividualProductsList[0].salePrice) + ParseDouble(
                        getIndividualProductsList[0].discount
                    ) + ParseDouble(getIndividualProductsList[0].tax) + ParseDouble(
                        getIndividualProductsList[0].shipping_cost
                    ),
                    tax = ParseDouble(getIndividualProductsList[0].tax),
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

    private fun ParseDouble(value: String?): Double {
        return if (value != null && value.isNotEmpty()) {
            try {
                value.toDouble()
            } catch (e: java.lang.Exception) {
                (-1).toDouble()
            }
        } else 0.0
    }

}