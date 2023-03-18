package com.zeroillusion.shopapp.model

data class Latest(
    val category: String,
    val name: String,
    val price: Int,
    val image_url: String
): DisplayableItem