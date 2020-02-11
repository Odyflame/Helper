package com.example.myhelper

import com.google.gson.annotations.SerializedName

class Login {

    @SerializedName("email")
    var email : String?=null
    @SerializedName("authToken")
    var authToken : String?=null

    constructor(email : String, authToken : String){
        this.email = email
        this.authToken =authToken
    }

}