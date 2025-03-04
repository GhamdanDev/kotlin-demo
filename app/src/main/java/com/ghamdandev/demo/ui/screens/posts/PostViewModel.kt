package com.ghamdandev.demo.ui.screens.posts

import FirebaseService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.ghamdandev.demo.data.api.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch



class PostViewModel : ViewModel() {
    private val firebaseService = FirebaseService()

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
                _posts.value = firebaseService.getPosts()
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createPost(title: String, body: String, userId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val newPost = Post(id = 0, userId = userId, title = title, body = body)
                val createdPost = firebaseService.createPost(newPost)
                if (createdPost != null) {
                    _posts.value = _posts.value + createdPost
                }
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updatePost(updatedPost: Post) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val success = firebaseService.updatePost(updatedPost)
                if (success) {
                    _posts.value = _posts.value.map { if (it.id == updatedPost.id) updatedPost else it }
                }
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

}