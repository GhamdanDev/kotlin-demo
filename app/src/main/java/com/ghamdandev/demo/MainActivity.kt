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

import PostsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.ghamdandev.demo.ui.theme.ClickCounterAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.ghamdandev.demo.data.api.ApiClient
import com.ghamdandev.demo.data.api.model.Post
import com.ghamdandev.demo.ui.theme.ui.navigation.AppNavigation
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    val db = Firebase.firestore
    // Create a new user with a first and last name
    val user = hashMapOf(
        "first" to "Ada",
        "last" to "Lovelace",
        "born" to 1815
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClickCounterAppTheme {
                AppNavigation()
            }
        }
    }
}



// 👇 شاشة عداد النقرات

// 👇 شاشة الإعدادات
@Composable
fun SettingsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "🚀 إعدادات التطبيق", fontSize = 24.sp)
    }
}


@Composable
fun UsersScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "users", fontSize = 24.sp)

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    ClickCounterAppTheme {
        AppNavigation()
    }
}



