package com.example.spaceexample.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        const val MAIN_URL = "https://run.mocky.io/"
        private fun retrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(MAIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }

        val apiSpaceService: SpaceService by lazy {
            retrofit().create(SpaceService::class.java)
        }
    }



}
