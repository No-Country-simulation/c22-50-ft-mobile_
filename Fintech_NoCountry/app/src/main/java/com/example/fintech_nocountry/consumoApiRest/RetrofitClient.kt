package com.example.fintech_nocountry.consumoApiRest

import com.example.fintech_nocountry.consumoApiRest.dto.AdministradorDTO
import com.example.fintech_nocountry.consumoApiRest.dto.CrowdfundingDTO
import com.example.fintech_nocountry.consumoApiRest.dto.EmprendedorDTO
import com.example.fintech_nocountry.consumoApiRest.dto.EtiquetaDTO
import com.example.fintech_nocountry.consumoApiRest.dto.InversionDTO
import com.example.fintech_nocountry.consumoApiRest.dto.InversorDTO
import com.example.fintech_nocountry.consumoApiRest.dto.MensajeDTO
import com.example.fintech_nocountry.consumoApiRest.dto.SolicitudDTO
import com.example.fintech_nocountry.consumoApiRest.dto.UsuarioDTO
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    /*Reemplazar esta url por la ip de la máquina que hostea el servidor.
    No olvidarse de agregar el /fintech_nocountry al final ni del
    http:// al inicio*/
    private const val URL = "http://192.168.1.60/fintech_nocountry/"

    /*
    Para cambios de diseño y creación de nuevas DTOs que extienden de MensajeDTO, hay
    que agregar el subtipo correspondiente junto con un string que lo identifique
     */
    private val moshi = Moshi.Builder()
            .add(PolymorphicJsonAdapterFactory.of(MensajeDTO::class.java, "type")
            .withSubtype(UsuarioDTO::class.java, "usuario")
            .withSubtype(InversorDTO::class.java, "inversor")
            .withSubtype(EmprendedorDTO::class.java, "emprendedor")
            .withSubtype(AdministradorDTO::class.java, "administrador")
            .withSubtype(CrowdfundingDTO::class.java, "crowdfunding")
            .withSubtype(SolicitudDTO::class.java, "solicitud")
            .withSubtype(EtiquetaDTO::class.java, "etiqueta")
            .withSubtype(InversionDTO::class.java, "inversion")
            .withSubtype(MensajeDTO::class.java, "mensaje")
            )
        .add(KotlinJsonAdapterFactory())
        .build()
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}