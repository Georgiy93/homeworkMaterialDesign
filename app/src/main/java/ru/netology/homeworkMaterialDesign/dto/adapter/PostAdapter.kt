package ru.netology.homeworkMaterialDesign.dto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

import ru.netology.homeworkMaterialDesign.databinding.CardPostBinding
import ru.netology.homeworkMaterialDesign.dto.Post
interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onShare(post: Post) {}
    fun onView(post: Post) {}
}
class PostAdapter(

private val onInteractionListener: OnInteractionListener

) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }



}

