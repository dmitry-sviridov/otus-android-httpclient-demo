package ru.otus.httpclientsdemo.ui.posts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.otus.httpclientsdemo.domain.ResourceState
import ru.otus.httpclientsdemo.model.Post
import ru.otus.httpclientsdemo.repository.PostRepository
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(val service: PostRepository) : ViewModel() {

    private val _postsData = MutableLiveData<ResourceState<Post>>()
    val postData: LiveData<ResourceState<Post>>
        get() = _postsData

    fun getPost(postId: String) = viewModelScope.launch {
        _postsData.postValue(ResourceState.loading(null))
        val response = service.fetchPostById(postId)
        
        if (response.isSuccessful) {
            Log.d(TAG, "getPost: success: ${response.body()}")
            _postsData.postValue(ResourceState.success(response.body()))
        } else {
            Log.d(TAG, "getPost: received unsuccessful response: ${response.errorBody()}")
            _postsData.postValue(ResourceState.error(response.errorBody().toString(), null))
        }
        
    }

    companion object {
        private const val TAG = "PostsViewModel"
    }
}