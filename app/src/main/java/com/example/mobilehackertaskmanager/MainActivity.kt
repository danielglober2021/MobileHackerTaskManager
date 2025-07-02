package com.example.mobilehackertaskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobilehackertaskmanager.data.repository.TaskSeeder
import com.example.mobilehackertaskmanager.ui.navigation.AppRoot
import com.example.mobilehackertaskmanager.ui.theme.MobileHackerTaskManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileHackerTaskManagerTheme {
                AppRoot()
            }
        }

        // TaskSeeder.seedInitialTasks()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MobileHackerTaskManagerTheme {
        Greeting("Android")
    }
}