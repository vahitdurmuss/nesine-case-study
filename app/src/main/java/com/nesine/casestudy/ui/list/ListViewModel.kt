package com.nesine.casestudy.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nesine.casestudy.ui.common.UIResult
import com.nesine.casestudy.ui.core.data.PostModel
import com.nesine.casestudy.ui.core.data.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListViewModel(postRepository: PostRepository) : ViewModel() {

    private val _postsStateFlow = MutableStateFlow<UIResult<List<PostModel>?>>(
        UIResult.Success(
            emptyList()
        )
    )
    val postStateFlow: StateFlow<UIResult<List<PostModel>?>> = _postsStateFlow

    private val _uiStatusStateFlow = MutableStateFlow<UIStatus>(UIStatus.Loading)
    val uiStatusStateFlow: StateFlow<UIStatus> = _uiStatusStateFlow

    sealed class UIStatus {
        object Loading : UIStatus()
        object Done : UIStatus()
    }

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