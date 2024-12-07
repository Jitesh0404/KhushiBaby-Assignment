package com.jitesh.assignment_khushibaby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jitesh.assignment_khushibaby.ui.theme.AssignmentKhushiBabyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssignmentKhushiBabyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    setContent {
                        MovieAppTheme {
                            Surface(
                                modifier = Modifier.fillMaxSize().padding(innerPadding),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                MovieNavigation()
                            }
                        }
                    }
                }
            }
        }
    }
}
