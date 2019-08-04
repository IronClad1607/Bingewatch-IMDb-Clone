package com.ironclad.bingewatch.movie_modal

data class CreditsResponse(
    val id:Int,
    val cast:ArrayList<Cast>,
    val crew:ArrayList<Crew>
)