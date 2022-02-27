package com.example.codechallenge.api

import com.example.codechallenge.model.Album
import com.example.codechallenge.model.Photo
import com.example.codechallenge.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("albums")
    suspend fun getAlbum(): Response<List<Album>>

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("photos ")
    suspend fun getPhoto(): Response<List<Photo>>
}