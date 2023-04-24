package com.zeroillusion.shopapp.feature.home.domain.model

data class LatestProduct(
    val category: String,
    val name: String,
    val price: Int,
    val imageUrl: String
)
