package com.example.movierecommendations.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movierecommendations.MovieDetailActivity
import com.example.movierecommendations.databinding.FragmentSearchBinding
import com.example.movierecommendations.ui.adapters.MovieAdapter
import  androidx.core.widget.addTextChangedListener
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MovieAdapter
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        adapter = MovieAdapter { movie ->
            val intent = Intent(requireContext(), MovieDetailActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }

        binding.recyclerSearchResults.adapter = adapter
        binding.recyclerSearchResults.layoutManager = LinearLayoutManager(requireContext())

        binding.editSearch.addTextChangedListener {
            val query = it.toString()
            searchViewModel.updateQuery(query)
        }

        searchViewModel.filteredMovies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
            binding.textNoResults.visibility = if (movies.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}