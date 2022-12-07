package com.androiddevs.shoppinglisttestingbestpracticeplackner.repositories

import androidx.lifecycle.LiveData
import com.androiddevs.shoppinglisttestingbestpracticeplackner.data.local.ShoppingItem
import com.androiddevs.shoppinglisttestingbestpracticeplackner.data.remote.responses.ImageResponse
import com.androiddevs.shoppinglisttestingbestpracticeplackner.other.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}
