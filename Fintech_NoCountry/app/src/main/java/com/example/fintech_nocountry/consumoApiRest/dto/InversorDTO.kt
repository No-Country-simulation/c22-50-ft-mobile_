package com.example.fintech_nocountry.consumoApiRest.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InversorDTO(
    @Json(name = "usuario_id")val usuarioId: Int,
    @Json(name = "fondos_disponibles") val fondosDisp: Double
): MensajeDTO()
