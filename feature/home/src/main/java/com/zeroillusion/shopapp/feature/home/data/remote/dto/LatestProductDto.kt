package com.zeroillusion.shopapp.feature.home.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.zeroillusion.shopapp.feature.home.data.local.entity.LatestProductEntity

data class LatestProductDto(
    @SerializedName("category")
    val category: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("image_url")
    val imageUrl: String
) {
    fun toLatestProductEntity(): LatestProductEntity {
        return LatestProductEntity(
            category = category,
            name = name,
            price = price,
            imageUrl = imageUrl
        )
    }
}