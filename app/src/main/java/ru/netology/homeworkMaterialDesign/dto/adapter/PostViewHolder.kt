package ru.netology.homeworkMaterialDesign.dto.adapter

import android.system.Os.remove
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.homeworkMaterialDesign.DisplayingNumbers
import ru.netology.homeworkMaterialDesign.R
import ru.netology.homeworkMaterialDesign.databinding.CardPostBinding
import ru.netology.homeworkMaterialDesign.dto.Post

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.isChecked = post.likedByMe
            share.isChecked = post.sharedByMe
            view.isChecked = post.viewedByMe


            view.text = DisplayingNumbers(post.view)
            likes.text = DisplayingNumbers(post.like)
            share.text = DisplayingNumbers(post.share)
            likes.setOnClickListener {
                onInteractionListener.onLike(post)

            }
            share.setOnClickListener {
                onInteractionListener.onShare(post)

            }

            view.setOnClickListener {
                onInteractionListener.onView(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

        }

    }
}