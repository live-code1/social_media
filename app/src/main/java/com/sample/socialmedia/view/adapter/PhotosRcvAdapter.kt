package com.sample.socialmedia.view.adapter

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.target.Target
import com.sample.socialmedia.R
import com.sample.socialmedia.data.model.Photo
import com.sample.socialmedia.databinding.ItemPhotoBinding


class PhotosRcvAdapter(
    private var ctx: Activity
) :
    RecyclerView.Adapter<PhotosRcvAdapter.ViewHolder>() {
    private val photos: MutableList<Photo> = ArrayList()

    inner class ViewHolder(private var binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.photo = photo

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_photo,
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[holder.adapterPosition])
    }

    //setting data to the mutable list
    fun swap(fruits: MutableList<Photo>?) {

        val diffCallback =
            PhSingleConversationListDiffUtils(
                this.photos,
                fruits ?: ArrayList()
            )
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.photos.clear()
        this.photos.addAll(fruits ?: ArrayList())
        diffResult.dispatchUpdatesTo(this)
    }


}


//DiffUtil is a utility class that calculates the difference between two lists and
// outputs a list of update operations that converts the first list into the second one.
private class PhSingleConversationListDiffUtils(
    private val oldList: MutableList<Photo>, private val newList: MutableList<Photo>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id

    }
}

