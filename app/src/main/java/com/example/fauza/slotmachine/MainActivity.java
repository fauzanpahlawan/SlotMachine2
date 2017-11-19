package com.example.fauza.slotmachine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView textViewInfo;
    private TextView textViewWin;
    private Button buttonSpin;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private int slotCounter = 0;

    private ArrayList<Integer> slotImages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slotImages = new ArrayList<>();
        slotImages.add(R.drawable.one);
        slotImages.add(R.drawable.two);
        slotImages.add(R.drawable.three);
        slotImages.add(R.drawable.four);
        slotImages.add(R.drawable.five);
        slotImages.add(R.drawable.six);
        slotImages.add(R.drawable.jackpot);

        imageView1 = findViewById(R.id.imageView_slot1);
        imageView2 = findViewById(R.id.imageView_slot2);
        imageView3 = findViewById(R.id.imageView_slot3);

        buttonSpin = findViewById(R.id.button_spin);

        textViewInfo = findViewById(R.id.textView_info);
        textViewWin = findViewById(R.id.textView_win);


        buttonSpin.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);

        textViewInfo.setVisibility(View.INVISIBLE);
        textViewWin.setVisibility(View.INVISIBLE);


    }

    RandomTask randomTask1;
    RandomTask randomTask2;
    RandomTask randomTask3;
    boolean cheat = true; //set TRUE or FALSE to active Cheat
    int cheatValue = R.drawable.jackpot;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_spin:
                slotCounter = 0;
                if (randomTask1 == null || randomTask1.getStatus() == AsyncTask.Status.FINISHED) {
                    randomTask1 = new RandomTask(this.slotImages, this.imageView1);
                    executeAsyncTaskPararrel(randomTask1);
                }
                if (randomTask2 == null || randomTask2.getStatus() == AsyncTask.Status.FINISHED) {
                    randomTask2 = new RandomTask(this.slotImages, this.imageView2);
                    executeAsyncTaskPararrel(randomTask2);
                }
                if (randomTask3 == null || randomTask3.getStatus() == AsyncTask.Status.FINISHED) {
                    randomTask3 = new RandomTask(this.slotImages, this.imageView3);
                    executeAsyncTaskPararrel(randomTask3);
                }

                buttonSpin.setVisibility(View.INVISIBLE);
                textViewInfo.setVisibility(View.VISIBLE);
                textViewWin.setVisibility(View.INVISIBLE);
                break;
            case R.id.imageView_slot1:
                if (randomTask1 != null) {
                    randomTask1.cancel(true);
                }
                if (cheat) {
                    imageView1.setImageResource(cheatValue);
                    imageView1.setTag(cheatValue);
                }
                checkCounter();
                break;
            case R.id.imageView_slot2:
                if (randomTask2 != null) {
                    randomTask2.cancel(true);
                }
                if (cheat) {
                    imageView2.setImageResource(cheatValue);
                    imageView2.setTag(cheatValue);
                }
                checkCounter();
                break;
            case R.id.imageView_slot3:
                if (randomTask3 != null) {
                    randomTask3.cancel(true);
                }
                if (cheat) {
                    imageView3.setImageResource(cheatValue);
                    imageView3.setTag(cheatValue);
                }
                checkCounter();
                break;
        }
    }

    private void checkCounter() {
        slotCounter++;
        if (slotCounter == 3) {
            textViewInfo.setVisibility(View.INVISIBLE);
            buttonSpin.setVisibility(View.VISIBLE);
            textViewWin.setVisibility(View.VISIBLE);
            winCondition();
            slotCounter = 0;
        }
    }

    private void executeAsyncTaskPararrel(RandomTask randomTask) {
        randomTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void winCondition() {
        int indicator = (int) imageView1.getTag();
        if (checkMatch(imageView2, imageView3) && indicator == slotImages.get(0)) {
            textViewWin.setText(R.string.WinOne);
        } else if (checkMatch(imageView2, imageView3) && indicator == slotImages.get(1)) {
            textViewWin.setText(R.string.WinTwo);
        } else if (checkMatch(imageView2, imageView3) && indicator == slotImages.get(2)) {
            textViewWin.setText(R.string.WinThree);
        } else if (checkMatch(imageView2, imageView3) && indicator == slotImages.get(3)) {
            textViewWin.setText(R.string.WinFour);
        } else if (checkMatch(imageView2, imageView3) && indicator == slotImages.get(4)) {
            textViewWin.setText(R.string.WinFive);
        } else if (checkMatch(imageView2, imageView3) && indicator == slotImages.get(5)) {
            textViewWin.setText(R.string.WinSix);
        } else if (checkMatch(imageView2, imageView3) && indicator == slotImages.get(6)) {
            textViewWin.setText(R.string.WinSeven);
        } else {
            textViewWin.setText(R.string.WinZonk);
        }
    }

    private boolean checkMatch(ImageView... ImageView) {
        boolean flag = true;
        for (ImageView iv : ImageView) {
            if ((int) iv.getTag() != (int) imageView1.getTag()) {
                flag = false;
            }
        }
        return flag;
    }
}
