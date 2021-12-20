package com.example.musicant.mvvm.ui.music

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicant.R
import com.example.musicant.mvvm.ui.AlbumRecyclerAdapter

class AlbumsFragment : Fragment() {
    companion object {
        private const val TAG = "ListFragment"
    }

    private val viewModel by viewModels<ListViewModel>()
    private lateinit var albumsRecyclerView: RecyclerView
    private var adapter: AlbumRecyclerAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        requireActivity().supportFragmentManager.setFragmentResult(
//            MainActivity.NAVIGATION_EVENT,
//            bundleOf(MainActivity.NAVIGATION_EVENT_DATA_KEY to "CountriesFragment Created")
//        )

        lifecycle.addObserver(viewModel)
        albumsRecyclerView = view.findViewById(R.id.list_albums)
        albumsRecyclerView.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true).apply {
                    stackFromEnd = true
                }
        }

        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.albumsLiveData.observe(viewLifecycleOwner, { albums ->
            adapter = AlbumRecyclerAdapter(albums) { album -> Log.d(TAG, album.toString()) }
            albumsRecyclerView.adapter = adapter
        })
    }

}