package com.example.musicant.mvvm.ui.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.example.musicant.R
import com.example.musicant.mvvm.model.network.lastfm.ArtistsRecyclerItemDecoration
import com.example.musicant.mvvm.model.network.lastfm.LastFMNetwork
import com.example.musicant.mvvm.model.network.lastfm.LastFMNetworkImpl

class ArtistsFragment : Fragment() {
    companion object {
        private const val TAG = "MusicFragment"
    }

    private val viewModel by viewModels<MusicViewModel>()
    private lateinit var recyclerArtists: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        val fadeBrowser = inflater.inflateTransition(R.transition.fade_browser)
        val slideBrowser = inflater.inflateTransition(R.transition.slide_browser)

        enterTransition = slideBrowser
        exitTransition = fadeBrowser
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lastFMNetwork = LastFMNetworkImpl.getInstance() as LastFMNetwork
        viewModel.setArtistService(lastFMNetwork.getArtistsService())
        lifecycle.addObserver(viewModel)

        recyclerArtists = view.findViewById(R.id.recycler_artists)
        recyclerArtists.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerArtists.addItemDecoration(ArtistsRecyclerItemDecoration(16))

        setListeners()
        subscribeToLiveData()
    }

    private fun setListeners() {
        /**TODO: Some logic*/
    }

    private fun subscribeToLiveData() {
        viewModel.artistsLiveData.observe(viewLifecycleOwner, { artists ->
            recyclerArtists.adapter = ArtistsAdapter(artists) { artist ->
                println(artist)
            }
        })
    }
}