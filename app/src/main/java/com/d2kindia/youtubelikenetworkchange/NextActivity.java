package com.d2kindia.youtubelikenetworkchange;

import android.os.Bundle;

public class NextActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

    }

    @Override
    public void onNetworkChanged(boolean value) {
        //setConnection(value);
        showSnack(value);
    }
}
