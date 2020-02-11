package com.example.myhelper

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

class Movie {//RetroPhoto

    @SerializedName("id")
    var id : Int?=null
    @SerializedName("title")
    var title : String?=null
    @SerializedName("genre")
    var genre:String?=null
    @SerializedName("image")
    var image :String?=null
    @SerializedName("StartTime")
    var StartTime :String?=null
    @SerializedName("Summary")
    var Summary :String?=null

    constructor(id :Int, title :String, genre:String, image:String, StartTime : String, Summary : String){
        this.id = id
        this.title = title
        this.genre = genre
        this.image = image
        this.StartTime = StartTime
        this.Summary = Summary
    }

}