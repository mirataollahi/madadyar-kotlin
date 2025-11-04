package ir.madadyar.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ir.madadyar.data.model.Book
import ir.madadyar.data.model.BookCategory
import ir.madadyar.data.repository.BooksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BooksListViewModel(application: Application) : AndroidViewModel(application) {
    private val booksRepository = BooksRepository()
    
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()
    
    private val _categories = MutableStateFlow<List<BookCategory>>(emptyList())
    val categories: StateFlow<List<BookCategory>> = _categories.asStateFlow()
    
    private val _selectedCategoryId = MutableStateFlow<Int?>(null)
    val selectedCategoryId: StateFlow<Int?> = _selectedCategoryId.asStateFlow()
    
    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()
    
    private val _totalPages = MutableStateFlow(1)
    val totalPages: StateFlow<Int> = _totalPages.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()
    
    private val _searchQuery = MutableStateFlow<String?>(null)
    val searchQuery: StateFlow<String?> = _searchQuery.asStateFlow()
    
    fun loadBooks(categoryId: Int? = null, query: String? = null, reset: Boolean = true) {
        viewModelScope.launch {
            if (reset) {
                _isLoading.value = true
                _currentPage.value = 1
                _books.value = emptyList()
            } else {
                _isLoadingMore.value = true
            }
            
            val result = when {
                query != null -> booksRepository.searchBooks(query, _currentPage.value)
                categoryId != null -> booksRepository.getBooksByCategory(categoryId, _currentPage.value)
                else -> booksRepository.getBooks(_currentPage.value)
            }
            
            result.fold(
                onSuccess = { (books, totalPages) ->
                    if (reset) {
                        _books.value = books
                    } else {
                        _books.value = _books.value + books
                    }
                    _totalPages.value = totalPages
                    _isLoading.value = false
                    _isLoadingMore.value = false
                },
                onFailure = { exception ->
                    _isLoading.value = false
                    _isLoadingMore.value = false
                }
            )
        }
    }
    
    fun loadCategories(categoryId: Int? = null) {
        viewModelScope.launch {
            val result = if (categoryId != null) {
                booksRepository.getBookCategoriesByParent(categoryId)
            } else {
                booksRepository.getBookCategories()
            }
            
            result.fold(
                onSuccess = { categories ->
                    _categories.value = categories
                },
                onFailure = { }
            )
        }
    }
    
    fun selectCategory(categoryId: Int) {
        _selectedCategoryId.value = categoryId
        loadBooks(categoryId, null, true)
        loadCategories(categoryId)
    }
    
    fun search(query: String) {
        _searchQuery.value = query
        _selectedCategoryId.value = null
        loadBooks(null, query, true)
    }
    
    fun loadMore() {
        if (_currentPage.value < _totalPages.value && !_isLoadingMore.value) {
            _currentPage.value = _currentPage.value + 1
            val categoryId = _selectedCategoryId.value
            val query = _searchQuery.value
            loadBooks(categoryId, query, false)
        }
    }
    
    fun resetToMain() {
        _selectedCategoryId.value = null
        _searchQuery.value = null
        loadBooks(null, null, true)
        loadCategories(null)
    }
}

