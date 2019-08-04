package com.ironclad.bingewatch.movie_modal

data class MovieResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: ArrayList<MoviesDetails>
)