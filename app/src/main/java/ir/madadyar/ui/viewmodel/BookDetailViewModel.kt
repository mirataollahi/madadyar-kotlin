package ir.madadyar.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ir.madadyar.data.model.Book
import ir.madadyar.data.repository.BooksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val booksRepository = BooksRepository()
    
    private val _book = MutableStateFlow<Book?>(null)
    val book: StateFlow<Book?> = _book.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    fun loadBook(bookId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = booksRepository.getBookById(bookId)
            result.fold(
                onSuccess = { book ->
                    _book.value = book
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _isLoading.value = false
                }
            )
        }
    }
}

