package com.ironclad.bingewatch.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroClient {
    private fun retrofit() = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val authAPI = retrofit().create(AuthAPI::class.java)!!

    val movieAPI = retrofit().create(MovieAPI::class.java)!!

    val peopleAPI = retrofit().create(PeopleAPI::class.java)!!
}