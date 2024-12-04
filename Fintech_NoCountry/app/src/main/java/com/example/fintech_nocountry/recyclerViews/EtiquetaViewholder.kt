package com.example.fintech_nocountry.recyclerViews

import android.widget.TextView
import com.example.fintech_nocountry.R
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class EtiquetaViewholder(view: View): RecyclerView.ViewHolder(view){
    var descripcion: TextView = view.findViewById(R.id.descripcion_etiqueta)
}