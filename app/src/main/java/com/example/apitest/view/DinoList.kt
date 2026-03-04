package com.example.apitest.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.apitest.DinosaurCard
import com.example.apitest.model.Dinosaur

@Preview(showBackground = true)
@Composable
fun DinoList() {

    val testDino = Dinosaur(
        id = "1",
        name = "T-Rex",
        weight = "8000 kg",
        height = "4 m",
        length = "12 m",
        diet = "Carnívoro",
        period = "Cretácico"
    )

    DinosaurCard(dinosaur = testDino)
}

