package com.a14roxgmail.prasanna.healthcareapp.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class signup_activity extends AppCompatActivity {
    private TextView lnkSignin;
    private Button btnSignUp;

    private EditText etName;
    private EditText etDateOfBirth;
    private EditText etNIC;
    private Spinner  lstDistrict;
    private EditText etPhone;
    private EditText etPassword;
    private EditText etRePassword;
    private Spinner lstRole;

    private userDAO user_dao;
    private database sqlite_db;

    public void init(){
        lstDistrict = (Spinner)findViewById(R.id.lstDistrict);
        lstRole = (Spinner) findViewById(R.id.lstRole);
        lnkSignin = (TextView) findViewById(R.id.lnkSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        etName = (EditText) findViewById(R.id.etName);
        etDateOfBirth = (EditText) findViewById(R.id.etDateOfBirth);
        etNIC = (EditText) findViewById(R.id.etNic);
        etPhone = (EditText) findViewById(R.id.etMobile);
        etPassword = (EditText) findViewById(R.id.etSuPassword);
        etRePassword =(EditText) findViewById(R.id.etRePassword);
        sqlite_db = new database(this,constants.database_name,null,1);
        user_dao = new userDAO(this,sqlite_db.getDatabase());

        String[] items; //items for the dropdown list
        ArrayAdapter<String> adapter; //adapter for dropdown lists
        items = new String[]{"patient", "Health Officer", "Medical Officer"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        lstRole.setAdapter(adapter);

        items = new String[]{"Colombo", "Gampaha", "Kandy", "Matale", "Nuwara Eliya","Hambantota" ,"Badulla"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        lstDistrict.setAdapter(adapter);
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

    public boolean nic_Validate(String nic){
        return true;
    }

    public boolean validate(){
        //validation process
        boolean con = true;
        if(!nic_Validate(etNIC.getText().toString())){
            etNIC.setError("NIC is invalied");
            con = false;
        }
        if(etName.getText().toString().equals("")){
            etName.setError("Name cannot left blank");
            con = false;
        }
        if(etDateOfBirth.getText().toString().equals("")){
            etDateOfBirth.setError("Date of birth cannot left blank");
            con = false;
        }
        if(etNIC.getText().toString().equals("")){
            etNIC.setError("Nic field cannot left blank");
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
            final server_request request = new server_request(8,this);
            String role = lstRole.getSelectedItem().toString();
            if(role.equals("patient")){
                Log.i(constants.TAG,"Role is patient");
                request.set_server_url(constants.server_signup_patient_url);
            }else if(role.equals("Health Officer")){
                Log.i(constants.TAG,"Role is health officer");
                request.set_server_url(constants.server_signup_health_officer_url);
            }else if(role.equals("Medical Officer")){
                Log.i(constants.TAG,"Role is medical officer");
                request.set_server_url(constants.server_signup_medical_officer_url);
            }

            request.setParams(etName.getText().toString(),"name");
            request.setParams(etDateOfBirth.getText().toString(),"dob");
            request.setParams(constants.FAKE_TAG,"token");
            request.setParams(String.valueOf(lstDistrict.getSelectedItemId()+1),"district_id");
            request.setParams(etNIC.getText().toString(),"nic");
            request.setParams(etPassword.getText().toString(),"password");
            request.setParams(lstRole.getSelectedItem().toString(),"role");
            request.setParams(etPhone.getText().toString(),"phone");
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
            Log.i(constants.TAG,"Signup Server Response :- " + response);
            JSONObject objResponse = null;
            try {
                objResponse = new JSONObject(response);
                String message = "";
                if(objResponse.isNull("error")){
                    Toast.makeText(getApplicationContext(), "Account Successfully Created", Toast.LENGTH_LONG).show();
                    String nic = etNIC.getText().toString();
                    user_dao.insert(new user(nic,0));
                    Intent i = new Intent(this, login_activity.class);
                    i.putExtra("NIC", nic);
                    startActivity(i);
                    this.finish();
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                }else{
                    message = objResponse.getString("error");
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i(constants.TAG,"Jason error :- " + e.toString());
            }


/*
            String nic = etNIC.getText().toString();
            user_dao.insert(new user(nic,0));
            Intent i = new Intent(this, login_activity.class);
            i.putExtra("NIC", nic);
            startActivity(i);
            this.finish();
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
            */
        }
    }
}
