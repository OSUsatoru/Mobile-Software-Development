package com.example.mediaplayer69.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer69.R
import com.example.mediaplayer69.data.SearchInfo


class HistoryAdapter()
    : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private var cityInfoList = mutableListOf<SearchInfo>()

    fun updateCityList(newCityList: MutableList<SearchInfo>?) {
        if (newCityList != null) {
            cityInfoList = newCityList
        }
        this.notifyDataSetChanged()
    }

    override fun getItemCount() = cityInfoList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_history_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.cityInfoList[position])
    }

    class ViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
        private val historyItemTV: TextView = itemView.findViewById(R.id.history_item)
        private var currentHistoryInfo: SearchInfo? = null

        fun bind(searchInfo: SearchInfo) {
            currentHistoryInfo = searchInfo
            historyItemTV.text = searchInfo.searchInfo
        }
    }
}