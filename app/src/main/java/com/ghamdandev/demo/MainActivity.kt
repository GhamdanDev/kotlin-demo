////package com.ghamdandev.demo
////
////import android.os.Bundle
////import androidx.activity.ComponentActivity
////import androidx.activity.compose.setContent
////import androidx.activity.enableEdgeToEdge
////import androidx.compose.foundation.layout.fillMaxSize
////import androidx.compose.foundation.layout.padding
////import androidx.compose.material3.Scaffold
////import androidx.compose.material3.Text
////import androidx.compose.runtime.Composable
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.tooling.preview.Preview
////import com.ghamdandev.demo.ui.theme.DemoTheme
////
////class MainActivity : ComponentActivity() {
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        enableEdgeToEdge()
////        setContent {
////            DemoTheme {
////                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
////                    Greeting(
////                        name = "Android",
////                        modifier = Modifier.padding(innerPadding)
////                    )
////                }
////            }
////        }
////    }
////}
////
////@Composable
////fun Greeting(name: String, modifier: Modifier = Modifier) {
////    Text(
////        text = "Hello $name!",
////        modifier = modifier
////    )
////
////}
////
////@Preview(showBackground = true)
////@Composable
////fun GreetingPreview() {
////    DemoTheme {
////        Greeting("Android")
////    }
////}
//
//
//package com.ghamdandev.demo
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.ghamdandev.demo.ui.theme.ClickCounterAppTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            ClickCounterAppTheme {
//                ClickCounterScreen()
//            }
//        }
//    }
//}
//
//@Composable
//fun ClickCounterScreen() {
//    var count by remember { mutableStateOf(0) }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "عدد النقرات: $count",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = { count++ }) {
//            Text(text = "اضغط هنا")
//        }
//        if (count >= 10) {
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(text = "🎉 تهانينا! وصلت إلى 10 نقرات!", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ClickCounterPreview() {
//    ClickCounterAppTheme {
//        ClickCounterScreen()
//    }
//}



package com.ghamdandev.demo

//import PostScreen
import PostsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*

import androidx.compose.material3.Text

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ghamdandev.demo.data.api.model.Post
import com.ghamdandev.demo.ui.screens.posts.PostViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: PostViewModel = viewModel()
            PostsScreen(viewModel)
        }
    }
}

@Composable
fun DisplayData(viewModel: PostViewModel) {
    val posts by viewModel.posts.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }

    if (posts.isEmpty()) {
        Text(text = "Loading data...")
    } else {
        LazyColumn {
            itemsIndexed(posts) { index, post ->
                PostItem(post = post)
            }

        }
    }
}

@Composable
fun PostItem(post: Post) {
    Column() {
        Text(text = "ID: ${post.id}")
        Text(text = "User ID: ${post.userId}")
        Text(text = "Title: ${post.title}")
        Text(text = "Body: ${post.body}")
    }
}


//import com.ghamdandev.demo.ui.screens.posts.PostViewModel
//
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            ClickCounterAppTheme {
//                val viewModel: PostViewModel = hiltViewModel()
//                PostsScreen(viewModel)
//            }
//        }
//    }
//}
//
//class MainActivity : ComponentActivity() {
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            ClickCounterAppTheme {
//                PostsScreen()
////                AppNavigation()
//            }
//        }
//    }
//}



// 👇 شاشة عداد النقرات

// 👇 شاشة الإعدادات
//@Composable
//fun SettingsScreen() {
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Text(text = "🚀 إعدادات التطبيق", fontSize = 24.sp)
//    }
//}

//
//@Composable
//fun UsersScreen() {
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Text(text = "users", fontSize = 24.sp)
//
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewApp() {
//    ClickCounterAppTheme {
//      // AppNavigation()
//        PostsScreen()
//    }
//}
//
//

