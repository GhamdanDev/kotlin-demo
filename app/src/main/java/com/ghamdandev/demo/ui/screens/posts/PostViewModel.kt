package com.ghamdandev.demo.ui.screens.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghamdandev.demo.data.api.ApiClient
import com.ghamdandev.demo.data.api.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _posts.value = ApiClient.api.getPosts()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun updatePost(updatedPost: Post) {
        _posts.value = _posts.value.map { if (it.id == updatedPost.id) updatedPost else it }
        // Persist changes to your data source here
    }

    fun createPost(title: String, body: String, userId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val newPost = Post(id = 0, userId = userId, title = title, body = body)
                val createdPost = ApiClient.api.createPost(newPost)
                _posts.value = _posts.value + createdPost
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}