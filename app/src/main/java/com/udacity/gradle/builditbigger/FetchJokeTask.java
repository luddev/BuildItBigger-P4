package com.udacity.gradle.builditbigger;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.futuretraxex.joketank.jokeAPI.JokeAPI;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import show.joketeller.JokeActivity;

/**
 * Created by lud on 23-03-2016.
 */
public class FetchJokeTask extends AsyncTask<Integer, Integer, String > {

    public interface AsyncTaskCB {
        public void onFinish(String joke);
    }

    private JokeAPI mJokeAPI;
    private AsyncTaskCB mTaskCB;

    private Context mContext;
    public FetchJokeTask(Context context, AsyncTaskCB taskCB) {
        super();
        mContext = context;
        mTaskCB = taskCB;
        mJokeAPI = null;

    }

    @Override
    protected String doInBackground(Integer... params) {
        if(mJokeAPI == null)    {
            JokeAPI.Builder builder = new JokeAPI.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("http://192.168.56.2:8080/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });
            mJokeAPI = builder.build();

        }

        try {
            return mJokeAPI.joke().execute().getData();
        }
        catch(IOException iox)  {
            return iox.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        super.onPostExecute(joke);
        mTaskCB.onFinish(joke);

    }
}
