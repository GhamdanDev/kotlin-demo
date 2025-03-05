//import android.util.Log
//import com.ghamdandev.demo.data.api.model.Post
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.tasks.await
//import javax.inject.Inject
//
//class FirebaseService @Inject constructor(
//    private val firestore: FirebaseFirestore
//) {
//    private val postsCollection = firestore.collection("posts")//class FirebaseService @Inject constructor(){
////class FirebaseService {
////    private val db = FirebaseFirestore.getInstance()
////    private val postsCollection = db.collection("posts")
//
//    suspend fun getPosts(): List<Post> {
//        return try {
//            val snapshot = postsCollection.get().await()
//            snapshot.documents.map { it.toObject(Post::class.java)!! }
//        } catch (e: Exception) {
//            Log.e("FirebaseService", "Error getting posts", e)
//            emptyList()
//        }
//    }
//
//    suspend fun createPost(post: Post): Post? {
//        return try {
//            val docRef = postsCollection.add(post).await()
//            val newPost = post.copy(id = docRef.id.hashCode())  // توليد ID من Firebase
//            postsCollection.document(docRef.id).set(newPost).await()
//            newPost
//        } catch (e: Exception) {
//            Log.e("FirebaseService", "Error creating post", e)
//            null
//        }
//    }
//    suspend fun updatePost(post: Post): Boolean {
//        return try {
//            postsCollection.document(post.id.toString()).set(post).await()
//            true
//        } catch (e: Exception) {
//            false
//        }
//    }
//}
