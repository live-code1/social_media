package com.sample.socialmedia.view

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.socialmedia.R
import com.sample.socialmedia.data.network.ResultResponse
import com.sample.socialmedia.util.AppUtils
import com.sample.socialmedia.util.Utils
import com.sample.socialmedia.util.toast
import com.sample.socialmedia.view.adapter.PhotosRcvAdapter
import com.sample.socialmedia.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_photos.*

@AndroidEntryPoint
class PhotosActivity : AppCompatActivity() {
    private val postViewModel: PostViewModel by viewModels()
    private var photoId = "1"
    var mAdapter: PhotosRcvAdapter? = null
    private var mProgressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
        showProgressDialog()
        photoId = intent.getStringExtra("albumId") ?: "1"
        rcvPhotos.layoutManager = LinearLayoutManager(this)
        mAdapter = PhotosRcvAdapter(this)
        rcvPhotos.adapter = mAdapter
        if(AppUtils.isNetworkAvailable(this)){
            getPhotos(photoId)
        }
        else{
            toast(getString(R.string.no_network))
        }


        back_button.setOnClickListener {
            finish()
        }
    }

    //get photos from server
    private fun getPhotos(albumId: String) {
        postViewModel.getPhotos(albumId).observe(this) { resultResponse ->
            when (resultResponse.status) {
                ResultResponse.Status.SUCCESS -> {
                    val serverResponse = resultResponse.data?.body()
                    if (serverResponse ?: "" != "") {
                        Log.e("success1 comments size", (serverResponse).toString())

                        if (serverResponse?.size ?: 0 > 0) {

                            mAdapter?.swap(serverResponse)
                            mAdapter?.notifyDataSetChanged()
                        }


                    } else {
                        toast("Something went wrong")
                    }
                    hideProgressDialog()

                }
                ResultResponse.Status.ERROR -> {
                    Log.e("failed", resultResponse.message.toString())
                    hideProgressDialog()
                }
                else -> {
                    Log.e("failed", resultResponse.message.toString())
                    hideProgressDialog()
                }
            }
        }
    }

    //show progress dialog
    private fun showProgressDialog() {
        mProgressDialog =
            ProgressDialog(
                this,
                R.style.AppCompatAlertDialogStyle
            )
        mProgressDialog?.isIndeterminate = false
        mProgressDialog?.setCancelable(false)
        Utils.showProgressDialog(
            mProgressDialog,
            "",
            getString(R.string.please_wait)
        )
    }

    //hide progress dialog
    private fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog?.isShowing!!) {
            Utils.hideProgressDialog(mProgressDialog)
        }
    }
}