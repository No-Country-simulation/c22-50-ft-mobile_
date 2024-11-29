package com.example.fintech_nocountry.consumoApiRest.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EtiquetaDTO(
    val descripcion: String?,
    @Json(name = "crowdfunding_id") val crowdfundingId: Int?
): TablaDTO()
