package com.ironclad.bingewatch.movie_modal

data class PeopleCredits(
    val id: Int,
    val cast: ArrayList<CastPeople>,
    val crew: ArrayList<CrewPeople>
)