package com.example.mediaplayer69.youtube_ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer69.R
import com.example.mediaplayer69.databinding.YoutubevideoLayoutBinding
import com.example.mediaplayer69.youtube_data.YoutubeVideoAdapter

class YoutubeVideoFragment : Fragment() {
    private var _bind: YoutubevideoLayoutBinding? = null
    private val bind get() = _bind!!
    private var viewModel: YoutubeVideoViewModel? = null
    private val adapter = YoutubeVideoAdapter()
    private var isLoading = false
    private var isScroll = false
    private var curItem = -1
    private var totalItem = -1
    private var scrollItem = -1
    private var isLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = LinearLayoutManager(requireContext())
        bind.rvVideo.adapter = adapter
        bind.rvVideo.layoutManager = manager
        bind.rvVideo.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScroll = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                curItem = manager.childCount
                totalItem = manager.itemCount
                scrollItem = manager.findFirstVisibleItemPosition()
                if (isScroll && (curItem + scrollItem == totalItem)){
                    isScroll = false
                    if (!isLoading){
                        if (!isLoaded){
                            viewModel?.getVideo()
                        } else {
                            Toast.makeText(requireContext(), "All video loaded", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        })

        viewModel?.video?.observe(viewLifecycleOwner, {
            if (it != null && it.items.isNotEmpty()){
                adapter.setData(it.items, bind.rvVideo)
            }
        })

        viewModel?.isLoading?.observe(viewLifecycleOwner, {
            isLoading = it
            bind.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel?.isLoaded?.observe(viewLifecycleOwner, {
            isLoaded = it
            if (it) Toast.makeText(requireContext(), "All video has been loaded", Toast.LENGTH_SHORT).show()
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(YoutubeVideoViewModel::class.java)
        _bind = YoutubevideoLayoutBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.search_title)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String): Boolean {
                if (q.isNotEmpty()){
                    viewModel?.querySearch = q
                    viewModel?.nextPageToken = null
                    adapter.clearAll()
                    viewModel?.getVideo()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()){
                    viewModel?.querySearch = null
                    viewModel?.nextPageToken = null
                    adapter.clearAll()
                    viewModel?.getVideo()
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }


}