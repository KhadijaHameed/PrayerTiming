package com.sixlogs.pt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.sixlogs.pt.R
import com.sixlogs.pt.activity.model.SliderData
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(requireActivity: FragmentActivity, sliderDataArrayList: ArrayList<SliderData>) :
    SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder>() {

    // list for storing urls of images.
   private var mSliderItems: List<SliderData>? = null

    init {
        mSliderItems = sliderDataArrayList
    }

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterViewHolder? {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.slider_layout, null)
        return SliderAdapterViewHolder(inflate)
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    override fun onBindViewHolder(viewHolder: SliderAdapterViewHolder, position: Int) {
        val sliderItem: SliderData = mSliderItems!![position]

        // Glide is use to load image
        // from url in your imageview.
      //  Glide.with(viewHolder.itemView).load(sliderItem.getImgUrl()).fitCenter().into(viewHolder.imageViewBackground)
        viewHolder.imageViewBackground.setImageResource(sliderItem.imagResource)
    }

    // this method will return
    // the count of our list.
    override fun getCount(): Int {
        return mSliderItems!!.size
    }

    class SliderAdapterViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        // Adapter class for initializing
        // the views of our slider view.
        var itemView: View = itemView
        var imageViewBackground: ImageView = itemView.findViewById(R.id.myimage)

       /* init {
            this.itemView = itemView
        }*/
    }
}