package com.example.movierecommendations

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.movierecommendations.model.Movie

class Database(context: Context) : SQLiteOpenHelper(context, "UserDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE users (
                Title TEXT,
                category TEXT,
                description TEXT,
                drawable TEXT
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    // 插入單筆電影資料
    fun insertMovie(movie: Movie): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("Title", movie.title)
            put("category", movie.category)
            put("description", movie.description)
            put("drawable", movie.drawable) // 存圖片資源ID (Int)
        }
        val result = db.insert("users", null, values)
        db.close()
        return result != -1L
    }

    // 插入所有電影資料（一次性寫入 movieList）
    fun insertAllMovies(movieList: List<Movie>) {
        val db = this.writableDatabase
        db.beginTransaction()
        try {
            for (movie in movieList) {
                val values = ContentValues().apply {
                    put("Title", movie.title)
                    put("category", movie.category)
                    put("description", movie.description)
                    put("drawable", movie.drawable)
                }
                db.insert("users", null, values)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    // 從資料庫讀出所有電影資料
    fun getAllMovies(): List<Movie> {
        val movieList = mutableListOf<Movie>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users", null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndexOrThrow("Title"))
                val category = cursor.getString(cursor.getColumnIndexOrThrow("category"))
                val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                val drawable = cursor.getInt(cursor.getColumnIndexOrThrow("drawable"))

                movieList.add(Movie(title, category, description, drawable))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return movieList
    }
}
