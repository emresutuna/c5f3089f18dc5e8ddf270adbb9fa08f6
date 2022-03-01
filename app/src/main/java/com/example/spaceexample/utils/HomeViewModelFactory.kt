package com.example.spaceexample.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spaceexample.dao.SpaceDao
import com.example.spaceexample.fragments.HomeViewModel


class HomeViewModelFactory(private val spaceDao: SpaceDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(spaceDao) as T
    }
}