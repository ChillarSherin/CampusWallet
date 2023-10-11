package com.chillarcards.campuswallet.Diary.TimeTable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
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
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.utils.ImageDownloadActivity;

public class TeacherMessageAdapter extends RecyclerView.Adapter<TeacherMessageAdapter.ViewHolder> {

    private List<String> date = new ArrayList<>();
    private List<String> desc = new ArrayList<>();
    private List<String> due = new ArrayList<>();
    private List<String> by = new ArrayList<>();
    private List<String> imageUrl = new ArrayList<>();
    private List<String> imageExists = new ArrayList<>();
    List<Integer> id1=new ArrayList<>();
    private int rowLayout;
    private Context mContext;
    private Activity activity;
    ProgressDialog progressDialog;
    String PINN;
    int pos;
    String parameters;
    DownloadManager downloadManager ;
    ArrayList<Long> DownloadRefList;

    public TeacherMessageAdapter(List<Integer> id, List<String> desc, List<String> date, List<String> by, List<String> due, List<String> imageExists, List<String> imageUrl, Activity activity, int rowLayout, Context context) {
        this.date = date;
        id1 = id;
        this.desc = desc;
        this.imageExists = imageExists;
        this.imageUrl = imageUrl;
        this.activity = activity;
        this.rowLayout = rowLayout;
        this.mContext = context;
        this.by = by;
        this.due=due;
        progressDialog = new ProgressDialog(activity);
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadRefList = new ArrayList<>();
        context.registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v);



    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final int pos=position;

        holder.StatusImg.setVisibility(View.GONE);

       /* try {
            holder.date.setText(date.get(position));
        } catch (Exception e) {


        }
        try {
            holder.desc.setText(desc.get(position));
        } catch (Exception e) {


        }

        try {


                holder.due.setText(due.get(position));

        } catch (Exception ignored) {


        }*/

        try {
            holder.by.setText(by.get(holder.getAdapterPosition()));
        } catch (Exception ignored) {


        }

      /*  if(position%2==0){

            holder.Card.setBackgroundColor(Color.WHITE);

        }else{

            holder.Card.setBackgroundColor(Color.parseColor("#f2f6f9"));



        }*/


        try {
            holder.desc.setText(desc.get(holder.getAdapterPosition()));
        } catch (Exception ignored) {


        }

        try {

            if ( date.get(holder.getAdapterPosition()).equals("null"))
            {
                holder.date.setVisibility(View.GONE);
            }
            else {
                holder.date.setVisibility(View.VISIBLE);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date1 = format.parse(due.get(holder.getAdapterPosition()));
                    //System.out.println("ijaz  :  date  " + date1);

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy");

                    String datetime = format1.format(date1);
                    //System.out.println("ijaz  : Date Time : " + datetime);
                    //System.out.println("ijaz  : due : " + due.get(holder.getAdapterPosition()));
                    holder.date.setText(mContext.getResources().getString(R.string.due_on)+datetime);


                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }


        } catch (Exception ignored) {


        }
        try {

            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date1 = format.parse(date.get(holder.getAdapterPosition()));
                //System.out.println("ijaz  :  date  "+date1);

                @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

                String datetime = format1.format(date1);
                //System.out.println("ijaz  : Date Time : " + datetime);
                //System.out.println("ijaz  : due : " + date.get(holder.getAdapterPosition()));
                holder.due.setText(datetime);


            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (Exception ignored) {


        }


        if (imageExists.get(holder.getAdapterPosition()).equals("1")){
            holder.StatusImg.setVisibility(View.VISIBLE);
            //System.out.println("TESTCHILLAR2 :  "+imageExists.get(holder.getAdapterPosition()).length());
            //System.out.println("TESTELDHO :  "+imageUrl.get(holder.getAdapterPosition()).length());
            if (imageUrl.get(holder.getAdapterPosition()).endsWith(".pdf")){
                {
                    //System.out.println("TESTCHILLAR3 :  "+imageUrl.get(holder.getAdapterPosition()).length());


                    //System.out.println("TESTCHILLAR4 :  "+imageUrl.get(holder.getAdapterPosition()).length());
                    Glide.with(mContext).load(R.drawable.pdf_new)
                            .thumbnail(0.5f)
                            .into(holder.StatusImg);


                    holder.StatusImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //                                URL Download_Uri = new URL(Constants.BASE_IMAGE_URL+imageUrl.get(position));
                            Uri Download_Uri= Uri.parse(Constants.BASE_IMAGE_URL+imageUrl.get(position));

                            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setAllowedOverRoaming(false);
                            request.setTitle(mContext.getResources().getString(R.string.campus_wallet));
                            request.setDescription(mContext.getResources().getString(R.string.downloading) );
                            request.setVisibleInDownloadsUi(true);
                            File imageFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CWDownloads");
                            if (! imageFolder.exists())
                            {
                                imageFolder.mkdir();
                            }
                            //System.out.println("Download Folder : "+imageFolder.getAbsolutePath());
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,  "/CWDownloads/"
                                    +position+"_" +getcurrentdatetime()+".pdf");


                            long refid = downloadManager.enqueue(request);
                            DownloadRefList.add(refid);
                            Toast.makeText(mContext.getApplicationContext(),mContext.getResources().getString(R.string.downloading),Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }else {
                try {
                    //System.out.println("SHANIL "+ Constants.BASE_URL+imageUrl.get(holder.getAdapterPosition()));

//                    Glide.with(mContext).load(Constants.BASE_IMAGE_URL+imageUrl.get(position))
//                            .thumbnail(0.5f)
//                            .into(holder.StatusImg);
                    Picasso.get().load(Constants.BASE_IMAGE_URL+imageUrl.get(position))
                            .placeholder(R.drawable.place_holder)
                            .error(R.drawable.place_holder_error)
                            .into(holder.StatusImg);


                    holder.StatusImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent in=new Intent(mContext,ImageDownloadActivity.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Bundle b=new Bundle();
                            b.putString("imageview", Constants.BASE_URL+imageUrl.get(holder.getAdapterPosition()));
                            b.putString("index", pos+"");

                            in.putExtras(b);
                            mContext.startActivity(in);

//                            CommonImageDownloadDialog dialog=new CommonImageDownloadDialog(Constants.BASE_URL+imageUrl.get(holder.getAdapterPosition()))
//
//                            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
//                            dialog.show(ft, CommonImageDownloadDialog.TAG);

                        }
                    });
                } catch (Exception ignored) {

                    ignored.printStackTrace();

                }

            }



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

    @Override
    public int getItemCount() {
        return date.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView date,desc,by,due;
        ConstraintLayout Card;
        ImageView StatusImg;

        public ViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.TvDue);
            desc = itemView.findViewById(R.id.tv_content);
            by = itemView.findViewById(R.id.TvBy);
            Card = itemView.findViewById(R.id.cardLay);
            due = itemView.findViewById(R.id.TvDate);
            StatusImg = itemView.findViewById(R.id.imageid);

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

    BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {

            // get the refid from the download manager
            // long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                downloadManager = (DownloadManager)mContext.getSystemService(Context.DOWNLOAD_SERVICE);
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
                        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, newIntent, 0);
// remove it from our list
                        DownloadRefList.remove(downloadId);

// if list is empty means all downloads completed
                        if (DownloadRefList.isEmpty())
                        {

// show a notification
                            Log.e("INSIDE", "" + downloadId);
                            NotificationCompat.Builder mBuilder =
                                    new NotificationCompat.Builder(mContext,mContext.getResources().getString(R.string.notification_id_channel))
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setContentTitle(mContext.getResources().getString(R.string.campus_wallet))
                                            .setContentText(mContext.getResources().getString(R.string.downloading))
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
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
                            mBuilder.setContentText(mContext.getResources().getString(R.string.download_complete))
                                    .setProgress(0,0,false);
                            NotificationManager notificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.notify(455, mBuilder.build());
                            Toast.makeText(mContext,mContext.getResources().getString(R.string.download_complete),Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        }
    };


}
