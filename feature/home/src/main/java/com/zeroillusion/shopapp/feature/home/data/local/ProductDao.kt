package com.zeroillusion.shopapp.feature.home.data.local

import androidx.room.*
import com.zeroillusion.shopapp.feature.home.data.local.entity.FlashSaleProductEntity
import com.zeroillusion.shopapp.feature.home.data.local.entity.LatestProductEntity

@Dao
interface ProductDao {

    @Insert(entity = LatestProductEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLatestProductList(latestProductList: List<LatestProductEntity>)

    @Query("DELETE FROM latestproductentity")
    suspend fun deleteLatestProductList()

    @Query("SELECT * FROM latestproductentity")
    suspend fun getLatestProductList(): List<LatestProductEntity>

    @Insert(entity = FlashSaleProductEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashSaleProductList(products: List<FlashSaleProductEntity>)

    @Query("DELETE FROM flashsaleproductentity")
    suspend fun deleteFlashSaleProductList()

    @Query("SELECT * FROM flashsaleproductentity")
    suspend fun getFlashSaleProductList(): List<FlashSaleProductEntity>
}