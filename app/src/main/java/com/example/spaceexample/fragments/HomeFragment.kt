package com.example.spaceexample.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spaceexample.adapters.ShipAdapter
import com.example.spaceexample.dao.FavoriteDao
import com.example.spaceexample.dao.SpaceDao
import com.example.spaceexample.databinding.HomeFragmentBinding
import com.example.spaceexample.models.Ships
import com.example.spaceexample.utils.HomeViewModelFactory
import com.example.spaceexample.utils.ResponseHandler
import com.example.spaceexample.utils.SpaceDb
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var spaceDao: SpaceDao
    private lateinit var favoriteDao: FavoriteDao
    private lateinit var v: View
    private val gson = Gson()
    private lateinit var shipsAdapter: ShipAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        v = binding.root

        spaceDao = SpaceDb.getAppDatabase((requireActivity()))?.spaceDao()!!
        favoriteDao = SpaceDb.getAppDatabase((requireActivity()))?.favoriteDao()!!
        viewModel =
            ViewModelProvider(this, HomeViewModelFactory(spaceDao))[HomeViewModel::class.java]
        viewModel.spaceResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is ResponseHandler.Success -> {
                    val groupListType: Type = object : TypeToken<ArrayList<Ships?>?>() {}.type
                    val spaceData: ArrayList<Ships> =
                        gson.fromJson<ArrayList<Ships>>(response.data, groupListType)
                    if (!spaceData.isNullOrEmpty()) {
                        setUpViews()
                        shipListAddData(spaceData)
                    }


                }
                is ResponseHandler.Loading -> {

                }
                is ResponseHandler.Error -> {

                }
            }

        })
        viewModel.shipData.observe(viewLifecycleOwner, Observer { ship ->
            if (ship != null) {
                binding.shipName.text = ship.name
                binding.ugsText.text = "UGS:" + setUGS(ship.capacity!!)
                binding.eusText.text = "UGS:" + setEUS(ship.speed!!)
                binding.dsText.text = "UGS:" + setDs(ship.strength!!)
            }

        })
        return v
    }

    private fun setUGS(capacity: Int): String {
        return (capacity * 10000).toString()
    }

    private fun setEUS(speed: Int): String {
        return (speed * 20).toString()
    }

    private fun setDs(strength: Int): String {
        return (strength * 10000).toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, HomeViewModelFactory(spaceDao))[HomeViewModel::class.java]
    }

    private fun setUpViews() {
        binding.searchView.setOnQueryTextListener(this)
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        shipsAdapter = ShipAdapter(favoriteDao)
        binding.recycler.adapter = shipsAdapter

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        shipsAdapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        shipsAdapter.filter.filter(newText)
        return false
    }

    private fun shipListAddData(shipList: ArrayList<Ships>) {
        shipsAdapter.addData(shipList)
        shipsAdapter.notifyDataSetChanged()

    }

}