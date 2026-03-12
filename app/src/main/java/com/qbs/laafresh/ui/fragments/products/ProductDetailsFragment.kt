package com.qbs.laafresh.ui.fragments.products

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.qbs.laafresh.R
import com.qbs.laafresh.data.dbhelper.LaaFreshDAO
import com.qbs.laafresh.data.dbhelper.LaaFreshDB
import com.qbs.laafresh.data.dbhelper.table.ProductEntity
import com.qbs.laafresh.data.preference.PreferenceManager
import com.qbs.laafresh.data.viewmodel.VMProduct
import com.qbs.laafresh.databinding.FragmentProductDetailsBinding
import com.qbs.laafresh.ui.extension.isConnected
import com.qbs.laafresh.ui.extension.toSpanned
import com.qbs.laafresh.ui.extension.toast


class ProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    var db: LaaFreshDAO? = null
    var getProductsList = ArrayList<ProductEntity>()
    var productId = ""
    var userId = "0"
    val vmProduct: VMProduct by lazy {
        this.let {
            ViewModelProvider(it, VMProduct.Factory(requireContext())).get(VMProduct::class.java)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //nav overwrite issue - bottom
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bottomInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            v.setPadding(0, 0, 0, bottomInset)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.tbProductsDetails) { v, insets ->
            val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            v.setPadding(0, statusBar, 0, 0)
            insets
        }

        db = LaaFreshDB.getInstance(context = requireContext())?.laaFreshDAO()
        val bundle = this.arguments
        initAppBar()
        if (bundle != null) productId = bundle.getString("productId")!!
        getProductOffline(productId)
        binding.productItemShare.setOnClickListener {
            shareProduct();
        }
        binding.productDetailsWishIcon.tag = "ic_favorite_border_black_18dp"

        binding.productItemWishList.setOnClickListener {
            if (PreferenceManager(requireContext()).getIsLoggedIn()) {
                addOrRemoveWishList()
            } else activity?.toast("You Must Login !")
        }

    }

    private fun getProductOffline(productId: String) {
        getProductsList.clear()
        getProductsList = db?.getProductItemsDetails(productId) as ArrayList<ProductEntity>
        //  if (getProductsList.size == 0) {

        if (isConnected(requireActivity())) {
            userId = if (PreferenceManager(requireContext()).getIsLoggedIn()) PreferenceManager(
                requireContext()
            ).getCustomerId() else "0"
            getProductOnline(userId)
            //  }

        } else activity?.toast(getString(R.string.no_internet))/*  Glide.with(this)
              .load(getProductsList[0].image)
              .placeholder(R.drawable.product_placeholder)
              .diskCacheStrategy(DiskCacheStrategy.DATA)
              .into(ivProductDetailsImage)
          tvDescriptionTitle.text = getProductsList[0].title
          tvProductDescription.text = getProductsList[0].description.toSpanned()
          tvProductNetWeight.text = getProductsList[0].net_weight
          tvProductsPrice.text = context?.getString(R.string.Rs) + " " + getProductsList[0].salePrice*/
    }

    private fun getProductOnline(userId: String) {
        vmProduct.getIndividualProduct(productId, userId, onSuccess = {
            binding.pbIndividualProduct.visibility = View.GONE
            Glide.with(this).load(it.image).into(binding.ivProductDetailsImage)
            binding.tvDescriptionTitle.text = it.title
            binding.tvProductDescription.text = it.description?.toSpanned()
            binding.tvProductNetWeight.text = it.net_weight
            binding.tvProductsPrice.text =
                context?.getString(R.string.Rs) + " " + getProductsList[0].salePrice
            if (it.in_wishlist!!)
                binding.productDetailsWishIcon.setImageResource(R.drawable.ic_favorite_red_24dp)
            else binding.productDetailsWishIcon.setImageResource(
                R.drawable.ic_favorite_border_black_18dp
            )

        }, onError = {
            binding.pbIndividualProduct.visibility = View.GONE
        })
    }

    private fun initAppBar() {
        binding.tbProductsDetails.apply {
            (activity as AppCompatActivity).setSupportActionBar(binding.tbProductsDetails)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (binding.tbProductsDetails != null) {
            if (isLoading) binding.tbProductsDetails.visibility = View.VISIBLE
            else binding.tbProductsDetails.visibility = View.GONE
        }
    }

    private fun addOrRemoveWishList() {
        try {

            if (binding.productDetailsWishIcon.tag == "ic_favorite_border_black_18dp") {
                binding.productDetailsWishIcon.setImageResource(R.drawable.ic_favorite_red_24dp)
                binding.productDetailsWishIcon.tag = "ic_favorite_black_24dp"
                vmProduct.addWishList(productId, onSuccess = {
                    if (it.success == ("1")) {
                        if (it.message.equals("Product already in wishlists")) {
                            if (binding.productDetailsWishIcon.tag == "ic_favorite_black_24dp") {
                                binding.productDetailsWishIcon.setImageResource(R.drawable.ic_favorite_border_black_18dp)
                                binding.productDetailsWishIcon.tag = "ic_favorite_border_black_18dp"
                                deleteWishList()

                            }
                        }

                    } else {
                        if (binding.productDetailsWishIcon.tag == "ic_favorite_black_24dp") {
                            binding.productDetailsWishIcon.setImageResource(R.drawable.ic_favorite_border_black_18dp)
                            binding.productDetailsWishIcon.tag = "ic_favorite_border_black_18dp"
                            deleteWishList()

                        }
                    }
                }, onError = {
                    activity?.toast(it.toString())
                })
            } else if (binding.productDetailsWishIcon.tag == "ic_favorite_black_24dp") {
                binding.productDetailsWishIcon.setImageResource(R.drawable.ic_favorite_border_black_18dp)
                binding.productDetailsWishIcon.tag = "ic_favorite_border_black_18dp"
                deleteWishList()

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteWishList() {
        vmProduct.deleteWishList(productId, onSuccess = {
            Log.e("remove_facroite>>>", it.toString())
            if (it.success == ("1")) {

            } else {
                activity?.toast(it.message.toString())
            }
        }, onError = {
            activity?.toast(it.toString())
        })
    }

    private fun shareProduct() {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL")
        i.putExtra(Intent.EXTRA_TEXT, "https://laafresh.com/index.php/home/product_view/$productId")
        startActivity(Intent.createChooser(i, "Share URL"))
    }

}