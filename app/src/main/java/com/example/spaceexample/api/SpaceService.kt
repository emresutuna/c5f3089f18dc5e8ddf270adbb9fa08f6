package com.example.spaceexample.api

import com.google.gson.JsonArray
import retrofit2.Response
import retrofit2.http.GET

interface SpaceService {
    @GET("/v3/e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
    suspend fun getSpaceData(): Response<JsonArray>
}