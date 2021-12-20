package com.example.musicant.mvvm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicant.R
import com.example.musicant.mvvm.model.entity.Album

class AlbumRecyclerAdapter(
    private val albums: List<Album>,
    private val selectedItem: (Album) -> Unit
) :
    RecyclerView.Adapter<AlbumRecyclerAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_album_item, parent, false)
        return AlbumViewHolder(view, selectedItem)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.setAlbum(albums[position])
    }

    override fun getItemCount(): Int = albums.size

    class AlbumViewHolder(private val view: View, val selectedItem: (Album) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private var album: Album? = null
        private var image: ImageView? = null

        init {
            view.setOnClickListener {
                album?.let {
                    selectedItem(it)
                }
            }
        }

        fun setAlbum(album: Album) {
            image = view.findViewById(R.id.album_logo)

            this.album = album
            view.findViewById<TextView>(R.id.album_name).text = album.name
            image?.let { Glide.with(view.context).load(album.imageUrl).into(it) }
        }
    }
}