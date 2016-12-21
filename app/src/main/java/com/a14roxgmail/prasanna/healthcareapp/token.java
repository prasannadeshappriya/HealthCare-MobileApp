package com.a14roxgmail.prasanna.healthcareapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Prasanna Deshappriya on 12/20/2016.
 */
public class token {
    //to store the token send from the server
    public static String token_number = "";
    public static final String TEMP_FILE_NAME = "health_care.txt";

    //for test purposes only - fake token number
    public static final String fake_token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwibmljIjoiOTQyMjIyNDY3diJ9.gUMNfA0esi2Oeql4oezPrTAPvGSSb0Fjv-3MowXOvQM";

    //set the token number
    public static void setTokenNumber(String token_number, Context context){

        final File tempFile;
        FileWriter writer=null;
        //Getting Cache Directory
        File cDir = context.getCacheDir();
        //Getting a reference to temporary file, if created earlier
        tempFile = new File(cDir.getPath() + "/" + TEMP_FILE_NAME) ;
        try {
            writer = new FileWriter(tempFile);
            //Saving the contents to the file
            writer.write(token_number);
            //Closing the writer object
            writer.close();
            Log.i(constants.TAG,"Token written to the file path :- " + tempFile.getPath());
        } catch (IOException e) {
            Log.i(constants.TAG,"Error when written to the file :- " + e.toString());
            e.printStackTrace();
        }

        token.token_number = token_number;
    }

    //get the token number
    public static String getTokenNumber(Context context){
        /*
        final File tempFile;
        //Getting Cache Directory
        File cDir = context.getCacheDir();
        String strLine="";
        StringBuilder text = new StringBuilder();
        try {
            tempFile = new File(cDir.getPath() + "/" + TEMP_FILE_NAME) ;
            FileReader fReader = new FileReader(tempFile);
            BufferedReader bReader = new BufferedReader(fReader);

            //Reading the contents of the file , line by line
            while( (strLine=bReader.readLine()) != null  ){
                text.append(strLine+"\n");
            }
            Log.i("TAG","Read From the file :- " + text);
            fReader.close();
            bReader.close();
            token_number = text.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return token_number;
        */
        //return token.token_number;
        return "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwibmljIjoiMTExMTExMTExdiJ9.IjBNrLGgWaZbMlm-LacHUr0IB-kNpRfo2QdHbCEf3oI";
    }
}
