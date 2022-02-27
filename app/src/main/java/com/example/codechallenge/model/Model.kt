package com.example.codechallenge.model

data class AlbumInfo(
    var title: String?,
    var owner: String? = null,
    var userId: Int?,
    val id: Int,
    var thumbnailUrl: String? = null,
    var url: String? = null
)

data class Album(
    var userId: Int,
    var id: Int,
    var title: String
)

data class Photo(
    var albumId: Int,
    var id: Int,
    var title: String,
    var url: String,
    var thumbnailUrl: String
)


data class User(
    var id: Int,
    var name: String,
    var username: String,
    var email: String,
    var address: Address,
    var phone: String,
    var website: String,
    var company: Company
)

data class Company(
    var name: String,
    var catchPhrase: String,
    var bs: String,
)

data class Address(
    var street: String,
    var suite: String,
    var city: String,
    var zipcode: String,
    var geo: Geo
)

data class Geo(
    var lat: String,
    var long: String
)

data class PhotoInfo(
    val thumbnailUrl: String,
    val fullSizeUrl: String
)

