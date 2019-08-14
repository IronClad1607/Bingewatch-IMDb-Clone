package com.ironclad.bingewatch.movie_modal

data class PeopleDetails(
    val birthday: String,
    val known_for_department: String,
    val id: Int,
    val name: String,
    val also_known_as: ArrayList<String>,
    val biography: String,
    val popularity: Double,
    val place_of_birth: String,
    val profile_path: String
)