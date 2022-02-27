package com.example.codechallenge.repository

import com.example.codechallenge.api.ApiService
import com.example.codechallenge.model.Album
import com.example.codechallenge.model.Photo
import com.example.codechallenge.model.User

class Repository(private val apiService: ApiService) {
    suspend fun getAlbums(): List<Album>? = apiService.getAlbum().body()
    suspend fun getUsers(): List<User>? = apiService.getUsers().body()
    suspend fun getPhoto(): List<Photo>? = apiService.getPhoto().body()
}