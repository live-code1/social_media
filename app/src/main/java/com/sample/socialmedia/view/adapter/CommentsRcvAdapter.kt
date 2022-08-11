package com.sample.socialmedia.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.socialmedia.R
import com.sample.socialmedia.data.model.Comment
import com.sample.socialmedia.databinding.ItemCommentBinding

class CommentsRcvAdapter(
    private var ctx: Activity
) :
    RecyclerView.Adapter<CommentsRcvAdapter.ViewHolder>() {
    private val comments: MutableList<Comment> = ArrayList()

    inner class ViewHolder(private var binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.comment = comment


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_comment,
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments[holder.adapterPosition])
    }

    //setting data to the mutable list
    fun swap(fruits: MutableList<Comment>?) {

        val diffCallback =
            CmSingleConversationListDiffUtils(
                this.comments,
                fruits ?: ArrayList()
            )
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.comments.clear()
        this.comments.addAll(fruits ?: ArrayList())
        diffResult.dispatchUpdatesTo(this)
    }


}
//DiffUtil is a utility class that calculates the difference between two lists and
// outputs a list of update operations that converts the first list into the second one.
private class CmSingleConversationListDiffUtils(
    private val oldList: MutableList<Comment>, private val newList: MutableList<Comment>
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

