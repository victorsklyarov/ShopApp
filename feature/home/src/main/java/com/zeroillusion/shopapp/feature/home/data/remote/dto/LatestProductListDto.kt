package com.zeroillusion.shopapp.feature.home.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LatestProductListDto(
    @SerializedName("latest")
    val latest: List<LatestProductDto>
)