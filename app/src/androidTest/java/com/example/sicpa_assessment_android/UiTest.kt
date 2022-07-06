package com.example.sicpa_assessment_android

import androidx.test.core.app.launchActivity
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sicpa_assessment_android.ui.main.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiTest : BaseTest() {

    @Test
    fun `article_content`() {
        launchActivity<MainActivity>()
        waitForView(withText("Most Viewed")).perform(click())
        waitForView(withText("2022")).check(matches(isDisplayed()))
    }
}