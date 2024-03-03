package com.nesine.casestudy.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nesine.casestudy.ui.common.UIResult
import com.nesine.casestudy.ui.core.data.PostModel
import com.nesine.casestudy.ui.core.data.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListViewModel private constructor(private val postRepository: PostRepository) : ViewModel() {


    class Factory(private val postRepository: PostRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ListViewModel(postRepository) as T
        }
    }


    private val _postsStateFlow = MutableStateFlow<UIResult<List<PostModel>?>>(
        UIResult.Success(
            emptyList()
        )
    )
    val postStateFlow: StateFlow<UIResult<List<PostModel>?>> = _postsStateFlow

    private val _progressVisibility = MutableLiveData(true)
    val progressVisibility: LiveData<Boolean> = _progressVisibility


    init {

        viewModelScope.launch {

            when (val result = postRepository.getPosts()) {
                is UIResult.Success -> {
                    _postsStateFlow.value = result

                }

                is UIResult.Failure -> {
                    _postsStateFlow.value = result
                }

                is UIResult.GeneralFailure -> {
                    _postsStateFlow.value = result
                }

            }

        }

    }

}