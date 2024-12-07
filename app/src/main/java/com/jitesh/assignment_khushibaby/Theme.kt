package com.jitesh.assignment_khushibaby

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.jitesh.assignment_khushibaby.ui.theme.DarkColorScheme
import com.jitesh.assignment_khushibaby.ui.theme.LightColorScheme
import com.jitesh.assignment_khushibaby.ui.theme.Typography

@Composable
fun MovieAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}