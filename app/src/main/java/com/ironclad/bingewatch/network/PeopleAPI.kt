package com.ironclad.bingewatch.network

import com.ironclad.bingewatch.movie_modal.ImageResponse
import com.ironclad.bingewatch.movie_modal.PeopleCredits
import com.ironclad.bingewatch.movie_modal.PeopleDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PeopleAPI {

    @GET("/3/person/{person_id}?api_key=d5b568462e39f02e011bb612583ead1e&language=en-US")
    suspend fun getDetails(@Path("person_id") person_id: Int): Response<PeopleDetails>

    @GET("/3/person/{person_id}/combined_credits?api_key=d5b568462e39f02e011bb612583ead1e&language=en-US")
    suspend fun getCombinedCredits(@Path("person_id")person_id: Int):Response<PeopleCredits>

    @GET("/3/person/{person_id}/images?api_key=d5b568462e39f02e011bb612583ead1e")
    suspend fun getImages(@Path("person_id")person_id: Int):Response<ImageResponse>

}