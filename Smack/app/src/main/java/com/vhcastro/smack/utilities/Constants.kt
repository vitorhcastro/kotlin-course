package com.vhcastro.smack.utilities

const val BASE_URL = "https://smack-chatty-app.herokuapp.com/v1/"
const val URL_REGISTER = "${BASE_URL}account/register"
const val URL_LOGIN = "${BASE_URL}account/login"
const val URL_CREATE_USER = "${BASE_URL}user/add"
const val URL_GET_USER = "${BASE_URL}user/byEmail/"

//Broadcast Constants
const val BROADCAST_USER_DATA_CHANGED = "BROADCAST_USER_DATA_CHANGED"