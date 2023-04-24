package com.zeroillusion.shopapp.feature.home.domain.repository

import com.zeroillusion.shopapp.core.domain.utils.Resource
import com.zeroillusion.shopapp.feature.home.domain.model.Products
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getProducts(): Flow<Resource<Products>>
}