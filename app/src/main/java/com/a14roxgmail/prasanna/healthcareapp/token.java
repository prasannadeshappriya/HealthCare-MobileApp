package com.a14roxgmail.prasanna.healthcareapp;

/**
 * Created by Prasanna Deshappriya on 12/20/2016.
 */
public class token {
    //to store the token send from the server
    public static String token_number = "";

    //for test purposes only - fake token number
    public static final String fake_token = "fk";

    //set the token number
    public static void setTokenNumber(String token_number){
        token.token_number = token_number;
    }

    //get the token number
    public static String getTokenNumber(){
        return token.token_number;
    }
}