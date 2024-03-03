package com.nesine.casestudy.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nesine.casestudy.R
import com.nesine.casestudy.ui.core.data.PostModel

class PostsAdapter(val dataSet: List<PostModel>,val listener: PostItemClickListener) :
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {


    interface PostItemClickListener{
        fun onClick(item: PostModel)
    }

    inner class PostViewHolder(val view: View): RecyclerView.ViewHolder(view){

        var iconimageView:ImageView
        var titleTextView:TextView
        var descriptionTextView:TextView

        init {
            iconimageView=view.findViewById(R.id.image_icon)
            titleTextView=view.findViewById(R.id.txt_title)
            descriptionTextView=view.findViewById(R.id.txt_description)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_post, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postItem= dataSet[position]

        with(holder){
            titleTextView.text=postItem.title
            descriptionTextView.text=postItem.description
        }

        holder.view.setOnClickListener {
            listener.onClick(postItem)
        }
    }

}