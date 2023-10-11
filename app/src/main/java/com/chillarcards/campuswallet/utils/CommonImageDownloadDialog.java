package com.chillarcards.campuswallet.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.ActivityImageDownloadBinding;
import com.chillarcards.campuswallet.databinding.LayoutRechargeHistoryBinding;

import java.util.ArrayList;
import java.util.Calendar;

import com.chillarcards.campuswallet.Diary.LeaveRequest.CallBack;

@SuppressLint("ValidFragment")
public class CommonImageDownloadDialog extends DialogFragment {

    public static String TAG = "CommonImageDownloadDialog";
    public static Calendar mCalendar;
    private int mYear, mMonth, mDay;
    private String dateFrom = "",dateTo = "";
    CallBack callback;
    String Imageurl;
    Context mContext;


    public CommonImageDownloadDialog(String Imageurl) {
        this.Imageurl=Imageurl;
    }

    DownloadManager downloadManager ;
    ArrayList<Long> DownloadRefList;

    ActivityImageDownloadBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = ActivityImageDownloadBinding.inflate(inflater, container, false);
        binding.CloseBTN.setOnClickListener(view -> closedia());
        binding.DownloadBTN.setOnClickListener(view -> download());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        mCalendar = Calendar.getInstance();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if ( context instanceof CallBack ) {
            callback = (CallBack) context;
            mContext=context;
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadRefList = new ArrayList<>();
            context.registerReceiver(onComplete,
                    new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        } else {
            throw new RuntimeException(context.getClass().getSimpleName()
                    + " must implement Callback");
        }
    }

    public void download(){
        Uri Download_Uri= Uri.parse(Imageurl);

        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("Campus wallet PDF");
        request.setDescription("Downloading PDF" );
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/CWDownloads/"  + "/"
                /*+by.get(position)+"_"*/ +getcurrentdatetime()+".pdf");


        long refid = downloadManager.enqueue(request);
        DownloadRefList.add(refid);
        callback.message("");
        dismiss();

    }
    public void closedia(){
        callback.message("");
        dismiss();
    }
    public String getcurrentdatetime()
    {
        int mYear, mMonth, mDay,h,m,s;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        h=c.get(Calendar.HOUR_OF_DAY);
        m=c.get(Calendar.MINUTE);
        s=c.get(Calendar.SECOND);
        return mYear+mMonth+mDay+h+m+s+"";
    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {

            // get the refid from the download manager
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

// remove it from our list
            DownloadRefList.remove(referenceId);

// if list is empty means all downloads completed
            if (DownloadRefList.isEmpty())
            {

// show a notification
                Log.e("INSIDE", "" + referenceId);
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(mContext,mContext.getResources().getString(R.string.notification_id_channel))
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("CampusWallet")
                                .setContentText("Download completed");


                NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(455, mBuilder.build());


            }

        }
    };


}
