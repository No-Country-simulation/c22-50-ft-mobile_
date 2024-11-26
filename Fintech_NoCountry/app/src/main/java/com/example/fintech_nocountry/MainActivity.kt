package com.example.fintech_nocountry

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fintech_nocountry.consumoApiRest.ICrudATablas
import com.example.fintech_nocountry.consumoApiRest.RetrofitClient
import com.example.fintech_nocountry.consumoApiRest.dto.MensajeDTO
import com.example.fintech_nocountry.consumoApiRest.dto.UsuarioDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    lateinit var etQuery: EditText
    lateinit var tvJson: TextView
    lateinit var btnEjecutar: Button
    var api: ICrudATablas? = RetrofitClient.retrofit.create(ICrudATablas::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etQuery = findViewById(R.id.etId)
        tvJson = findViewById(R.id.tvJson)
        btnEjecutar = findViewById(R.id.btnEjecutar)

        btnEjecutar.setOnClickListener{
            val segmentos = etQuery.text.toString().split("%")
            if(segmentos.size >= 3){
                api!!.getEnTabla(segmentos[0], segmentos[1], segmentos[2])
                    .enqueue(object : retrofit2.Callback<List<MensajeDTO>>{
                        override fun onResponse(
                            call: Call<List<MensajeDTO>>,
                            response: Response<List<MensajeDTO>>
                        ) {
                            val body = response.body()
                            if(body != null){
                                tvJson.text = body.toString()
                            }
                        }

                        override fun onFailure(call: Call<List<MensajeDTO>>, t: Throwable) {
                            tvJson.text = t.message.toString()
                        }
                    })
            } else {
                tvJson.text = """La query a ingresar debe estar delimitada por '%',
                     | donde el primer segmento es la tabla a consultar, el segundo
                     | son las condiciones del WHERE y el tercero son las columnas a
                     | traer
                """.trimMargin()

            }
        }
    }
}