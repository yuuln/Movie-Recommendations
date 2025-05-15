package com.example.movierecommendations.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movierecommendations.R
import com.example.movierecommendations.ui.adapters.MovieAdapter
import com.example.movierecommendations.model.Movie

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private lateinit var movieList: List<Movie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewMovies)
        recyclerView.layoutManager = LinearLayoutManager(context)

        movieList = listOf(
            Movie("ㄟ晚餐要吃啥", "我也不知道","我現在肚子扁扁因為我好餓2222222222222",R.drawable.crycry) //依照格式打
        )

        adapter = MovieAdapter(movieList)
        recyclerView.adapter = adapter

        return view
    }
}