package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import show.joketeller.JokeActivity;


public class MainActivity extends ActionBarActivity {


    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        if(BuildConfig.FLAVOR.equals("free"))   {
            requestNewInterstitial();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        if(progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void tellJoke(View view){
        final Context context = this;
        if(BuildConfig.FLAVOR.equals("paid"))   {
            FetchJokeTask fetchJokeTask = new FetchJokeTask(this, new FetchJokeTask.AsyncTaskCB() {
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
            findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        } else if (BuildConfig.FLAVOR.equals("free"))   {
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
                    findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

                }
            });
            if(mInterstitialAd.isLoaded())  {
                mInterstitialAd.show();
            }
            else {
                Toast.makeText(this, "Need Internet Connection or a Paid Version!", Toast.LENGTH_LONG).show();
            }


        }


    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


}
