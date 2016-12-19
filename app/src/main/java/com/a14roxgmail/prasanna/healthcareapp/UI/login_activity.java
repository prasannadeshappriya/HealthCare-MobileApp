package com.a14roxgmail.prasanna.healthcareapp.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.a14roxgmail.prasanna.healthcareapp.DAO.userDAO;
import com.a14roxgmail.prasanna.healthcareapp.Database.database;
import com.a14roxgmail.prasanna.healthcareapp.Models.user;
import com.a14roxgmail.prasanna.healthcareapp.R;
import com.a14roxgmail.prasanna.healthcareapp.constants;
import com.a14roxgmail.prasanna.healthcareapp.server_request;

import org.json.JSONException;
import org.json.JSONObject;

public class login_activity extends AppCompatActivity {
    private Button btnSignIn;
    private EditText etEmail;
    private EditText etPassword;
    private TextView lnkSignUp;

    private userDAO user_dao;
    private database sqlite_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();


        String autoLogIn = user_dao.checkAutoLogin();
        if (!autoLogIn.equals("")) {
            Intent i = new Intent(this, home_activity.class);
            Log.i(constants.TAG, "Fucking insert :- " + autoLogIn);
            i.putExtra("EMAIL_ADDRESS", autoLogIn);
            startActivity(i);
            this.finish();
        }


        Bundle emailParam = getIntent().getExtras();
        if(emailParam!=null){
            String emailParameter = emailParam.getString("EMAIL_ADDRESS");
            etEmail.setText(emailParameter);
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

    private boolean validate(){
        boolean con = true;
        if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).matches()){
            etEmail.setError("Email address is invalid");
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
            final server_request request = new server_request(3, this);
            request.set_server_url(constants.server_login_url);
            request.setParams("SIGN_IN", "PROCESS");
            request.setParams(etEmail.getText().toString(), "email");
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
                    String token = objResponse.getString("token");
                    constants.setToken(token);
                    Log.i(constants.TAG,"token :- " + token);
                    //input email address
                    String email = etEmail.getText().toString();
                    //check the email address with the sqlite database
                    if (!user_dao.isExistsUser(email)) {
                        user_dao.insert(new user(email,1)); //insert the  new email to the sqlite database,
                                                            // if the email is not exist on the embedded database
                    }
                    Intent i = new Intent(this,home_activity.class);
                    i.putExtra("EMAIL_ADDRESS",email);startActivity(i);
                    this.finish();
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                }else{
                    Toast.makeText(this, "Invalid login details", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(this, "Jason Error while connecting to the server", Toast.LENGTH_LONG).show();
                Log.i(constants.TAG,"Jason Error :- " + e.toString());
                e.printStackTrace();
            }
        }
    }

    public void init(){
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        etEmail = (EditText) findViewById(R.id.etEmail);
        lnkSignUp = (TextView) findViewById(R.id.lnkSignUp);
        etPassword = (EditText) findViewById(R.id.etPassword);
        sqlite_db = new database(this,constants.database_name,null,1);
        user_dao = new userDAO(this,sqlite_db.getDatabase());
    }
}
