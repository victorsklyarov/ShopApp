package com.zeroillusion.shopapp.model

data class BlockLatest (
    val title: String,
    val items: List<Latest>
): DisplayableItem