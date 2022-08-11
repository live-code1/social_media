package com.sample.socialmedia.view.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.socialmedia.R
import com.sample.socialmedia.data.model.Album
import com.sample.socialmedia.databinding.ItemAlbumBinding
import com.sample.socialmedia.view.PhotosActivity

class AlbumsRcvAdapter(
    private var ctx: Activity
) :
    RecyclerView.Adapter<AlbumsRcvAdapter.ViewHolder>() {
    private val albums: MutableList<Album> = ArrayList()

    inner class ViewHolder(private var binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.album = album
            binding.root.setOnClickListener {
                val intent=Intent(ctx, PhotosActivity::class.java )
                intent.putExtra("albumId",album.id.toString())
                ctx.startActivity(intent)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_album,
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albums[holder.adapterPosition])
    }

    //setting data to the mutable list
    fun swap(fruits: MutableList<Album>?) {

        val diffCallback =
            AlSingleConversationListDiffUtils(
                this.albums,
                fruits ?: ArrayList()
            )
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.albums.clear()
        this.albums.addAll(fruits ?: ArrayList())
        diffResult.dispatchUpdatesTo(this)
    }


}
//DiffUtil is a utility class that calculates the difference between two lists and
// outputs a list of update operations that converts the first list into the second one.
private class AlSingleConversationListDiffUtils(
    private val oldList: MutableList<Album>, private val newList: MutableList<Album>
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

