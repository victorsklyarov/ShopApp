package com.zeroillusion.shopapp.feature.home.data.repository

import com.zeroillusion.shopapp.core.domain.utils.Resource
import com.zeroillusion.shopapp.feature.home.data.local.ProductDao
import com.zeroillusion.shopapp.feature.home.data.remote.ProductsApi
import com.zeroillusion.shopapp.feature.home.domain.model.Products
import com.zeroillusion.shopapp.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class HomeRepositoryImpl(
    private val api: ProductsApi,
    private val dao: ProductDao
) : HomeRepository {

    override fun getProducts(): Flow<Resource<Products>> = flow {
        emit(Resource.Loading())

        val latestProducts = dao.getLatestProductList().map { it.toLatestProduct() }
        val flashSaleProducts = dao.getFlashSaleProductList().map { it.toFlashSaleProduct() }

        val products = Products(
            latestProductList = latestProducts,
            flashSaleProductList = flashSaleProducts
        )
        emit(Resource.Loading(data = products))

        try {
            val remoteLatestProducts = api.getLatestList().latest
            val remoteFlashSaleProducts = api.getFlashSaleList().flashSale
            dao.deleteLatestProductList()
            dao.deleteFlashSaleProductList()
            dao.insertLatestProductList(remoteLatestProducts.map { it.toLatestProductEntity() })
            dao.insertFlashSaleProductList(remoteFlashSaleProducts.map { it.toFlashSaleProductEntity() })
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "An unexpected error occurred",
                    data = products
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server. Check your internet connection.",
                    data = products
                )
            )
        }

        val newLatestProducts = dao.getLatestProductList().map { it.toLatestProduct() }
        val newFlashSaleProducts = dao.getFlashSaleProductList().map { it.toFlashSaleProduct() }
        emit(
            Resource.Success(
                Products(
                    latestProductList = newLatestProducts,
                    flashSaleProductList = newFlashSaleProducts
                )
            )
        )
    }
}