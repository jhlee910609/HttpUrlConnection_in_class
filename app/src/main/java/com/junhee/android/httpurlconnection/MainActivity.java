package com.junhee.android.httpurlconnection;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    // String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        String url = "http://google.com";
        newTask(url);
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

    //  thread를 생성
    // AsyncTask는 return값을 받아올 수 없음
    // 계속 작업을 진행하는 중이라 값을 받아올 수가 절대 없는 구조임
    public void newTask(String url) {


        new AsyncTask<String, Void, String>() {
            // backGround 처리를 할 함수를 오버라이드해줌
            @Override
            protected String doInBackground(String... params) {

                String result = "";

                try {
                    // 2. 여기애 배열 값을 넣어줌.
                    // 2.1 getData(); 함수를 가져옴
                    result = getData(params[0]);
                    Log.i("NETWORK", result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                textView.setText(result);
            }

            // 1. 내가 넘긴 url을 parms 배열에 넣어서
        }.execute(url);
    }


    // 인자로 받은 url로 networkd를 통해 데이터를 가져오는 함수
    // 무조건 리턴값 String
    public String getData(String url) throws Exception { // <- 요쳥한 곳에서 Exception 처리를 해줌
        String result = "";

        // network 처리
        // 1. 요청처리 request
        // 1.1. url 객체 만들기
        URL serverUrl = new URL(url);

        // 1.2. 연결객체 생성
        HttpURLConnection con = (HttpURLConnection) serverUrl.openConnection();// url 객체에서 연결을 꺼낸다.
        // 1.3. http method 결정 (RESTAPI 4개 명령어)
        con.setRequestMethod("GET");

        // 2. 응답처리 Response
        // 2.1. 응답코드 분석
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // 정상적인 코드 처리

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String temp = null;
            while ((temp = br.readLine()) != null) {
                result += temp;
            }
        } else {
            // 각자 호출 측으로 Exception을 만들어서 넘겨 줄 것, 오류처리
            // Exception을 강제로 발생하여 상위로 보내줌
            Log.e("NETWORK", "Error_code = " + responseCode);
        }
        return result;
    }

}
