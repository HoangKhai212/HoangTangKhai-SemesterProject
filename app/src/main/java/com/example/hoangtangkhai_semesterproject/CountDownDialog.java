package com.example.hoangtangkhai_semesterproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.hoangtangkhai_semesterproject.model.Exercise;

public class CountDownDialog extends Dialog implements android.view.View.OnClickListener {


    private Activity c;
    private Dialog d;
    private TextView nameEx, numSets, numReps, countDown, date;
    private Exercise e;
    private int time;
    private String sdate;
    private Button close;


    public CountDownDialog(Activity a, Exercise e, int time, String sdate) {
        super(a);
        this.c = a;
        this.e = e;
        this.time = time;
        this.sdate = sdate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTitle("CountDown Time"); //ChronometerLabel
        setContentView(R.layout.countdown_dialog);
        this.setCanceledOnTouchOutside(false); // make it modal
        init();
        new CountDownTimer(time*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                countDown.setText(""+millisUntilFinished / 1000);
            }

            public void onFinish() {
                countDown.setText("Time out");
            }
        }.start();
        nameEx.setText(e.getNameEx());
        numSets.setText(e.getNumSets() + "");
        numReps.setText(e.getNumReps()+ "");
//        countDown.setText(time + "");
        date.setText(sdate);
        close.setOnClickListener(this);

    }
    private void init(){
        nameEx = findViewById(R.id.nameEx);
        numSets = findViewById(R.id.numSets);
        numReps = findViewById(R.id.numReps);
        date = findViewById(R.id.date);
        countDown = findViewById(R.id.countDown);
        close = findViewById(R.id.closeDialog);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.closeDialog){
            dismiss();
        }
    }
}