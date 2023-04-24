package com.zeroillusion.shopapp.feature.home.domain.model

data class Products(
    val latestProductList: List<LatestProduct>,
    val flashSaleProductList: List<FlashSaleProduct>
)