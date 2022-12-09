package com.androiddevs.shoppinglisttestingbestpracticeplackner.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.androiddevs.shoppinglisttestingbestpracticeplackner.MainCoroutineRuleAndroidTest
import com.androiddevs.shoppinglisttestingbestpracticeplackner.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import com.androiddevs.shoppinglisttestingbestpracticeplackner.R
import com.androiddevs.shoppinglisttestingbestpracticeplackner.getOrAwaitValue
import com.androiddevs.shoppinglisttestingbestpracticeplackner.repositories.FakeShoppingRepositoryAndroidTest
import com.google.common.truth.Truth

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingItemFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRuleAndroidTest()

    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setup() {
        hiltRule.inject()
        viewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        verify(navController).popBackStack()
    }

    @Test
    fun clickShoppingImage_navigateToImagePickFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.ivShoppingImage)).perform(click())

        verify(navController).navigate(
            AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
        )
    }

    @Test
    fun pressBackButton_curImageUrlIsEmpty() {
        val navController = mock(NavController::class.java)

        viewModel.setCurrentImageUrl("")

        val url = viewModel.currentImageUrl.getOrAwaitValue()

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        Truth.assertThat(url).isEqualTo("")
    }
}