package com.ironclad.bingewatch.auth

data class AuthResponse(
    var success: Boolean,
    val expires_at: String,
    val request_token: String
)