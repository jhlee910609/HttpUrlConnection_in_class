package com.junhee.android.httpurlconnection;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JunHee on 2017. 6. 12..
 */

public class Remote {

    // 바로 사용하기 위해 메소드를 static으로 선언함
    // 인자로 받은 url로 networkd를 통해 데이터를 가져오는 함수
    // 무조건 리턴값 String
    public static String getData(String url) throws Exception { // <- 요쳥한 곳에서 Exception 처리를 해줌
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



    //  thread를 생성
    // AsyncTask는 return값을 받아올 수 없음
    // 계속 작업을 진행하는 중이라 값을 받아올 수가 절대 없는 구조임
    // 여기에 넘어오는 클래스가 taskinterface를 구현했다는 이야기

    public static void newTask(final TaskInterface taskInterface) {

        // 타입만 맞으면 실행해줄게
        // 객체지향적 설계
        // 알아서 강제 캐스팅되어 아래 작업들을 수행함
        // 단, 전제는 implement를 의무적으로 구현되어 있어야 함
        // TODO 고민해보기 그리고 구현해보기

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
                taskInterface.postExecute(result);
            }

            // 1. 내가 넘긴 url을 parms 배열에 넣어서
        }.execute(taskInterface.getUrl());
    }
}
