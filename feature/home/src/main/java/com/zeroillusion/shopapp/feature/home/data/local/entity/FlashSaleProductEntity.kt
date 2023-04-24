package com.zeroillusion.shopapp.feature.home.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zeroillusion.shopapp.feature.home.domain.model.FlashSaleProduct

@Entity
data class FlashSaleProductEntity(
    val category: String,
    val discount: Int,
    val imageUrl: String,
    val name: String,
    val price: Double,
    @PrimaryKey val id: Int? = null
) {
    fun toFlashSaleProduct(): FlashSaleProduct {
        return FlashSaleProduct(
            category = category,
            discount = discount,
            imageUrl = imageUrl,
            name = name,
            price = price
        )
    }
}