package com.zeroillusion.shopapp.feature.home.presentation

import com.zeroillusion.shopapp.feature.home.domain.model.FlashSaleProduct
import com.zeroillusion.shopapp.feature.home.domain.model.LatestProduct

data class HomeState(
    val latestProductItems: List<LatestProduct> = emptyList(),
    val flashSaleProductItems: List<FlashSaleProduct> = emptyList(),
    val isLoading: Boolean = false
)