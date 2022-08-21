package ru.otus.httpclientsdemo.ui.posts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.httpclientsdemo.R
import ru.otus.httpclientsdemo.databinding.FragmentPostsBinding
import ru.otus.httpclientsdemo.domain.ResourceState
import ru.otus.httpclientsdemo.model.Post
import java.time.Instant

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private val viewModel: PostsViewModel by viewModels()
    private lateinit var binding: FragmentPostsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostsBinding.bind(view)

        Log.d(TAG, "onViewCreated: ${Instant.now()}")

        viewModel.postData.observe(viewLifecycleOwner) { resourceState ->
            render(resourceState)
        }

        binding.receivePostButton.setOnClickListener {
            val postId = binding.postIdInput.text.toString()
            if (postId.isNotBlank()) {
                viewModel.getPost(postId)
            } else {
                showErrorSnackBar("Empty post id provided!")
            }
        }
    }

    private fun showErrorSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .show();
    }

    private fun render(rs: ResourceState<Post>) {
        when (rs.status) {
            ResourceState.FetchingStatus.ERROR -> {
                Log.d(TAG, "render: ERROR")
                binding.progressCircular.visibility = View.GONE
                showErrorSnackBar("Error on post fetching")
            }
            ResourceState.FetchingStatus.LOADING -> {
                Log.d(TAG, "render: LOADING")
                binding.progressCircular.visibility = View.VISIBLE
            }
            ResourceState.FetchingStatus.SUCCESS -> {
                Log.d(TAG, "render: SUCCESS")
                binding.progressCircular.visibility = View.GONE
                binding.postCard.visibility = View.VISIBLE

                binding.postUserId.text = rs.data?.userId.toString()
                binding.postPostId.text = rs.data?.id.toString()
                binding.postTitle.text = rs.data?.title.toString()
                binding.postText.text = rs.data?.body.toString()
            }
        }
    }

    companion object {
        private const val TAG = "PostsFragment"
    }
}