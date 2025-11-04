package ir.madadyar.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ir.madadyar.data.model.Video
import ir.madadyar.data.model.VideoCategory
import ir.madadyar.data.repository.VideosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VideosListViewModel(application: Application) : AndroidViewModel(application) {
    private val videosRepository = VideosRepository()
    
    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos.asStateFlow()
    
    private val _categories = MutableStateFlow<List<VideoCategory>>(emptyList())
    val categories: StateFlow<List<VideoCategory>> = _categories.asStateFlow()
    
    private val _selectedCategoryId = MutableStateFlow<Int?>(null)
    val selectedCategoryId: StateFlow<Int?> = _selectedCategoryId.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _searchQuery = MutableStateFlow<String?>(null)
    val searchQuery: StateFlow<String?> = _searchQuery.asStateFlow()
    
    fun loadVideos(categoryId: Int? = null, query: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            
            val result = when {
                query != null -> videosRepository.searchVideos(query)
                categoryId != null -> videosRepository.getVideosByCategory(categoryId)
                else -> videosRepository.getVideos()
            }
            
            result.fold(
                onSuccess = { videos ->
                    _videos.value = videos
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _isLoading.value = false
                }
            )
        }
    }
    
    fun loadCategories(categoryId: Int? = null) {
        viewModelScope.launch {
            val result = if (categoryId != null) {
                videosRepository.getVideoCategoriesByParent(categoryId)
            } else {
                videosRepository.getVideoCategories()
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
        loadVideos(categoryId, null)
        loadCategories(categoryId)
    }
    
    fun search(query: String) {
        _searchQuery.value = query
        _selectedCategoryId.value = null
        loadVideos(null, query)
    }
    
    fun resetToMain() {
        _selectedCategoryId.value = null
        _searchQuery.value = null
        loadVideos(null, null)
        loadCategories(null)
    }
}

