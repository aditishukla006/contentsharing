package com.example.contentsharingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TileAdapter(private val tileList: List<Tile>) :
    RecyclerView.Adapter<TileAdapter.TileViewHolder>() {

    inner class TileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tileImage: ImageView = itemView.findViewById(R.id.tileImage)
        val tileText: TextView = itemView.findViewById(R.id.tileText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tile_item, parent, false)
        return TileViewHolder(view)
    }

    override fun onBindViewHolder(holder: TileViewHolder, position: Int) {
        val tile = tileList[position]
        holder.tileText.text = tile.title
        Glide.with(holder.itemView.context)
            .load(tile.imageUrl.ifEmpty { "https://via.placeholder.com/150" })
            .into(holder.tileImage)
    }

    override fun getItemCount(): Int = tileList.size
}
