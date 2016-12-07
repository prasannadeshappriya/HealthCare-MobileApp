package com.a14roxgmail.prasanna.healthcareapp;

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
import com.a14roxgmail.prasanna.healthcareapp.Database.database;
import org.json.JSONException;

public class login_activity extends AppCompatActivity {
    private Button btnSignIn;
    private EditText etEmail;
    private EditText etPassword;
    private TextView lnkSignUp;

    private database sqlite_db;
    final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();


        String autoLogIn = sqlite_db.checkAutoLogin();
        if (!autoLogIn.equals("")) {
            Intent i = new Intent(this, home_activity.class);
            Log.i(TAG, "Fucking insert :- " + autoLogIn);
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
        return true;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void signIn(){
        if(validate()) {
            String email = etEmail.getText().toString();
            if (sqlite_db.isExistsUser(email)) {
                final server_request request = new server_request(3, this);
                request.set_server_url("http://192.168.43.101/request/HealthCareServer.php");
                //request.set_server_url("http://10.0.2.2/request/HealthCareServer.php");
                request.setParams("SIGN_IN", "PROCESS");
                request.setParams(etEmail.getText().toString(), "EMAIL");
                request.setParams(etPassword.getText().toString(), "PASSWORD");
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
                    public void onFinish() {
                        response_data_process(request);
                        Log.i(TAG, "Fucking here onFinish is reach");
                        pd.dismiss();
                    }

                    @Override
                    public void onTick(long millisLeft) {
                    }
                };
                timer.start();

            } else {
                Toast.makeText(this, "Email address is not exists", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void response_data_process(server_request request){
        String response = request.getResponse();
        if(response.equals("")) {
            Toast.makeText(this, "Server timeout", Toast.LENGTH_LONG).show();
        }else{
            if(response.equals("0")){
                Toast.makeText(this, "Incorrect password or email address", Toast.LENGTH_LONG).show();
            }else if(response.equals("1")){
                String email = etEmail.getText().toString();
                sqlite_db.login_detail_update(email,"1");
                Intent i = new Intent(this,home_activity.class);
                i.putExtra("EMAIL_ADDRESS",email);
                startActivity(i);
                this.finish();
                overridePendingTransition(R.anim.left_in,R.anim.left_out);
            }else{
                Toast.makeText(this, response, Toast.LENGTH_LONG).show();
            }

        }
    }

    public void init(){
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        etEmail = (EditText) findViewById(R.id.etEmail);
        lnkSignUp = (TextView) findViewById(R.id.lnkSignUp);
        etPassword = (EditText) findViewById(R.id.etPassword);
        sqlite_db = new database(this,"Health_Care",null,1);
    }
}