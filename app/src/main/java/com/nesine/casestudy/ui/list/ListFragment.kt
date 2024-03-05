package com.nesine.casestudy.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.nesine.casestudy.R
import com.nesine.casestudy.common.UIResult
import com.nesine.casestudy.core.data.PostModel
import com.nesine.casestudy.core.data.PostRepository
import com.nesine.casestudy.databinding.FragmentListBinding
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
                            postsAdapter= PostsAdapter(it.data.toMutableList(),this@ListFragment)
                            val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
                            itemTouchHelper.attachToRecyclerView( binding.recylerviewPost)
                            binding.recylerviewPost.adapter=postsAdapter
                        }
                    }

                    is UIResult.Failure ->{
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                    }

                }

            }

        }
    }

    override fun onClick(item: PostModel) {

        parentFragmentManager.beginTransaction().run {
            replace(R.id.container_fragment, DetailFragment().apply {
                this.arguments= bundleOf().apply { putParcelable(DetailFragment.PARAMETER_NAME,item) }
            })
            addToBackStack(null)
            commit()
        }
    }

   private var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            Toast.makeText(requireContext(), resources.getString(R.string.deleted), Toast.LENGTH_SHORT).show()
            val position = viewHolder.adapterPosition
            val deletedOne=postsAdapter.removeItem(position)
            viewModel.removePost(deletedOne)
        }
    }

}