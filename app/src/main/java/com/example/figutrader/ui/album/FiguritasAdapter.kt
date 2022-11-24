package com.example.figutrader.ui.album

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.figutrader.R

interface FiguClickedListener {
    fun onFiguClicked(figurita: FiguritaDataView)
}

class FiguritasAdapter(var myDataset: List<FiguritaDataView>, val figuClickedListener: FiguClickedListener) :
    RecyclerView.Adapter<FiguritasAdapter.MyViewHolder>() {
    private val VIEWTYPE_COUNTRY: Int = 1
    private val VIEWTYPE_FIGURITA: Int = 2

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FiguritasAdapter.MyViewHolder {
        val view: View = if (viewType == VIEWTYPE_COUNTRY)
            LayoutInflater.from(parent.context)
                .inflate(R.layout.country, parent, false)
        else
            LayoutInflater.from(parent.context)
                .inflate(R.layout.figurita, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.figurita_nombre).text =
            myDataset[position].descripcion

        holder.view.findViewById<TextView>(R.id.CantidadTextView).text =
            myDataset[position].cantidad.toString()

        Log.v("FiguritasAdapter", "${myDataset[position].cantidad}")
        Log.v("FiguritasAdapter", "${myDataset[position].descripcion}")

        if (myDataset[position].cantidad == 0) {
            holder.view.findViewById<TextView>(R.id.figurita_nombre)
                .setBackgroundResource(R.color.gris_figurita)
        }
        else {
            holder.view.findViewById<TextView>(R.id.figurita_nombre)
                .setBackgroundResource(R.color.verde_figurita)
        }

        holder.itemView.setOnClickListener{ figuClickedListener.onFiguClicked(myDataset[position])}
    }

    override fun getItemViewType(position: Int): Int {
        return if (1 == 2)
            VIEWTYPE_COUNTRY
        else
            VIEWTYPE_FIGURITA
    }

    override fun getItemCount() = myDataset.size
}