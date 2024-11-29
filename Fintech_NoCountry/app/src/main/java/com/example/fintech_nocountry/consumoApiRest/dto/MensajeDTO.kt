package com.example.fintech_nocountry.consumoApiRest.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class MensajeDTO (
    val message: String?
): TablaDTO()