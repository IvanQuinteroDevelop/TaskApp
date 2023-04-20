package com.navi.taskapp.addtasks.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class TaskScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenDialogGetATrue_thenShowDialog() {
        composeTestRule.setContent {
            AddTaskDialog(show = true, onDismiss = {}, onTaskAdded = {})
        }

        composeTestRule.onNodeWithTag("dialogTask").assertIsDisplayed()
    }

    @Test
    fun whenDialogGetAFalse_thenDoNotShowDialog() {
        composeTestRule.setContent {
            AddTaskDialog(show = false, onDismiss = {}, onTaskAdded = {})
        }

        composeTestRule.onNodeWithTag("dialogTask").assertDoesNotExist()
    }
}