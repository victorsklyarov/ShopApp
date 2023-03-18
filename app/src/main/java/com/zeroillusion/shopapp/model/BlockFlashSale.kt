package com.zeroillusion.shopapp.model

data class BlockFlashSale (
    val title: String,
    val items: List<FlashSale>
): DisplayableItem