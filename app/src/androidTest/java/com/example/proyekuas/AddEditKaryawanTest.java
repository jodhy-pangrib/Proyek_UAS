package com.example.proyekuas;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddEditKaryawanTest {

    @Rule
    public ActivityTestRule<AddEditKaryawan> mActivityTestRule = new ActivityTestRule<>(AddEditKaryawan.class);

    @Test
    public void addEditKaryawanTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btn_save), withText("SIMPAN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton.perform(click());
        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.et_nama),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_nama),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.et_nama),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_nama),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("sapi"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btn_save), withText("SIMPAN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton2.perform(click());
        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.et_noKaryawan),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_noKaryawan),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("0345"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btn_save), withText("SIMPAN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton3.perform(click());
        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.et_noKaryawan), withText("0345"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_noKaryawan),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText6.perform(click());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.et_noKaryawan), withText("0345"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_noKaryawan),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("034"));

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.et_noKaryawan), withText("034"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_noKaryawan),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText8.perform(closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btn_save), withText("SIMPAN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton4.perform(click());
        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.et_noKaryawan), withText("034"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_noKaryawan),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText9.perform(click());

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.et_noKaryawan), withText("034"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_noKaryawan),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText10.perform(replaceText("03455"));

        ViewInteraction textInputEditText11 = onView(
                allOf(withId(R.id.et_noKaryawan), withText("03455"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_noKaryawan),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText11.perform(closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btn_save), withText("SIMPAN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton5.perform(click());
        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText12 = onView(
                allOf(withId(R.id.et_noKaryawan), withText("03455"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_noKaryawan),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText12.perform(click());

        ViewInteraction textInputEditText13 = onView(
                allOf(withId(R.id.et_noKaryawan), withText("03455"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_noKaryawan),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText13.perform(replaceText("1345"));

        ViewInteraction textInputEditText14 = onView(
                allOf(withId(R.id.et_noKaryawan), withText("1345"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_noKaryawan),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText14.perform(closeSoftKeyboard());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.btn_save), withText("SIMPAN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton6.perform(click());
        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText15 = onView(
                allOf(withId(R.id.et_umur),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_umur),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText15.perform(replaceText("19"), closeSoftKeyboard());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.btn_save), withText("SIMPAN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton7.perform(click());
        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText16 = onView(
                allOf(withId(R.id.et_umur), withText("19"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_umur),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText16.perform(click());

        ViewInteraction textInputEditText17 = onView(
                allOf(withId(R.id.et_umur), withText("19"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_umur),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText17.perform(replaceText("44"));

        ViewInteraction textInputEditText18 = onView(
                allOf(withId(R.id.et_umur), withText("44"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_umur),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText18.perform(closeSoftKeyboard());

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.btn_save), withText("SIMPAN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton8.perform(click());
        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText19 = onView(
                allOf(withId(R.id.et_umur), withText("44"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_umur),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText19.perform(click());

        ViewInteraction textInputEditText20 = onView(
                allOf(withId(R.id.et_umur), withText("44"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_umur),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText20.perform(replaceText("21"));

        ViewInteraction textInputEditText21 = onView(
                allOf(withId(R.id.et_umur), withText("21"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_umur),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText21.perform(closeSoftKeyboard());

        ViewInteraction materialButton9 = onView(
                allOf(withId(R.id.btn_save), withText("SIMPAN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton9.perform(click());
        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction materialAutoCompleteTextView = onView(
                allOf(withId(R.id.ed_jenisKelamin),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_jenisKelamin),
                                        0),
                                1),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(click());

        onView(withText("Perempuan"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        ViewInteraction materialButton10 = onView(
                allOf(withId(R.id.btn_save), withText("SIMPAN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton10.perform(click());
        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction materialAutoCompleteTextView2 = onView(
                allOf(withId(R.id.ed_role),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_role),
                                        0),
                                1),
                        isDisplayed()));
        materialAutoCompleteTextView2.perform(click());

        onView(withText("Office Boy"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        ViewInteraction materialButton11 = onView(
                allOf(withId(R.id.btn_save), withText("SIMPAN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton11.perform(click());
        onView(isRoot()).perform(waitFor(3000));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static ViewAction waitFor(long delay) {
        return new ViewAction() {
            @Override public Matcher<View> getConstraints() {
                return isRoot();
            }
            @Override public String getDescription() {
                return "wait for " + delay + "milliseconds";
            }
            @Override public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(delay);
            }
        };
    }
}
