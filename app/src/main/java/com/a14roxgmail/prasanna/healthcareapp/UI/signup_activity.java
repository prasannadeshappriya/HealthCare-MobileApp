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

public class signup_activity extends AppCompatActivity {
    private TextView lnkSignin;
    private Button btnSignUp;

    private EditText etName;
    private EditText etAddress;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPassword;
    private EditText etRePassword;

    private userDAO user_dao;
    private database sqlite_db;

    public void init(){
        lnkSignin = (TextView) findViewById(R.id.lnkSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        etName = (EditText) findViewById(R.id.etName);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etMobile);
        etPassword = (EditText) findViewById(R.id.etSuPassword);
        etRePassword =(EditText) findViewById(R.id.etRePassword);
        sqlite_db = new database(this,constants.database_name,null,1);
        user_dao = new userDAO(this,sqlite_db.getDatabase());
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
                        Log.i(constants.TAG,constants.getToken());
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
        //validation process
        boolean con = true;
        if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
            etEmail.setError("Email address is invalid");
            con = false;
        }
        if(etName.getText().toString().equals("")){
            etName.setError("Name cannot left blank");
            con = false;
        }
        if(etAddress.getText().toString().equals("")){
            etAddress.setError("Address cannot left blank");
            con = false;
        }
        if(etEmail.getText().toString().equals("")){
            etEmail.setError("Email address cannot left blank");
            con = false;
        }
        if(etPhone.getText().toString().equals("")){
            etPhone.setError("Phone number cannot left blank");
            con = false;
        }
        if(etPassword.getText().length()<4){
            etPassword.setError("Password should contain minimum 4 characters");
            con=false;
        }
        if(!etPassword.getText().toString().equals(etRePassword.getText().toString())){
            Toast.makeText(getApplicationContext(),"Passwords are not same",Toast.LENGTH_LONG).show();
            con = false;
        }
        if(con){
            return true;
        }else{
            return false;
        }
    }

    private void start_signup_process(){
        if(validate()){
            final server_request request = new server_request(6,this);
            request.set_server_url(constants.server_login_url);
            request.setParams("SIGN_UP","PROCESS");
            request.setParams(etName.getText().toString(),"NAME");
            request.setParams(etAddress.getText().toString(),"ADDRESS");
            request.setParams(etEmail.getText().toString(),"EMAIL");
            request.setParams(etPhone.getText().toString(),"PHONE");
            request.setParams(etPassword.getText().toString(),"PASSWORD");
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
                user_dao.insert(new user(email,0));
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
