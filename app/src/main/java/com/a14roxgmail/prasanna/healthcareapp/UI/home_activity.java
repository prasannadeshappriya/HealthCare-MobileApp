package com.a14roxgmail.prasanna.healthcareapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.a14roxgmail.prasanna.healthcareapp.DAO.userDAO;
import com.a14roxgmail.prasanna.healthcareapp.Database.database;
import com.a14roxgmail.prasanna.healthcareapp.Fragments.*;
import com.a14roxgmail.prasanna.healthcareapp.R;
import com.a14roxgmail.prasanna.healthcareapp.Services.sync_service;
import com.a14roxgmail.prasanna.healthcareapp.constants;

import org.json.JSONException;
import org.json.JSONObject;

public class home_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView tvEmail;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private String signInNic ="";
    private database sqldb;
    private userDAO user_dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent i = new Intent(getApplicationContext(),sync_service.class);
        i.putExtra("args","SYNC");
        startService(i);

        home_fragment home = new home_fragment();
        FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
        fragTrans.replace(R.id.frmMain,home);
        fragTrans.commit();

        sqldb = new database(this, constants.database_name,null,1);
        user_dao = new userDAO(this,sqldb.getDatabase());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i(constants.TAG,"startup");
        Bundle Param = getIntent().getExtras();
        if(Param.getString("user")==null){
            signInNic = Param.getString("nic");
        }else{
            try {
                JSONObject objUser = new JSONObject(Param.getString("user"));
                signInNic = objUser.getString("nic");
            } catch (JSONException e) {
                Log.i(constants.TAG,"Error while passing JSONObject [" + e.toString() + "]");
            }
        }


        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        tvEmail = (TextView)header.findViewById(R.id.tvEmail);
        tvEmail.setText(signInNic);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            toolbar.setTitle("Home");
            home_fragment home = new home_fragment();
            FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
            fragTrans.replace(R.id.frmMain,home);
            fragTrans.commit();
        } else if (id == R.id.nav_patient) {
            toolbar.setTitle("Patients");
            patients_fragment patients = new patients_fragment();
            FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
            fragTrans.replace(R.id.frmMain,patients);
            fragTrans.commit();
        } else if (id == R.id.nav_diseases) {
            toolbar.setTitle("Diseases");
            diseases_fragment diseases = new diseases_fragment();
            FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
            fragTrans.replace(R.id.frmMain,diseases);
            fragTrans.commit();
        } else if (id == R.id.nav_manage) {
            toolbar.setTitle("Settings");
            settings_fragment settings = new settings_fragment();
            FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
            fragTrans.replace(R.id.frmMain, settings);
            fragTrans.commit();
        }else if (id == R.id.nav_profile) {
            toolbar.setTitle("Profile");
            profile_fragment profile = new profile_fragment();
            FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
            fragTrans.replace(R.id.frmMain,profile);
            fragTrans.commit();
        }else if (id == R.id.nav_medical_reoprts) {
            toolbar.setTitle("Reports");
            mreports_fragment report = new mreports_fragment();
            FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
            fragTrans.replace(R.id.frmMain,report);
            fragTrans.commit();
        } else if (id == R.id.nav_signout) {
            user_dao.signout(signInNic);
            Intent i = new Intent(this,login_activity.class);
            startActivity(i);
            this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
