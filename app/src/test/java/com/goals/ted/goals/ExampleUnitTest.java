package com.goals.ted.goals;

import android.content.Context;
import android.test.AndroidTestCase;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void titleIsCorrect() throws Exception {
        int id = 13;
        AndroidTestCase test = new AndroidTestCase();
        Context context = test.getContext();
        MyDB myDB = new MyDB(context);

        Goal goal = myDB.selectByID(id);
        assertNotNull(goal);
        String title = goal.getTitle();

        assertEquals(goal.getTitle(), "Read Programming");

    }
}