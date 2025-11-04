package ir.madadyar.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ir.madadyar.data.model.Book
import ir.madadyar.data.model.BookCategory
import ir.madadyar.data.model.Video
import ir.madadyar.data.repository.BooksRepository
import ir.madadyar.data.repository.VideosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val booksRepository = BooksRepository()
    private val videosRepository = VideosRepository()
    
    private val _bookCategories = MutableStateFlow<List<BookCategory>>(emptyList())
    val bookCategories: StateFlow<List<BookCategory>> = _bookCategories.asStateFlow()
    
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()
    
    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos.asStateFlow()
    
    private val _selectedCategoryIndex = MutableStateFlow(0)
    val selectedCategoryIndex: StateFlow<Int> = _selectedCategoryIndex.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    init {
        loadData()
    }
    
    fun loadData() {
        loadBookCategories()
        loadBooks()
        loadVideos()
    }
    
    private fun loadBookCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = booksRepository.getMainBookCategories()
            result.fold(
                onSuccess = { categories ->
                    _bookCategories.value = categories
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _errorMessage.value = exception.message
                    _isLoading.value = false
                }
            )
        }
    }
    
    private fun loadBooks() {
        viewModelScope.launch {
            val result = booksRepository.getBooks(1)
            result.fold(
                onSuccess = { (books, _) ->
                    _books.value = books
                },
                onFailure = { exception ->
                    _errorMessage.value = exception.message
                }
            )
        }
    }
    
    private fun loadVideos() {
        viewModelScope.launch {
            val result = videosRepository.getVideos()
            result.fold(
                onSuccess = { videos ->
                    _videos.value = videos
                },
                onFailure = { exception ->
                    _errorMessage.value = exception.message
                }
            )
        }
    }
    
    fun setSelectedCategoryIndex(index: Int) {
        _selectedCategoryIndex.value = index
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}

