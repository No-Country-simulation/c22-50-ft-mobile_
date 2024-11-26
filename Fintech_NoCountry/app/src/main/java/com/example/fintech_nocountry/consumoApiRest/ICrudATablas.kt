package com.example.fintech_nocountry.consumoApiRest

import com.example.fintech_nocountry.consumoApiRest.dto.MensajeDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.http.Query

interface ICrudATablas {

    @GET("{tabla}")
    fun getEnTabla(@Path("tabla") tabla: String,
                   @Query("condicion") condicion: String,
                   @Query("columnas") columnas: String): Call<List<MensajeDTO>>

    @POST("{tabla}")
    fun postEnTabla(@Path("tabla") tabla: String): Call<MensajeDTO>

    @PUT("{tabla}")
    fun putEnTabla(@Path("tabla") tabla: String): Call<MensajeDTO>

    @DELETE("{tabla}")
    fun deleteEnTabla(@Path("tabla") tabla: String,
                      @Query("condicion") condicion: String): Call<MensajeDTO>


}