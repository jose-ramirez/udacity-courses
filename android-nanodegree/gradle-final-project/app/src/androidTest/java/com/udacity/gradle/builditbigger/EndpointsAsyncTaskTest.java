package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by jose on 14/01/18.
 */
@RunWith(AndroidJUnit4.class)
public class EndpointsAsyncTaskTest {

    @Test
    public void test(){
        try {
            AsyncTask<Context, Void, String> jokeTask = new EndpointsAsyncTask();
            Context context = InstrumentationRegistry.getContext();
            jokeTask.execute(context);
            String joke = (String)jokeTask.get(10, TimeUnit.SECONDS);
            assertNotNull(joke);
            assertTrue(joke.contains("chicken"));
        }catch(InterruptedException ex){
            fail(ex.getMessage());
        }catch(ExecutionException ex){
            fail(ex.getMessage());
        }catch(TimeoutException ex){
            fail("Timeout (probably) exceeded.");
        }
    }
}
