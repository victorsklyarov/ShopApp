package com.zeroillusion.shopapp.repository

import com.zeroillusion.shopapp.api.ApiService

class MainRepository constructor(private val apiService: ApiService) {
    suspend fun getLatest() = apiService.getLatest()
    suspend fun getFlashSale() = apiService.getFlashSale()
}