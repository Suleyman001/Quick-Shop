package nje.hu.quickshop;



import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import nje.hu.quickshop.ui.registration.RegistrationActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class RegistrationActivityTest {

    @Rule
    public ActivityScenarioRule<RegistrationActivity> rule =
            new ActivityScenarioRule<>(RegistrationActivity.class);

    @Test
    public void canFillFormAndClickSave() {
        // Name
        onView(withId(R.id.register_nameText))
                .perform(typeText("Melik Test"), closeSoftKeyboard());

        // Email
        onView(withId(R.id.register_emailText))
                .perform(typeText("melik@example.com"), closeSoftKeyboard());

        // Password
        onView(withId(R.id.register_passwordText))
                .perform(typeText("123456"), closeSoftKeyboard());

        // Save butonuna tıklama
        onView(withId(R.id.register_saveButton)).perform(click());
    }

    @Test
    public void saveButton_isDisplayed() {
        onView(withId(R.id.register_saveButton))
                .check(matches(isDisplayed()));
    }
}
