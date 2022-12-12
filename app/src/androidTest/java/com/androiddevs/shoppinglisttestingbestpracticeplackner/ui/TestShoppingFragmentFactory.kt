package com.androiddevs.shoppinglisttestingbestpracticeplackner.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.androiddevs.shoppinglisttestingbestpracticeplackner.adapters.ImageAdapter
import com.androiddevs.shoppinglisttestingbestpracticeplackner.adapters.ShoppingItemAdapter
import com.androiddevs.shoppinglisttestingbestpracticeplackner.repositories.FakeShoppingRepositoryAndroidTest
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class TestShoppingFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide: RequestManager,
    private val shoppingItemAdapter: ShoppingItemAdapter
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            AddShoppingItemFragment::class.java.name -> AddShoppingItemFragment(glide = glide)
            ShoppingFragment::class.java.name -> ShoppingFragment(
                shoppingItemAdapter,
                ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
            )
            else -> super.instantiate(classLoader, className)
        }
    }
}
