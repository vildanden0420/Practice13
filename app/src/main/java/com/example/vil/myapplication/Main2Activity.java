package com.example.vil.myapplication;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    EditText et;
    ImageView imageView;
    int count = 0;
    ViewFlipper flipper;
    Boolean checked = true;
    int interval = 1000;
    TextView tv;
    CustomTask task;
    int seconds = 0;
    String food[] = {"피자", "햄버거", "도넛"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        et = (EditText)findViewById(R.id.editText);
        imageView = (ImageView)findViewById(R.id.image);
        flipper = (ViewFlipper)findViewById(R.id.flip);
        tv = (TextView)findViewById(R.id.text);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xdcdcdc));


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageView.setVisibility(View.GONE);
                flipper.setVisibility(View.VISIBLE);
                flipper.setFlipInterval(interval);
                flipper.startFlipping();
                task = new CustomTask();
                task.execute(interval);
                tv.setText("시작부터 "+1+"초");
                if(et.getText().toString().equals("")){
                    interval= 1000;
                }else{
                    interval = Integer.parseInt(et.getText().toString())*1000;
                }


            }
        });




        flipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked){
                    checked=false;
                    flipper.stopFlipping();
                    task.cancel(true);
                    tv.setText(food[count] + "가 선택되었습니다. ("+(seconds+1)+"초)");
                }else{
                    checked=true;

                }
            }
        });



    }

    public void onClick(View v){
        imageView.setVisibility(View.VISIBLE);
        flipper.setVisibility(View.GONE);
        flipper.stopFlipping();
        flipper.setDisplayedChild(0);
        tv.setText("");
    }

    private class CustomTask extends AsyncTask<Integer, Integer, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Integer... params) {
            for(int i = 1;i<101;i++){
                if(isCancelled() ==true){
                    return null;
                }
                try {
                    Thread.sleep(1000);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            tv.setText("시작부터 "+(values[0]+1)+"초");
            count = values[0]%3;
            seconds = values[0];
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
