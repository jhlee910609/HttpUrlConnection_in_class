package com.junhee.android.httpurlconnection.domain;

/**
 * Created by JunHee on 2017. 6. 13..
 */

public class Data {

    private SearchPublicToiletPOIService SearchPublicToiletPOIService;

    public SearchPublicToiletPOIService getSearchPublicToiletPOIService() {
        return SearchPublicToiletPOIService;
    }

    public void setSearchPublicToiletPOIService(SearchPublicToiletPOIService SearchPublicToiletPOIService) {
        this.SearchPublicToiletPOIService = SearchPublicToiletPOIService;
    }

    @Override
    public String toString() {
        return "ClassPojo [SearchPublicToiletPOIService = " + SearchPublicToiletPOIService + "]";
    }
}
