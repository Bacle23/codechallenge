package com.example.codechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codechallenge.model.AlbumInfo
import com.example.codechallenge.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val albumInfoMutableLiveData = MutableLiveData<List<AlbumInfo>?>()
    private val isLoadingMutableLiveData = MutableLiveData(false)
    val albumInfoLivedata: LiveData<List<AlbumInfo>?> by lazy { albumInfoMutableLiveData }
    val isLoadingLiveData: LiveData<Boolean> by lazy { isLoadingMutableLiveData }

    init {
        retrieveAlbumData()
    }

    private fun retrieveAlbumData() {
        isLoadingMutableLiveData.postValue(true)
        viewModelScope.launch {
            val userFlow = flowOf(repository.getUsers())
            val albumFlow = flowOf(repository.getAlbums())
            albumFlow.zip(userFlow) { listAlbum, listUser ->
                val listInfo = arrayListOf<AlbumInfo>()
                listAlbum?.forEach { album ->
                    val matchUser = listUser?.find { user ->
                        user.id == album.userId
                    }

                    listInfo.add(
                        AlbumInfo(
                            title = album.title,
                            owner = matchUser?.name,
                            userId = album.userId,
                            id = album.id
                        )
                    )
                }
                return@zip listInfo.toList()
            }.collectLatest {
                albumInfoMutableLiveData.postValue(it)
            }
            isLoadingMutableLiveData.postValue(false)
        }
    }
}