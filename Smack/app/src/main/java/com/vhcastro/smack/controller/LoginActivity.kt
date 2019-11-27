package com.vhcastro.smack.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.vhcastro.smack.R
import com.vhcastro.smack.services.AuthService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun createUserBtnClicked(view: View){
        var registerUserIntent = Intent( this, RegistrationActivity::class.java)
        startActivity(registerUserIntent)
    }

    fun loginBtnClicked(view: View) {
        val email = loginEmail.text.toString()
        val password = loginPasswordText.text.toString()

        AuthService.loginUser(this, email, password){ loginSuccess ->
            if(loginSuccess){
                AuthService.findUserByEmail(this) { findUserSuccess ->
                    if(findUserSuccess){
                        finish()
                    }
                }
            }
        }
    }
}
