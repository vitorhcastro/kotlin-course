package com.vhcastro.smack.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vhcastro.smack.R
import com.vhcastro.smack.model.Message
import com.vhcastro.smack.services.UserDataService

class MessageAdapter(val context: Context, val messages: ArrayList<Message>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val userImage = itemView.findViewById<ImageView>(R.id.messageUserImage)
        val timestamp = itemView.findViewById<TextView>(R.id.messageTimestamp)
        val username = itemView.findViewById<TextView>(R.id.messageUsername)
        val messageBody = itemView.findViewById<TextView>(R.id.messageBodyLabel)

        fun bindMessage(context: Context, message: Message){
            val resourceId = context.resources.getIdentifier(message.userAvatar, "drawable", context.packageName)
            userImage.setImageResource(resourceId)
            userImage.setBackgroundColor(UserDataService.returnAvatarColor(message.userAvatarColor))
            timestamp.text = message.timeStamp
            username.text = message.username
            messageBody.text = message.messageBody
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMessage(context, messages[position])
    }
}