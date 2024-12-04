package com.example.fintech_nocountry.recyclerViews

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fintech_nocountry.R
import com.example.fintech_nocountry.consumoApiRest.dto.EtiquetaDTO

class EtiquetaAdapter(private var lstEtiquetas: MutableList<EtiquetaDTO>,
                      private var context: Context)
    : RecyclerView.Adapter<EtiquetaViewholder>(){

    private val seleccionados = mutableSetOf<EtiquetaDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtiquetaViewholder {
        return EtiquetaViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_etiquetas, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return lstEtiquetas.size
    }

    override fun onBindViewHolder(holder: EtiquetaViewholder, position: Int) {
        val item = lstEtiquetas[position]
        holder.descripcion.text = item.descripcion

        holder.descripcion.setOnClickListener{
            clickEnEtiqueta(holder, lstEtiquetas[position])
        }
    }

    private fun clickEnEtiqueta(holder: EtiquetaViewholder, etiqueta: EtiquetaDTO){
        if(!seleccionados.contains(etiqueta))
        {   holder.descripcion.setBackgroundResource(R.drawable.etiqueta_seleccionada)
            holder.descripcion.setTextColor(ContextCompat.getColor(context, R.color.white))
            seleccionados.add(etiqueta)
        }
        else
        {
            holder.descripcion.setBackgroundResource(R.drawable.etiqueta_redondeada)
            holder.descripcion.setTextColor(ContextCompat.getColor(context, R.color.black))
            seleccionados.remove(etiqueta)
        }
    }
}