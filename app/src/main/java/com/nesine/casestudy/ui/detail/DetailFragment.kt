package com.nesine.casestudy.ui.detail

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.nesine.casestudy.databinding.FragmentDetailBinding
import com.nesine.casestudy.ui.core.data.PostModel
import com.nesine.casestudy.ui.list.ListViewModel

class DetailFragment : Fragment() {

    private val listViewModel: ListViewModel by activityViewModels()
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm=viewModel

        val model=arguments?.getParcelable<PostModel>("post")
        model?.run {
            viewModel.model=model
        }


        viewModel.model.let {
            viewModel.title.value= it.title.toString()
            viewModel.body.value=it.body.toString()
            Glide.with(requireContext()).load(it.imageUrl).into(binding.imageIcon)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

    }

}