package com.chillarcards.campuswallet.Diary.Gallary;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.networkmodels.Gallery.Image;
import com.chillarcards.campuswallet.utils.CommonDialogues_Utils;

/**
 * Created by user on 4/2/2018.
 */

public class StoriesImageAdapter extends PagerAdapter {

    private List<Image> images;
    private ArrayList<String> imageSpons;
    private LayoutInflater inflater;
    private Context context;
    private Activity activity;

    private boolean doNotifyDataSetChangedOnce = false;
    private ProgressDialog pDialog;
    private ProgressDialog progressDialog;
    private DownloadManager downloadManager;
    private long enqueue;
    ArrayList<Long> DownloadRefList;
    FirebaseAnalytics mFirebaseAnalytics;

    public StoriesImageAdapter(Context mContext, Activity act, List<Image> giftImage, FirebaseAnalytics mFirebaseAnalytics) {
        this.context = mContext;
        this.activity=act;
        this.mFirebaseAnalytics=mFirebaseAnalytics;
        this.images=giftImage;
        inflater = LayoutInflater.from(mContext);
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadRefList = new ArrayList<>();
        //DownloadRefList.clear();
        context.registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //System.out.println("Image added to gallery : "+ filePath);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();

    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {

        View myImageLayout = inflater.inflate(R.layout.slider, view, false);
        ImageView myImage = myImageLayout.findViewById(R.id.image);
        Button DownloadSingleBTN = myImageLayout.findViewById(R.id.DownloadSingleBTN);

        Picasso.get().load(Constants.BASE_IMAGE_URL+images.get(position).getImageURL())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder_error)
                .into(myImage);

        Constants.SingleImageURL=Constants.BASE_IMAGE_URL+images.get(position).getImageURL();

        //System.out.println("ActivityImageURL : "+Constants.BASE_IMAGE_URL+images.get(position).getImageURL());
        DownloadSingleBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(context.getResources().getString(R.string.Gallary_Single_Image_Download_Clicked),new Bundle());
                DownloadImage(position,Constants.BASE_IMAGE_URL+images.get(position).getImageURL());
            }
        });


        view.addView(myImageLayout);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    public void DownloadImage(int index,String Imageurl)
    {
        try {

        Uri Download_Uri= Uri.parse(Imageurl);

        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle(context.getResources().getString(R.string.campus_wallet));
        request.setDescription(context.getResources().getString(R.string.downloading) );
        request.setVisibleInDownloadsUi(true);
        File imageFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CWDownloads");
        if (! imageFolder.exists())
        {
            imageFolder.mkdir();
        }

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);// Visibility of the download Notification
        //request.setDestinationUri(Uri.fromFile(file));// Uri of the destination file
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            request.setRequiresCharging(false);// Set if charging is required to begin the download
        }
        request.setAllowedOverMetered(true);// Set if download is allowed on Mobile network
        request.setAllowedOverRoaming(true);// Set if download is allowed on roaming network

//                request.setDestinationInExternalFilesDir(ImageDownloadActivity.this,Environment.getExternalStorageDirectory().getAbsolutePath(),  "/CWDownloads/"
//                        +index+"_" +getcurrentdatetime()+".jpg");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,  "/CWDownloads/"+
                index+"_" +getcurrentdatetime()+".jpg");

        long refid = downloadManager.enqueue(request);
        DownloadRefList.add(refid);
        Toast.makeText(context,context.getResources().getString(R.string.downloading),Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            CommonDialogues_Utils commonDialogues_utils=new CommonDialogues_Utils();
            commonDialogues_utils.showDialogPopup(context);
        }
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

    BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {

            // get the refid from the download manager
            // long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
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
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0);
// remove it from our list
                        DownloadRefList.remove(downloadId);

// if list is empty means all downloads completed
                        if (DownloadRefList.isEmpty())
                        {
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
                                    new NotificationCompat.Builder(context,context.getResources().getString(R.string.notification_id_channel))
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setContentTitle(context.getResources().getString(R.string.campus_wallet))
                                            .setContentText(context.getResources().getString(R.string.downloading))
                                            .setChannelId(CHANNEL_ID)
                                            .setContentIntent(pendingIntent);

//// When done, update the notification one more time to remove the progress bar
                            mBuilder.setContentText(context.getResources().getString(R.string.download_complete))
                                    .setProgress(0,0,false);
                            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.notify(455, mBuilder.build());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                notificationManager.createNotificationChannel(mChannel);
                            }
                            Toast.makeText(context,context.getResources().getString(R.string.download_complete),Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        }
    };


}