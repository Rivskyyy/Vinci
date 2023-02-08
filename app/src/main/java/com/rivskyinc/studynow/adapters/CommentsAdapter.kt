package com.rivskyinc.studynow.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rivskyinc.studynow.Comment
import com.rivskyinc.studynow.R

class CommentsAdapter(
    private var mContext: Context,
    private var commentArrayList: ArrayList<Comment>,
) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_comments, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment: Comment = commentArrayList.get(position)
        holder.bindComment(commentArrayList.get(position))

    }

    override fun getItemCount(): Int {
        return commentArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avatarChat: ImageView = itemView.findViewById(R.id.avatarChat)
        var userName: TextView = itemView.findViewById(R.id.textViewUserName)

        //        var timestamp : TextView = itemView.findViewById(R.id.textViewDate)
        var text: TextView = itemView.findViewById(R.id.textViewComment)


        @SuppressLint("SuspiciousIndentation")
        fun bindComment(comment: Comment) {

//            al date = MyApplication.formatTimeStamp(comments.timestamp!!.toLong())
            Glide.with(itemView).load(comment.userImage).into(avatarChat).toString() //avatarChat
            userName.text = comment.userName
//           timestamp.text = comments.timestamp
            text.text = comment.text.toString()


        }
    }


}

