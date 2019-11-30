package com.vhcastro.smack.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.vhcastro.smack.R
import com.vhcastro.smack.services.AuthService
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginSpinner.visibility = View.INVISIBLE
    }

    fun createUserBtnClicked(view: View){
        var registerUserIntent = Intent( this, RegistrationActivity::class.java)
        startActivity(registerUserIntent)
    }

    fun loginBtnClicked(view: View) {
        showSpinner(true)
        val email = loginEmail.text.toString()
        val password = loginPasswordText.text.toString()
        hideKeyboard()
        if(!email.isNullOrBlank() && !password.isNullOrBlank()){
            AuthService.loginUser(this, email, password){ loginSuccess ->
                if(loginSuccess){
                    AuthService.findUserByEmail(this) { findUserSuccess ->
                        if(findUserSuccess){
                            finish()
                        } else{
                            longErrorToast()
                        }
                    }
                } else{
                    longErrorToast()
                }
            }
        } else {
            longErrorToast("Please fill in both email and password")
        }
    }

    fun longErrorToast(errorMessage: String = "Something went wrong. Please try again."){
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        showSpinner(false)
    }

    fun showSpinner(spinnerVisible: Boolean){
        if(spinnerVisible){
            loginSpinner.visibility = View.VISIBLE
        } else {
            loginSpinner.visibility = View.INVISIBLE
        }
        loginBtn.isEnabled = !spinnerVisible
        loginCreateUserBtn.isEnabled = !spinnerVisible
    }
}
