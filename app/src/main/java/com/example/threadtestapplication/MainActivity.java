package com.example.threadtestapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String netString = "";
    private TextView tv_test;
    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                String strdata = (String) msg.obj;
                tv_test.setText(strdata);
                Toast.makeText(MainActivity.this, "主线程接收成功", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_test = findViewById(R.id.tv_test);
        Button btn_start = findViewById(R.id.btn_start);
        Button btn_jump = findViewById(R.id.btn_jump);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startthread();
            }
        });

    }

    private void startthread() {
        //采用匿名类的方式来定义一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                netString = getStringfromnet();
                //假装从网络上获取信息
                Message message = new Message();
                message.what = 0;
                message.obj = netString;
                handler.sendMessage(message);
            }
        }).start();

        Toast.makeText(MainActivity.this, "子线程开始执行", Toast.LENGTH_LONG).show();

    }

    public String getStringfromnet() {
        String result = "";
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 100; i++) {
            stringBuilder.append("字符串" + i + '\n');
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        result = stringBuilder.toString();

        return result;
    }
}