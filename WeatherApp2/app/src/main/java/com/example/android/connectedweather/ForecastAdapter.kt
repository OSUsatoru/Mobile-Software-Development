package com.example.android.connectedweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.connectedweather.data.ForecastPeriod
import com.google.android.material.snackbar.Snackbar
import java.util.*

class ForecastAdapter(private val onForecastClick: (ForecastPeriod) -> Unit)
    :RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    var forecastPeriods = listOf<ForecastPeriod>()

    fun updateForecast(newForecastPeriods:List<ForecastPeriod>?){
        forecastPeriods = newForecastPeriods ?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount() = this.forecastPeriods.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_list_item, parent, false)
        return ViewHolder(view, onForecastClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.forecastPeriods[position])
    }

    class ViewHolder(view: View, val onClick:(ForecastPeriod)-> Unit )
        : RecyclerView.ViewHolder(view) {
        private val monthTV: TextView = view.findViewById(R.id.tv_month)
        private val dayTV: TextView = view.findViewById(R.id.tv_day)
        private val timeTV: TextView = view.findViewById(R.id.tv_time)
        private val highTempTV: TextView = view.findViewById(R.id.tv_high_temp)
        private val lowTempTV: TextView = view.findViewById(R.id.tv_low_temp)
        private val shortDescTV: TextView = view.findViewById(R.id.tv_short_description)
        private val popTV: TextView = view.findViewById(R.id.tv_pop)
        private var currentForecastPeriod: ForecastPeriod? = null

        // click event
        init {
            view.setOnClickListener {
                currentForecastPeriod?.let(onClick)
            }
        }

        fun bind(forecastPeriod: ForecastPeriod) {
            currentForecastPeriod = forecastPeriod

            val cal = Calendar.getInstance()
            val dates = forecastPeriod.date.split(" ")
            //2022-02-05 21:00:00"

            val dateInfo = dates[0]
            val splitDate = dateInfo.split("-")
            val year = splitDate[0].toInt()
            val month = splitDate[1].toInt()-1
            val day = splitDate[2].toInt()
            val time = dates[1]
            cal.set(year, month, day)


            timeTV.text = time
            monthTV.text = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
            dayTV.text = cal.get(Calendar.DAY_OF_MONTH).toString()
            highTempTV.text = (forecastPeriod.main_info.highTemp.toString() + "°F")
            lowTempTV.text = (forecastPeriod.main_info.lowTemp.toString() + "°F")
            popTV.text = ((forecastPeriod.pop*100).toInt().toString() + "% precip.")
            shortDescTV.text = forecastPeriod.weather[0].shortDesc
        }
    }
}