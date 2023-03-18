package com.zeroillusion.shopapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zeroillusion.shopapp.model.*
import com.zeroillusion.shopapp.repository.MainRepository
import kotlinx.coroutines.*
import java.net.UnknownHostException

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    //val latestList = MutableLiveData<List<Latest>>()
    //val flashSaleList = MutableLiveData<List<FlashSale>>()
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()

    private val _displayedItems = MutableLiveData<List<DisplayableItem>>()
    val displayableItems: LiveData<List<DisplayableItem>> = _displayedItems

    fun getPageContent() {
        job = CoroutineScope(Dispatchers.IO).launch {
            try {
                val displayableItems = ArrayList<DisplayableItem>()
                val responseLatest = mainRepository.getLatest()
                val responseFlashSale = mainRepository.getFlashSale()
                withContext(Dispatchers.Main) {
                    if ((responseLatest.isSuccessful) and (responseFlashSale.isSuccessful)) {
                        //latestList.postValue(responseLatest.body()!!.latestList)
                        //flashSaleList.postValue(responseFlashSale.body()!!.flashSaleList)
                        displayableItems.add(BlockLatest("Latest", responseLatest.body()!!.latestList))
                        displayableItems.add(BlockFlashSale("Flash Sale", responseFlashSale.body()!!.flashSaleList))
                        displayableItems.add(BlockLatest("Brands", responseLatest.body()!!.latestList))
                        loading.value = false
                    } else {
                        onError("Error : ${responseLatest.message()}; ${responseFlashSale.message()}")
                    }
                }
                _displayedItems.postValue(displayableItems)
            } catch (e: UnknownHostException) {
                onError("Отсутствует соединение с сервером")
            } catch (e: Exception) {
                onError("Error: $e")
            }
        }
    }

    private val _categoryItems = MutableLiveData<List<DisplayableItem>>()
    val categoryItems: LiveData<List<DisplayableItem>> = _categoryItems

    fun setCategory(categoryList: ArrayList<DisplayableItem>){
        _categoryItems.postValue(categoryList)
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}