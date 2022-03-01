package com.example.spaceexample.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spaceexample.dao.FavoriteDao
import com.example.spaceexample.dao.SpaceDao
import com.example.spaceexample.fragments.FavoritesViewModel


class FavoriteViewModelFactory(private val favoriteDao: FavoriteDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoritesViewModel(favoriteDao) as T
    }
}