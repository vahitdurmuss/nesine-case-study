package com.nesine.casestudy.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nesine.casestudy.databinding.FragmentListBinding
import com.nesine.casestudy.ui.common.UIResult
import com.nesine.casestudy.ui.core.data.PostModel
import com.nesine.casestudy.ui.core.data.PostRepository
import kotlinx.coroutines.launch

class ListFragment : Fragment(), PostsAdapter.PostItemClickListener {

    private val viewModel: ListViewModel by viewModels { ListViewModel.Factory(PostRepository()) }
    private lateinit var binding: FragmentListBinding
    lateinit var  postsAdapter: PostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm=viewModel
        observeStates()
    }

    private fun observeStates() {

        lifecycleScope.launch {

            viewModel.postStateFlow.collect {

                when(it){

                    is UIResult.Success-> {
                        it.data?.run {
                            postsAdapter= PostsAdapter(it.data,this@ListFragment)
                            binding.recylerviewPost.adapter=postsAdapter
                        }
                    }

                    is UIResult.Failure->{

                    }

                    is UIResult.GeneralFailure->{

                    }

                }

            }

        }
    }

    override fun onClick(item: PostModel) {
        //TODO("navigate to detail with postmodel")
    }

}