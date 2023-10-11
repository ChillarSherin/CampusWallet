package com.chillarcards.campuswallet.Diary.Gallary;

import android.app.Activity;
import android.app.Dialog;
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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.networkmodels.Gallery.Image;
import com.chillarcards.campuswallet.utils.CommonDialogues_Utils;

public class ActivityImageHorizontalAdapetr extends RecyclerView.Adapter<ActivityImageHorizontalAdapetr.ViewHolder>  {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private final int rowLayout;
    List<Image> ImagesArraylist= new ArrayList<>();
    Context cntxt;
    Activity activity;
    Dialog dialogpopup;
    private ProgressDialog pDialog;
    DownloadManager downloadManager ;
    ArrayList<Long> DownloadRefList;
    FirebaseAnalytics mFirebaseAnalytics;


    public ActivityImageHorizontalAdapetr(List<Image> imagesArraylist, Context cntxt, Activity act, int rowLayout,FirebaseAnalytics mFirebaseAnalytics) {
        this.ImagesArraylist.clear();
        this.ImagesArraylist = imagesArraylist;
        this.cntxt = cntxt;
        this.mFirebaseAnalytics = mFirebaseAnalytics;
        this.activity = act;
        this.rowLayout = rowLayout;
        downloadManager = (DownloadManager) cntxt.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadRefList = new ArrayList<>();
        //DownloadRefList.clear();
        cntxt.registerReceiver(onComplete,
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

    @NonNull
    @Override
    public ActivityImageHorizontalAdapetr.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ActivityImageHorizontalAdapetr.ViewHolder(v);
    }
//
//    }

    @Override
    public void onBindViewHolder(@NonNull ActivityImageHorizontalAdapetr.ViewHolder holder, final int position) {
//        if (position>=3)
//        {
//            holder.ExtraImageTV.setVisibility(View.VISIBLE);
//            holder.ExtraImageTV.setText("+"+(ImagesArraylist.size()-3));
//        }
//        else
//        {
        //System.out.println("ImagesArraylist Image Links : "+ Constants.BASE_IMAGE_URL + ImagesArraylist.get(position).getImageURL());

        //  holder.ActivityRowImageIV.setBackground(cntxt.getDrawable(R.drawable.ic_menu_gallery));

        if (!ImagesArraylist.get(position).getImageURL().equalsIgnoreCase("")
                ||!ImagesArraylist.get(position).getImageURL().equalsIgnoreCase("null")) {
            holder.ExtraImageTV.setVisibility(View.GONE);
            Picasso.get().load(Constants.BASE_IMAGE_URL+ ImagesArraylist.get(position).getImageURL())
                    .resize(300,300)
                    .centerCrop()
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.place_holder_error)
//                                .memoryPolicy(MemoryPolicy.NO_CACHE)
//                                .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(holder.ActivityRowImageIV);
        }
        else {
            holder.ExtraImageTV.setVisibility(View.VISIBLE);
            holder.ExtraImageTV.setText(cntxt.getResources().getString(R.string.na));

        }
        holder.ActivityRowImageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(cntxt.getResources().getString(R.string.Gallary_Item_Single_Image_Clicked),new Bundle());
                launchGiftspopup(ImagesArraylist,position);
            }
        });
    }

    @Override
    public int getItemCount() {

        return ImagesArraylist.size();

    }

    private void launchGiftspopup(final List<Image> ImagesArraylist, int position) {
        final int pos=position;

        dialogpopup = new Dialog(cntxt, android.R.style.Theme_Dialog);
        dialogpopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogpopup.setContentView(R.layout.layout_gifts_image_slider2);
        dialogpopup.setCanceledOnTouchOutside(true);

        dialogpopup.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialogpopup.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogpopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        try {

            dialogpopup.show();

        }catch (Exception e){

            e.printStackTrace();

        }
        Button DownloadSingleBTN=dialogpopup.findViewById(R.id.DownloadSingleBTN);
        DownloadSingleBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(cntxt.getResources().getString(R.string.Gallary_Single_Image_Download_Clicked),new Bundle());
                DownloadImage(pos,Constants.BASE_IMAGE_URL+ImagesArraylist.get(pos).getImageURL());;
            }
        });
        Button CloseActGalleryBTN=dialogpopup.findViewById(R.id.CloseActGalleryBTN);
        CloseActGalleryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogpopup.dismiss();

            }
        });

        mPager = dialogpopup.findViewById(R.id.pager1);
        StoriesImageAdapter myAdapter=new StoriesImageAdapter(cntxt,activity,ImagesArraylist,mFirebaseAnalytics);
        mPager.setAdapter(myAdapter);




    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ActivityRowImageIV;
        TextView ExtraImageTV;
        public ViewHolder(View itemView) {
            super(itemView);


            ExtraImageTV=itemView.findViewById(R.id.ExtraImageTV);
            ActivityRowImageIV=itemView.findViewById(R.id.ActivityRowImageIV);
        }
    }

    public void DownloadImage(int index,String Imageurl) {
        try{
            Uri Download_Uri= Uri.parse(Imageurl);

            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setAllowedOverRoaming(false);
            request.setTitle(cntxt.getResources().getString(R.string.campus_wallet));
            request.setDescription(cntxt.getResources().getString(R.string.campus_wallet) );
            request.setVisibleInDownloadsUi(true);
//        File imageFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CWDownloads");
//        if (! imageFolder.exists())
//        {
//            imageFolder.mkdir();
//        }

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
            Toast.makeText(cntxt,cntxt.getResources().getString(R.string.downloading),Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            CommonDialogues_Utils commonDialogues_utils=new CommonDialogues_Utils();
            commonDialogues_utils.showDialogPopup(cntxt);
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
                downloadManager = (DownloadManager)cntxt.getSystemService(Context.DOWNLOAD_SERVICE);
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
                        PendingIntent pendingIntent = PendingIntent.getActivity(cntxt, 0, newIntent, 0);
// remove it from our list
                        DownloadRefList.remove(downloadId);

// if list is empty means all downloads completed
                        if (DownloadRefList.isEmpty())
                        {
                            NotificationChannel mChannel = null;
                            String CHANNEL_ID = "cw_channel_01";// The id of the channel.
                            CharSequence name = cntxt.getResources().getString(R.string.notification_id_channel);// The user-visible name of the channel.
                            int importance = NotificationManager.IMPORTANCE_HIGH;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                            }
// show a notification
                            Log.e("INSIDE", "" + downloadId);
                            NotificationCompat.Builder mBuilder =
                                    new NotificationCompat.Builder(cntxt,cntxt.getResources().getString(R.string.notification_id_channel))
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setContentTitle(cntxt.getResources().getString(R.string.campus_wallet))
                                            .setContentText(cntxt.getResources().getString(R.string.downloading))
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
                            mBuilder.setContentText(cntxt.getResources().getString(R.string.download_complete))
                                    .setProgress(0,0,false);
                            NotificationManager notificationManager = (NotificationManager)cntxt.getSystemService(Context.NOTIFICATION_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                notificationManager.createNotificationChannel(mChannel);
                            }
                            notificationManager.notify(455, mBuilder.build());
                            Toast.makeText(cntxt,cntxt.getResources().getString(R.string.download_complete),Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        }
    };

}
