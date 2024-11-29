package com.example.fintech_nocountry.consumoApiRest.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CrowdfundingDTO(
    val id: Int?,
    val titulo: String?,
    val descripcion: String?,
    @Json(name = "monto_recaudado") val montoRecaudado: Double?,
    @Json(name = "monto_objetivo") val montoObjetivo: Double?,
    val estado: EstadoCrowd?,
    @Json(name = "fecha_creacion") val fechaCreacion: String?,
    @Json(name = "fecha_finalizacion") val fechaFinalizacion: String?,
    @Json(name = "emprendedor_id") val emprendedorId: Int?
): TablaDTO()

enum class EstadoCrowd {
    Pendiente,
    Aprobado,
    Finalizado
}
