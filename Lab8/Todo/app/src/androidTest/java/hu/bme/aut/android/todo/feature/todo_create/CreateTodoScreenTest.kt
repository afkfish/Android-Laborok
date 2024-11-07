package hu.bme.aut.android.todo.feature.todo_create

import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import hu.bme.aut.android.todo.MainActivity
import org.junit.Rule
import org.junit.Test

// JOYAXJ
class CreateTodoScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
    @Test
    fun testDatePickerDialogIsShownWhenClickedOnDatePickerIcon() {
        composeTestRule.activity.setContent {
            CreateTodoScreen(
                onNavigateBack = { })
        }

        composeTestRule.onNodeWithText("Select date").assertIsNotDisplayed()

        composeTestRule.onNode(hasTestTag("datePickerIcon")).performClick()

        composeTestRule.onNodeWithText("Select date").assertIsDisplayed()
    }
}