package com.chillarcards.campuswallet.home.drawer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;
//import com.onesignal.OneSignal;
//import com.moe.pushlibrary.MoEHelper;

import org.json.JSONObject;

import java.util.Iterator;

import com.chillarcards.campuswallet.NotificationCenter.DatabaseHandler;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.home.CallPermissionCallback;
import com.chillarcards.campuswallet.home.WhatsAppCallback;
import com.chillarcards.campuswallet.login.LoginActivity;
import com.chillarcards.campuswallet.studentselection.StudentListActivity;

public class DrawerItemCustomAdapter extends ArrayAdapter<DataModel> {

    private Context mContext;
    private int layoutResourceId;
    private DataModel data[] = null;
    private PrefManager prefManager;
    private DrawerLayout mDrawerLayout;
    private CallPermissionCallback callback;
    private String version;
    private Dialog OtpDialoglogout;
    private WhatsAppCallback whatsAppCallback;
    private DatabaseHandler databaseHandler ;

    private boolean history;
    private FirebaseAnalytics mFirebaseAnalytics;

    public DrawerItemCustomAdapter(Context mContext, int layoutResourceId, DataModel[] data,
                                   DrawerLayout mDrawerLayout, CallPermissionCallback callback, String version,
                                   boolean history, FirebaseAnalytics mFirebaseAnalytics, WhatsAppCallback whatsAppCallback) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
        this.mDrawerLayout = mDrawerLayout;
        this.callback=callback;
        this.whatsAppCallback=whatsAppCallback;
        prefManager= new PrefManager(mContext);
        databaseHandler=new DatabaseHandler(mContext);
        this.version=version;
        this.history=history;
        this.mFirebaseAnalytics=mFirebaseAnalytics;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);
        ImageView imageViewIcon = (ImageView) listItem.findViewById(R.id.imageViewIcon);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewName);
        RelativeLayout drawerRL=(RelativeLayout)listItem.findViewById(R.id.drawerRL);
        DataModel folder = data[position];
        if (position == 4) {
                imageViewIcon.setVisibility(View.GONE);
                textViewName.setText(folder.name + " : " + version);
                textViewName.setTextSize(10);
        } else {
                imageViewIcon.setVisibility(View.VISIBLE);
                imageViewIcon.setImageResource(folder.icon);
                textViewName.setText(folder.name);
                textViewName.setTextSize(12);
        }


        final int pos=position;
        drawerRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    switch (pos)
                    {
                        case 0:
                        {
                            mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Navigation_Select_Student_Clicked),new Bundle());
                            Intent i=new Intent(mContext, StudentListActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mContext.startActivity(i);
                            ((Activity) mContext).finish();
                            break;
                        }
                        case 1:
                        {
                            mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Navigation_Call_Us_Clicked),new Bundle());
                            callback.callSupport();
                            break;
                        }
                        case 2:
                        {
                            mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Navigation_Chat_Clicked),new Bundle());
                            whatsAppCallback.chatSupport();
                            break;
                        }case 3:
                        {
                            mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Navigation_LogOut_Clicked),new Bundle());
                            LogoutPopup();
                            break;
                        }
                        case 4:
                        {

                            break;
                        }
                    }
                }

        });

        return listItem;
    }



    public void LogoutPopup()
    {
        OtpDialoglogout = new Dialog(mContext);
        OtpDialoglogout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        OtpDialoglogout.setContentView(R.layout.confirm_logout_popup);
        OtpDialoglogout.setCanceledOnTouchOutside(true);
        OtpDialoglogout.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        OtpDialoglogout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView confirm = (TextView) OtpDialoglogout.findViewById(R.id.confirmLogout);
        TextView deny=(TextView) OtpDialoglogout.findViewById(R.id.cancelLogout);

        try {
            OtpDialoglogout.show();
        }catch (Exception e){
            //System.out.println("Logout PopUp Error : "+ e.toString());
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtpDialoglogout.dismiss();
                databaseHandler.deleteTransactions();
                prefManager.clearAll();
                // logout from moenguage
//                        MoEHelper.getInstance(mContext.getApplicationContext()).logoutUser();
                mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Confirm_LogOut_Clicked),new Bundle());
                Constants.BASE_URL=Constants.BASE_URL_ORIGINAL;

//                OneSignal.getTags(new OneSignal.GetTagsHandler() {
//                    @Override
//                    public void tagsAvailable(JSONObject tags) {
//                        //System.out.println("One Signal Tags : "+tags.toString());
//                        Iterator<String> iter = tags.keys();
//                        while (iter.hasNext()) {
//                            String key = iter.next();
//                            //System.out.println("One Signal Tags key : "+key);
//                            OneSignal.deleteTag(key);
//                        }
//                    }
//                });
//                OneSignal.sendTag("log_out", "LogOut_Confirm_Button_click");
                Intent i=new Intent(mContext, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(i);
                ((Activity) mContext).finish();

            }
        });
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Cancel_LogOut_Clicked),new Bundle());
                OtpDialoglogout.dismiss();
            }
        });
    }
}