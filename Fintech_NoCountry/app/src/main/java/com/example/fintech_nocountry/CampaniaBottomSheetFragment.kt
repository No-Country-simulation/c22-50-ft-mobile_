package com.example.fintech_nocountry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.example.fintech_nocountry.consumoApiRest.ICrudATablas
import com.example.fintech_nocountry.consumoApiRest.RetrofitClient
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class CampaniaBottomSheetFragment(): BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_campania, container, false)
        val etTitulo: EditText = view.findViewById(R.id.form_campania_titulo)
        val etDescripcion: EditText = view.findViewById(R.id.form_campania_descripcion)
        val etFechaFinalizacion: EditText = view.findViewById(R.id.form_campania_fecha_finalizacion)
        val etMontoObjetivo: EditText = view.findViewById(R.id.form_campania_monto_objetivo)
        val layoutFormulario: LinearLayout = view.findViewById(R.id.form_campania_layout_formulario)
        val btnEnviar: Button = view.findViewById(R.id.form_campania_enviar)
        val imgEnviado: ImageView = view.findViewById(R.id.form_campania_imagen_enviado)
        val tvTituloEnviado: TextView = view.findViewById(R.id.form_campania_titulo_enviado)
        val tvTextoEnviado: TextView = view.findViewById(R.id.form_campania_texto_enviado)
        val etImagen: EditText = view.findViewById(R.id.form_campania_url_imagen)

        btnEnviar.setOnClickListener {
            if(etTitulo.text.toString().isNotEmpty() && etDescripcion.text.toString().isNotEmpty()
                && etFechaFinalizacion.text.toString().isNotEmpty() && etMontoObjetivo.text.toString().isNotEmpty()){
                layoutFormulario.visibility = View.GONE

                val url: String? = etImagen.text.toString().ifEmpty { null }

                CoroutineScope(Dispatchers.IO).launch{
                    try{
                        val api = RetrofitClient.retrofit.create(ICrudATablas::class.java)
                        val map = mapOf(
                            "columnas" to "titulo, descripcion, fecha_finalizacion, monto_objetivo, url_imagen, emprendedor_id, fecha_creacion",
                            "valores" to "${etTitulo.text}, ${etDescripcion.text}, ${etFechaFinalizacion.text}, ${etMontoObjetivo.text}, $url, ${requireArguments().getInt("id", 0)}, ${LocalDate.now()}"
                        )
                        api.postEnTabla("crowdfunding", map)
                    } catch (e: Exception) {
                        Log.e("ApiError", e.message!!)
                    }
                }

                imgEnviado.visibility = View.VISIBLE
                tvTituloEnviado.visibility = View.VISIBLE
                tvTextoEnviado.visibility = View.VISIBLE
            }
            else
            {
                Toast.makeText(view.context, "Se deben rellenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}