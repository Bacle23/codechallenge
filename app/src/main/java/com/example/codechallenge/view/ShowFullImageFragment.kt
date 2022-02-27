package com.example.codechallenge.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.codechallenge.databinding.ShowImageFragmentBinding
import com.example.codechallenge.view.ListThumbnailFragment.Companion.PHOTO_URL_DATA_KEY
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowFullImageFragment : Fragment() {

    private var _binding: ShowImageFragmentBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShowImageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arguments?.get(PHOTO_URL_DATA_KEY) as String
        Picasso.get().load(data).into(binding.albumImageView)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}