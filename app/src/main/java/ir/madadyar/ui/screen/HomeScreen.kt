package ir.madadyar.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalDensity
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import ir.madadyar.data.model.Book
import ir.madadyar.data.model.BookCategory
import ir.madadyar.data.model.Video
import ir.madadyar.ui.theme.*
import ir.madadyar.ui.viewmodel.HomeViewModel
import ir.madadyar.data.api.ApiClient
import androidx.lifecycle.viewmodel.compose.viewModel
import ir.madadyar.ui.component.ErrorDialog
import ir.madadyar.ui.component.LoadingIndicator
import androidx.compose.foundation.background
import androidx.compose.ui.platform.LocalContext
import ir.madadyar.util.ErrorHandler
import ir.madadyar.util.toFullImageUrl
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onOpenDrawer: () -> Unit,
    onNavigateToBooksList: (Int?) -> Unit,
    onNavigateToVideosList: (Int?) -> Unit,
    onNavigateToBookDetail: (Book) -> Unit,
    onNavigateToVideoDetail: (Int) -> Unit
) {
    val context = LocalContext.current
    val bookCategories by viewModel.bookCategories.collectAsState()
    val books by viewModel.books.collectAsState()
    val videos by viewModel.videos.collectAsState()
    val selectedIndex by viewModel.selectedCategoryIndex.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }
    
    val pagerState = rememberPagerState(initialPage = 0)
    
    // Auto-play carousel
    LaunchedEffect(bookCategories.size, pagerState.currentPage) {
        if (bookCategories.isNotEmpty() && bookCategories.size > 1) {
            delay(6000) // 6 seconds like Flutter app
            val nextPage = (pagerState.currentPage + 1) % bookCategories.size
            if (nextPage != pagerState.currentPage) {
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }
    
    LaunchedEffect(pagerState.currentPage) {
        viewModel.setSelectedCategoryIndex(pagerState.currentPage)
    }
    
    // Show error dialog
    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            showErrorDialog = true
        }
    }
    
    // Check network before loading
    LaunchedEffect(Unit) {
        if (!ErrorHandler.isNetworkAvailable(context)) {
            showErrorDialog = true
        }
    }
    
    
    Scaffold(
        containerColor = DarkBackground
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading && bookCategories.isEmpty()) {
                LoadingIndicator()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                ) {
            // Carousel Header
            if (bookCategories.isNotEmpty()) {
                HorizontalPager(
                    count = bookCategories.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) { page ->
                    CategoryCarouselItem(
                        category = bookCategories[page],
                        onItemClick = {
                            if (bookCategories[page].type == 0) {
                                onNavigateToBooksList(bookCategories[page].id)
                            } else {
                                onNavigateToVideosList(bookCategories[page].id)
                            }
                        }
                    )
                }
                
                // Pagination Dots
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(bookCategories.size) { index ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 3.dp)
                                .size(10.dp)
                                .clip(RoundedCornerShape(50))
                                .background(if (index == selectedIndex) White else White.copy(alpha = 0.5f))
                        )
                    }
                }
            }
            
            // Books Section
            BooksHorizontalSection(
                books = books,
                onBookClick = onNavigateToBookDetail,
                onSeeAllClick = { onNavigateToBooksList(null) }
            )
            
            // Videos Section
            VideosGridSection(
                videos = videos.take(3),
                onVideoClick = onNavigateToVideoDetail,
                onSeeAllClick = { onNavigateToVideosList(null) }
            )
                }
            }
            
            // Error Dialog
            if (showErrorDialog && errorMessage != null) {
                ErrorDialog(
                    message = errorMessage!!,
                    onDismiss = {
                        showErrorDialog = false
                        viewModel.clearError()
                    },
                    onRetry = {
                        viewModel.loadData()
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryCarouselItem(
    category: BookCategory,
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onItemClick)
    ) {
        AsyncImage(
            model = category.image.toFullImageUrl(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Black.copy(alpha = 0.9f),
                            Transparent,
                            Transparent,
                            DarkBackground.copy(alpha = 0.9f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )
        
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = category.name,
                        fontFamily = iransansFontFamily,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        color = White
                    )
                    
                    Spacer(modifier = Modifier.height(5.dp))
                    
                    Surface(
                        color = White,
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "اطلاعات بیشتر",
                                fontFamily = iransansFontFamily,
                                color = Black,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                
                Text(
                    text = if (category.type == 0) "کتاب" else "فیلم",
                    fontFamily = iransansFontFamily,
                    color = White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun BooksHorizontalSection(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    onSeeAllClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "کتابهای جدید",
                fontFamily = iransansFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = White
            )
            
            Row(
                modifier = Modifier.clickable(onClick = onSeeAllClick),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "مشاهده همه",
                    fontFamily = iransansFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Yellow
                )
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Yellow,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(10.dp))
        
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
            items(books) { book ->
                BookCard(
                    book = book,
                    onClick = { onBookClick(book) }
                )
            }
        }
    }
}

@Composable
fun BookCard(
    book: Book,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(270.dp)
            .height(130.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Box {
            AsyncImage(
                model = book.image.toFullImageUrl(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(
                        androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(Transparent, Black.copy(alpha = 0.8f))
                        )
                    )
                    .padding(10.dp)
            ) {
                Text(
                    text = book.name,
                    fontFamily = iransansFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun VideosGridSection(
    videos: List<Video>,
    onVideoClick: (Int) -> Unit,
    onSeeAllClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ویدئو‌های جدید",
                fontFamily = iransansFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = White
            )
            
            Row(
                modifier = Modifier.clickable(onClick = onSeeAllClick),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "مشاهده همه",
                    fontFamily = iransansFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Yellow
                )
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Yellow,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(10.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            videos.forEach { video ->
                VideoCard(
                    video = video,
                    onClick = { onVideoClick(video.id) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun VideoCard(
    video: Video,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val screenWidth = androidx.compose.ui.platform.LocalDensity.current.density * 360
    val cardWidth = (screenWidth * 0.3).dp
    val cardHeight = (cardWidth * 1.67f)
    
    Card(
        modifier = modifier
            .width(cardWidth)
            .height(cardHeight)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Box {
            AsyncImage(
                model = video.image.toFullImageUrl(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(
                        androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(Transparent, Black.copy(alpha = 0.8f))
                        )
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = video.name,
                    fontFamily = iransansFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

