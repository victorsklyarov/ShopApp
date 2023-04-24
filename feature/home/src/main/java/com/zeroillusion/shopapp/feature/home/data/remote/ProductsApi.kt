package com.zeroillusion.shopapp.feature.home.data.remote

import com.zeroillusion.shopapp.feature.home.data.remote.dto.FlashSaleProductListDto
import com.zeroillusion.shopapp.feature.home.data.remote.dto.LatestProductListDto
import retrofit2.http.GET

interface ProductsApi {

    @GET("/v3/cc0071a1-f06e-48fa-9e90-b1c2a61eaca7/")
    suspend fun getLatestList(): LatestProductListDto

    @GET("/v3/a9ceeb6e-416d-4352-bde6-2203416576ac/")
    suspend fun getFlashSaleList(): FlashSaleProductListDto
}