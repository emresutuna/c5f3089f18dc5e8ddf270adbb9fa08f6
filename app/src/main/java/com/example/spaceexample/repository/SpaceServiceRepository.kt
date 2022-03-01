package com.example.spaceexample.repository

import com.example.spaceexample.api.RetrofitInstance
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Response

interface ISpaceServiceRepository {
    suspend fun getSpaceData(

    ): Response<JsonArray>
}

open class SpaceServiceRepository : ISpaceServiceRepository {
    override suspend fun getSpaceData(): Response<JsonArray> {
        return RetrofitInstance.apiSpaceService.getSpaceData()
    }

}