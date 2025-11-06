package ir.madadyar.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import ir.madadyar.data.model.Book
import ir.madadyar.ui.theme.*
import ir.madadyar.ui.viewmodel.BooksListViewModel
import ir.madadyar.util.toFullImageUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksListScreen(
    initialCategoryId: Int?,
    onBack: () -> Unit,
    onNavigateToBookDetail: (Int) -> Unit,
    viewModel: BooksListViewModel = viewModel()
) {
    val books by viewModel.books.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()
    val currentPage by viewModel.currentPage.collectAsState()
    val totalPages by viewModel.totalPages.collectAsState()

    LaunchedEffect(initialCategoryId) {
        viewModel.loadBooks(initialCategoryId, null, true)
        viewModel.loadCategories(initialCategoryId)
    }

    var search by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(listState.firstVisibleItemIndex, listState.layoutInfo.totalItemsCount) {
        val lastVisibleIndex = listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size
        if (lastVisibleIndex >= books.size - 2 && currentPage < totalPages && !isLoadingMore && !isLoading) {
            viewModel.loadMore()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("کتاب‌ها", color = White, fontFamily = iransansFontFamily) },
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
                placeholder = { Text("جستجوی کتاب...", color = White.copy(alpha = 0.7f), fontFamily = iransansFontFamily) },
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

            if (isLoading && books.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Yellow)
                }
            } else {
                LazyColumn(state = listState, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(books) { book ->
                        BookListItem(
                            book = book,
                            onClick = { onNavigateToBookDetail(book.id) },
                            onDownload = {
                                val url = (book.file ?: "").toFullImageUrl()
                                if (url != null) {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    onBack /* just to use param */
                                    // use LocalContext within item scope
                                }
                            }
                        )
                    }
                    if (isLoadingMore) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = Yellow)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BookListItem(
    book: Book,
    onClick: () -> Unit,
    onDownload: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = DarkSurface)
    ) {
        Row(modifier = Modifier.padding(10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            AsyncImage(
                model = book.image.toFullImageUrl(),
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(White.copy(alpha = 0.05f))
            )

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(book.name, fontFamily = iransansFontFamily, fontWeight = FontWeight.Bold, color = White, fontSize = 16.sp)
                book.category?.name?.let { Text(it, color = White.copy(alpha = 0.8f), fontFamily = iransansFontFamily, fontSize = 12.sp) }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    if ((book.price ?: -1) == 0) {
                        AssistChip(onClick = {
                            val url = (book.file ?: "").toFullImageUrl()
                            if (url != null) {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                            }
                        }, label = { Text("رایگان • دانلود", fontFamily = iransansFontFamily) })
                    } else {
                        Text(
                            text = (book.price?.toString() ?: "-") + " ریال",
                            color = Yellow,
                            fontFamily = iransansFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


