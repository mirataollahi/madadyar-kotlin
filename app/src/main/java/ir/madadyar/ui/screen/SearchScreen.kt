package ir.madadyar.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ir.madadyar.ui.theme.*
import ir.madadyar.ui.viewmodel.BooksListViewModel
import ir.madadyar.ui.viewmodel.VideosListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onOpenBook: (Int) -> Unit,
    onOpenVideo: (Int) -> Unit
) {
    val booksVM: BooksListViewModel = viewModel()
    val videosVM: VideosListViewModel = viewModel()
    val books by booksVM.books.collectAsState()
    val videos by videosVM.videos.collectAsState()
    val isBooksLoading by booksVM.isLoading.collectAsState()
    val isVideosLoading by videosVM.isLoading.collectAsState()

    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                if (it.length >= 2 || it.isEmpty()) {
                    if (it.isEmpty()) {
                        booksVM.resetToMain()
                        videosVM.resetToMain()
                    } else {
                        booksVM.search(it)
                        videosVM.search(it)
                    }
                }
            },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = White) },
            placeholder = { Text("جستجو...", color = White.copy(alpha = 0.7f), fontFamily = iransansFontFamily, fontSize = 14.sp) },
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

        if (isBooksLoading || isVideosLoading) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Yellow)
            }
        }

        if (books.isNotEmpty()) {
            Text("کتاب‌ها", color = White, fontFamily = iransansFontFamily)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(books.take(5)) { b ->
                    Text(b.name, color = Yellow, modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpenBook(b.id) }, fontFamily = iransansFontFamily)
                }
            }
        }
        if (videos.isNotEmpty()) {
            Text("ویدیوها", color = White, fontFamily = iransansFontFamily)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(videos.take(5)) { v ->
                    Text(v.name, color = Yellow, modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpenVideo(v.id) }, fontFamily = iransansFontFamily)
                }
            }
        }
    }
}


