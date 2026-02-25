package com.example.apitest.model

import com.google.gson.annotations.SerializedName

data class Dinosaur(
    @SerializedName("name")
    val name: String,
    @SerializedName("weight")
    val weight: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("length")
    val length: String,
    @SerializedName("diet")
    val diet: String,
    @SerializedName("period")
    val period: String,
)