package com.futuretraxex.joketank;

/** The object model for the data we are sending through endpoints */
public class Joke {

    private String joke;

    public String getData() {
        return joke;
    }

    public void setData(String data) {
        joke = data;
    }
}