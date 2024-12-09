package com.example.fintech_nocountry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ContactarBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_form, container, false)

        val etAsunto: EditText = view.findViewById(R.id.contactar_asunto)
        val etMensaje: EditText = view.findViewById(R.id.contactar_mensaje)
        val btnEnviar: Button = view.findViewById(R.id.contactar_enviar)
        val imgEnviado: ImageView = view.findViewById(R.id.contacto_imagen_enviado)
        val tvTituloEnviado: TextView = view.findViewById(R.id.contacto_titulo_enviado)
        val tvTextoEnviado: TextView = view.findViewById(R.id.contacto_texto_enviado)

        btnEnviar.setOnClickListener {
            if(etAsunto.text.toString().isNotEmpty() && etMensaje.text.toString().isNotEmpty()){
                etAsunto.visibility = View.GONE
                etMensaje.visibility = View.GONE
                btnEnviar.visibility = View.GONE
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