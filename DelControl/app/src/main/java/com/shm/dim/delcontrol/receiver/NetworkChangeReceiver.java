package com.shm.dim.delcontrol.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.shm.dim.delcontrol.R;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private AlertDialog mAlertDialog;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if(!isInternetAvailable(context)) {
            showAlertDialog(context);
        } else {
            dismissAlertDialog();
        }
    }

    public static boolean isInternetAvailable(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }

    private void showAlertDialog(final Context context) {
        if(mAlertDialog == null) {
            createAlertDialog(context);
        }
        mAlertDialog.show();
    }

    private void createAlertDialog(final Context context) {
        mAlertDialog
                = getAlertDialog(context,
                R.drawable.no_internet_connection,
                context.getResources().getString(R.string.check_internet_connection),
                context.getResources().getString(R.string.an_internet_connection_is_required));
    }

    private void dismissAlertDialog() {
        if(mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
    }

    private AlertDialog getAlertDialog(final Context context, final int icon,
                                       final String title, final String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(icon)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false);
        return builder.create();
    }

}