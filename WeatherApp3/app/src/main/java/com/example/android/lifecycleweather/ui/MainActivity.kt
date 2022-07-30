package com.example.android.lifecycleweather.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.android.lifecycleweather.BuildConfig
import com.example.android.lifecycleweather.R
import com.example.android.lifecycleweather.data.*
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/*
 * To use your own OpenWeather API key, create a file called `gradle.properties` in your
 * GRADLE_USER_HOME directory (this will usually be `$HOME/.gradle/` in MacOS/Linux and
 * `$USER_HOME/.gradle/` in Windows), and add the following line:
 *
 *   OPENWEATHER_API_KEY="<put_your_own_OpenWeather_API_key_here>"
 *
 * The Gradle build for this project is configured to automatically grab that value and store
 * it in the field `BuildConfig.OPENWEATHER_API_KEY` that's used below.  You can read more
 * about this setup on the following pages:
 *
 *   https://developer.android.com/studio/build/gradle-tips#share-custom-fields-and-resource-values-with-your-app-code
 *
 *   https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_configuration_properties
 *
 * Alternatively, you can just hard-code your API key below 🤷‍.
 */
const val OPENWEATHER_APPID = BuildConfig.OPENWEATHER_API_KEY


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener{

    private val tag = "MainActivity"
    private val apiBaseUrl = "https://api.openweathermap.org/data/2.5"

    private val viewModel: ForecastViewModel by viewModels()

    private lateinit var forecastAdapter: ForecastAdapter

    private lateinit var requestQueue: RequestQueue
    private lateinit var forecastJsonAdapter: JsonAdapter<FiveDayForecast>

    private lateinit var forecastListRV: RecyclerView
    private lateinit var loadingErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadingErrorTV = findViewById(R.id.tv_loading_error)
        loadingIndicator = findViewById(R.id.loading_indicator)
        forecastListRV = findViewById(R.id.rv_forecast_list)

        forecastAdapter = ForecastAdapter(::onForecastItemClick)

        forecastListRV.layoutManager = LinearLayoutManager(this)
        forecastListRV.setHasFixedSize(true)
        forecastListRV.adapter = forecastAdapter

        requestQueue = Volley.newRequestQueue(this)


        /*
        val moshi = Moshi.Builder()
            .add(OpenWeatherListJsonAdapter())
            .add(OpenWeatherCityJsonAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()
        forecastJsonAdapter = moshi.adapter(FiveDayForecast::class.java)*/

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

        val city = sharedPrefs.getString(
            getString(R.string.pref_location_key),
            "Corvallis,OR,US"
        )
        val unit = sharedPrefs.getString(
            getString(R.string.pref_units_key),
            "imperial"
        )
        forecastAdapter.updateUnit(unit)
        sharedPrefs.registerOnSharedPreferenceChangeListener(this)


        viewModel.fetchResults.observe(this){ fetchResults ->
            forecastAdapter.updateForecast(fetchResults)
        }
        viewModel.loadingStatus.observe(this){ loadingStatus ->
            when(loadingStatus){
                LoadingStatus.LOADING -> {
                    loadingIndicator.visibility = View.VISIBLE
                    forecastListRV.visibility = View.INVISIBLE
                    loadingErrorTV.visibility = View.INVISIBLE
                }
                LoadingStatus.ERROR -> {
                    loadingIndicator.visibility = View.INVISIBLE
                    forecastListRV.visibility = View.INVISIBLE
                    loadingErrorTV.visibility = View.VISIBLE
                }
                LoadingStatus.SUCCESS -> {
                    loadingIndicator.visibility = View.INVISIBLE
                    forecastListRV.visibility = View.VISIBLE
                    loadingErrorTV.visibility = View.INVISIBLE
                }
            }
        }




        /*
        var sharedPreferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener{
            sharedPreferences, key -> viewModel.loadFetchResults(
            sharedPreferences.getString(
                getString(R.string.pref_location_key), null),
            sharedPreferences.getString(
                getString(R.string.pref_units_key),
                null
            ))
        }*/


        viewModel.loadFetchResults(city,unit)
        //viewModel.loadFetchResults("Corvallis,OR,US", "imperial")
        //fetchFiveDayForecast("Corvallis,OR,US", "imperial")

    }

    override fun onResume() {
        Log.d(tag,"onResume()")
        super.onResume()

    }

    override fun onPause() {
        Log.d(tag,"onPause()")
        super.onPause()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val city = sharedPreferences!!.getString(
            getString(R.string.pref_location_key),
            "Corvallis,OR,US"
        )
        val unit = sharedPreferences!!.getString(
            getString(R.string.pref_units_key),
            "imperial"
        )
        forecastAdapter.updateUnit(unit)
        viewModel.loadFetchResults(city,unit)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_map -> {
                viewForecastCityOnMap()
                true
            }
            R.id.action_settings ->{
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    /**
     * This method fetches 5-day forecast data from the OpenWeather API using Volley.
     *
     * @param city The name of the city for which to fetch forecast data.  This should be in
     *   an OpenWeather-compatible format, e.g. "Corvallis,OR,US".
     * @param units The type of weather units to fetch from the OpenWeather API.  Possible
     *   values are "standard", "metric", and "imperial".
     */
    /*
    private fun fetchFiveDayForecast(city: String, units: String) {
        val url = "$apiBaseUrl/forecast?q=$city&units=$units&appid=$OPENWEATHER_APPID"

        val req = StringRequest(
            Request.Method.GET,
            url,
            {
                val results = forecastJsonAdapter.fromJson(it)
                forecastAdapter.updateForecast(results)
                supportActionBar?.title = forecastAdapter.forecastCity?.name
                loadingIndicator.visibility = View.INVISIBLE
                forecastListRV.visibility = View.VISIBLE
            },
            {
                Log.d(tag, "Error fetching from $url: ${it.message}")
                loadingErrorTV.text = getString(R.string.loading_error, it.message)
                loadingIndicator.visibility = View.INVISIBLE
                loadingErrorTV.visibility = View.VISIBLE
            }
        )

        loadingIndicator.visibility = View.VISIBLE
        forecastListRV.visibility = View.INVISIBLE
        loadingErrorTV.visibility = View.INVISIBLE
        requestQueue.add(req)
    }*/

    private fun onForecastItemClick(forecastPeriod: ForecastPeriod) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val unit = sharedPrefs.getString(
            getString(R.string.pref_units_key),
            "imperial"
        )

        val intent = Intent(this, ForecastDetailActivity::class.java).apply {
            putExtra(EXTRA_FORECAST_PERIOD, forecastPeriod)
            putExtra(EXTRA_FORECAST_CITY, forecastAdapter.forecastCity)
            putExtra(EXTRA_FORECAST_UNIT,unit)
        }
        startActivity(intent)
    }

    /**
     * This method generates a geo URI to represent location of the city for which the forecast
     * is being displayed and uses an implicit intent to view that location on a map.
     */
    private fun viewForecastCityOnMap() {
        if (forecastAdapter.forecastCity != null) {
            val geoUri = Uri.parse(getString(
                R.string.geo_uri,
                forecastAdapter.forecastCity?.lat ?: 0.0,
                forecastAdapter.forecastCity?.lon ?: 0.0,
                11
            ))
            val intent = Intent(Intent.ACTION_VIEW, geoUri)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                /*
                 * If there is no available app for viewing geo locations, display an error
                 * message in a Snackbar.
                 */
                Snackbar.make(
                    findViewById(R.id.coordinator_layout),
                    R.string.action_map_error,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}