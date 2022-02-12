package com.app.applaunchtask.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.applaunchtask.R
import com.app.applaunchtask.databinding.WeatherListBinding
import com.app.applaunchtask.model.HourlyItem
import java.text.SimpleDateFormat
import java.util.*

class HourAdapter(private var hourList: List<HourlyItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = DataBindingUtil.inflate<WeatherListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.weather_list,
            parent,
            false
        )
        return HourViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HourViewHolder) {
            holder.itemBinding.temp.text = "%.2f ${Html.fromHtml("&#xb0;")}C".format(convertFahToCel((hourList[position].temp)).toDouble())
            holder.itemBinding.time.text =
                getDateCurrentTimeZone(hourList[position].dt.toLong())
        }
    }


    fun getDateCurrentTimeZone(timestamp: Long): String? {
        return try {
            val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            sdf.format(Date(timestamp*1000))
        } catch (e: Exception) {
            ""
        }

    }


    fun convertFahToCel(tempF: Double?): String = tempF?.let { (tempF - 32) * 5 / 9 }.toString()



    override fun getItemCount(): Int {
        return hourList.size
    }

    class HourViewHolder(view: WeatherListBinding) : RecyclerView.ViewHolder(view.root) {
        val itemBinding = view
    }

}