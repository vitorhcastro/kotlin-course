package com.vhcastro.smack.controller

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.vhcastro.smack.R
import com.vhcastro.smack.services.AuthService
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.*

class RegistrationActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

    fun generateUserAvatar(view: View){
        val random = Random()
        var color = random.nextInt(2)
        var avatar = random.nextInt(28)

        if(color == 0){
            userAvatar = "light${avatar}"
        } else {
            userAvatar = "dark${avatar}"
        }
        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        registrationAvatarImageView.setImageResource(resourceId)
    }

    fun generateColorClicked(view: View){
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        registrationAvatarImageView.setBackgroundColor(Color.rgb(r,g,b))

        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() / 255
        val savedB = b.toDouble() / 255
        avatarColor = "[${savedR}, ${savedG}, ${savedB}, 1]"
    }

    fun createUserBtnClicked(view: View){
        AuthService.registerUser(this, "j@j.com", "123456") {
            complete ->
            if (complete){

            }
        }
    }
}
