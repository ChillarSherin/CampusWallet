package com.chillarcards.campuswallet.utils;

import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.ActivityImageDownloadBinding;
import com.chillarcards.campuswallet.databinding.LayoutStudentListActivityBinding;

import java.util.ArrayList;
import java.util.Calendar;

import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;

public class ImageDownloadActivity extends CustomConnectionBuddyActivity {

//    @BindView(R.id.binding.DownloadSingleBTN)
//    Button binding.DownloadSingleBTN;
//    @BindView(R.id.binding.CloseBTN)
//    Button binding.CloseBTN;
//    @BindView(R.id.binding.image)
//    ImageView binding.image;

    ImageView CommonImageView;
    String Imageurl,index;
    DownloadManager downloadManager ;
    ArrayList<Long> DownloadRefList;
    ActivityImageDownloadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_image_download);
        binding = ActivityImageDownloadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        CommonImageView = binding.sliderLL.image;
//        setContentView(R.layout.slider);
//        Fabric.with(this, new Answers());
        Bundle b = getIntent().getExtras();
        if (b!=null) {
            Imageurl = b.getString("imageview");
            index = b.getString("index");
        }
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadRefList = new ArrayList<>();
        DownloadRefList.clear();
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


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

    @Override
    protected void onResume() {
        super.onResume();

        Glide.with(this).load(Imageurl).error(R.drawable.place_holder_error).placeholder(R.drawable.place_holder)
                .into(CommonImageView);

//        Picasso.with(this).load(Imageurl)
//                .placeholder(R.drawable.place_holder)
//                .error(R.drawable.place_holder_error)
//                .resize(100,100).centerCrop()
//                .into(binding.image);

        binding.DownloadBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                Uri Download_Uri= Uri.parse(Imageurl);
                //System.out.println("Download Image : "+Imageurl);

                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setAllowedOverRoaming(false);
                request.setTitle(getResources().getString(R.string.campus_wallet));
                request.setDescription(getResources().getString(R.string.downloading) );
                request.setVisibleInDownloadsUi(true);
//                File imageFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CWDownloads");
//                if (! imageFolder.exists())
//                {
//                    imageFolder.mkdir();
//                }

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);// Visibility of the download Notification
                //request.setDestinationUri(Uri.fromFile(file));// Uri of the destination file
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    request.setRequiresCharging(false);// Set if charging is required to begin the download
                }
                request.setAllowedOverMetered(true);// Set if download is allowed on Mobile network
                request.setAllowedOverRoaming(true);// Set if download is allowed on roaming network

//                request.setDestinationInExternalFilesDir(ImageDownloadActivity.this,Environment.getExternalStorageDirectory().getAbsolutePath(),  "/CWDownloads/"
//                        +index+"_" +getcurrentdatetime()+".jpg");
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM,  "/CWDownloads/"+
                        index+"_" +getcurrentdatetime()+".jpg");

                long refid = downloadManager.enqueue(request);
                DownloadRefList.add(refid);
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.downloading),Toast.LENGTH_SHORT).show();

                }catch (Exception e)
                {
                    CommonDialogues_Utils commonDialogues_utils=new CommonDialogues_Utils();
                    commonDialogues_utils.showDialogPopup(ImageDownloadActivity.this);
                }
            }
        });
        binding.CloseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

    }


    public Point getDisplaySize(Display display) {
        Point size = new Point();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(size);
        } else {
            int width = display.getWidth();
            int height = display.getHeight();
            size = new Point(width, height);
        }

        return size;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplete);
    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {

            // get the refid from the download manager
           // long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
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

                        newIntent.setDataAndType(Uri.parse(uriString),mimeType);
                        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(ImageDownloadActivity.this, 0, newIntent, 0);
                        addImageToGallery(uriString,ImageDownloadActivity.this);
// remove it from our list
                        DownloadRefList.remove(downloadId);

// if list is empty means all downloads completed
                        if (DownloadRefList.isEmpty())
                        {
                            NotificationChannel mChannel = null;
                            String CHANNEL_ID = "cw_channel_01";// The id of the channel.
                            CharSequence name = getString(R.string.notification_id_channel);// The user-visible name of the channel.
                            int importance = NotificationManager.IMPORTANCE_HIGH;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                            }
// show a notification
                            Log.e("INSIDE", "" + downloadId);
                            NotificationCompat.Builder mBuilder =
                                    new NotificationCompat.Builder(ImageDownloadActivity.this,getResources().getString(R.string.notification_id_channel))
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setContentTitle(getResources().getString(R.string.campus_wallet))
                                            .setContentText(getResources().getString(R.string.downloading))
                                            .setChannelId(CHANNEL_ID)
                                            .setContentIntent(pendingIntent);
//                // Issue the initial notification with zero progress
//                int PROGRESS_MAX = 100;
//                int PROGRESS_CURRENT = 0;
//                mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
//
//
//
//                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(455, mBuilder.build());
//
//// Do the job here that tracks the progress.
//// Usually, this should be in a
//// worker thread
//// To show progress, update PROGRESS_CURRENT and update the notification with:
//// mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
//// notificationManager.notify(notificationId, mBuilder.build());
//
//// When done, update the notification one more time to remove the progress bar
                            mBuilder.setContentText(getResources().getString(R.string.download_complete))
                                    .setProgress(0,0,false);
                            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                notificationManager.createNotificationChannel(mChannel);
                            }
                            notificationManager.notify(455, mBuilder.build());
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.download_complete),Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }

        }
    };
    public static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //System.out.println("Image added to gallery : "+ filePath);
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
