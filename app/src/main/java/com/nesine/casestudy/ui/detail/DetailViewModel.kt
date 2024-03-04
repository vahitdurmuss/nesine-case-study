package com.nesine.casestudy.ui.detail

import androidx.lifecycle.ViewModel
import com.nesine.casestudy.ui.core.data.PostModel
import kotlinx.coroutines.flow.MutableStateFlow

class DetailViewModel : ViewModel() {

    val title= MutableStateFlow("")
    val body= MutableStateFlow("")
    lateinit var model: PostModel
}