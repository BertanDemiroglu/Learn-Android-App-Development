package com.bertan.learnandroidappdevelopment

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File


/**
 * Tests the UI of [MainFragment]
 *
 * @constructor Create empty Main fragment test
 */
@RunWith(AndroidJUnit4:: class)
class MainFragmentTest: TestCase() {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * Tests if items are added properly on view and also if wrong inputs are not possible.
     */
    @Test
    fun addItem(){
        onView(withId(R.id.floatingActionButton)).perform(click())
        // Enter item name and count in the dialog
        onView(withId(R.id.editText1)).perform(replaceText("Apple"))
        onView(withId(R.id.editText2)).perform(replaceText("3"))
        // Click on the "Add" button in the dialog
        onView(withText("Add")).perform(click())

        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.editText1)).perform(replaceText("Banana"))
        onView(withId(R.id.editText2)).perform(replaceText("100"))
        onView(withText("Add")).perform(click())

        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.editText1)).perform(replaceText(""))
        onView(withId(R.id.editText2)).perform(replaceText("11"))
        onView(withText("Add")).perform(click())

        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.editText1)).perform(replaceText("Orange"))
        onView(withId(R.id.editText2)).perform(replaceText(""))
        onView(withText("Add")).perform(click())

        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.editText1)).perform(replaceText("Kiwi"))
        onView(withId(R.id.editText2)).perform(replaceText("-1"))
        onView(withText("Add")).perform(click())

        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.editText1)).perform(replaceText("Bread"))
        onView(withId(R.id.editText2)).perform(replaceText("120"))
        onView(withText("Close")).perform(click())


        //verify if the items are added to the list.
        onView(withText("Apple")).check(matches(isDisplayed()))
        onView(withText("3")).check(matches(isDisplayed()))

        onView(withText("Banana")).check(matches(isDisplayed()))
        onView(withText("100")).check(matches(isDisplayed()))

        //verify if items are not added with wrong inputs
        onView(withId(R.id.lvTodoList)).check(matches(not(hasDescendant(withText("")))))

        onView(withId(R.id.lvTodoList)).check(matches(not(hasDescendant(withText("Orange")))))

        onView(withId(R.id.lvTodoList)).check(matches(not(hasDescendant(withText("Kiwi")))))

        onView(withId(R.id.lvTodoList)).check(matches(not(hasDescendant(withText("Bread")))))
        onView(withId(R.id.lvTodoList)).check(matches(not(hasDescendant(withText("120")))))
    }

    /**
     * Tests if item gets deleted from view.
     */
    @Test
    fun deleteItem(){
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.editText1)).perform(replaceText("Banana"))
        onView(withId(R.id.editText2)).perform(replaceText("100"))
        onView(withText("Add")).perform(click())

        onView(withText("Banana")).perform(longClick())
        onView(withText("Delete")).perform(click())

        onView(withText("Banana")).check(doesNotExist())
    }

    /**
     * Tests if all items get deleted from view.
     */
    @Test
    fun deleteAll(){
        onView(withId(R.id.floatingActionButton)).perform(click())
        onView(withId(R.id.editText1)).perform(replaceText("Banana"))
        onView(withId(R.id.editText2)).perform(replaceText("100"))
        onView(withText("Add")).perform(click())

        onView(withId(R.id.action_delete)).perform(click())
        onView(withText("Confirm")).perform(click())
        onView(withId(R.id.lvTodoList)).check(matches(hasChildCount(0)))
    }

    /**
     * Tests if shopping list gets saved and loaded correctly as json file.
     */
    @Test
    fun saveAndLoadShoppingItemsJsonFile() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val items = arrayListOf(
            Pair("Banana", "1"),
            Pair("Apple", "2"),
            Pair("Kiwi", "3")
        )

        // Save shopping items
        MainFragment().saveShoppingItemsToJsonFile(context, items)

        // Load shopping list
        var loadedItems = MainFragment().loadShoppingItemsFromJsonFile(context)

        // Check if loaded shopping list matches saved shopping list
        assertEquals(items.size, loadedItems.size)
        assertEquals(items, loadedItems)

        // Delete file, check if file exists
        val file = File(context.filesDir, "shopping_items.json")
        assertTrue(file.exists())
        file.delete()
        loadedItems = MainFragment().loadShoppingItemsFromJsonFile(context)
        assertEquals(loadedItems.size, 0)
        assertNotEquals(items, loadedItems)
    }


}