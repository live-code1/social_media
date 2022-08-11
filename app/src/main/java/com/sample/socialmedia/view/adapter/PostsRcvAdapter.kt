package com.sample.socialmedia.view.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.socialmedia.R
import com.sample.socialmedia.data.model.Post
import com.sample.socialmedia.databinding.ItemPostBinding
import com.sample.socialmedia.view.CommentsActivity

class PostsRcvAdapter(
    private var ctx: Activity
) :
    RecyclerView.Adapter<PostsRcvAdapter.ViewHolder>() {
    private val posts: MutableList<Post> = ArrayList()

    inner class ViewHolder(private var binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.post = post
            binding.root.setOnClickListener {
                val intent=Intent(ctx, CommentsActivity::class.java )
                intent.putExtra("postId",post.id.toString())
                ctx.startActivity(intent)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_post,
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[holder.adapterPosition])
    }

    //setting data to the mutable list
    fun swap(fruits: MutableList<Post>?) {

        val diffCallback =
            PoSingleConversationListDiffUtils(
                this.posts,
                fruits ?: ArrayList()
            )
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.posts.clear()
        this.posts.addAll(fruits ?: ArrayList())
        diffResult.dispatchUpdatesTo(this)
    }


}


//DiffUtil is a utility class that calculates the difference between two lists and
// outputs a list of update operations that converts the first list into the second one.
private class PoSingleConversationListDiffUtils(
    private val oldList: MutableList<Post>, private val newList: MutableList<Post>
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

