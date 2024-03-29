package com.nesine.casestudy.ui.list

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nesine.casestudy.R
import com.nesine.casestudy.core.data.PostModel

class PostsAdapter constructor(private val dataSet: MutableList<PostModel>, val listener: PostItemClickListener) :
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    companion object{
        private const val change_key="{position}"
        private const val image_url= "https://picsum.photos/300/300?random=$change_key&grayscale"
    }


    interface PostItemClickListener{
        fun onClick(item: PostModel)
    }

    fun removeItem(position:Int) : PostModel{
        val model=dataSet[position]
        dataSet.removeAt(position)
        notifyItemRemoved(position)
        return model
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

        Glide.with(holder.view.context)
            .load(positionImageUrl)
            .placeholder(ColorDrawable(Color.GRAY))
            .error(ColorDrawable(Color.RED))
            .into(holder.iconimageView)

        dataSet[position].imageUrl=positionImageUrl

    }

}