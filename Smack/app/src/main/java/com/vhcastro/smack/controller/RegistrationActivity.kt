package com.vhcastro.smack.controller

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.vhcastro.smack.R
import com.vhcastro.smack.services.AuthService
import com.vhcastro.smack.utilities.BROADCAST_USER_DATA_CHANGED
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.*

class RegistrationActivity : AppCompatActivity() {

    var userAvatar = "profiledefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        registerSpinner.visibility = View.INVISIBLE
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
        showSpinner(true)
        val username = registrationUsernameText.text.toString()
        val email = registrationEmailText.text.toString()
        val password = registrationPasswordText.text.toString()
        if(!username.isNullOrBlank() && !email.isNullOrBlank() && !password.isNullOrBlank()){
            AuthService.registerUser(email, password) { registerSuccess ->
                if (registerSuccess){
                    AuthService.loginUser(email, password) { loginSuccess ->
                        if (loginSuccess){
                            AuthService.createUser(
                                username,
                                email,
                                userAvatar,
                                avatarColor
                            ){ createSuccess ->
                                if(createSuccess){
                                    val userDataChanged = Intent(BROADCAST_USER_DATA_CHANGED)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChanged)
                                    showSpinner(false)
                                    finish()
                                } else {
                                    longErrorToast()
                                }
                            }
                        } else {
                            longErrorToast()
                        }
                    }
                } else {
                    longErrorToast()
                }
            }
        } else {
            longErrorToast("Make sure user name, email and password are filled in.")
        }
    }

    fun longErrorToast(errorMessage: String = "Something went wrong. Please try again."){
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        showSpinner(false)
    }

    fun showSpinner(spinnerVisible: Boolean){
        if(spinnerVisible){
            registerSpinner.visibility = View.VISIBLE
        } else {
            registerSpinner.visibility = View.INVISIBLE
        }
        registrationCreateUserBtn.isEnabled = !spinnerVisible
        registrationAvatarImageView.isEnabled = !spinnerVisible
        backgroundColorBtn.isEnabled = !spinnerVisible
    }
}
