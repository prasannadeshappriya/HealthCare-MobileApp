package com.a14roxgmail.prasanna.healthcareapp;

import android.content.Context;
import android.util.Log;

import com.a14roxgmail.prasanna.healthcareapp.DAO.tokenDAO;
import com.a14roxgmail.prasanna.healthcareapp.Database.database;
import com.a14roxgmail.prasanna.healthcareapp.Models.token;

/**
 * Created by Prasanna Deshappriya on 12/21/2016.
 */
public class tokenAuth {

        //to store the token send from the server
        public static String token_number = "";

        //set the token number
        //check if the token number exist
        public static void setTokenNumber(String token_number, Context context, String nic){
            database sqldb = new database(context,constants.database_name,null,1);
            tokenDAO token_dao = new tokenDAO(context,sqldb.getDatabase());
            if(token_dao.isExistsToken(nic.toUpperCase().toString())) {
                Log.i(constants.TAG,"Token already exist in the database");
            }else{
                Log.i(constants.TAG,"Token is not exist in the database");
                token_dao.insert(new token(nic.toUpperCase().toString(),token_number));
            }
        }

        //get the token value
        public static String getTokenNumber(Context context, String nic){
            database sqldb = new database(context,constants.database_name,null,1);
            tokenDAO token_dao = new tokenDAO(context,sqldb.getDatabase());
            token tokenChr = token_dao.getToken(nic.toUpperCase().toString());
            Log.i(constants.TAG,"Sending token value");
            return tokenChr.getToken();
        }


}
