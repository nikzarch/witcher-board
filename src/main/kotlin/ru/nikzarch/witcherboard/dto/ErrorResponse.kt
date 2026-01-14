package ru.nikzarch.witcherboard.dto

import java.time.Instant

data class ErrorResponse(
    val message: String,
    val date: Instant
)