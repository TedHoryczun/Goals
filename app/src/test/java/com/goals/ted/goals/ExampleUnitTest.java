package com.goals.ted.goals;

import android.content.Context;
import android.test.AndroidTestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(Parameterized.class)
public class ExampleUnitTest {
    private int one;
    private int two;

    public ExampleUnitTest(int one, int two){
        this.one = one;
        this.two = two;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        Object[][] data = new Object[][]{{1,2}, {5,4}, {1,1}};
        return Arrays.asList(data);
    }

    @Test
    public void multiple(){
        MyTest test = new MyTest();
        Assert.assertEquals(1, test.multiply(one, two));
    }
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
    public class MyTest{
        public int multiply(int p1, int p2){
            return p1*p2;
        }
    }
}