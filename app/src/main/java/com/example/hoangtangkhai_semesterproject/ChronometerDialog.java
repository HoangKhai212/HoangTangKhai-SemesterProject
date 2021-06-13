package com.example.hoangtangkhai_semesterproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;


public class ChronometerDialog extends Dialog implements android.view.View.OnClickListener {

    private Activity c;
    private Dialog d;
    private Button startstop, exit, reset;
    private Chronometer chrono;
    private long startTime = 0;
    private long stopTime = 0;
    private boolean chronoStarted = false;
    private boolean chronoResetted = false;

    public ChronometerDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTitle("Chronometer"); //ChronometerLabel
        setContentView(R.layout.chronometer_dialog);
        this.setCanceledOnTouchOutside(false); // make it modal
        init();
        startstop.setOnClickListener(this);
        exit.setOnClickListener(this);
        reset.setOnClickListener(this);
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();
        startTime = SystemClock.elapsedRealtime();
        chronoStarted = true;

        startstop.setText("Stop");

    }
    private void init(){
        startstop = findViewById(R.id.btnStart);
        exit = findViewById(R.id.btnExit);
        reset = findViewById(R.id.btnReset);
        chrono = findViewById(R.id.chronoValue);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                if (chronoStarted) {
                    chrono.stop();
                    stopTime = SystemClock.elapsedRealtime();
                    chronoStarted = false;
                    startstop.setText("Start");
                } else {
                    if (chronoResetted) {
                        startTime = SystemClock.elapsedRealtime();
                        chrono.setBase(startTime);
                    } else {
                        startTime = SystemClock.elapsedRealtime() - (stopTime - startTime);
                        chrono.setBase(startTime);
                    }
                    chrono.start();
                    chronoStarted = true;
                    startstop.setText("Stop");
                }
                chronoResetted = false;
                break;
            case R.id.btnReset:
                startTime = SystemClock.elapsedRealtime();
                chrono.setBase(startTime);
                chrono.setText("00:00");
                chronoResetted = true;
                break;
            case R.id.btnExit:
                chrono.stop();
                chronoStarted = false;
                chrono.setText("00:00");
                startstop.setText("Start");
                dismiss();
                break;
            default:
                break;
        }
    }
}


