package com.zeroillusion.shopapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListFlashSale (
    @SerializedName("flash_sale")
    @Expose
    var flashSaleList: List<FlashSale>
)