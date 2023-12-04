package com.example.myapplication22_sqlite


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication22_sqlite.ProductRoomDatabase
import com.example.myapplication22_sqlite.dao.ProductDao
import com.example.myapplication22_sqlite.entities.Product
import com.example.myapplication22_sqlite.repository.ProductRepository

class MainvViewModel(application: Application) : ViewModel(){
    val allProducts: LiveData<List<Product>>
    private val repository: ProductRepository
    val searchResults: MutableLiveData<List<Product>>

    init {
        val productDb = ProductRoomDatabase.getInstance(application)
        // can't be declared before > ProductRoomDatabase is abstract

        val productDao = productDb.productDao()
        // can't be declared before too > productDb.productDao() is abstract

        repository = ProductRepository(productDao)
        allProducts = repository.allProducts
        searchResults = repository.searchResults
    }

    fun insertProduct(product: Product) {
        repository.insertProduct(product)
    }
    fun findProduct(name: String) {
        repository.findProduct(name)
    }
    fun deleteProduct(name: String) {3
    repository.deleteProduct(name)}
}