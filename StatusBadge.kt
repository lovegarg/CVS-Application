package com.example.cvsapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    // Dynamic colors based on status
    val (bgColor, textColor) = when (status.lowercase()) {
        "alive" -> Color(0xFF4CAF50) to Color.White  // Green
        "dead" -> Color(0xFFF44336) to Color.White   // Red
        else -> Color(0xFF9E9E9E) to Color.White     // Gray
    }

    Box(
        modifier = modifier
            .background(
                color = bgColor,
                shape = MaterialTheme.shapes.small  // Rounded corners
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = status.replaceFirstChar { it.uppercase() },
            color = textColor,
            style = MaterialTheme.typography.labelSmall
        )
    }
}