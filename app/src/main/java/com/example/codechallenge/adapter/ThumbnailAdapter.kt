package com.example.codechallenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge.databinding.ThumbnailItemBinding
import com.example.codechallenge.model.PhotoInfo
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ThumbnailAdapter @Inject constructor() :
    RecyclerView.Adapter<ThumbnailAdapter.ThumbnailViewHolder>() {
    private var listThumbnail = listOf<PhotoInfo>()
    var onThumbnailSelected: (photoInfo: PhotoInfo) -> Unit = { _ -> }

    fun setListThumbnail(listData: List<PhotoInfo>) {
        listThumbnail = listData
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ThumbnailViewHolder = ThumbnailViewHolder(
        ThumbnailItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        val data = listThumbnail[position]
        holder.bind(data)
        holder.itemView.setOnClickListener {
            onThumbnailSelected(data)
        }
    }

    override fun getItemCount() = listThumbnail.size

    class ThumbnailViewHolder(private val binding: ThumbnailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(source: PhotoInfo) {
            binding.apply {
                source.also {
                    Picasso.get().load(it.thumbnailUrl).into(thumbnailImageView)
                }
            }
        }
    }
}