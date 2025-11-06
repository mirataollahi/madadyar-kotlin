package ir.madadyar.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ir.madadyar.data.model.Video
import ir.madadyar.ui.theme.*
import ir.madadyar.ui.viewmodel.VideosListViewModel
import ir.madadyar.util.toFullImageUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideosListScreen(
    initialCategoryId: Int?,
    onBack: () -> Unit,
    onNavigateToVideoDetail: (Int) -> Unit,
    viewModel: VideosListViewModel = viewModel()
) {
    val videos by viewModel.videos.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(initialCategoryId) {
        viewModel.loadVideos(initialCategoryId, null)
        viewModel.loadCategories(initialCategoryId)
    }

    var search by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ویدیوها", color = White, fontFamily = iransansFontFamily) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkSurface)
            )
        },
        containerColor = DarkBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = {
                    search = it
                    if (it.length >= 2 || it.isEmpty()) {
                        viewModel.search(if (it.isEmpty()) "" else it)
                    }
                },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = White) },
                placeholder = { Text("جستجوی ویدیو...", color = White.copy(alpha = 0.7f), fontFamily = iransansFontFamily) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = White,
                    unfocusedTextColor = White,
                    focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                    unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                    focusedIndicatorColor = White,
                    unfocusedIndicatorColor = White,
                    cursorColor = White
                )
            )

            if (categories.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    categories.forEach { cat ->
                        AssistChip(onClick = { viewModel.selectCategory(cat.id) }, label = {
                            Text(cat.name, fontFamily = iransansFontFamily)
                        })
                    }
                }
            }

            if (isLoading && videos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Yellow)
                }
            } else {
                LazyVerticalGrid(columns = GridCells.Adaptive(140.dp), verticalArrangement = Arrangement.spacedBy(10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(videos) { video ->
                        VideoGridItem(video = video) { onNavigateToVideoDetail(video.id) }
                    }
                }
            }
        }
    }
}

@Composable
private fun VideoGridItem(video: Video, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = DarkSurface)
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            AsyncImage(
                model = video.image.toFullImageUrl(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(White.copy(alpha = 0.05f))
            )
            Text(video.name, fontFamily = iransansFontFamily, fontWeight = FontWeight.Bold, color = White, fontSize = 14.sp)
        }
    }
}


