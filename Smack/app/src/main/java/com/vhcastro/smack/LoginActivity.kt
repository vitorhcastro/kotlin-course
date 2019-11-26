package com.vhcastro.smack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun createUserBtnClicked(view: View){
        var registerUserIntent = Intent(this, RegistrationActivity::class.java)
        startActivity(registerUserIntent)
    }
}
