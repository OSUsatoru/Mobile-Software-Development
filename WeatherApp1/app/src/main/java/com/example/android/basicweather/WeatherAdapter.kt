package com.example.android.basicweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeatherAdapter(private val weathers:MutableList<Weather>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    //val weathers: MutableList<Weather> = mutableListOf()

    override fun getItemCount() = this.weathers.size

    lateinit var listener: OnWeatherClickListener

    interface OnWeatherClickListener{
        fun onWeatherClick(weather: Weather)
    }
    /*
    fun setOnWeatherClickListener(listener: OnWeatherClickListener){
        this.listener = listener
    }
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.weather_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.weathers[position])

        holder.itemView.setOnClickListener{
            listener.onWeatherClick(this.weathers[position])
        }

    }




    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val weather_monthTV: TextView = itemView.findViewById(R.id.tv_weather_month)
        private val weather_dateTV: TextView = itemView.findViewById(R.id.tv_weather_date)
        private val weather_hightempTV: TextView = itemView.findViewById(R.id.tv_weather_high_temp)
        private val weather_lowtempTV: TextView = itemView.findViewById(R.id.tv_weather_low_temp)
        private val weather_precipTV: TextView = itemView.findViewById(R.id.tv_weather_precip_per)
        private val weather_shortdescTV: TextView = itemView.findViewById(R.id.tv_weather_short_description)



        fun bind(weather: Weather){

            this.weather_monthTV.text = weather.month
            this.weather_dateTV.text = weather.date.toString()
            this.weather_hightempTV.text = "${weather.high_temp.toString()}°F"
            this.weather_lowtempTV.text = "${weather.low_temp.toString()}°F"
            this.weather_precipTV.text = "${weather.precip_per.toString()}% precip"
            this.weather_shortdescTV.text = weather.short_description
        }

    }


}