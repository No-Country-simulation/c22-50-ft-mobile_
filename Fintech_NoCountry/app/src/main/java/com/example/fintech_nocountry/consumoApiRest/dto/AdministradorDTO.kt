package com.example.fintech_nocountry.consumoApiRest.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AdministradorDTO(
    @Json(name = "usuario_id") val usuarioId: Int?,
    val permisos: String?
): TablaDTO()