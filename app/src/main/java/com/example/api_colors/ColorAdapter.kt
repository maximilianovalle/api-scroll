package com.example.api_colors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ColorAdapter(private val colorList: List<Color>) :
    RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorName: TextView = itemView.findViewById(R.id.color_name)
        val colorHex: TextView = itemView.findViewById(R.id.color_hex)
        val createdDate: TextView = itemView.findViewById(R.id.created_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.color_item, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = colorList[position]
        holder.colorName.text = color.title
        holder.colorHex.text = "#${color.hex}"
        holder.createdDate.text = "Created: ${color.dateCreated}"
    }

    override fun getItemCount() = colorList.size
}
