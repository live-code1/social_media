package com.sample.socialmedia.data.network

import com.sample.socialmedia.data.model.Album
import com.sample.socialmedia.data.model.Comment
import com.sample.socialmedia.data.model.Photo
import com.sample.socialmedia.data.model.Post
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {


    @GET("posts")
    suspend fun getPosts(
    ): Response<MutableList<Post>>

    @GET("comments")
    suspend fun getComments(
        @Query("postId") postId: String
    ): Response<MutableList<Comment>>

    @GET("albums")
    suspend fun getAlbums(
    ): Response<MutableList<Album>>

    @GET("photos")
    suspend fun getPhotos(
        @Query("postId") albumId: String
    ): Response<MutableList<Photo>>




}


