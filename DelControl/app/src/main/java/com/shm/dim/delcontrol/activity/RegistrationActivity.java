package com.shm.dim.delcontrol.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class RegistrationActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;

    private EditText mCompanyId, mName, mAddress, mEmail,
            mPhonePrefix, mPhoneOperatorCode, mPhoneNumber,
            mPassword, mPasswordConfirm;

    private String companyId, name, address, email,
            phoneNumber, password, passwordConfirm;

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
        setContentView(R.layout.activity_registration);
        initComponents();
    }

    private void initComponents() {
        mProgressBar = findViewById(R.id.progress);
        mCompanyId = findViewById(R.id.company_id);
        mName = findViewById(R.id.name);
        mAddress = findViewById(R.id.address);
        mEmail = findViewById(R.id.email_address);
        mPhonePrefix = findViewById(R.id.phone_prefix);
        mPhoneOperatorCode = findViewById(R.id.phone_operator_code);
        mPhoneNumber = findViewById(R.id.phone_number);
        mPassword = findViewById(R.id.password);
        mPasswordConfirm = findViewById(R.id.confirm_password);
    }

    private boolean isEmptyValuesOnEditViews() {
        return (mCompanyId.getText().toString().isEmpty() ||
                mName.getText().toString().isEmpty() ||
                mAddress.getText().toString().isEmpty() ||
                mEmail.getText().toString().isEmpty() ||
                mPhonePrefix.getText().toString().isEmpty() ||
                mPhoneOperatorCode.getText().toString().isEmpty() ||
                mPhoneNumber.getText().toString().isEmpty() ||
                mPassword.getText().toString().isEmpty() ||
                mPasswordConfirm.getText().toString().isEmpty());
    }

    private boolean isEmailFormatCorrect(String str) {
        String reg = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
        return str.matches(reg);
    }

    private boolean isPhoneNumberFormatCorrect(String str) {
        String reg = "^[+][0-9]{10,13}$";
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
        companyId = mCompanyId.getText().toString();
        name = mName.getText().toString();
        address = mAddress.getText().toString();
        email = mEmail.getText().toString();
        phoneNumber = mPhonePrefix.getText().toString() +
                mPhoneOperatorCode.getText().toString() +
                mPhoneNumber.getText().toString();
        password = mPassword.getText().toString();
        passwordConfirm = mPasswordConfirm.getText().toString();
    }

    public void onClickCompleteRegistration(View view) {
        if(!isEmptyValuesOnEditViews()) {
            initValues();
            if (!password.equals(passwordConfirm)) {
                createDialogMsg(getResources().getString(R.string.passwords_must_match));
            } else if (!isPhoneNumberFormatCorrect(phoneNumber)) {
                createDialogMsg(getResources().getString(R.string.check_phone_number));
            } else if (!isEmailFormatCorrect(email)) {
                createDialogMsg(getResources().getString(R.string.check_email_address));
            } else {
                sendRestRequest("http://delcontrol.somee.com/api/CourierAccounts/",
                        "POST",
                        getJsonUser(companyId, name, address, email, phoneNumber, password));
            }
        } else {
            createDialogMsg(getResources().getString(R.string.fill_in_all_fields));
        }
    }

    public void onClickLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    protected String getJsonUser(String companyId, String name, String address, String email,
                                 String phoneNumber, String password) {
        return "{" +
                "'CompanyId':" + companyId + "," +
                "'Name':'" + name + "'," +
                "'Address':'" + address + "'," +
                "'Email':'" + email + "'," +
                "'Password':'" + password + "'," +
                "'Phone': '" + phoneNumber + "'" +
                "}";
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
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
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
        String accountId = null;
        try {
            JSONObject jObj = new JSONObject(responseBody);
            accountId = jObj.getString("Id");
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