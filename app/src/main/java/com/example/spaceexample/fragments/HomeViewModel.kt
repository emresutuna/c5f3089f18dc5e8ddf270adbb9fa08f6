package com.example.spaceexample.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceexample.dao.SpaceDao
import com.example.spaceexample.repository.SpaceServiceRepository
import com.example.spaceexample.utils.ResponseHandler
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import androidx.lifecycle.LiveData
import com.example.spaceexample.models.ShipModel


class HomeViewModel(private var spaceDao: SpaceDao) : ViewModel() {
    var spaceResponse: MutableLiveData<ResponseHandler<JsonArray>> = MutableLiveData()
    private var spaceService: SpaceServiceRepository = SpaceServiceRepository()
    lateinit var shipData: LiveData<ShipModel>

    init {
        callSpaceData()
        getUserShip()
    }

    private fun getUserShip() {
        viewModelScope.launch {
            shipData = spaceDao.getShip()

        }
    }

    private fun callSpaceData() {
        viewModelScope.launch(Dispatchers.Main) {
            spaceResponse.postValue(ResponseHandler.Loading())
            val response = spaceService.getSpaceData()
            spaceResponse.postValue(handleRequest(response))
        }

    }

    private fun handleRequest(response: Response<JsonArray>): ResponseHandler<JsonArray> {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return ResponseHandler.Success(resultResponse)
            }
        }
        response.errorBody()?.let { resultResponse ->
            val gson = Gson()
            val type = object : TypeToken<JsonArray>() {}.type
            val errorResponse: JsonArray? =
                gson.fromJson(
                    resultResponse.charStream(),
                    type
                )
            return ResponseHandler.Error(errorResponse!!)
        }
        return ResponseHandler.Error(JsonArray())
    }
}