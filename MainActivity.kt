package com.example.cvsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.cvsapplication.ui.navigation.NavGraph
import com.example.cvsapplication.ui.theme.CVSApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CVSApplicationTheme {
                RickMortyApp()
            }
        }
    }

    @Composable
    fun RickMortyApp() {
        MaterialTheme {
            Surface {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}