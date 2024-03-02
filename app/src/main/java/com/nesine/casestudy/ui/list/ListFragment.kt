package com.nesine.casestudy.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.nesine.casestudy.R
import com.nesine.casestudy.databinding.FragmentListBinding
import com.nesine.casestudy.ui.common.UIResult
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: FragmentListBinding

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
    }

    private fun observeStates() {

        lifecycleScope.launch {

            viewModel.postStateFlow.collect {

                when(it){

                    is UIResult.Success-> {

                    }

                    is UIResult.Failure->{

                    }

                    is UIResult.GeneralFailure->{

                    }

                }

            }

        }

        lifecycleScope.launch {

            viewModel.uiStatusStateFlow.collect {
               when(it){
                   ListViewModel.UIStatus.Loading->{

                   }
                   ListViewModel.UIStatus.Done->{

                   }
               }
            }

        }
    }

}