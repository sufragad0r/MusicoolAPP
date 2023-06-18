package com.musicoolapp.musicool.red

data class SolicitarOTP(
    val solicitarOTP: String,
    val info: String
)

data class Token(
    val access_token: String,
    val token_type: String,
    val rol: String
)