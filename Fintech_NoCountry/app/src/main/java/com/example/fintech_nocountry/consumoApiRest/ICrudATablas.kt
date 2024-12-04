package com.example.fintech_nocountry.consumoApiRest

import com.example.fintech_nocountry.consumoApiRest.dto.TablaDTO
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.http.Query

interface ICrudATablas {

    @GET("{tabla}")
    suspend fun getEnTabla(@Path("tabla") tabla: String,
                   @Query("condicion") condicion: String,
                   @Query("columnas") columnas: String): List<TablaDTO>

    @POST("{tabla}")
    suspend fun postEnTabla(@Path("tabla") tabla: String,
                    @Body columnasYValores: Map<String, String>
    ): TablaDTO

    @PUT("{tabla}")
    suspend fun putEnTabla(@Path("tabla") tabla: String,
                   @Body settersYCondicion: Map<String, String>): TablaDTO

    @DELETE("{tabla}")
    suspend fun deleteEnTabla(@Path("tabla") tabla: String,
                      @Query("condicion") condicion: String): TablaDTO


}