package com.junhee.android.httpurlconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SubActivity extends AppCompatActivity implements TaskInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Remote.newTask(this);
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void postExecute(String result) {
        // button.. progress...

    }
}
