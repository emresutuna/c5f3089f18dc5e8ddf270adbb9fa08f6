package com.example.spaceexample.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceexample.dao.FavoriteDao
import com.example.spaceexample.models.FavoriteShip
import kotlinx.coroutines.launch

class FavoritesViewModel(private val favoriteDao: FavoriteDao) : ViewModel() {
    lateinit var shipData: LiveData<List<FavoriteShip>>

    init {
        getUserShip()
    }

    private fun getUserShip() {
        viewModelScope.launch {
            shipData = favoriteDao.getAll()

        }
    }
}