package com.example.codechallenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge.databinding.AlbumItemBinding
import com.example.codechallenge.model.AlbumInfo
import javax.inject.Inject

class AlbumAdapter @Inject constructor() :
    RecyclerView.Adapter<AlbumAdapter.AlbumItemViewHolder>() {
    private var albumInfos: List<AlbumInfo> = emptyList()
    var onClickAlbum: (album: AlbumInfo) -> Unit = { _ -> }

    fun setAlbumList(albums: List<AlbumInfo>?) {
        albums?.let {
            albumInfos = albums
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = AlbumItemViewHolder(
        AlbumItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: AlbumItemViewHolder, position: Int) {
        val data = albumInfos[position]
        holder.bind(data)
        holder.itemView.setOnClickListener {
            onClickAlbum(data)
        }
    }

    override fun getItemCount() = albumInfos.size

    class AlbumItemViewHolder(private val binding: AlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: AlbumInfo) {
            binding.apply {
                album.also { (title, owner) ->
                    albumTitleTextView.text = title
                    albumOwnerTextView.text = owner
                }
            }
        }

    }
}
