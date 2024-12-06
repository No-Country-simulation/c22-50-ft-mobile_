package com.example.fintech_nocountry.consumoApiRest.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Parcelize
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
    @Json(name = "emprendedor_id") val emprendedorId: Int?,
    @Json(name = "url_imagen") val imagenUrl: String?
): TablaDTO(), Parcelable
{
    fun calcularDiasRestantes(): Long {
        if (fechaCreacion != null && fechaFinalizacion != null) {
            return LocalDate.parse(fechaCreacion)
                .until(
                    LocalDate.parse(fechaFinalizacion),
                    ChronoUnit.DAYS
                )
        }
        return -1
    }

    fun calcularPorcentajeActual(): Int{
        if(montoRecaudado != null && montoObjetivo != null){
            return ((montoRecaudado / montoObjetivo) * 100).toInt()
        }
        return 0
    }
}

enum class EstadoCrowd {
    Pendiente,
    Aprobado,
    Finalizado
}
