package com.seoin.example.webclient.retrofit.dto

data class GetSupportedStatesResponse(
    val status: String,
    val data: List<State>,
)

data class State(
    val state: String,
)
