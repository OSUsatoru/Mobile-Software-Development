package com.example.android.connectedweather

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.android.connectedweather.data.ForecastPeriod
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MainActivity : AppCompatActivity() {
    private val apiBaseUrl = "https://api.openweathermap.org"
    private val tag = "MainActivity"
    private val weatherAdapter = ForecastAdapter(::onForecastClick)

    private lateinit var requestQueue: RequestQueue
    private lateinit var forecastListRV: RecyclerView
    private lateinit var fetchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestQueue = Volley.newRequestQueue(this)

        fetchErrorTV = findViewById(R.id.tv_fetch_error)
        loadingIndicator = findViewById(R.id.loading_indicator)

        forecastListRV = findViewById<RecyclerView>(R.id.rv_forecast_list)
        forecastListRV.layoutManager = LinearLayoutManager(this)
        forecastListRV.setHasFixedSize(true)

        //changed
        doWeatherSearch()

        // set init info for each card
        //val forecastDataItems = this.initForecastPeriods()
        //forecastListRV.adapter = ForecastAdapter(forecastDataItems)
        forecastListRV.adapter = weatherAdapter

    }
    fun doWeatherSearch(q:String = "Corvallis,OR,US", units:String = "Imperial", appid:String = "d35715094214a5a1a5acde2c96539170"){
        val url = "$apiBaseUrl/data/2.5/forecast?q=$q&units=$units&appid=$appid"

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        //val listType = Types.newParameterizedType(List::class.java, DoWeatherSearchResults::class.java)
        //val jsonAdapter: JsonAdapter<List<DoWeatherSearchResults>> = moshi.adapter(listType)
        val jsonAdapter: JsonAdapter<DoWeatherSearchResults> = moshi.adapter(DoWeatherSearchResults::class.java)

        val req = StringRequest(
            Request.Method.GET,
            url,
            {
                var results = jsonAdapter.fromJson(it)
                Log.d(tag, results.toString())
                weatherAdapter.updateForecast(results?.items)
                loadingIndicator.visibility = View.INVISIBLE
                forecastListRV.visibility = View.VISIBLE


            },
            {
                Log.d(tag, "Error fetching from $url: ${it.message}")
                loadingIndicator.visibility = View.INVISIBLE
                fetchErrorTV.visibility = View.VISIBLE
            }
        )
        loadingIndicator.visibility = View.VISIBLE
        forecastListRV.visibility = View.INVISIBLE
        fetchErrorTV.visibility = View.INVISIBLE
        requestQueue.add(req)

    }
    private fun onForecastClick(forecast:ForecastPeriod){
        val intent = Intent(this, ForecastDetailActivity::class.java).apply {
            putExtra(EXTRA_FORECAST, forecast)
        }
        //intent.putExtra(EXTRA_FORECAST, forecast)
        startActivity(intent)
    }

    private data class DoWeatherSearchResults(
        @Json(name = "list") val items: List<ForecastPeriod>
    )

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_forecast_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_map -> {
                viewMapOnWeb()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
    private fun viewMapOnWeb(){
        val url = "geo:0,0?q=Corvallis"
        val uri = Uri.parse(url)
        val intent: Intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

}