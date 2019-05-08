package com.shm.dim.delcontrol.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shm.dim.delcontrol.R;
import com.shm.dim.delcontrol.asyncTask.RestRequestDelegate;
import com.shm.dim.delcontrol.asyncTask.RestRequestTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;

    private EditText mEmail, mPassword;

    private String accountId, companyId, name, address, email,
            phoneNumber, password;

    private SharedPreferences mSharedPreferences;

    private static final String AССOUNT_PREFERENCES = "ACCOUNT_INFO",
            AССOUNT_ID = "AССOUNT_ID",
            AССOUNT_COMPANY_ID = "AССOUNT_COMPANY_ID",
            AССOUNT_NAME = "AССOUNT_NAME",
            AССOUNT_ADDRESS = "AССOUNT_ADDRESS",
            AССOUNT_EMAIL = "AССOUNT_EMAIL",
            AССOUNT_PHONE = "AССOUNT_PHONE",
            AССOUNT_PASSWORD = "AССOUNT_PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
    }

    private void initComponents() {
        mProgressBar = findViewById(R.id.progress);
        mEmail = findViewById(R.id.email_address);
        mPassword = findViewById(R.id.password);
    }

    private boolean isEmptyValuesOnEditViews() {
        return (mEmail.getText().toString().isEmpty() ||
                mPassword.getText().toString().isEmpty());
    }

    private boolean isEmailFormatCorrect(String str) {
        String reg = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
        return str.matches(reg);
    }

    private void createDialogMsg(String msg) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage(msg).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                    }
                });
        AlertDialog ad = b.create();
        ad.show();
    }

    private void initValues() {
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();
    }

    public void onClickLogin(View view) {
        if(!isEmptyValuesOnEditViews()) {
            initValues();
            if (!isEmailFormatCorrect(email)) {
                createDialogMsg(getResources().getString(R.string.check_email_address));
            } else {
                sendRestRequest("http://192.168.43.234:46002/api/CourierAccounts/" +
                                email + "/" + password,
                        "GET", "");
            }
        } else {
            createDialogMsg(getResources().getString(R.string.fill_in_all_fields));
        }
    }

    public void onClickRegister(View view) {
        startActivity(new Intent(this, RegistrationActivity.class));
        finish();
    }

    protected void sendRestRequest(String url, String method, String body) {
        mProgressBar.setVisibility(View.VISIBLE);
        RestRequestTask request =
                new RestRequestTask(new RestRequestDelegate() {
                    @Override
                    public void executionFinished(int responseCode, String responseBody) {
                        onRestRequestFinished(responseCode, responseBody);
                    }
                });
        request.execute(url, method, body);
    }

    private void onRestRequestFinished(int responseCode, String responseBody) {
        mProgressBar.setVisibility(View.GONE);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            saveAccountInfo(responseBody);
            Toast.makeText(this, getResources().getString(R.string.complete),
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this,
                    getResources().getString(R.string.check_network_state) +
                            " (code: " + String.valueOf(responseCode) + ")",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void saveAccountInfo(String responseBody) {
        mSharedPreferences = getSharedPreferences(AССOUNT_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        try {
            JSONObject jObj = new JSONObject(responseBody);
            accountId = jObj.getString("Id");
            companyId = jObj.getString("CompanyId");
            name = jObj.getString("Name");
            address = jObj.getString("Address");
            phoneNumber = jObj.getString("Phone");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editor.putString(AССOUNT_ID, accountId);
        editor.putString(AССOUNT_COMPANY_ID, companyId);
        editor.putString(AССOUNT_NAME, name);
        editor.putString(AССOUNT_ADDRESS, address);
        editor.putString(AССOUNT_EMAIL, email);
        editor.putString(AССOUNT_PHONE, phoneNumber);
        editor.putString(AССOUNT_PASSWORD, password);
        editor.apply();
    }

}
