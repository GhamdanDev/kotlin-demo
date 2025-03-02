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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.ghamdandev.demo.ui.theme.ClickCounterAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClickCounterAppTheme {
                AppNavigation()
            }
        }
    }
}

// 👇 وظيفة التنقل بين الشاشات
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { ClickCounterScreen() }
            composable("settings") { SettingsScreen() }
            composable("users") { UsersScreen() }
        }
    }
}

// 👇 شريط التنقل السفلي
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            label = { Text("home") },
            selected = navController.currentDestination?.route == "home",
            onClick = { navController.navigate("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "home") }
        )
        NavigationBarItem(
            label = { Text("Users") },
            selected = navController.currentDestination?.route == "users",
            onClick = { navController.navigate("users") },
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Users") }
        )

        NavigationBarItem(
            label = { Text("settings") },
            selected = navController.currentDestination?.route == "settings",
            onClick = { navController.navigate("settings") },
            icon = { Icon(Icons.Default.Settings, contentDescription = "settings") }
        )
    }
}

// 👇 شاشة عداد النقرات
@Composable
fun ClickCounterScreen() {
    var count by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "عدد النقرات: $count", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { count++ }) {
            Text(text = "اضغط هنا")
        }
    }
}

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
