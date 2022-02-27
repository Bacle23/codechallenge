package com.example.codechallenge.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.codechallenge.MainActivity
import com.example.codechallenge.R
import com.example.codechallenge.adapter.ThumbnailAdapter
import com.example.codechallenge.databinding.ThumbnailListFragmentBinding
import com.example.codechallenge.view.ListAlbumFragment.Companion.ALBUM_DATA_KEY
import com.example.codechallenge.viewmodel.ThumbnailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListThumbnailFragment : Fragment() {
    companion object {
        const val PHOTO_URL_DATA_KEY = "PHOTO_URL_DATA_KEY"
    }

    private var _binding: ThumbnailListFragmentBinding? = null
    private val viewModel: ThumbnailViewModel by viewModels()

    @Inject
    lateinit var adapter: ThumbnailAdapter

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ThumbnailListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arguments?.get(ALBUM_DATA_KEY)
        viewModel.retrieveThumbnails(data as Int)
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.listThumbnailLiveData.observe(this) { listPhotoInfo ->
            listPhotoInfo?.let { it ->
                adapter.setListThumbnail(it)
                val staggeredGridLayoutManager =
                    StaggeredGridLayoutManager(6, LinearLayoutManager.VERTICAL)
                binding.thumbnailRecyclerView.layoutManager = staggeredGridLayoutManager
                binding.thumbnailRecyclerView.adapter = adapter
                adapter.onThumbnailSelected = { photoInfo ->
                    findNavController().navigate(
                        R.id.toFullImageFragment,
                        bundleOf(Pair(PHOTO_URL_DATA_KEY, photoInfo.fullSizeUrl))
                    )
                }
            }
        }
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            when (it) {
                true -> (activity as MainActivity).showLoading()
                false -> (activity as MainActivity).hideLoading()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
