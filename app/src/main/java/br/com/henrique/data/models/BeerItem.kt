package br.com.henrique.data.models

import java.io.Serializable

data class BeerItem(
    val abv: Double,
    val image_url: String,
    val name: String,
    val tagline: String,
    val ibu: Double,
    val description: String
) : Serializable