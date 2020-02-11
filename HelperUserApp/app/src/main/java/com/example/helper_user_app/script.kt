package com.example.helper_user_app

import com.google.gson.annotations.SerializedName

class script {

    @SerializedName("id")
    var id : Int?=null
    @SerializedName("movie_id")
    var movie_id : Int?=null
    @SerializedName("begin_at")
    var begin_at : Int?=null
    @SerializedName("end_at")
    var end_at :Int?=null
    @SerializedName("script")
    var script : String?=null

    constructor(id :Int, movie_id :Int, begin_at :Int,end_at :Int, script: String){
        this.id = id
        this.movie_id = movie_id
        this.begin_at = begin_at
        this.end_at = end_at
        this.script =script
    }

}