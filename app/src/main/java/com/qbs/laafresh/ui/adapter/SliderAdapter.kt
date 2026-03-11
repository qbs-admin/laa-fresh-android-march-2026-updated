package com.qbs.laafresh.ui.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.qbs.laafresh.R
import com.qbs.laafresh.data.network.api.reponse.SlidesItem
import com.qbs.laafresh.databinding.SliderLayoutBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter : SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder>() {

    var slidesItem = ArrayList<SlidesItem>()
    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterViewHolder {
        return SliderAdapterViewHolder(
            SliderLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        viewHolder: SliderAdapterViewHolder, position: Int
    ) {
        viewHolder.bindUi(position)
    }

    override fun getCount(): Int = slidesItem.size

    fun addSlides(slides: List<SlidesItem>) {
        Log.e("slides", slides.toString())
        slidesItem.clear()
        slidesItem.addAll(slides)
        notifyDataSetChanged()
    }

    inner class SliderAdapterViewHolder(var binding: SliderLayoutBinding) :
        ViewHolder(binding.root) {
        fun bindUi(position: Int) {
            binding.apply {
                Glide.with(root.context)
                    .load(slidesItem[position].images)
                    .apply(
                        RequestOptions().placeholder(R.drawable.slides_placeholder)
                            .error(R.drawable.slides_placeholder)
                    )
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(imageSlider)
            }
        }
    }


}