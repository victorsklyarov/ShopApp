package com.zeroillusion.shopapp.feature.home.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zeroillusion.shopapp.feature.home.domain.model.LatestProduct

@Entity
data class LatestProductEntity(
    val category: String,
    val imageUrl: String,
    val name: String,
    val price: Int,
    @PrimaryKey val id: Int? = null
) {
    fun toLatestProduct(): LatestProduct {
        return LatestProduct(
            category = category,
            imageUrl = imageUrl,
            name = name,
            price = price
        )
    }
}