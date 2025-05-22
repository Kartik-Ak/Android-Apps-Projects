package com.example.spamdetector
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spamdetector.ui.theme.SpamDetectorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpamDetectorTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "Ui", builder = {
                    composable("Ui") {
                        Ui(navController)
                    }
                    composable("Screen1/{score}") { backStackEntry ->
                        val score1 = backStackEntry.arguments?.getString("score") ?: "N/A"
                        Output1(score1)
                    }
                    composable("Screen2/{score}") { backStackEntry ->
                        val score2 = backStackEntry.arguments?.getString("score") ?: "N/A"
                        Output2(score2)
                    }
                })
        }
    }
    }
}