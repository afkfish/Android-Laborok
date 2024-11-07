package hu.bme.aut.android.todo.ui.common

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
import hu.bme.aut.android.todo.ui.model.PriorityUi
import kotlinx.datetime.LocalDate
import org.junit.Rule
import org.junit.Test

class TodoEditorTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
    @Test
    fun testPriorityFieldsAreShownWhenClickedOnPriorityIcon() {
        composeTestRule.activity.setContent {
            TodoEditor(
                titleValue = "Test Editor",
                titleOnValueChange = {},
                descriptionValue = "Test Description",
                descriptionOnValueChange = {},
                selectedPriority = PriorityUi.None,
                onPrioritySelected = {},
                pickedDate = LocalDate.parse("2022-01-01"),
                onDatePickerClicked = {}
            )
        }

        composeTestRule.onNodeWithText("none").assertIsDisplayed()

        composeTestRule.onNodeWithText("low").assertIsNotDisplayed()
        composeTestRule.onNodeWithText("medium").assertIsNotDisplayed()
        composeTestRule.onNodeWithText("high").assertIsNotDisplayed()

        composeTestRule.onNode(hasTestTag("priorityDropdown")).performClick()

        composeTestRule.onNodeWithText("low").assertIsDisplayed()
        composeTestRule.onNodeWithText("medium").assertIsDisplayed()
        composeTestRule.onNodeWithText("high").assertIsDisplayed()
    }
}
// JOYAXJ