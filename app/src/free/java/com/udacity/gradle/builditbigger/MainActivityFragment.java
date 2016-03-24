package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import show.joketeller.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener{

    InterstitialAd mInterstitialAd;
    Button mTellJoke;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        if(BuildConfig.FLAVOR.equals("free"))   {
            requestNewInterstitial();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."

        mTellJoke = (Button)root.findViewById(R.id.tell_joke);
        mTellJoke.setOnClickListener(this);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);




        return root;
    }

    @Override
    public void onClick(View v) {
        final Context context = getActivity();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                FetchJokeTask fetchJokeTask = new FetchJokeTask(context, new FetchJokeTask.AsyncTaskCB() {
                    @Override
                    public void onFinish(String joke) {
                        Intent jokeIntnet = new Intent(context, JokeActivity.class);
                        jokeIntnet.putExtra("JOKE", joke);
                        context.startActivity(jokeIntnet);
                    }
                });
                Integer values[] = new Integer[1];
                values[0] = 0;
                fetchJokeTask.execute(values);
                ProgressBar progressBar = (ProgressBar) getView().findViewById(R.id.progress_bar);
                if(progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                }


            }
        });
        if(mInterstitialAd.isLoaded())  {
            mInterstitialAd.show();
        }
        else {
            Toast.makeText(context, "Need Internet Connection or a Paid Version!", Toast.LENGTH_LONG).show();
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
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