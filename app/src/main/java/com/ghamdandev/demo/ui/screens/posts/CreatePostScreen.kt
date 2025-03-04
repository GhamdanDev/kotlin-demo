import android.widget.Button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import com.ghamdandev.demo.ui.screens.posts.PostViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.lifecycle.viewmodel.compose.viewModel

import com.ghamdandev.demo.data.api.model.Post
import com.google.type.DateTime

@Composable
fun CreatePostScreen(
    viewModel: PostViewModel = hiltViewModel(), // استخدام Hilt لحقن ViewModel
    onPostCreated: () -> Unit
) {
      val firebaseService = FirebaseService()
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
var post  =Post(0,1,title,body)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = body,
            onValueChange = { body = it },
            label = { Text("Body") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
//                viewModel.createPost(post) // استدعاء الوظيفة داخل ViewModel
//                onPostCreated()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Post")
        }
    }
}