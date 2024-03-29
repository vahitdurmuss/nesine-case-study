package com.nesine.casestudy.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nesine.casestudy.common.UIResult
import com.nesine.casestudy.core.data.PostModel
import com.nesine.casestudy.core.data.PostRepository
import com.nesine.casestudy.core.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {


    private val _postsStateFlow = MutableStateFlow<UIResult<MutableList<PostModel>>>(UIResult.Success(
        mutableListOf()
    ))


    val postStateFlow: StateFlow<UIResult<List<PostModel>>> = _postsStateFlow

    val removePost: (post: PostModel)-> Unit ={
        (_postsStateFlow.value as? UIResult.Success)?.run {
           this.data.remove(it)
        }
    }

    val changeWithEditedPost: (editedPost: PostModel)-> Unit ={
        (_postsStateFlow.value as? UIResult.Success)?.run {
            val post=this.data.find { post->it.id==post.id }
            val index= this.data.indexOf(post)
            this.data.removeAt(index)
            this.data.add(index,it)
        }
    }

    private val _progressVisibility=MutableLiveData(false)
    val progressVisibility: LiveData<Boolean> = _progressVisibility


    private val _errorVisibility = MutableLiveData(false)
    val errorVisibility: LiveData<Boolean> = _errorVisibility


    init {

        viewModelScope.launch {

            _progressVisibility.value = true

            when (val result = postRepository.getPosts()) {
                is NetworkResult.Success -> {
                    _postsStateFlow.value = UIResult.Success(result.data?.toMutableList()?: mutableListOf())

                }

                is NetworkResult.Failure -> {
                    _postsStateFlow.value = UIResult.Failure(result.error.message?:"Sunucu hatası oluştu!")
                    _errorVisibility.value=true
                }

                is NetworkResult.GeneralFailure -> {
                    _postsStateFlow.value = UIResult.Failure(result.e.message?:"Genel bir hata oluştu!")
                    _errorVisibility.value=true
                }

            }

            _progressVisibility.value = false

        }

    }

}