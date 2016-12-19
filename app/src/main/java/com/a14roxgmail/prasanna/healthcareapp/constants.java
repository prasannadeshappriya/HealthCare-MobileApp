package com.a14roxgmail.prasanna.healthcareapp;

/**
 * Created by Prasanna Deshappriya on 12/16/2016.
 */
public class constants {
    //database name
    public static final String database_name = "Health_Care";

    //java sprint tomcat server
    public static final String server_login_url = "http://192.168.8.100:8080/login";
    public static final String server_disease_search_url = "http://192.168.8.100:8080/diseases/search";

    //php localhost server
    //public static final String server_url = "http://192.168.43.101/request/HealthCareServer.php";

    //emulator
    //public static final String server_url = "http://10.0.2.2/request/HealthCareServer.php";

    //Log
    public static final String TAG = "TAG";

    //Internet connetion
    public static final int[] Internet_Array = {0,1};
    // 0 - Mobile Data
    // 1 - Wifi

    //Flag values for identify weather the data sync with the server or not
    public static final String OFFLINE_FLAG = "0";
    public static final String ONLINE_FLAG = "1";

    public static String token = "";
    public static final String FAKE_TAG = "fk";
    public static void setToken(String token){
        constants.token= token;
    }
    public static String getToken(){
        return constants.token;
    }

}


