package com.shm.dim.delcontrol.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestRequestTask extends AsyncTask<String, Void, Void> {

    private URL url;

    private HttpURLConnection connection;

    private int responseCode;

    private final Context context;

    private RestRequestDelegate delegate;

    public RestRequestTask(Context context, RestRequestDelegate delegate) {
        this.context = context;
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(params[1]);
            connection.setRequestProperty("Content-Type", "application/json");
            try (BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(connection.getOutputStream()))) {
                bw.write(params[2]);
            }
            responseCode = connection.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        delegate.executionFinished(responseCode);
    }

}