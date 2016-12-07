package com.a14roxgmail.prasanna.healthcareapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.a14roxgmail.prasanna.healthcareapp.Database.database;
import com.android.volley.NoConnectionError;

import org.json.JSONException;

public class signup_activity extends AppCompatActivity {
    private TextView lnkSignin;
    private Button btnSignUp;

    private EditText etName;
    private EditText etAddress;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPassword;
    private EditText etRePassword;

    private database sqlite_db;
    private final String TAG = "TAG";

    public void init(){
        lnkSignin = (TextView) findViewById(R.id.lnkSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        etName = (EditText) findViewById(R.id.etName);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etMobile);
        etPassword = (EditText) findViewById(R.id.etSuPassword);
        etRePassword =(EditText) findViewById(R.id.etRePassword);
        sqlite_db = new database(this,"Health_Care",null,1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
        lnkSignin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start_signin_activity();
                    }
                }
        );
        btnSignUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start_signup_process();
                    }
                }
        );
    }
    public void start_signin_activity(){
        Intent i = new Intent(this,login_activity.class);
        startActivity(i);
        this.finish();
        overridePendingTransition(R.anim.left_in,R.anim.left_out);
    }

    public boolean validate(){
        return true;
    }

    private void start_signup_process(){
        if(validate()){
            final server_request request = new server_request(6,this);
            request.set_server_url("http://192.168.43.101/request/HealthCareServer.php");
            //request.set_server_url("http://10.0.2.2/request/HealthCareServer.php");
            request.setParams("SIGN_UP","PROCESS");
            request.setParams(etName.getText().toString(),"NAME");
            request.setParams(etAddress.getText().toString(),"ADDRESS");
            request.setParams(etEmail.getText().toString(),"EMAIL");
            request.setParams(etPhone.getText().toString(),"PHONE");
            request.setParams(etPassword.getText().toString(),"PASSWORD");
            try {
                String req = request.sendRequest();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog pd = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
            pd.setIndeterminate(true);
            pd.setMessage("Authenticating..");
            pd.show();

            CountDownTimer timer = new CountDownTimer(2000, 1000) {
                @Override
                public void onFinish() {response_data_process(request); pd.dismiss();}

                @Override
                public void onTick(long millisLeft) {
                }
            };
            timer.start();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void response_data_process(server_request request){
        String response = request.getResponse();
        if(response.equals("")) {
            Toast.makeText(this, "Server timeout", Toast.LENGTH_LONG).show();
        }else{
            if (response.equals("Account successfully created")) {
                Toast.makeText(this, response, Toast.LENGTH_LONG).show();
                String email = etEmail.getText().toString();
                sqlite_db.login_detail_insert(email);
                Intent i = new Intent(this, login_activity.class);
                i.putExtra("EMAIL_ADDRESS", email);
                startActivity(i);
                this.finish();
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }else{
                Toast.makeText(this, response, Toast.LENGTH_LONG).show();
            }
        }
    }
}
