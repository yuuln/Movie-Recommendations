package com.example.movierecommendations

import android.os.Bundle
import android.os.Build
import android.view.Menu
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movierecommendations.model.Movie
import android.content.Context
import com.example.movierecommendations.R
import android.view.MenuItem

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var movie: Movie
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)  // ← 按鈕在這
        supportActionBar?.title = "電影介紹"

        val receivedMovie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("movie", Movie::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("movie") as? Movie
        }

        if (receivedMovie == null) {
            Toast.makeText(this, "無法取得電影資料", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        movie = receivedMovie

        val imagePoster = findViewById<ImageView>(R.id.detailImage)
        val textTitle = findViewById<TextView>(R.id.detailTitle)
        val textCategory = findViewById<TextView>(R.id.detailCategory)
        val textDescription = findViewById<TextView>(R.id.detailDescription)

        imagePoster.setImageResource(movie.imageResId)
        imagePoster.contentDescription = "電影海報：${movie.title}"
        textTitle.text = movie.title
        textCategory.text = "分類：${movie.category}"
        textDescription.text = movie.description

        //從 SharedPreferences 讀取是否已收藏
        val prefs = getSharedPreferences("favorites", Context.MODE_PRIVATE)
        isFavorite = prefs.getBoolean(movie.title, false)
    }

    // 建立右上角愛心選單
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_movie_detail, menu)
        // 設定 icon 狀態
        val favItem = menu?.findItem(R.id.action_favorite)
        favItem?.icon = if (isFavorite)
            getDrawable(R.drawable.ic_favorite)
        else
            getDrawable(R.drawable.ic_favorite_border)
        return true
    }

    // 點擊愛心按鈕時儲存狀態 + 切換圖示
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                isFavorite = !isFavorite
                item.setIcon(
                    if (isFavorite) R.drawable.ic_favorite
                    else R.drawable.ic_favorite_border
                )
                val prefs = getSharedPreferences("favorites", Context.MODE_PRIVATE)
                prefs.edit().putBoolean(movie.title, isFavorite).apply()

                Toast.makeText(this,
                    if (isFavorite) "已加入收藏" else "已移除收藏",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }

            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}