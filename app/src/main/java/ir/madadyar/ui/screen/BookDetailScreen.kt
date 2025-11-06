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
import ir.madadyar.ui.viewmodel.BookDetailViewModel
import ir.madadyar.util.toFullImageUrl
import androidx.compose.ui.viewinterop.AndroidView
import android.text.method.LinkMovementMethod
import android.widget.TextView
import android.text.Html

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    bookId: Int,
    viewModel: BookDetailViewModel = viewModel(),
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val book by viewModel.book.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(bookId) {
        viewModel.loadBook(bookId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = book?.name ?: "جزئیات کتاب", color = White, fontFamily = iransansFontFamily) },
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
        } else if (book != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    model = book!!.image.toFullImageUrl(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )

                Text(text = book!!.name, fontFamily = iransansFontFamily, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = White)

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
                        it.text = Html.fromHtml(book!!.description, Html.FROM_HTML_MODE_LEGACY)
                    }
                )

                book!!.price?.let { price ->
                    Text(text = "قیمت: $price ریال", color = White, fontFamily = iransansFontFamily)
                }

                book!!.category?.name?.let { catName ->
                    Text(text = "دسته‌بندی: $catName", color = White, fontFamily = iransansFontFamily)
                }

                book!!.file?.let { filePath ->
                    Button(
                        onClick = {
                            val url = filePath.toFullImageUrl()
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Yellow)
                    ) {
                        Text(text = "دانلود فایل", color = Black, fontFamily = iransansFontFamily)
                    }
                }
            }
        }
    }
}


