package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import java.util.concurrent.ExecutionException;

/**
 * Created by lud on 24-03-2016.
 */
public class AsyncTaskTest extends AndroidTestCase {

    public void testJokeFetchTask() {
        FetchJokeTask fetchJokeTask = new FetchJokeTask(getContext(), new FetchJokeTask.AsyncTaskCB() {
            @Override
            public void onFinish(String joke) {
                assertEquals(joke.length() > 0, true);
                assertEquals("Punny Joke ?? true",joke, "Punny joke!");
            }
        });
        Integer[] values = new Integer[1];

        fetchJokeTask.execute(values);


    }
}
