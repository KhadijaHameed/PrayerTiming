package com.sixlogs.pt.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sixlogs.pt.databinding.ItemQazaPrayersBinding
import com.sixlogs.pt.model.QazaPrayerModel

@SuppressLint("SetTextI18n")
class QazaPrayersAdapter(val context: Context) :
    RecyclerView.Adapter<QazaPrayersAdapter.ViewHolder>() {
    //, var listner : ClickListnerIndex
    private lateinit var itemsDaysList: ArrayList<QazaPrayerModel>
    lateinit var view: ItemQazaPrayersBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: QazaPrayerModel) {

            view.date.text = item.date
            if (item.fajar == true) {
                view.fajar.text = "✔"
            }
            if (item.duhar == true) {
                view.duharr.text = "✔"
            }
            if (item.asar == true) {
                view.asar.text = "✔"
            }
            if (item.maghrib == true) {
                view.maghrib.text = "✔"
            }
            if (item.isha == true) {
                view.isha.text = "✔"
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = ItemQazaPrayersBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    /*    if (position==0){
            view.llHeader.visibility == VISIBLE
        }else{
            view.llHeader.visibility == GONE

        }*/
        val itemDayName = itemsDaysList[position]
        holder.bindItems(itemDayName)




        holder.itemView.setOnClickListener {
            //listner.onClick(position)
        }

        //   holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.slide_transition_animation)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return when {
            ::itemsDaysList.isInitialized -> itemsDaysList.size
            else -> 0
        }
    }

    fun setList(qazaPrayersList: ArrayList<QazaPrayerModel>) {
        itemsDaysList = qazaPrayersList
        notifyDataSetChanged()
    }


}
