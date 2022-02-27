package com.example.codechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codechallenge.model.PhotoInfo
import com.example.codechallenge.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThumbnailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val thumbnailMutableLiveData = MutableLiveData<List<PhotoInfo>?>()
    private val isLoadingMutableLiveData = MutableLiveData(false)
    val isLoadingLiveData: LiveData<Boolean> by lazy { isLoadingMutableLiveData }
    val listThumbnailLiveData: LiveData<List<PhotoInfo>?> by lazy { thumbnailMutableLiveData }

    fun retrieveThumbnails(albumId: Int) {
        viewModelScope.launch {
            isLoadingMutableLiveData.postValue(true)
            val listPhoto = repository.getPhoto()
            val listThumbnail =
                listPhoto?.filter { photo -> photo.albumId == albumId }
                    ?.map { filteredPhoto ->
                        PhotoInfo(filteredPhoto.thumbnailUrl, filteredPhoto.url)
                    }
            thumbnailMutableLiveData.postValue(listThumbnail)
            isLoadingMutableLiveData.postValue(false)
        }
    }
}