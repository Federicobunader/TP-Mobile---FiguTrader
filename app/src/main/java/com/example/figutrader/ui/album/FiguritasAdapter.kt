package com.example.figutrader.ui.album

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.figutrader.R
import com.example.figutrader.model.FiguritaDataView

interface FiguClickedListener {
    fun onFiguClicked(figurita: FiguritaDataView)
}

class FiguritasAdapter(var context: Context?, var myDataset: List<FiguritaDataView>, val figuClickedListener: FiguClickedListener) :
    RecyclerView.Adapter<FiguritasAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.figurita, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.CantidadTextView).text =
            myDataset[position].cantidad.toString()

        if (myDataset[position].cantidad == 0) {
            holder.view.findViewById<TextView>(R.id.FiguTextView)
                .setBackgroundResource(R.drawable.figurita)
            holder.view.findViewById<TextView>(R.id.FiguTextView).text = myDataset[position].descripcion
        }
        else {
            val imagenName : String = "f" + myDataset[position].figuId.toString()
            holder.view.findViewById<TextView>(R.id.FiguTextView).text = ""
            holder.view.findViewById<TextView>(R.id.FiguTextView)
                .setBackgroundResource(context!!.resources.getIdentifier(imagenName,"drawable", context!!.packageName))
        }

        holder.itemView.setOnClickListener{ figuClickedListener.onFiguClicked(myDataset[position])}
    }

    override fun getItemCount() = myDataset.size
}