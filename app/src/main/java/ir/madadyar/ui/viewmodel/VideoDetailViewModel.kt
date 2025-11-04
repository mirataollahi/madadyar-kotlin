package ir.madadyar.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ir.madadyar.data.model.Video
import ir.madadyar.data.repository.VideosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VideoDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val videosRepository = VideosRepository()
    
    private val _video = MutableStateFlow<Video?>(null)
    val video: StateFlow<Video?> = _video.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    fun loadVideo(videoId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = videosRepository.getVideoById(videoId)
            result.fold(
                onSuccess = { video ->
                    _video.value = video
                    _isLoading.value = false
                },
                onFailure = { exception ->
                    _isLoading.value = false
                }
            )
        }
    }
}

