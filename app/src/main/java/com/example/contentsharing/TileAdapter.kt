package com.example.contentsharingapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class TileAdapter(private val tileList: List<Tile>) :
    RecyclerView.Adapter<TileAdapter.TileViewHolder>() {

    inner class TileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tileImage: ImageView = itemView.findViewById(R.id.tileImage)
        val tileText: TextView = itemView.findViewById(R.id.tileText)
        val youtubePlayerView: YouTubePlayerView =
            itemView.findViewById(R.id.youtubePlayerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.tile_item, parent, false)
        return TileViewHolder(view)
    }

    override fun onBindViewHolder(holder: TileViewHolder, position: Int) {
        val tile = tileList[position]
        holder.tileText.text = tile.title

        if (tile.webUrl.contains("youtube.com") || tile.webUrl.contains("youtu.be")) {

            holder.tileImage.visibility = View.GONE
            holder.youtubePlayerView.visibility = View.VISIBLE

            val videoId = extractYouTubeVideoId(tile.webUrl)
            holder.youtubePlayerView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    videoId?.let { youTubePlayer.cueVideo(it, 0f) }
                }
            })

        } else {

            holder.youtubePlayerView.visibility = View.GONE
            holder.tileImage.visibility = View.VISIBLE

            Glide.with(holder.itemView.context)
                .load(tile.imageUrl)
                .into(holder.tileImage)

            holder.itemView.setOnClickListener {
                val intent = android.content.Intent(
                    android.content.Intent.ACTION_VIEW,
                    Uri.parse(tile.webUrl)
                )
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = tileList.size

    private fun extractYouTubeVideoId(url: String): String? {
        return when {
            url.contains("v=") -> url.substringAfter("v=").substringBefore("&")
            url.contains("youtu.be/") -> url.substringAfter("youtu.be/").substringBefore("?")
            else -> null
        }
    }
}
