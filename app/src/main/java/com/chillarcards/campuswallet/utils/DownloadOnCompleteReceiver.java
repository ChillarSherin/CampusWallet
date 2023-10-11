package com.chillarcards.campuswallet.utils;

import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.chillarcards.campuswallet.R;

import java.util.ArrayList;

public class DownloadOnCompleteReceiver extends BroadcastReceiver {

    ArrayList<Long> DownloadRefList;

    public DownloadOnCompleteReceiver() {
    }

    public DownloadOnCompleteReceiver(ArrayList<Long> downloadRefList) {
        this.DownloadRefList = downloadRefList;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // get the refid from the download manager
        // long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Cursor c = downloadManager.query(query);
            if (c.moveToFirst()) {
                int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                    String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    //TODO : Use this local uri and launch intent to open file
                    //System.out.println("local uri : "+uriString);
                    MimeTypeMap myMime = MimeTypeMap.getSingleton();
                    Intent newIntent = new Intent(Intent.ACTION_VIEW);
                    String mimeType = myMime.getMimeTypeFromExtension(fileExt(uriString));
                    newIntent.setDataAndType(Uri.parse(uriString), mimeType);
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0);
// remove it from our list
                    DownloadRefList.remove(downloadId);

// if list is empty means all downloads completed
                    if (DownloadRefList.isEmpty()) {
                        NotificationChannel mChannel = null;
                        String CHANNEL_ID = "cw_channel_01";// The id of the channel.
                        CharSequence name = context.getResources().getString(R.string.notification_id_channel);// The user-visible name of the channel.
                        int importance = NotificationManager.IMPORTANCE_HIGH;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                        }
// show a notification
                        Log.e("INSIDE", "" + downloadId);
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(context, context.getResources().getString(R.string.notification_id_channel))
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setContentTitle("CampusWallet")
                                        .setContentText("Downloading...")
                                        .setChannelId(CHANNEL_ID)
                                        .setContentIntent(pendingIntent);
//// When done, update the notification one more time to remove the progress bar
                        mBuilder.setContentText("Download complete")
                                .setProgress(0, 0, false);
                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            notificationManager.createNotificationChannel(mChannel);
                        }
                        notificationManager.notify(455, mBuilder.build());
                        Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }
    }

    private String fileExt(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }
}
