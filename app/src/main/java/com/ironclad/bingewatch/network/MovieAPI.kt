package com.ironclad.bingewatch.network

import com.ironclad.bingewatch.movie_modal.CreditsResponse
import com.ironclad.bingewatch.movie_modal.MovieAllDetails
import com.ironclad.bingewatch.movie_modal.MovieResponse
import com.ironclad.bingewatch.movie_modal.Reviews
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    @GET("/3/movie/popular?api_key=d5b568462e39f02e011bb612583ead1e&language=en-US")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<MovieResponse>

    @GET("/3/movie/now_playing?api_key=d5b568462e39f02e011bb612583ead1e&language=en-US")
    suspend fun getInCinema(@Query("page") page: Int): Response<MovieResponse>

    @GET("/3/movie/upcoming?api_key=d5b568462e39f02e011bb612583ead1e&language=en-US")
    suspend fun getUpcoming(@Query("page") page: Int): Response<MovieResponse>

    @GET("/3/movie/top_rated?api_key=d5b568462e39f02e011bb612583ead1e&language=en-US")
    suspend fun getTopRated(@Query("page") page: Int): Response<MovieResponse>

    @GET("/3/movie/{movie_id}?api_key=d5b568462e39f02e011bb612583ead1e&language=en-US")
    suspend fun getMovieDetails(@Path("movie_id") movie_id: Int): Response<MovieAllDetails>

    @GET("/3/movie/{movie_id}/credits?api_key=d5b568462e39f02e011bb612583ead1e")
    suspend fun getCreditDetails(@Path("movie_id") movie_id: Int): Response<CreditsResponse>

    @GET("/3/movie/{movie_id}/reviews?api_key=d5b568462e39f02e011bb612583ead1e&language=en-US")
    suspend fun getReviews(@Path("movie_id") movie_id: Int, @Query("page") page: Int): Response<Reviews>

    @GET("/3/movie/{movie_id}/similar?api_key=d5b568462e39f02e011bb612583ead1e&language=en-US")
    suspend fun getSimilar(@Path("movie_id") movie_id: Int, @Query("page") page: Int): Response<MovieResponse>


}