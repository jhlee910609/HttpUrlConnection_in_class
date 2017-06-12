package com.junhee.android.httpurlconnection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TaskInterface{

    TextView textView;
    String url = "www.google.com";
    // String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        Task.newTask(this);
        //textView.setText(result);

//        try {
//            String url = "http://google.com";
//            String result = getData(url);  // <- 받아온 예외를 처리해주는 try/catch 구문이 필요해서 만들어 줌. 없으면 빨간줄 뜸
//
//            Log.i("Result", result);
//        } catch (Exception e){
//            Log.e("Error", e.toString());
//            Toast.makeText(this, "네트워크 오류" + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
    }


    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void postExecute(String result) {
        textView.setText(result);

    }
}
