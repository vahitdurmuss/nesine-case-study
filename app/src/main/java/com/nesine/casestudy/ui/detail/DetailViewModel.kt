package com.nesine.casestudy.ui.detail

import androidx.lifecycle.ViewModel
import com.nesine.casestudy.core.data.PostModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : ViewModel() {

    val title= MutableStateFlow("")
    val body= MutableStateFlow("")
    lateinit var model: PostModel
}