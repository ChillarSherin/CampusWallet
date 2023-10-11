package com.chillarcards.campuswallet.Diary.NoticeBoard;

import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.networkmodels.noticeboard.Code;
import com.chillarcards.campuswallet.utils.CommonDialogues_Utils;
import com.chillarcards.campuswallet.utils.ImageDownloadActivity;

/*
Created By : Abhinand
Created Date : 09-10-2018
*/
public class NoticeBoardAdapter extends  RecyclerView.Adapter<NoticeBoardAdapter.ViewHolder> {
    Context context;
    List<Code> myDataset = new ArrayList<>();
    DownloadManager downloadManager ;
    ArrayList<Long> DownloadRefList;
    FirebaseAnalytics mFirebaseAnalytics;
    int index = -1;



    public NoticeBoardAdapter(List<Code> myDataset, int index, NoticeBoardActivity noticeBoardActivity, FirebaseAnalytics mFirebaseAnalytics) {

        this.myDataset = myDataset;
        this.context = noticeBoardActivity;
        this.index = index;
        this.mFirebaseAnalytics = mFirebaseAnalytics;
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadRefList = new ArrayList<>();
        //DownloadRefList.clear();
        context.registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @NonNull
    @Override
    public NoticeBoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_noticeboard, parent, false);
        return new NoticeBoardAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeBoardAdapter.ViewHolder holder, final int position) {

        holder.cardView.setCardBackgroundColor(Color.TRANSPARENT);
        if(index!=-1){

            if(index == position){
                holder.cardView.setCardBackgroundColor(Color.LTGRAY);
            }

        }

        holder.TeacherNameTV.setText(myDataset.get(position).getNoticeCreatedBy());
        holder.TeachermessageDetailsTV.setText(myDataset.get(position).getNotice());
        holder.FromteacherMessageDateTV.setText(myDataset.get(position).getNoticeCreatedOn());

        if (myDataset.get(position).getImageExists()==1)
        {
            //System.out.println("Image Exists : "+myDataset.get(position).getImageExists());

            //System.out.println("Image Url : "+Constants.BASE_IMAGE_URL+myDataset.get(position).getImageUrl());
            holder.imageid.setVisibility(View.VISIBLE);
            if (myDataset.get(position).getImageUrl().endsWith(".pdf"))
            {
                Glide.with(context).load(R.drawable.pdf_new)
                        .thumbnail(0.5f)
                        .into(holder.imageid);
                holder.imageid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            mFirebaseAnalytics.logEvent(context.getResources().getString(R.string.Notice_Board_Item_Attachement_Clicked),new Bundle());
                        Uri Download_Uri= Uri.parse(Constants.BASE_IMAGE_URL+myDataset.get(position).getImageUrl());

                        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                        request.setAllowedOverRoaming(true);
                        request.setTitle(context.getResources().getString(R.string.campus_wallet));
                        request.setDescription(context.getResources().getString(R.string.downloading) );
                        request.setVisibleInDownloadsUi(true);
//                        File imageFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CWDownloads");
//                        if (! imageFolder.exists())
//                        {
//                            imageFolder.mkdir();
//                        }
//                        //System.out.println("Download Folder : "+imageFolder.getAbsolutePath());
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM,  "/CWDownloads/"
                                +position+"_" +getcurrentdatetime()+".pdf");


                        long refid = downloadManager.enqueue(request);
                        DownloadRefList.add(refid);
                        Toast.makeText(context.getApplicationContext(),context.getResources().getString(R.string.downloading),Toast.LENGTH_SHORT).show();

                        }catch (Exception e)
                        {
                            CommonDialogues_Utils commonDialogues_utils=new CommonDialogues_Utils();
                            commonDialogues_utils.showDialogPopup(context);
                        }
                    }
                });

            }
            else {

                Picasso.get().load(Constants.BASE_IMAGE_URL+myDataset.get(position).getImageUrl())
                        .placeholder(R.drawable.place_holder)
                        .error(R.drawable.place_holder_error)
                        .into(holder.imageid);


                holder.imageid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFirebaseAnalytics.logEvent(context.getResources().getString(R.string.Notice_Board_Item_Attachement_Clicked),new Bundle());
                        Intent in=new Intent(context,ImageDownloadActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle b=new Bundle();
                        b.putString("imageview", Constants.BASE_IMAGE_URL+myDataset.get(position).getImageUrl());
                        b.putString("index", position+"");

                        in.putExtras(b);
                        context.startActivity(in);

//                            CommonImageDownloadDialog dialog=new CommonImageDownloadDialog(Constants.BASE_URL+imageUrl.get(holder.getAdapterPosition()))
//
//                            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
//                            dialog.show(ft, CommonImageDownloadDialog.TAG);

                    }
                });
            }
        }
        else {
            holder.imageid.setVisibility(View.GONE);
            //System.out.println("Image Exists : "+myDataset.get(position).getImageExists());
        }

    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TeacherNameTV,FromteacherMessageDateTV,TeacherSubjectNameTV,TeachermessageDetailsTV;
        ImageView imageid;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            TeacherNameTV=itemView.findViewById(R.id.name);
            cardView=itemView.findViewById(R.id.cardView);
            FromteacherMessageDateTV=itemView.findViewById(R.id.TvDue);
            TeachermessageDetailsTV=itemView.findViewById(R.id.tv_content);
            imageid=itemView.findViewById(R.id.imageid);

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
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                notificationManager.createNotificationChannel(mChannel);
                            }
                            notificationManager.notify(455, mBuilder.build());
                            Toast.makeText(context,context.getResources().getString(R.string.download_complete),Toast.LENGTH_SHORT).show();

//                            context.unregisterReceiver(this);
//                            onComplete=null;


                        }
                    }
                }
            }
        }
    };

}