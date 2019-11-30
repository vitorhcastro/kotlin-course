package com.vhcastro.smack.services

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.vhcastro.smack.controller.App
import com.vhcastro.smack.utilities.BROADCAST_USER_DATA_CHANGED
import java.util.*

object UserDataService {
    var id = ""
    var avatarColor = ""
    var avatarName = ""
    var email = ""
    var name = ""

    fun returnAvatarColor(components: String): Int{
        val strippedColor = components
            .replace("[","")
            .replace("]","")
            .replace(",","")

        var r = 0
        var g = 0
        var b = 0

        val scanner = Scanner(strippedColor)
        if(scanner.hasNext()){
            r = (scanner.nextDouble() * 255).toInt()
            g = (scanner.nextDouble() * 255).toInt()
            b = (scanner.nextDouble() * 255).toInt()
        }

        return Color.rgb(r, g, b)
    }

    fun logout(context: Context){
        id = ""
        avatarColor = ""
        avatarName = ""
        email = ""
        name = ""
        App.prefs.authToken = ""
        App.prefs.userEmail = ""
        App.prefs.isLoggedIn = false
        MessageService.clearMessages()
        MessageService.clearChannels()
        val userDataChanged = Intent(BROADCAST_USER_DATA_CHANGED)
        LocalBroadcastManager.getInstance(context).sendBroadcast(userDataChanged)
    }
}