package com.example.mindsettle

//import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

//@Generated("com.robohorse.robopojogenerator")
data class Mood(

    @field:SerializedName("dateTime")
    var dateTime: String? = null,

    @field:SerializedName("userMood")
    var userMood: String? = null,

    @field:SerializedName("comment")
    var comment: String? = null
)