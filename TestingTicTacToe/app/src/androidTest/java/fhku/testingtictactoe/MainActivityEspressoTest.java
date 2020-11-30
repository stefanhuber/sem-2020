package fhku.testingtictactoe;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testPlayingGame() {
        onView(withId(R.id.button_1))
            .perform(click())
            .check(matches(withText("X")));

        onView(withId(R.id.button_2))
            .perform(click())
            .check(matches(withText("O")));

        onView(withId(R.id.button_3))
            .perform(click())
            .check(matches(withText("X")));

        onView(withId(R.id.gameState))
            .check(matches(withText("Zug Nummer: 4")));
    }
}