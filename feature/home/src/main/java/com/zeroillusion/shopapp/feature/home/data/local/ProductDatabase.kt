package com.zeroillusion.shopapp.feature.home.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zeroillusion.shopapp.feature.home.data.local.entity.FlashSaleProductEntity
import com.zeroillusion.shopapp.feature.home.data.local.entity.LatestProductEntity

@Database(
    entities = [LatestProductEntity::class, FlashSaleProductEntity::class],
    version = 1
)
abstract class ProductDatabase : RoomDatabase() {

    abstract val dao: ProductDao
}