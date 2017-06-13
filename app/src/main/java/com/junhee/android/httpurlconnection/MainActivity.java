package com.junhee.android.httpurlconnection;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.junhee.android.httpurlconnection.domain.Data;
import com.junhee.android.httpurlconnection.domain.Row;

import java.util.ArrayList;
import java.util.List;


/*
   ============================[공공데이터 정보]===================================

   http://openAPI.seoul.go.kr:8088/66594148776a686c34306951484958/json/SearchPublicToiletPOIService/1/5/
   인증키 :

   */
// google map 사용하기 위해 임플리먼트
public class MainActivity extends AppCompatActivity implements TaskInterface, OnMapReadyCallback {

    ListView listView;

    TextView textView;
    String url = "";
    static final String URL_PREFIX = "http://openAPI.seoul.go.kr:8088/";
    static final String URL_CERT = "66594148776a686c34306951484958/";
    // 공공 데이터 종류가 바뀔 경우, 미드 부분만 만들어주면 됨
    static final String URL_MID = "json/SearchPublicToiletPOIService/";
    static final int OFFSET = 50;

    int pageBegin = 1;
    int pageEnd = 10;

    // 한 페이지에 불러오는 데이터의 수
    // String result;

    // 어댑터에서 사용할 데이터 공간
    // 메모리 공간이 바뀔 가능성이 제로가 됨
    final List<String> datas = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        listView = (ListView) findViewById(R.id.listView);

        // 1. 데이터
        // 1.1. 전역에서 초기화해주고, 아래에서 더해줌 -> 계속 저장함
        // 2. 어댑터
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        // 3. 어댑터 셋팅
        listView.setAdapter(adapter);

        // 맵을 셋팅
        FragmentManager manger = getSupportFragmentManager();
        // map을 app 화면 상에 올릴 수 있게 도와주는 객체 (LayoutInflater와 비슷한 역할)
        SupportMapFragment mapFragment = (SupportMapFragment) manger.findFragmentById(R.id.mapView);
        // 로드되면 onReady 호출하도록 강제 타입캐스팅 되서 알아서 됨
        mapFragment.getMapAsync(this);

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

    private void setPage(int page) {
        // 바뀌지 않는 값이기 떄문에 굳이 인자로 넘겨줄 필요가 없음
        pageEnd = page * OFFSET;
        pageBegin = pageEnd - OFFSET + 1;

    }

    private void setUrl(int begin, int end) {
        // 페이징 처리를 하는 소스코드
        // 나눈 것을 조합해서 만들어 줌

        // String
        // StringBuffer
        // StringBuilder

        // String 연산..... 복잡하지 않은 String 연산 알아서 StringBuilder로 알아서 해줌
        // String result = ("문자열" + "문자열") + "문자열";
        //                 ----------------
        //                  메모리 공간 할당
        //                 -----------------------------
        //                           메모리 공간 할당
//        StringBuffer sb = new StringBuffer(); // 동기화 지원
//        sb.append("문자열");
//        sb.append("문자열");
//
//        StringBuilder sb1 = new StringBuilder(); // 동기화 미지원
//        sb1.append("문자열");
//        sb1.append("문자열");
        url = URL_PREFIX + URL_CERT + URL_MID + begin + "/" + end;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void postExecute(String jsonString) {
        Gson gson = new Gson();
        // 1. json String -> class로 변환
        Data data = gson.fromJson(jsonString, Data.class);
        // 2. class를 -> jsonString으로 변환

        // 1. 총개수를 화면에 셋팅
        textView.setText("총 화장실 개수 : " + data.getSearchPublicToiletPOIService().getList_total_count());

        // 2. 건물의 이름을 listView에 셋팅
        Row[] rows = data.getSearchPublicToiletPOIService().getRow();

        // 네트웍에서 가져온 데이터를 꺼내서 datas에 담아줌
        for (Row row : rows) {
            datas.add(row.getFNAME());
            // row를 돌면서 좌표 상에 마커를 생성한다.
            // marker 생성하여 지도 위에 찍어줌
            MarkerOptions marker = new MarkerOptions();
            LatLng tempCoord = new LatLng(row.getY_WGS84(), row.getX_WGS84());
            marker.position(tempCoord);
            marker.title(row.getFNAME());

            myMap.addMarker(marker);
        }

        LatLng sinsa = new LatLng(37.516292, 127.020014);
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sinsa, 10));

        // 그리고 adapter를 갱신해줌
        // 기존 데이터가 없기 떄문에 .clear(); 메소드를 통해 초기화 해주지 않아도 됨
        adapter.notifyDataSetChanged();
    }

    GoogleMap myMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // 안해주면 nullPointerException
        myMap = googleMap;

        // 최초 호출 시, 첫 번째 집합을 불러온다
        setPage(1);
        setUrl(pageBegin, pageEnd);
        Remote.newTask(this);
    }
}
