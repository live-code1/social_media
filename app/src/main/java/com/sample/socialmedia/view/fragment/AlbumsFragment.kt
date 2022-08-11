package com.sample.socialmedia.view.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.sample.socialmedia.R
import com.sample.socialmedia.data.network.ResultResponse
import com.sample.socialmedia.util.AppUtils
import com.sample.socialmedia.util.Utils
import com.sample.socialmedia.util.toast
import com.sample.socialmedia.view.adapter.AlbumsRcvAdapter
import com.sample.socialmedia.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_albums.*

@AndroidEntryPoint
class AlbumsFragment : Fragment() {
    private val postViewModel: PostViewModel by viewModels()
    var mAdapter: AlbumsRcvAdapter? = null
    private var mProgressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressDialog()
        rcv_albums.layoutManager = GridLayoutManager(requireActivity(), 2)
        mAdapter = AlbumsRcvAdapter(requireActivity())
        rcv_albums.adapter = mAdapter

        if(AppUtils.isNetworkAvailable(requireActivity())){
            getAlbums()
        }
        else{
            requireActivity().toast(getString(R.string.no_network))
        }


    }

    //get albums from server
    private fun getAlbums() {
        postViewModel.getAlbums().observe(requireActivity()) { resultResponse ->
            when (resultResponse.status) {
                ResultResponse.Status.SUCCESS -> {
                    val serverResponse = resultResponse.data?.body()
                    if (serverResponse ?: "" != "") {
                        Log.e("success1 fruits size", (serverResponse).toString())

                        if (serverResponse?.size ?: 0 > 0) {

                            mAdapter?.swap(serverResponse)
                            mAdapter?.notifyDataSetChanged()
                        }


                    } else {
                        requireActivity().toast("Something went wrong")
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
                requireActivity(),
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