package com.example.mediaplayer69.ui


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer69.R

class HistoryActivity : AppCompatActivity() {

    private lateinit var historyRV: RecyclerView
    private val historyAdapter = HistoryAdapter()
    private val viewModel: SearchInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        historyRV = findViewById(R.id.history_rv)
        historyRV.layoutManager = LinearLayoutManager(this)
        historyRV.setHasFixedSize(true)
        historyRV.adapter = historyAdapter

        viewModel.searchInfoRepos.observe(this) {
            historyAdapter.updateCityList(it)
        }
    }
}