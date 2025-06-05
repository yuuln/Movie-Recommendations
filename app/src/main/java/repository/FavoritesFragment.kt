package com.example.movierecommendations.ui.fragments
import android.content.Context
import android.content.Intent
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
import com.example.movierecommendations.viewmodel.MovieViewModel
import com.example.movierecommendations.model.AppDatabase
import com.example.movierecommendations.repository.MovieRepository
import androidx.lifecycle.ViewModelProvider
import com.example.movierecommendations.MovieDetailActivity
import com.example.movierecommendations.viewmodel.MovieViewModelFactory

class FavoritesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerFavorites)
        val textEmpty = view.findViewById<TextView>(R.id.textEmpty)
        adapter = MovieAdapter { selectedMovie ->
            val intent = Intent(requireContext(), MovieDetailActivity::class.java)
            intent.putExtra("movie", selectedMovie)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dao = AppDatabase.getDatabase(requireContext()).movieDao()
        val repository = MovieRepository(dao)
        movieViewModel = ViewModelProvider(this, MovieViewModelFactory(repository))[MovieViewModel::class.java]
        val prefs = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val favoriteTitles = prefs.all.filter { it.value == true }.map { it.key }

        movieViewModel.loadFavoriteMovies(favoriteTitles)

        movieViewModel.allMovies.observe(viewLifecycleOwner) { movies ->
            if (movies.isNullOrEmpty()) {
                recyclerView.visibility = View.GONE
                textEmpty.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                textEmpty.visibility = View.GONE
                adapter.submitList(movies)
            }
        }
    }
}

