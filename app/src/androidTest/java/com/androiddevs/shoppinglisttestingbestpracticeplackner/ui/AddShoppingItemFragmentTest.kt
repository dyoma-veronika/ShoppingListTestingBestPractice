package com.androiddevs.shoppinglisttestingbestpracticeplackner.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.androiddevs.shoppinglisttestingbestpracticeplackner.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import com.androiddevs.shoppinglisttestingbestpracticeplackner.R
import com.androiddevs.shoppinglisttestingbestpracticeplackner.data.local.ShoppingItem
import com.androiddevs.shoppinglisttestingbestpracticeplackner.getOrAwaitValue
import com.androiddevs.shoppinglisttestingbestpracticeplackner.repositories.FakeShoppingRepositoryAndroidTest
import com.google.common.truth.Truth.assertThat
import com.androiddevs.shoppinglisttestingbestpracticeplackner.MainCoroutineRuleAndroidTest
import javax.inject.Inject

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

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    private lateinit var testViewModel: ShoppingViewModel

    @Before
    fun setup() {
        hiltRule.inject()
        testViewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
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
    fun clickInsertIntoDb_shoppingItemInsertedIntoDb() {
        launchFragmentInHiltContainer<AddShoppingItemFragment>(
            fragmentFactory = fragmentFactory
        ) {
            viewModel = testViewModel
        }

        onView(withId(R.id.etShoppingItemName)).perform(replaceText("shopping item"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("5"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("5.5"))
        onView(withId(R.id.btnAddShoppingItem)).perform(click())

        assertThat(testViewModel.shoppingItems.getOrAwaitValue())
            .contains(ShoppingItem("shopping item", 5, 5.5f, ""))
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

        testViewModel.setCurrentImageUrl("")

        val url = testViewModel.currentImageUrl.getOrAwaitValue()

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        assertThat(url).isEqualTo("")
    }
}