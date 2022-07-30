package com.example.android.connectedweather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.example.android.connectedweather.data.ForecastPeriod
import org.w3c.dom.Text
import java.util.*

const val EXTRA_FORECAST = "ForecastPeriod"

class ForecastDetailActivity : AppCompatActivity() {
    private var forecast: ForecastPeriod? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_detail)

        if(intent!=null && intent.hasExtra(EXTRA_FORECAST)){

            forecast = intent.getSerializableExtra(EXTRA_FORECAST) as ForecastPeriod

            val cal = Calendar.getInstance()
            val dates = forecast!!.date.split(" ")
            //2022-02-05 21:00:00"

            val dateInfo = dates[0]
            val splitDate = dateInfo.split("-")
            val year = splitDate[0].toInt()
            val month = splitDate[1].toInt()-1
            val day = splitDate[2].toInt()
            val time = dates[1]
            cal.set(year, month, day)

            findViewById<TextView>(R.id.tv_detail_month).text = cal.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault())
            findViewById<TextView>(R.id.tv_detail_day).text = cal.get(Calendar.DAY_OF_MONTH).toString()
            findViewById<TextView>(R.id.tv_detail_name).text = "Corvallis"
            findViewById<TextView>(R.id.tv_detail_desc).text = forecast!!.weather[0].shortDesc
            findViewById<TextView>(R.id.tv_detail_high_temp).text = (forecast!!.main_info.highTemp.toString() + "°F")
            findViewById<TextView>(R.id.tv_detail_low_temp).text = (forecast!!.main_info.lowTemp.toString() + "°F")
            findViewById<TextView>(R.id.tv_detail_time).text = time


        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_forecast_detail, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_share -> {
                shareForecast()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun shareForecast() {
        if (forecast != null) {

            val intent: Intent = Intent().apply{
                val cal = Calendar.getInstance()
                val dates = forecast!!.date.split(" ")
                //2022-02-05 21:00:00"

                val dateInfo = dates[0]
                val splitDate = dateInfo.split("-")
                val year = splitDate[0].toInt()
                val month = splitDate[1].toInt()-1
                val day = splitDate[2].toInt()
                val time = dates[1]

                cal.set(year, month, day)
                action = Intent.ACTION_SEND

                val text: String = "City: Corvallis, Data: ${cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())} ${cal.get(Calendar.DAY_OF_MONTH)} time: ${time} Detail: ${forecast!!.weather[0].shortDesc!!}, low_temp: ${forecast!!.main_info.lowTemp.toString()}F, high_temp: ${forecast!!.main_info.highTemp.toString()}, pop: ${forecast!!.pop*100} "
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"

            }
            val shareIntent = Intent.createChooser(intent, null)
            startActivity(shareIntent)
        }
    }


}