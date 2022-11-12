package ru.netology.homeworkMaterialDesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.Group
import ru.netology.homeworkMaterialDesign.dto.adapter.OnInteractionListener
import ru.netology.homeworkMaterialDesign.dto.adapter.PostAdapter

import ru.netology.homeworkMaterialDesign.databinding.ActivityMainBinding
import ru.netology.homeworkMaterialDesign.dto.Post
import ru.netology.homeworkMaterialDesign.util.AndroidUtils

import ru.netology.homeworkMaterialDesign.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    val viewModel: PostViewModel by viewModels()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    lateinit var adapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)



        initViews()
        subscribe()
        setupListeners()
    }

    private fun initViews() {
        val group = findViewById<Group>(R.id.group)
        group.visibility=View.GONE
        adapter = PostAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {

                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }

            override fun onView(post: Post) {
                viewModel.viewById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }
        })

        binding.list.adapter = adapter

    }

    private fun subscribe() {

        viewModel.edited.observe(this) { post ->

            if (post.id == 0L) {

                return@observe
            }
            with(binding.content) {

                requestFocus()
                setText(post.content)
            }
        }
        viewModel.data.observe(this) { posts ->

            adapter.submitList(posts)

        }

    }

    private fun setupListeners() {
        val group = findViewById<Group>(R.id.group)
        binding.content.setOnClickListener{

        }
        binding.content.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                group.visibility = View.VISIBLE
            else
                group.visibility = View.GONE

        }
        binding.save.setOnClickListener {

            with(binding.content) {

                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }

        }
        binding.cancel.setOnClickListener {
            with(binding.content) {
                viewModel.cancel()
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)

            }

        }
    }

}






