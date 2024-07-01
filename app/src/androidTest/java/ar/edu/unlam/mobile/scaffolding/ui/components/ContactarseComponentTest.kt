package ar.edu.unlam.mobile.scaffolding.ui.components

import android.content.Intent
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import ar.edu.unlam.mobile.scaffolding.ui.components.publicationDetails.ShowContact
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContactarseComponentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun teardown() {
        Intents.release()
    }

    @Test
    fun shouldStartCallIntent_whenCallButtonIsClicked() {
        composeTestRule.setContent {
            ShowContact(phoneNumber = 123456789) {}
        }
        // verifica que exista el button "llamar"
        composeTestRule.onNodeWithText("Llamar").assertExists()

        // simulo el click en el button
        composeTestRule.onNodeWithText("Llamar").performClick()
        // verifica que se llame al intent
        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_DIAL))
    }

    @Test
    fun shouldStartWhatsAppIntent_whenWhatsAppButtonIsClicked() {
        composeTestRule.setContent {
            ShowContact(phoneNumber = 123456789) {}
        }
        // verifica que exista el button "WhatsApp"
        composeTestRule.onNodeWithText("WhatsApp").assertExists()

        // simulo el click en el button
        composeTestRule.onNodeWithText("WhatsApp").performClick()
        // verifica que se llame al intent
        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_VIEW))
    }
}
