package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import show.joketeller.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener{

    private Button mTellJoke;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mTellJoke = (Button)root.findViewById(R.id.tell_joke);
        mTellJoke.setOnClickListener(this);


        return root;
    }

    @Override
    public void onClick(View v) {
        FetchJokeTask fetchJokeTask = new FetchJokeTask(getActivity(), new FetchJokeTask.AsyncTaskCB() {
            @Override
            public void onFinish(String joke) {
                Context context = getActivity();
                Intent jokeIntnet = new Intent(context, JokeActivity.class);
                jokeIntnet.putExtra("JOKE", joke);
                context.startActivity(jokeIntnet);
            }
        });
        Integer values[] = new Integer[1];
        values[0] = 0;
        fetchJokeTask.execute(values);
        ProgressBar progressBar = (ProgressBar) getView().findViewById(R.id.progress_bar);
        if(progressBar != null)   {
            progressBar.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ProgressBar progressBar = (ProgressBar) getView().findViewById(R.id.progress_bar);
        if(progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


}
