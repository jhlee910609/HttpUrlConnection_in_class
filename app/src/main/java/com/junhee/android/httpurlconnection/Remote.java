package com.junhee.android.httpurlconnection;

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


}
