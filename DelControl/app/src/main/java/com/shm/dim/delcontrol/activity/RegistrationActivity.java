package com.shm.dim.delcontrol.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shm.dim.delcontrol.R;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegistrationActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;

    private EditText mCompanyId, mName, mAddress, mEmail, mPhoneNumber, mPassword, mPasswordConfirm;

    private String companyId, name, address, email, phoneNumber, password, passwordConfirm;

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
        mPhoneNumber = findViewById(R.id.phone_number);
        mPassword = findViewById(R.id.password);
        mPasswordConfirm = findViewById(R.id.confirm_password);
        mCompanyId = findViewById(R.id.company_id);
    }

    private boolean isEmptyValuesOnEditViews() {
        return (mCompanyId.getText().toString().isEmpty() || mName.getText().toString().isEmpty() ||
                mAddress.getText().toString().isEmpty() || mEmail.getText().toString().isEmpty() ||
                mPhoneNumber.getText().toString().isEmpty() || mPassword.getText().toString().isEmpty() ||
                mPasswordConfirm.getText().toString().isEmpty());
    }

    private boolean formatPhoneNumberIsCorrect(String str) {
        String reg = "^[+][0-9]{10,13}$";
        return str.matches(reg);
    }

    private boolean formatEmailIsCorrect(String str) {
        String reg = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";
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

    public void onClickCompleteRegistration(View view) {
        if(!isEmptyValuesOnEditViews()) {
            companyId = mCompanyId.getText().toString();
            name = mName.getText().toString();
            address = mAddress.getText().toString();
            email = mEmail.getText().toString();
            phoneNumber = mPhoneNumber.getText().toString();
            password = mPassword.getText().toString();
            passwordConfirm = mPasswordConfirm.getText().toString();
            if (password != passwordConfirm) {
                createDialogMsg(getResources().getString(R.string.passwords_must_match));
            } else if (formatPhoneNumberIsCorrect(phoneNumber)) {
                createDialogMsg(getResources().getString(R.string.check_phone_number));
            } else if (formatEmailIsCorrect(email)) {
                createDialogMsg(getResources().getString(R.string.check_email_address));
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
                RestServiceRequest("http://localhost:64239/api/CourierAccounts/",
                        "POST",
                        GetJsonUser(companyId, name, address, email, phoneNumber, password));
            }
        } else {
            createDialogMsg(getResources().getString(R.string.fill_in_all_fields));
        }
    }

    protected String GetJsonUser(String companyId, String name, String address, String email,
                                 String phoneNumber, String password) {
        return "{" +
                "'CompanyId':" + companyId + "," +
                "'Name':'" + name + "'," +
                "'Address':'" + address + "'," +
                "'Email':'" + email + "'," +
                "'Password':'" + password + "'," +
                "'Phone': '" + phoneNumber +
                "}";
    }

    protected void RestServiceRequest(String url, String method, String body){
        new RestServiceRequestTask(RegistrationActivity.this.getApplicationContext()).execute(url, method, body);
    }

    public class RestServiceRequestTask extends AsyncTask<String, Void, Void> {

        private URL url;

        private HttpURLConnection connection;

        private int responseCode;

        private final Context context;

        RestServiceRequestTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url = null;
            connection = null;
            responseCode = 0;
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                // Headlines
                connection.setDoOutput(true);
                connection.setRequestMethod(params[1]);
                connection.setRequestProperty("Content-Type", "application/json");
                // Body
                try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"))) {
                    bw.write(params[2]);
                }
                // Execute
                responseCode = connection.getResponseCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (connection != null) {
                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == 204) {
                    Toast.makeText(context,
                            getResources().getString(R.string.complete),
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context,
                            getResources().getString(R.string.check_internet_connection) +
                                    "(" + String.valueOf(responseCode) + ")",
                            Toast.LENGTH_LONG).show();
                }
            }
            mProgressBar.setVisibility(View.GONE);
        }
    }
}