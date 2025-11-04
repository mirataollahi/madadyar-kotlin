package ir.madadyar.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import ir.madadyar.ui.theme.MadadyarTheme
import kotlinx.coroutines.delay

@Composable
fun LandingScreen(
    onNavigateAway: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(4000) // 4 seconds like Flutter app
        onNavigateAway()
    }
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // You can add a landing image here
        // For now, just showing a placeholder
    }
}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    MadadyarTheme {
        LandingScreen(onNavigateAway = {})
    }
}

