package com.nesine.casestudy.ui.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.nesine.casestudy.databinding.FragmentDetailBinding
import com.nesine.casestudy.core.data.PostModel
import com.nesine.casestudy.ui.list.ListViewModel

class DetailFragment : Fragment() , OnBackPressedListener{

    private val listViewModel: ListViewModel by activityViewModels()
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailBinding

    companion object{
        const val PARAMETER_NAME="post"
    }

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
        binding.lifecycleOwner = this

        val model=arguments?.getParcelable<PostModel>(PARAMETER_NAME)

        model?.run {
            viewModel.model=model
        }

        viewModel.model.let {

            viewModel.title.value= it.title.toString()
            viewModel.body.value=it.body.toString()

            Glide.with(requireContext())
                .load(it.imageUrl)
                .placeholder(ColorDrawable(Color.GRAY))
                .error(ColorDrawable(Color.RED))
                .into(binding.imageIcon)

        }

    }

    override fun onBackPressed() {

        if (isPostEditted()) {
            val edittedPost = PostModel(
                viewModel.model.id,
                viewModel.model.imageUrl,
                viewModel.title.value,
                viewModel.body.value
            )
            listViewModel.changeWithEditedPost(edittedPost)
        }

    }

    private val isPostEditted: () -> Boolean = {
        ((viewModel.model.body != viewModel.body.value) || (viewModel.model.title != viewModel.title.value))
    }

}