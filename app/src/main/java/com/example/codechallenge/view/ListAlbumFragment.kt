package com.example.codechallenge.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.codechallenge.MainActivity
import com.example.codechallenge.R
import com.example.codechallenge.adapter.AlbumAdapter
import com.example.codechallenge.databinding.AlbumListFragmentBinding
import com.example.codechallenge.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ListAlbumFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModels()
    private var _binding: AlbumListFragmentBinding? = null

    @Inject
    lateinit var adaper: AlbumAdapter
    private val binding get() = _binding!!

    companion object {
        const val ALBUM_DATA_KEY = "ALBUM_DATA_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlbumListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
    }

    private fun setupView() {
        binding.albumListRecyclerView.adapter = adaper
        val dividerItemDecoration = DividerItemDecoration(
            binding.albumListRecyclerView.context,
            DividerItemDecoration.VERTICAL
        )
        binding.albumListRecyclerView.addItemDecoration(dividerItemDecoration)
        setupAction()
    }

    private fun setupAction() {
        adaper.onClickAlbum = {
            findNavController().navigate(
                R.id.toThumbnailFragment, bundleOf(Pair(ALBUM_DATA_KEY, it.id))
            )
        }
    }

    private fun setupObserver() {
        mainViewModel.albumInfoLivedata.observe(this) { listAlbumInfo ->
            adaper.setAlbumList(listAlbumInfo)
            setupView()
        }
        mainViewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            when (it) {
                true -> (activity as MainActivity).showLoading()
                false -> (activity as MainActivity).hideLoading()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
