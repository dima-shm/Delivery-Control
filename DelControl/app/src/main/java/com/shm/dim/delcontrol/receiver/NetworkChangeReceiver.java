package com.shm.dim.delcontrol.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.shm.dim.delcontrol.R;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if(!isInternetAvailable(context)) {
            AlertDialog alertDialog
                    = createAlertDialog(context,
                    R.drawable.no_internet_connection,
                    context.getResources().getString(R.string.check_internet_connection),
                    context.getResources().getString(R.string.an_internet_connection_is_required));
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
    }

    public static boolean isInternetAvailable(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                cm.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING);
    }

    private AlertDialog createAlertDialog(final Context context, final int icon,
                                          final String title, final String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(icon)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", getDialogButtonClickListener(context, builder));
        return builder.create();
    }

    private DialogInterface.OnClickListener getDialogButtonClickListener(final Context context,
                                                                         final AlertDialog.Builder builder) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!isInternetAvailable(context)) {
                    builder.show();
                }
            }
        };
    }

}