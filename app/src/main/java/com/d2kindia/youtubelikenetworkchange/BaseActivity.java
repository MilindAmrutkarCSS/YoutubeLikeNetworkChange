package com.d2kindia.youtubelikenetworkchange;

import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class BaseActivity extends AppCompatActivity implements INetworkChanged {

    private TextView tv_check_connection;
    private NetworkChangeReceiver networkChangeReceiver;
    private FrameLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkChangeReceiver = new NetworkChangeReceiver();
        networkChangeReceiver.setListener(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        RelativeLayout parent = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_base, null);

        container = parent.findViewById(R.id.container);
        tv_check_connection = parent.findViewById(R.id.tv_check_connection);
        getLayoutInflater().inflate(layoutResID, container, true);
        super.setContentView(parent);
    }

    public void showSnack(boolean isConnected) {

        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.GREEN;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar.make(container, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();


    }

    public void setConnection(boolean value){
        if(value){
            tv_check_connection.setText(getString(R.string.we_are_back));
            tv_check_connection.setBackgroundColor(ContextCompat.getColor(this,R.color.colorConnected));
            tv_check_connection.setTextColor(Color.WHITE);

            Handler handler = new Handler();
            Runnable delayRunnable = new Runnable() {
                @Override
                public void run() {
                    tv_check_connection.setVisibility(View.GONE);
                }
            };
            handler.postDelayed(delayRunnable, 3000);


        }else {
            tv_check_connection.setVisibility(View.VISIBLE);
            tv_check_connection.setText(getString(R.string.cannot_connect));
            tv_check_connection.setBackgroundColor(ContextCompat.getColor(this,R.color.colorDisConnected));
            tv_check_connection.setTextColor(Color.WHITE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
}
