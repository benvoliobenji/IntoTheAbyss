package com.example.intotheabyss

import android.content.Intent
import android.support.test.rule.ActivityTestRule
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import junit.framework.Assert.assertNotNull
import org.junit.Rule

class StartupSteps {
    @Rule
    @JvmField var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before("@startup-feature")
    fun setup() {
        activityTestRule.launchActivity(Intent())
        var activity = activityTestRule.activity
    }

    @After("@startup-feature")
    fun tearDown() {
        activityTestRule.finishActivity()
    }

    @Given("^I have a device with our app loaded$")
    fun i_have_app() {
        assertNotNull(activity)
    }

    @When("^I select the app$")
    fun select_app() {
        assertNotNull(activity)
    }

    @Then("^The app should start$")
    fun started_app() {
        assertNotNull(activity)
    }
}