package com.zeroillusion.shopapp.feature.home.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FlashSaleProductListDto(
    @SerializedName("flash_sale")
    val flashSale: List<FlashSaleProductDto>
)