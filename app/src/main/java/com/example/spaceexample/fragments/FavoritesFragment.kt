package com.example.spaceexample.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spaceexample.R
import com.example.spaceexample.adapters.FavoriteShipAdapter
import com.example.spaceexample.adapters.ShipAdapter
import com.example.spaceexample.dao.FavoriteDao
import com.example.spaceexample.databinding.FavoritesFragmentBinding
import com.example.spaceexample.models.FavoriteShip
import com.example.spaceexample.utils.FavoriteViewModelFactory
import com.example.spaceexample.utils.HomeViewModelFactory
import com.example.spaceexample.utils.SpaceDb

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var binding: FavoritesFragmentBinding
    private lateinit var favoriteDao: FavoriteDao
    private lateinit var favoriteShipAdapter: FavoriteShipAdapter

    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavoritesFragmentBinding.inflate(inflater, container, false)
        v = binding.root
        favoriteDao = SpaceDb.getAppDatabase((requireActivity()))?.favoriteDao()!!
        viewModel =
            ViewModelProvider(
                this,
                FavoriteViewModelFactory(favoriteDao)
            )[FavoritesViewModel::class.java]
        viewModel.shipData.observe(viewLifecycleOwner, Observer { ship ->
            if (ship != null) {
                setList(ship, favoriteDao)
            }

        })
        return v
    }

    private fun setList(list: List<FavoriteShip>, favoriteDao: FavoriteDao) {
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        favoriteShipAdapter = FavoriteShipAdapter(list, favoriteDao)
        binding.recycler.adapter = favoriteShipAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(
                this,
                FavoriteViewModelFactory(favoriteDao)
            )[FavoritesViewModel::class.java]
    }


}