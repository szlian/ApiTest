package com.example.apitest.model

import com.google.gson.annotations.SerializedName

data class Dinosaur(
    @SerializedName("_id")
    val id: String,

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

    @SerializedName("existed")
    val existed: String? = null,

    @SerializedName("region")
    val region: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("image")
    val image: String? = null // Campo imagen añadido
)