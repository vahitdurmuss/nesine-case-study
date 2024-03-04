package com.nesine.casestudy.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.nesine.casestudy.R
import com.nesine.casestudy.databinding.FragmentListBinding
import com.nesine.casestudy.common.UIResult
import com.nesine.casestudy.core.data.PostModel
import com.nesine.casestudy.core.data.PostRepository
import com.nesine.casestudy.ui.detail.DetailFragment
import kotlinx.coroutines.launch

class ListFragment : Fragment(), PostsAdapter.PostItemClickListener {

    private val viewModel: ListViewModel by activityViewModels { ListViewModel.Factory(
        PostRepository()
    ) }
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
        binding.lifecycleOwner=this@ListFragment
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

        parentFragmentManager.beginTransaction().run {
            replace(R.id.container_fragment, DetailFragment().apply {
                this.arguments= bundleOf().apply { putParcelable("post",item) }
            })
            addToBackStack(null)
            commit()
        }
    }

}