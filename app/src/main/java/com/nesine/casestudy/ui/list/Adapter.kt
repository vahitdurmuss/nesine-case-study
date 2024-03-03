package com.nesine.casestudy.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nesine.casestudy.R
import com.nesine.casestudy.ui.core.data.PostModel

class PostsAdapter(var dataSet: List<PostModel>, val listener: PostItemClickListener) :
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    companion object{
        private const val change_key="{position}"
        private const val image_url= "https://picsum.photos/300/300?random=$change_key&grayscale"
    }


    interface PostItemClickListener{
        fun onClick(item: PostModel)
    }

    class PostViewHolder(val view: View): RecyclerView.ViewHolder(view){

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
            descriptionTextView.text=postItem.body
        }

        loadImageIntoImageView(holder,position)

        holder.view.setOnClickListener {
            listener.onClick(postItem)
        }
    }

    private fun loadImageIntoImageView(holder: PostViewHolder, position: Int){
        val positionImageUrl = image_url.replace(change_key, position.toString(), false)
        Glide.with(holder.view.context).load(positionImageUrl).into(holder.iconimageView)
        dataSet[position].imageUrl=positionImageUrl
        //.placeholder(R.drawable.placeholder) // Optional placeholder image while loading
        //.error(R.drawable.error) // Optional error image if the load fails
    }

}