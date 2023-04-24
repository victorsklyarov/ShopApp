package com.zeroillusion.shopapp.feature.home.domain.use_case

import com.zeroillusion.shopapp.core.domain.utils.Resource
import com.zeroillusion.shopapp.feature.home.domain.model.Products
import com.zeroillusion.shopapp.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetProducts(
    private val repository: HomeRepository
) {
    operator fun invoke(): Flow<Resource<Products>> {
        return repository.getProducts()
    }
}