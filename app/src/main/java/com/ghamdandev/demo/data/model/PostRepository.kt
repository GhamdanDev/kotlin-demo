
package com.ghamdandev.demo.data.model

import android.util.Log
import com.ghamdandev.demo.data.api.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import timber.log.Timber
class PostRepository @Inject constructor(private val firestore: FirebaseFirestore) {

    suspend fun getPosts(): List<Post> {
        return try {
            val documents = firestore.collection("posts").get().await()
            documents.map { document ->
                Post(
                    id = document.getLong("id") ?: 0,
                    userId = document.getLong("userId") ?: 0,
                    title = document.getString("title") ?: "",
                    body = document.getString("body") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addPost(post: Post): Boolean {
        return try {
            val postMap = hashMapOf(
                "id" to post.id,
                "userId" to post.userId,
                "title" to post.title,
                "body" to post.body
            )
            firestore.collection("posts").document(post.id.toString()).set(postMap).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updatePost(post: Post): Boolean {
        return try {
            val postMap = hashMapOf(
                "id" to post.id,
                "userId" to post.userId,
                "title" to post.title,
                "body" to post.body
            )
            Timber.d("Updating post: $postMap")
            val query = firestore.collection("posts")
                .whereEqualTo("id", post.id)
                .get()
                .await()

            if (query.documents.isNotEmpty()) {

                val documentId = query.documents[0].id
                firestore.collection("posts").document(documentId).set(postMap).await()
                true
            } else {
                Timber.e("UpdatePostError", "No document found with id: ${post.id}")
                false
            }
        } catch (e: Exception) {
            Timber.e("UpdatePostError", "Error updating post: ${e.message}")
            false
        }
    }
    suspend fun deletePost(postId: Long): Boolean {
        return try {

            val query = firestore.collection("posts")
                .whereEqualTo("id", postId)
                .get()
                .await()

            if (query.documents.isNotEmpty()) {

                val documentId = query.documents[0].id
                firestore.collection("posts").document(documentId).delete().await()


            }
                true
        } catch (e: Exception) {
            false
        }
    }
}
