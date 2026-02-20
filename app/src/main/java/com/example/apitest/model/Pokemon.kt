package com.example.apitest.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName ("id")
    val id: Int,
    @SerializedName ("name")
    val name: String,
    @SerializedName ("weight")
    val weight: Int,
    @SerializedName ("height")
    val height: Int,
    //@SerializedName ("species")
    //val species: List<Specie>
)
/*
data class Specie(
    @SerializedName ("url")
    val url: String,
    @SerializedName ("name")
    val name: String
)*/

