package com.example.android.roomyweather.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomyweather.R
import com.example.android.roomyweather.data.ForecastLocation


class BookmarkedAdapter(private val onClick: (ForecastLocation) -> Unit)
    : RecyclerView.Adapter<BookmarkedAdapter.ViewHolder>() {
    var forecastBookmarkList = listOf<ForecastLocation>()

    fun updateBookmarkList(newforecastList: List<ForecastLocation>?) {
        forecastBookmarkList = newforecastList ?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount() = forecastBookmarkList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_bookmark_item, parent, false)
        return ViewHolder(itemView, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(forecastBookmarkList[position])
    }

    class ViewHolder(itemView: View, val onClick: (ForecastLocation) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        private val nameTV: TextView = itemView.findViewById(R.id.tv_bookcityname)
        private var currentBookmarkforecast: ForecastLocation? = null

        init {
            itemView.setOnClickListener {
                currentBookmarkforecast?.let(onClick)
            }
        }

        fun bind(forecast: ForecastLocation) {
            currentBookmarkforecast = forecast
            nameTV.text = forecast.city_name
        }
    }
}