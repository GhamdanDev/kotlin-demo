
package com.ghamdandev.demo.ui.screens.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghamdandev.demo.data.api.model.Post
import com.ghamdandev.demo.data.model.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _operationStatus = MutableStateFlow<Boolean?>(null)
    val operationStatus: StateFlow<Boolean?> = _operationStatus

    fun fetchPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _posts.value = repository.getPosts()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to fetch posts"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addPost(post: Post) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val isSuccess = repository.addPost(post)
                _operationStatus.value = isSuccess
                if (isSuccess) {
                    fetchPosts()
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to add post"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updatePost(post: Post) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val isSuccess = repository.updatePost(post)
                _operationStatus.value = isSuccess
                if (isSuccess) {
                    fetchPosts()
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to update post"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deletePost(postId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val isSuccess = repository.deletePost(postId)
                _operationStatus.value = isSuccess
                if (isSuccess) {
                    fetchPosts()
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to delete post"
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun clearError() {
        _error.value = null
    }

    fun clearOperationStatus() {
        _operationStatus.value = null
    }
}
