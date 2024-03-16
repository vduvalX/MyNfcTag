package com.vincentduval.mynfctag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    static TextView tv_title; static TextView tv_message;

    @Override protected void onCreate(Bundle savedInstanceState) {
        Log.d("VINCENT_TAG", "Main/onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_title = findViewById(R.id.tv_title); tv_message = findViewById(R.id.tv_message);
    }

    private BroadcastReceiver monPingReceiver = new BroadcastReceiver() {@Override public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        Log.d("VINCENT_TAG", "Main/monPingReceiver: message = " + message);
        tv_message.setText(message);
    }};

    @Override protected void onResume() {super.onResume(); IntentFilter filter = new IntentFilter("MaMessagerie"); registerReceiver(monPingReceiver, filter);}

    @Override protected void onPause() {super.onPause();unregisterReceiver(monPingReceiver);}

}