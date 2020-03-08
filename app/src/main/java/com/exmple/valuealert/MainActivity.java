package com.exmple.valuealert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int value[];
    TextView v1,v2,v3,v4,v5,tv;
    Button btnStart;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        v4 = findViewById(R.id.v4);
        v5 = findViewById(R.id.v5);
        btnStart = findViewById(R.id.btnStart);

        value = new int[]{50, 51, 52, 53, 54};
        values();
        Toast.makeText(this, "Click 'Start' To Start The Process", Toast.LENGTH_LONG).show();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==false) {
                    value = new int[]{50, 51, 52, 53, 54};
                    values();
                    MyCount myCount = new MyCount(120000, 1000);
                    myCount.start();
                    flag=true;

                }
                else{
                    Toast.makeText(MainActivity.this, "Timer has already started", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            tv.setText("0 second left");
            flag=false;
            calculation(value[0],1);
            calculation(value[1],2);
            calculation(value[2],3);
            calculation(value[3],4);
            calculation(value[4],5);
            Toast.makeText(MainActivity.this, "Finished", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long a = millisUntilFinished/1000+1;
            if(a%5 ==0 && a != 120){
                value[0] = value[0]+1;
                value[1] = value[1]-1;
                value[2] = value[2]+2;
                value[3] = value[3]-2;
                value[4] = value[4]+3;
            }
            values();
            tv.setText( a +" second left");

        }

    }
    private void values(){
        v1.setText(Integer.toString(value[0]));
        v2.setText(Integer.toString(value[1]));
        v3.setText(Integer.toString(value[2]));
        v4.setText(Integer.toString(value[3]));
        v5.setText(Integer.toString(value[4]));
    }

    //
    private void popupWindow(int value, int position) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(popupWindow.getContentView() , Gravity.CENTER, 0, 0);
        TextView tvResult = popupWindow.getContentView().findViewById(R.id.tvResult);
        tvResult.setText("Value of value "+position+ " is "+value+" and is out of bound.");

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    private void calculation(int value , int position){
        if(value>35||value<15){
            popupWindow( value, position);
        }
    }
}
