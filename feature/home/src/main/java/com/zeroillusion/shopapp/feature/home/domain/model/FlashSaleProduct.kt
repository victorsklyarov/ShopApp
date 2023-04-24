package com.zeroillusion.shopapp.feature.home.domain.model

data class FlashSaleProduct(
    val category: String,
    val name: String,
    val price: Double,
    val discount: Int,
    val imageUrl: String
)
