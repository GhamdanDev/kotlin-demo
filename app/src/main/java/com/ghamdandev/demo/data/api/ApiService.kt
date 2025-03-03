package com.ghamdandev.demo.data.api


import com.ghamdandev.demo.data.api.model.Post
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
    @POST("posts")
    suspend fun createPost(@Body post: Post): Post
}
