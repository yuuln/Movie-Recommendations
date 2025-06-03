package com.example.movierecommendations.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movierecommendations.R
import com.example.movierecommendations.model.MovieEntity

class MovieAdapter(
    private val onItemClick: (MovieEntity) -> Unit
) : ListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(DiffCallback) {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imagePoster)
        val title: TextView = itemView.findViewById(R.id.textTitle)
        val category: TextView = itemView.findViewById(R.id.textCategory)
        val desc: TextView = itemView.findViewById(R.id.textDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.title.text = movie.title
        holder.category.text = movie.category
        holder.desc.text = movie.description

        // 顯示圖片
        val context = holder.itemView.context
        val imageResId = context.resources.getIdentifier(
            movie.imageUrl, "drawable", context.packageName
        )
        holder.image.setImageResource(imageResId)

        // 點擊事件
        holder.itemView.setOnClickListener {
            onItemClick(movie)
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
