package com.example.android.lifecycleweather.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.lifecycleweather.R
import com.example.android.lifecycleweather.data.FiveDayForecast
import com.example.android.lifecycleweather.data.ForecastCity
import com.example.android.lifecycleweather.data.ForecastPeriod
import com.example.android.lifecycleweather.util.openWeatherEpochToDate

class ForecastAdapter(private val onClick: (ForecastPeriod) -> Unit)
        : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    var forecastPeriods: List<ForecastPeriod> = listOf()
    var forecastCity: ForecastCity? = null

    var forecastUnit: String? = "F"

    fun updateForecast(forecast: FiveDayForecast?) {
        forecastPeriods = forecast?.periods ?: listOf()
        forecastCity = forecast?.city
        notifyDataSetChanged()
    }

    fun updateUnit(newUnit: String?){
        when(newUnit){
            "imperial" -> forecastUnit = "F"
            "metric" -> forecastUnit = "C"
            "kelvin" -> forecastUnit = "K"
            else -> forecastUnit = "F"
        }
        notifyDataSetChanged()
    }


    override fun getItemCount() = this.forecastPeriods.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_list_item, parent, false)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.forecastPeriods[position], forecastCity, forecastUnit)
    }

    class ViewHolder(itemView: View, val onClick: (ForecastPeriod) -> Unit)
            : RecyclerView.ViewHolder(itemView) {
        private val dateTV: TextView = itemView.findViewById(R.id.tv_date)
        private val timeTV: TextView = itemView.findViewById(R.id.tv_time)
        private val highTempTV: TextView = itemView.findViewById(R.id.tv_high_temp)
        private val lowTempTV: TextView = itemView.findViewById(R.id.tv_low_temp)
        private val iconIV: ImageView = itemView.findViewById(R.id.iv_forecast_icon)
        private val popTV: TextView = itemView.findViewById(R.id.tv_pop)

        private var currentForecastPeriod: ForecastPeriod? = null

        init {
            itemView.setOnClickListener {
                currentForecastPeriod?.let(onClick)
            }
        }

        fun bind(forecastPeriod: ForecastPeriod, forecastCity: ForecastCity?, forecastUnit:String?) {
            currentForecastPeriod = forecastPeriod




            val ctx = itemView.context
            val date = openWeatherEpochToDate(forecastPeriod.epoch, forecastCity?.tzOffsetSec ?: 0)

            dateTV.text = ctx.getString(R.string.forecast_date, date)
            timeTV.text = ctx.getString(R.string.forecast_time, date)
            highTempTV.text = ctx.getString(R.string.forecast_temp, forecastPeriod.highTemp, forecastUnit)
            lowTempTV.text = ctx.getString(R.string.forecast_temp, forecastPeriod.lowTemp, forecastUnit)
            popTV.text = ctx.getString(R.string.forecast_pop, forecastPeriod.pop)

            /*
             * Load forecast icon into ImageView using Glide: https://bumptech.github.io/glide/
             */
            Glide.with(ctx)
                .load(forecastPeriod.iconUrl)
                .into(iconIV)
        }
    }
}