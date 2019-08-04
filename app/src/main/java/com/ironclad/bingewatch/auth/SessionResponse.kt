package com.ironclad.bingewatch.auth

data class SessionResponse(
    var success: Boolean,
    val session_id: String
)