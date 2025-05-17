package com.example.movierecommendations

import android.os.Bundle
import android.os.Build
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movierecommendations.model.Movie

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)  // ← 按鈕在這
        supportActionBar?.title = "電影介紹"

        val movie: Movie? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("movie", Movie::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("movie") as? Movie
        }
        if (movie != null) {
            val imagePoster = findViewById<ImageView>(R.id.detailImage)
            val textTitle = findViewById<TextView>(R.id.detailTitle)
            val textCategory = findViewById<TextView>(R.id.detailCategory)
            val textDescription = findViewById<TextView>(R.id.detailDescription)

            imagePoster.setImageResource(movie.imageResId)
            imagePoster.contentDescription = "電影海報：${movie.title}"
            textTitle.text = movie.title
            textCategory.text = "分類：${movie.category}"
            textDescription.text = movie.description
        } else {
            Toast.makeText(this, "無法取得電影資料", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}