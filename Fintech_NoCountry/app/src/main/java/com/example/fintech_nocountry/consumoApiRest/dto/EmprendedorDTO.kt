package com.example.fintech_nocountry.consumoApiRest.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmprendedorDTO(
    @Json(name="usuario_id") val usuarioId: Int?,
    val descripcion: String?
): TablaDTO()