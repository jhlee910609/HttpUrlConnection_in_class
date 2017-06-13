package com.junhee.android.httpurlconnection.domain;

/**
 * Created by JunHee on 2017. 6. 13..
 */

public class SearchPublicToiletPOIService {

    // codebeautify 구조 보면서 공부하기

    private RESULT RESULT;
    // 직접 변수에 값을 넣을 수 있기 때문에 class를 따로 만들지 않는다
    private String list_total_count;

    private Row[] row;

    public RESULT getRESULT() {
        return RESULT;
    }

    public void setRESULT(RESULT RESULT) {
        this.RESULT = RESULT;
    }

    public String getList_total_count() {
        return list_total_count;
    }

    public void setList_total_count(String list_total_count) {
        this.list_total_count = list_total_count;
    }

    public Row[] getRow() {
        return row;
    }

    public void setRow(Row[] row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "ClassPojo [RESULT = " + RESULT + ", list_total_count = " + list_total_count + ", row = " + row + "]";
    }
}
