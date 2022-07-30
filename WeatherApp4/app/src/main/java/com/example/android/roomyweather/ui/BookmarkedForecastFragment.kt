package com.example.android.roomyweather.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomyweather.R
import com.example.android.roomyweather.data.ForecastLocation

class BookmarkedForecastFragment :Fragment(R.layout.bookmarked_forecast) {

    private val forecastListAdapter = BookmarkedAdapter(::onForecastClick)
    private lateinit var bookmarkedforecastRV: RecyclerView

    private val BookviewModel: BookmarkedForecastViewModel by viewModels()

    private val viewModel: FiveDayForecastViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookmarkedforecastRV = view.findViewById(R.id.rv_bookmarked_forecast)
        bookmarkedforecastRV.layoutManager = LinearLayoutManager(requireContext())
        bookmarkedforecastRV.setHasFixedSize(true)
        bookmarkedforecastRV.adapter = this.forecastListAdapter

        BookviewModel.bookmarkedForecasts.observe (viewLifecycleOwner) { bookmarked ->
            forecastListAdapter.updateBookmarkList(bookmarked)
        }

    }


    //https://guides.codepath.com/android/Storing-and-Accessing-SharedPreferences
    private fun onForecastClick(fore: ForecastLocation) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val editor: SharedPreferences.Editor = sharedPrefs.edit()
        editor.putString(getString(R.string.pref_city_key), fore.city_name)
        editor.apply()

        val directions = BookmarkedForecastFragmentDirections.navigateToForecastSearch()
        findNavController().navigate(directions)
        //val city = fore.city_name
        //viewModel.loadFiveDayForecast(city, units = "C", OPENWEATHER_APPID)

        /*
        val intent = Intent(this, BookmarkedAdapter::class.java).apply {
        }
        startActivity(intent)*/
    }
}