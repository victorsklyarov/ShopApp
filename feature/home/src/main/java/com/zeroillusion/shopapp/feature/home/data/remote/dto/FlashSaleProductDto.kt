package com.zeroillusion.shopapp.feature.home.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.zeroillusion.shopapp.feature.home.data.local.entity.FlashSaleProductEntity

data class FlashSaleProductDto(
    @SerializedName("category")
    val category: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("image_url")
    val imageUrl: String
) {
    fun toFlashSaleProductEntity(): FlashSaleProductEntity {
        return FlashSaleProductEntity(
            category = category,
            name = name,
            price = price,
            discount = discount,
            imageUrl = imageUrl
        )
    }
}