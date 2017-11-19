package com.example.fauza.slotmachine;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.util.ArrayList;

public class RandomTask extends AsyncTask<Void, Integer, Void> {

    @SuppressLint("StaticFieldLeak")
    private
    ImageView imageView;
    private ArrayList<Integer> slotImages;

    RandomTask(ArrayList<Integer> slotImages, ImageView imageView) {
        this.imageView = imageView;
        this.slotImages = slotImages;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            while (true) {
                int number = (int) (Math.random() * 7);
                publishProgress(number);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int number = values[0];
        this.imageView.setImageResource(slotImages.get(number));
        imageView.setTag(slotImages.get(number));
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
