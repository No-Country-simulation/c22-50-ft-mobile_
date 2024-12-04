package com.example.fintech_nocountry

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fintech_nocountry.consumoApiRest.ICrudATablas
import com.example.fintech_nocountry.consumoApiRest.RetrofitClient
import com.example.fintech_nocountry.consumoApiRest.dto.CrowdfundingDTO
import com.example.fintech_nocountry.consumoApiRest.dto.InversionDTO
import com.example.fintech_nocountry.consumoApiRest.dto.MensajeDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalleActivity : AppCompatActivity() {

    lateinit var imagen: ImageView
    lateinit var btnVolver: ImageButton
    lateinit var btnContactar: Button
    lateinit var tvTitulo: TextView
    lateinit var tvDescripcion: TextView
    lateinit var tvCantidadInversores: TextView
    lateinit var tvRecaudado: TextView
    lateinit var tvPorcentajeFinanciado: TextView
    lateinit var progressBar: ProgressBar
    lateinit var tvDiasRestantes: TextView
    lateinit var tvNombreEmprendedor: TextView
    private val api: ICrudATablas? = RetrofitClient.retrofit.create(ICrudATablas::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imagen = findViewById(R.id.detalle_imagen)
        btnVolver = findViewById(R.id.detalle_volver)
        btnContactar = findViewById(R.id.detalle_contactar_emprendedor)
        tvDescripcion = findViewById(R.id.detalle_descripcion)
        tvTitulo = findViewById(R.id.detalle_titulo)
        tvCantidadInversores = findViewById(R.id.detalle_cantidad_inversores)
        tvRecaudado = findViewById(R.id.detalle_cantidad_recaudada)
        tvPorcentajeFinanciado = findViewById(R.id.detalle_porcentaje)
        progressBar = findViewById(R.id.detalle_progressbar)
        tvDiasRestantes = findViewById(R.id.detalle_dias_restantes)
        tvNombreEmprendedor = findViewById(R.id.detalle_nombre_emprendedor)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            cargarViews(
                intent.getParcelableExtra("crowdfunding", CrowdfundingDTO::class.java)
            )
        } else {
            //La api es menor a la version 33, entonces se tiene que usar este tipo de getParcelable()
            @Suppress("DEPRECATION")
            cargarViews(
                intent.getParcelableExtra("crowdfunding")
            )
        }

        btnVolver.setOnClickListener{
            finish()
        }
    }

    private fun cargarViews(crowdfunding: CrowdfundingDTO?){
        try{
            tvDescripcion.text = crowdfunding!!.descripcion
            tvTitulo.text = crowdfunding.titulo
            tvRecaudado.text ="US$ ${crowdfunding.montoRecaudado}"
            tvDiasRestantes.text = "${crowdfunding.calcularDiasRestantes()} días"
            progressBar.progress = crowdfunding.calcularPorcentajeActual()
            tvPorcentajeFinanciado.text = "${crowdfunding.calcularPorcentajeActual()}%"
            cargarCantidadInversores(crowdfunding)
        }
        catch (e: Exception)
        {
            Log.e("DetalleError", e.message!!)
        }
    }

    private fun cargarCantidadInversores(crowdfunding: CrowdfundingDTO?){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = api!!.getEnTabla("inversion", "crowdfunding_id = ${crowdfunding!!.id}", "count(*) as count")

                withContext(Dispatchers.Main){
                    if(response.isEmpty())
                        tvCantidadInversores.text = "0"
                    else if(response[0] is MensajeDTO)
                        Log.e("ApiError", (response[0] as MensajeDTO).message!!)
                    else
                        tvCantidadInversores.text = (response[0] as InversionDTO).count.toString()
                }
            }
            catch (e: Exception)
            {
                Log.e("ApiError", e.message!!)
            }
        }
    }
}