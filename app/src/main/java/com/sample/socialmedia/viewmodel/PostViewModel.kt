package com.sample.socialmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sample.socialmedia.data.model.Album
import com.sample.socialmedia.data.model.Comment
import com.sample.socialmedia.data.model.Photo
import com.sample.socialmedia.data.model.Post
import com.sample.socialmedia.data.network.ResultResponse
import com.sample.socialmedia.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PostViewModel
@Inject constructor(
    private var repository: PostRepository
) : ViewModel() {



    fun getPosts(
    ): LiveData<ResultResponse<Response<MutableList<Post>>>> = liveData {
        var responseGet: Response<MutableList<Post>>? = null
        kotlin.runCatching {
            responseGet = repository.getPosts(
            )
        }.onSuccess {
            when (responseGet?.code()) {
                200 -> {
                    emit(ResultResponse.success(responseGet!!))
                }
                502, 522, 523, 500 -> {
                    emit(ResultResponse.error(responseGet!!.message().toString(), responseGet))
                }
                else -> {
                    emit(ResultResponse.error(responseGet!!.message().toString(), responseGet))
                }
            }
        }.onFailure {
            emit(ResultResponse.error(it.message.toString(), responseGet))
        }
    }

    fun getComments(postId: String
    ): LiveData<ResultResponse<Response<MutableList<Comment>>>> = liveData {
        var responseGet: Response<MutableList<Comment>>? = null
        kotlin.runCatching {
            responseGet = repository.getComments(postId
            )
        }.onSuccess {
            when (responseGet?.code()) {
                200 -> {
                    emit(ResultResponse.success(responseGet!!))
                }
                502, 522, 523, 500 -> {
                    emit(ResultResponse.error(responseGet!!.message().toString(), responseGet))
                }
                else -> {
                    emit(ResultResponse.error(responseGet!!.message().toString(), responseGet))
                }
            }
        }.onFailure {
            emit(ResultResponse.error(it.message.toString(), responseGet))
        }
    }




    fun getAlbums(
    ): LiveData<ResultResponse<Response<MutableList<Album>>>> = liveData {
        var responseGet: Response<MutableList<Album>>? = null
        kotlin.runCatching {
            responseGet = repository.getAlbums(
            )
        }.onSuccess {
            when (responseGet?.code()) {
                200 -> {
                    emit(ResultResponse.success(responseGet!!))
                }
                502, 522, 523, 500 -> {
                    emit(ResultResponse.error(responseGet!!.message().toString(), responseGet))
                }
                else -> {
                    emit(ResultResponse.error(responseGet!!.message().toString(), responseGet))
                }
            }
        }.onFailure {
            emit(ResultResponse.error(it.message.toString(), responseGet))
        }
    }


    fun getPhotos(albumId: String
    ): LiveData<ResultResponse<Response<MutableList<Photo>>>> = liveData {
        var responseGet: Response<MutableList<Photo>>? = null
        kotlin.runCatching {
            responseGet = repository.getPhotos(albumId
            )
        }.onSuccess {
            when (responseGet?.code()) {
                200 -> {
                    emit(ResultResponse.success(responseGet!!))
                }
                502, 522, 523, 500 -> {
                    emit(ResultResponse.error(responseGet!!.message().toString(), responseGet))
                }
                else -> {
                    emit(ResultResponse.error(responseGet!!.message().toString(), responseGet))
                }
            }
        }.onFailure {
            emit(ResultResponse.error(it.message.toString(), responseGet))
        }
    }
}