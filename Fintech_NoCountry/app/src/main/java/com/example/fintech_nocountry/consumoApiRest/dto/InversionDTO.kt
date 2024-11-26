package com.example.fintech_nocountry.consumoApiRest.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InversionDTO(
    val id: Int,
    val monto: Double,
    @Json(name = "fecha_inversion") val fechaInversion: String,
    @Json(name = "inversor_id") val inversorId: Int,
    @Json(name = "crowdfunding_id") val crowdfundingId: Int
): MensajeDTO()
