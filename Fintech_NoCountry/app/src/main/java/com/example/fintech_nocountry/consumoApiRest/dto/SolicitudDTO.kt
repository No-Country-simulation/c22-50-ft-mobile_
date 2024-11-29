package com.example.fintech_nocountry.consumoApiRest.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SolicitudDTO(
    val id: Int?,
    val descripcion: String?,
    val estado: EstadoSolicitud?,
    @Json(name = "fecha_envio") val fechaEnvio: String?,
    @Json(name = "crowdfunding_id") val crowdfundingId: Int?
): TablaDTO()

enum class EstadoSolicitud{
    Pendiente,
    Rechazada,
    Aprobada
}