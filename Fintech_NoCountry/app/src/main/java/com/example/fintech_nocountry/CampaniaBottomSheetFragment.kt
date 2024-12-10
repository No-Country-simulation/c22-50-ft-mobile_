package com.example.fintech_nocountry

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.fintech_nocountry.consumoApiRest.ICrudATablas
import com.example.fintech_nocountry.consumoApiRest.RetrofitClient
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

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
        val progressBar: ProgressBar = view.findViewById(R.id.form_campania_progressbar)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        etFechaFinalizacion.setOnClickListener{
            val datePickerDialog = DatePickerDialog(view.context,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                    val formattedMonth = selectedMonth + 1
                    val selectedDate = "$selectedDay/$formattedMonth/$selectedYear"
                    etFechaFinalizacion.setText(selectedDate)
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        btnEnviar.setOnClickListener {
            if(etTitulo.text.toString().isEmpty()
                || etDescripcion.text.toString().isEmpty()
                || etFechaFinalizacion.text.toString().isEmpty()
                || etMontoObjetivo.text.toString().isEmpty())
            {
                Toast.makeText(view.context, "Se deben rellenar todos los campos", Toast.LENGTH_SHORT).show()
            }
            else if(LocalDate.parse(
                    etFechaFinalizacion.text.toString(),
                    DateTimeFormatter.ofPattern("d/M/yyyy"))
                .isBefore(LocalDate.now())
                )
            {
                Toast.makeText(view.context, "La fecha de finalización debe ser posterior a la actual", Toast.LENGTH_SHORT).show()
            }
            else
            {
                layoutFormulario.visibility = View.GONE
                progressBar.visibility = View.VISIBLE

                CoroutineScope(Dispatchers.IO).launch{
                    try{
                        val api = RetrofitClient.retrofit.create(ICrudATablas::class.java)
                        val crowdId = requireArguments().getInt("id", 0).let { it1 ->
                            CorreoYUsuario.insertarCrowdfunding(
                                etTitulo.text.toString(),
                                etDescripcion.text.toString(),
                                etMontoObjetivo.text.toString().toDouble(),
                                etFechaFinalizacion.text.toString(),
                                it1,
                                etImagen.text.toString().ifEmpty { "" }
                            )
                        }
                        val response = api.postEnTabla("solicitud", mapOf(
                            "columnas" to "descripcion, fecha_envio, crowdfunding_id",
                            "valores" to "'${etDescripcion.text}', '${LocalDate.now()}', $crowdId "
                        ))
                        Log.i("Api", response.toString())
                        withContext(Dispatchers.Main){
                            progressBar.visibility = View.GONE
                            imgEnviado.visibility = View.VISIBLE
                            tvTituloEnviado.visibility = View.VISIBLE
                            tvTextoEnviado.visibility = View.VISIBLE
                        }
                    } catch (e: Exception) {
                        Log.e("ApiError", e.message!!)
                    }
                }
            }
        }

        return view
    }
}