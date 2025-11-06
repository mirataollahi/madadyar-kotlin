package ir.madadyar.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ir.madadyar.ui.theme.*
import ir.madadyar.ui.viewmodel.VideoDetailViewModel
import ir.madadyar.util.toFullImageUrl
import androidx.compose.ui.viewinterop.AndroidView
import android.text.method.LinkMovementMethod
import android.widget.TextView
import android.text.Html

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoDetailScreen(
    videoId: Int,
    viewModel: VideoDetailViewModel = viewModel(),
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val video by viewModel.video.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(videoId) {
        viewModel.loadVideo(videoId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = video?.name ?: "جزئیات ویدیو", color = White, fontFamily = iransansFontFamily) },
                navigationIcon = {},
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkSurface)
            )
        },
        containerColor = DarkBackground
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Yellow)
            }
        } else if (video != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    model = video!!.image.toFullImageUrl(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )

                Text(text = video!!.name, fontFamily = iransansFontFamily, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = White)

                // HTML description
                AndroidView(
                    modifier = Modifier.fillMaxWidth(),
                    factory = { ctx ->
                        TextView(ctx).apply {
                            setTextColor(android.graphics.Color.WHITE)
                            movementMethod = LinkMovementMethod.getInstance()
                        }
                    },
                    update = {
                        it.text = Html.fromHtml(video!!.description, Html.FROM_HTML_MODE_LEGACY)
                    }
                )

                Button(
                    onClick = {
                        val url = video!!.link
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Yellow)
                ) {
                    Text(text = "پخش ویدیو", color = Black, fontFamily = iransansFontFamily)
                }
            }
        }
    }
}


