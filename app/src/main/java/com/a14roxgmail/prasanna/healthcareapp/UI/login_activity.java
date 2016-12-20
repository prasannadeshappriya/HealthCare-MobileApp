package com.a14roxgmail.prasanna.healthcareapp.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.a14roxgmail.prasanna.healthcareapp.DAO.districtDAO;
import com.a14roxgmail.prasanna.healthcareapp.DAO.userDAO;
import com.a14roxgmail.prasanna.healthcareapp.Database.database;
import com.a14roxgmail.prasanna.healthcareapp.Models.user;
import com.a14roxgmail.prasanna.healthcareapp.R;
import com.a14roxgmail.prasanna.healthcareapp.constants;
import com.a14roxgmail.prasanna.healthcareapp.server_request;
import com.a14roxgmail.prasanna.healthcareapp.token;

import org.json.JSONException;
import org.json.JSONObject;

public class login_activity extends AppCompatActivity {
    private Button btnSignIn;
    private EditText etNic;
    private EditText etPassword;
    private TextView lnkSignUp;
    private String user_details; //to store the data send from the server

    private userDAO user_dao;
    private database sqlite_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        //check if some user is already login
        String autoLogIn = user_dao.checkAutoLogin();
        if (!autoLogIn.equals("")) {
            Intent i = new Intent(this, home_activity.class);
            Log.i(constants.TAG, "Auto login account detected (autoLogId):- " + autoLogIn);
            i.putExtra("nic", autoLogIn);
            //should have to put jason respond as an bundle extra
            this.finish();
            startActivity(i);
        }


        Bundle Param = getIntent().getExtras();
        if(Param!=null){
            String nicParamater = Param.getString("NIC");
            user_details = Param.getString("USER");
            etNic.setText(nicParamater);
            etPassword.requestFocus();
        }


        btnSignIn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        signIn();
                    }
                }
        );
        lnkSignUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start_signup_activity();
                    }
                }
        );
    }

    private void start_signup_activity() {
        Intent i =new Intent(this,signup_activity.class);
        startActivity(i);
        overridePendingTransition(R.anim.left_in,R.anim.left_out);
    }

    public boolean nic_Validate(String nic){
        return true;
    }

    private boolean validate(){
        boolean con = true;
        if(!nic_Validate(etNic.getText().toString())){
            etNic.setError("NIC number is invalid");
            con = false;
        }
        if(etNic.getText().toString().equals("")){
            con = false;
        }
        if(con) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void signIn(){
        if(validate()) {
            final server_request request = new server_request(2, this);
            request.set_server_url(constants.server_login_url);
            request.setParams(etNic.getText().toString(), "nic");
            request.setParams(etPassword.getText().toString(), "password");
            try {
                String req = request.sendPostRequest();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog pd = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
            pd.setIndeterminate(true);
            pd.setMessage("Authenticating..");
            pd.show();

            CountDownTimer timer = new CountDownTimer(2000, 1000) {
                @Override
                public void onFinish() {
                    response_data_process(request);
                    Log.i(constants.TAG, "OnFinish method triggered");
                    pd.dismiss();
                }

                @Override
                public void onTick(long millisLeft) {}
            };
            timer.start();
        }
    }

    public void response_data_process(server_request request){
        String response = request.getResponse();
        if(response.equals("")) {
            Toast.makeText(this, "Server timeout", Toast.LENGTH_LONG).show();
        }else{
            try {
                Log.i(constants.TAG,"Server Response :- " + response);
                JSONObject objResponse = new JSONObject(response);
                String server_auth = objResponse.getString("status");
                if (server_auth.equals("success")){
                    //get the token send by the server
                    String token_number = objResponse.getString("token");
                    token.setTokenNumber(token_number);
                    Log.i(constants.TAG,"Token :- " + token.getTokenNumber());

                    //input nic address
                    String nic = etNic.getText().toString();

                    //check the nic address with the sqlite database
                    //if user exist in sqlite database return true
                    JSONObject objUser = new JSONObject(objResponse.getString("user"));
                    if (user_dao.isExistsUser(nic)) {
                        user_dao.update(new user(nic,1,objUser.getString("role")));
                    }else{
                        user_dao.insert(new user(nic,1,objUser.getString("role"))); //insert the  new nic to the sqlite database,
                                                          // if the nic is not exist on the embedded database
                        if(objUser.getString("role").equals("patient")){
                            Log.i(constants.TAG, "assdsad");
                            user_dao.create_patient(
                                    objUser.getString("name"),
                                    objUser.getString("dob"),
                                    objUser.getString("nic"),
                                    objUser.getString("district_id"),
                                    "1"
                            );
                            Log.i(constants.TAG, "Anonimus user created");
                        }
                    }

                    Intent i = new Intent(this,home_activity.class);
                    i.putExtra("user",objUser.toString());
                    this.finish();
                    startActivity(i);
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                }else{
                    Toast.makeText(this, "Invalid login details", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(this, "JSONE Error while connecting to the server", Toast.LENGTH_LONG).show();
                Log.i(constants.TAG,"JSONE Error :- " + e.toString());
                e.printStackTrace();
            }
        }
    }

    public void init(){
        user_details = "";
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        etNic = (EditText) findViewById(R.id.etLoginNic);
        lnkSignUp = (TextView) findViewById(R.id.lnkSignUp);
        etPassword = (EditText) findViewById(R.id.etPassword);
        sqlite_db = new database(this,constants.database_name,null,1);
        user_dao = new userDAO(this,sqlite_db.getDatabase());

        districtDAO district_dao = new districtDAO(getApplicationContext(),sqlite_db.getDatabase());
        district_dao.init();
    }
}
