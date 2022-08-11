package com.sample.socialmedia.data.repository

import com.sample.socialmedia.data.model.Album
import com.sample.socialmedia.data.model.Comment
import com.sample.socialmedia.data.model.Photo
import com.sample.socialmedia.data.model.Post
import com.sample.socialmedia.data.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class PostRepository @Inject constructor(private val backEndApi: ApiService) {

    suspend fun getPosts(): Response<MutableList<Post>> {
        return backEndApi.getPosts()
    }


    suspend fun getComments(postId: String): Response<MutableList<Comment>> {
        return backEndApi.getComments(postId)
    }

    suspend fun getAlbums(): Response<MutableList<Album>> {
        return backEndApi.getAlbums()
    }


    suspend fun getPhotos(albumId: String): Response<MutableList<Photo>> {
        return backEndApi.getPhotos(albumId)
    }
}