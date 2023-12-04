package com.example.myapplication22_sqlite.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication22_sqlite.entities.Product

@Dao
interface ProductDao {
    // all dao methods are created by Room during the build
    // // generated Java code here : /app/build/generated/source/kapt/...
    @Insert
    fun insertProduct(product: Product)

    @Query("SELECT * FROM products WHERE productName = :name")
    fun findProduct(name: String): List<Product>

    @Query("DELETE FROM products WHERE productName = :name")
    fun deleteProduct(name: String)
    // @Delete exists but an entity has to be passed in param, not a string

    @Query("SELECT * FROM products")
    fun getAllProducts() : LiveData<List<Product>>
}