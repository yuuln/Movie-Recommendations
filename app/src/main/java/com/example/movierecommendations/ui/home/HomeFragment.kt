package com.example.movierecommendations.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movierecommendations.MovieDetailActivity
import com.example.movierecommendations.databinding.FragmentHomeBinding
import com.example.movierecommendations.model.AppDatabase
import com.example.movierecommendations.repository.MovieRepository
import com.example.movierecommendations.ui.adapters.MovieAdapter
import com.example.movierecommendations.viewmodel.MovieViewModel
import com.example.movierecommendations.viewmodel.MovieViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter { selectedMovie ->
            val intent = Intent(requireContext(), MovieDetailActivity::class.java)
            intent.putExtra("movie", selectedMovie)
            startActivity(intent)
        }

        binding.recyclerViewMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMovies.adapter = adapter

        val dao = AppDatabase.getDatabase(requireContext()).movieDao()
        val repository = MovieRepository(dao)
        val factory = MovieViewModelFactory(repository)
        movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        movieViewModel.allMovies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        movieViewModel.loadAllMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
