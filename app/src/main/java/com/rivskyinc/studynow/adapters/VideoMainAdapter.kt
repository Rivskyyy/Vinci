package com.rivskyinc.studynow.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivskyinc.studynow.R
import com.rivskyinc.studynow.Video

class VideoMainAdapter(
    private var mContext: Context,
    private var videoArrayList: ArrayList<Video>,
) : RecyclerView.Adapter<VideoMainAdapter.ViewHolder>() {
    var onVideoClickListenerObject: OnVideoClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video: Video = videoArrayList[position]
        holder.bindVideo(videoArrayList[position])
        holder.itemView.setOnClickListener {

            onVideoClickListenerObject?.onVideoClick(video)

        }
    }

    override fun getItemCount(): Int {
        return videoArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var image: ImageView = itemView.findViewById(R.id.thumbnailImage)
        private var title: TextView = itemView.findViewById(R.id.titleView)

        @SuppressLint("SuspiciousIndentation")
        fun bindVideo(video: Video) {

            val im = Glide.with(itemView).load(video.thumbnail).into(image)
            title.text = video.title
        }
    }

    interface OnVideoClickListener {
        fun onVideoClick(video: Video)
    }

}
