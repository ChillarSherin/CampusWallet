package com.chillarcards.campuswallet.NotificationCenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.chillarcards.campuswallet.Diary.Calendar.CalendarNew;
import com.chillarcards.campuswallet.Diary.EDiary.E_Diary_Activity;
import com.chillarcards.campuswallet.Diary.EDiary.TeachermessageDetailsActivity;
import com.chillarcards.campuswallet.Diary.Gallary.GalleryActivity;
import com.chillarcards.campuswallet.Diary.LeaveRequest.LeaveRequestActivity;
import com.chillarcards.campuswallet.Diary.NoticeBoard.NoticeBoardActivity;
import com.chillarcards.campuswallet.Payment.History.PaymentHistoryActivity;
import com.chillarcards.campuswallet.payments.cardtransaction.CardTransactionActivity;

/**
 * Created by user on 12/2/2017.
 */

public class Notifications_list_adapterNW extends RecyclerView.Adapter<Notifications_list_adapterNW.ViewHolder> {

    private List<Contact> contacts;
    private final int rowLayout;
    private final Context mContext;
    private DatabaseHandler db;
    private FirebaseAnalytics mFirebaseAnalytics;
    public Notifications_list_adapterNW(List<Contact> contacts, Activity activity, int rowLayout, Context context,FirebaseAnalytics mFirebaseAnalytics) {
        this.contacts=contacts;
        this.rowLayout = R.layout.item_notification_center;
        this.mContext = context;
        this.mFirebaseAnalytics = mFirebaseAnalytics;
        db = new DatabaseHandler(activity);
    }

    @Override
    public Notifications_list_adapterNW.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new Notifications_list_adapterNW.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Notifications_list_adapterNW.ViewHolder holder, final int position) {

        String pushType="";
        String pushMessage = "";
        String notifId = "";
        String additionalData ="";
        try{

            String JSDataString =contacts.get(position).getDatas();
            JSONObject json = new JSONObject(JSDataString);
            pushMessage = json.getString("body");
            notifId = json.getString("notificationID");
            additionalData= json.optString("additionalData");
            JSONObject jsonobjct=new JSONObject(additionalData);
            pushType=jsonobjct.getString("pushType");

        }catch (JSONException e){
            e.printStackTrace();
        }

        if (contacts.get(position).getClicked().equals("0")){
            holder.mainLayout.setBackgroundColor(Color.parseColor("#eceded"));
        }else {
            holder.mainLayout.setBackgroundColor(Color.TRANSPARENT);
        }

        if (pushType.equals("home_status")){
            holder.iconimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.notification_homestatus));
            holder.Content.setText(mContext.getResources().getString(R.string.general_notification));
            holder.MessageBody.setText(" "+pushMessage);
        }else if (pushType.equals("notice board")){
            holder.iconimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.notification_noticeboard));
            holder.Content.setText(mContext.getResources().getString(R.string.notice_board_title));
            holder.MessageBody.setText(" "+pushMessage);
        }else if (pushType.equals("notice board std")){
            holder.iconimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.notification_noticeboard));
            holder.Content.setText(mContext.getResources().getString(R.string.notice_board_title));
            holder.MessageBody.setText(" "+pushMessage);
        }else if (pushType.equals("calender")){
            holder.iconimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.notification_calendar));
            holder.Content.setText(mContext.getResources().getString(R.string.calendar));
            holder.MessageBody.setText(" "+pushMessage);
        }else if (pushType.equals("calender std")){
            holder.iconimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.notification_calendar));
            holder.Content.setText(mContext.getResources().getString(R.string.calendar));
            holder.MessageBody.setText(" "+pushMessage);
        }else if (pushType.equals("Leave request Approved")){
            holder.Content.setText(mContext.getResources().getString(R.string.approved_leave_req));
            holder.MessageBody.setText(" "+pushMessage);
            holder.iconimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.notification_leave_requestapproved));
        }else if (pushType.equals("Inbox message")){
            holder.Content.setText(mContext.getResources().getString(R.string.inbox_message));
            holder.MessageBody.setText(" "+pushMessage);
            holder.iconimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.notification_inboxmessage));
        }else if (pushType.equals("Messages")){
            holder.Content.setText(mContext.getResources().getString(R.string.message_));
            holder.MessageBody.setText(" "+pushMessage);
            holder.iconimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.notification_message));
        }else if (pushType.equals("Transaction")){
            holder.Content.setText(mContext.getResources().getString(R.string.transactions_notification));
            holder.MessageBody.setText(" "+pushMessage);
            holder.iconimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.notification_transaction));
        }else if (pushType.equals("adds")){
           /* holder.Content.setText(mContext.getResources().getString(R.string.adds_notification));
            holder.MessageBody.setText(" "+pushMessage);
            holder.iconimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.noticication_add)); */
        }else if (pushType.equals("success_recharge")){
            holder.Content.setText(mContext.getResources().getString(R.string.recharge_success));
            holder.MessageBody.setText(" "+pushMessage);
            holder.iconimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.notification_transaction));
        }else if (pushType.equals("Messages all")){
            holder.Content.setText(mContext.getResources().getString(R.string.message_));
            holder.MessageBody.setText(" "+pushMessage);
            holder.iconimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.notification_message));
        }else if (pushType.equals("Bus Tracking")){
            holder.Content.setText(mContext.getResources().getString(R.string.home_bustrack));
            holder.MessageBody.setText(" "+pushMessage);
            holder.iconimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.bus_tracking_icon));
        } else if (pushType.equals("Activity")){
            holder.Content.setText(mContext.getResources().getString(R.string.gallery_notification));
            holder.MessageBody.setText(" "+pushMessage);
            holder.iconimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_highlights));
        } else if (pushType.equals("School Bus Notification")){
            holder.Content.setText(mContext.getResources().getString(R.string.home_bustrack));
            holder.MessageBody.setText(" "+pushMessage);
            holder.iconimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.bus_tracking_icon));
        } else {
            holder.Content.setText(mContext.getResources().getString(R.string.notification_noti));
            holder.MessageBody.setText(" "+pushMessage);
            holder.iconimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_notification_grey));
        }


        final String finalNotifId = notifId;
        final String finalPushType = pushType;
        final String finalAdditionalData = additionalData;
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Notification_Item_Clicked),new Bundle());
                db.UpdateTable(finalNotifId);

                Bundle bundle = new Bundle();
                bundle.putInt("messageID",contacts.get(position).getMessageID());

                if (finalPushType.equals("notice board")||finalPushType.equals("notice board std")){
                    /*Navigate to specific message*/


                    Intent intent = new Intent(mContext, NoticeBoardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);


                }else if (finalPushType.equals("calender")||finalPushType.equals("calender std")){

                    Intent intent = new Intent(mContext, CalendarNew.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);

                }
//                else if (finalPushType.equals("School Bus Notification")||finalPushType.equals("Bus Tracking")){
//
//                    Intent intent = new Intent(mContext, ParentMapsActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    mContext.startActivity(intent);
//
//                }
                else if (finalPushType.equals("Activity")){

                    Intent intent = new Intent(mContext, GalleryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);

                }else if (finalPushType.equals("Leave request Approved")) {
                    /*Navigate to specific message*/
                    Intent intent = new Intent(mContext, LeaveRequestActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);

                }else if (finalPushType.equals("Inbox message")){
                    /* Navigate to specific message -NOT DONE! */

                    Intent intent = new Intent(mContext, E_Diary_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    bundle.putString("MessageTo", "RequestFromParent");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);


                }else if (finalPushType.equals("Messages")||finalPushType.equals("Messages all")){
                    /*Navigate to specific message*/

                    try {

                        JSONObject jsonObject = new JSONObject(finalAdditionalData);
                        String SubjectID = jsonObject.optString("subjectID");
                        String SubjectName = jsonObject.optString("subjectName");

                        if(SubjectID.equals("0")){

                            Intent mIntent = new Intent(mContext, TeachermessageDetailsActivity.class);
                            bundle.putString("SubjectId", SubjectID);
                            bundle.putString("SubjectName", SubjectName);
                            bundle.putBoolean("Flagteacher",true);
                            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mIntent.putExtras(bundle);
                            mContext.startActivity(mIntent);

                        }else{

                            Intent mIntent = new Intent(mContext, TeachermessageDetailsActivity.class);
                            bundle.putString("SubjectId", SubjectID);
                            bundle.putString("SubjectName", SubjectName);
                            bundle.putBoolean("Flagteacher",false);
                            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mIntent.putExtras(bundle);
                            mContext.startActivity(mIntent);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }else if (finalPushType.equals("Transaction")){

                    try {

                        JSONObject  jsonObject = new JSONObject(finalAdditionalData);
                        String TransTypId = jsonObject.optString("transactionTypeID");

                        Intent mIntent = new Intent(mContext, CardTransactionActivity.class);
                        Bundle b = new Bundle();
                        b.putString("transactionTypeID", TransTypId);
                        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mIntent.putExtras(b);
                        mContext.startActivity(mIntent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }else if (finalPushType.equals("success_recharge")){

                    Intent intent = new Intent(mContext, PaymentHistoryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle b=new Bundle();
                    b.putString("pos","1");
                    intent.putExtras(b);
                    mContext.startActivity(intent);

                }

            }
        });


    /*    SchoolID = prefManager.getSChoolID();
        StandardDivId = prefManager.getStudentStandardDivisionID();
        StandardName = prefManager.getStudentStandard();
        StudID = prefManager.getStudentId();
        String SchoolName = prefManager.getSChoolName();
        String JSDataString="",JSadditionalData="",JSbody="",JSnotificationID="",
                JSpayment="",JSTitle="",JSpushTypeLists = "",JSschoolID="",JSstudentID="";
        JSONObject json=null,jsonobjct = null;
        try {
            JSDataString=contacts.get(position).getDatas();
            json = new JSONObject(JSDataString);
            JSadditionalData = json.optString("additionalData");
            jsonobjct=new JSONObject(JSadditionalData);
            JSbody= json.getString("body");
            JSnotificationID=json.getString("notificationID");
            JSTitle= json.getString("title");
            JSpushTypeLists=jsonobjct.getString("pushType");
            JSpayment=jsonobjct.getString("payment");

            JSschoolID=jsonobjct.getString("schoolID");
            JSstudentID=jsonobjct.getString("studentID");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final String finalJSTitle = JSTitle;
        final String finalJSnotificationID = JSnotificationID;
        final JSONObject finalJsonobjct = jsonobjct;
        final String finalJSpayment = JSpayment;
        final String finalJSpushTypeLists = JSpushTypeLists;
        final String finalJSbody = JSbody;
        holder.Groupnamecardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Notification_Item_Clicked),new Bundle());
                db.UpdateTable(finalJSnotificationID);
                try {
                    if (finalJsonobjct != null) {

                        type = finalJsonobjct.optString("type");
                        if (type.equals("school")) {
                            studentStandard = finalJsonobjct.optString("standardName");
                            studentDiv = finalJsonobjct.optString("standardDivisionName");
                            SchoolidKey = finalJsonobjct.optString("schoolID");
                        } else if (type.equals("college")) {
                            studentStandard = finalJsonobjct.optString("courseName");
                            studentDiv = finalJsonobjct.optString("semesterName");
                        }
                        if (finalJSpushTypeLists.equalsIgnoreCase("home_status")) {
                            SchoolidKey = finalJsonobjct.optString("schoolID");
                            PhoneNo = finalJsonobjct.optString("phoneNo");
                            studentName = finalJsonobjct.optString("studentName");
                            studentSchool = finalJsonobjct.optString("schoolName");
                            StudentCode = finalJsonobjct.optString("studentCode");
                            type = finalJsonobjct.optString("type");
                            StudIDKEY = finalJsonobjct.optString("studentID");
                            try {
                                StdDivIdKey = finalJsonobjct.getString("standardDivisionID");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //System.out.println("SHANIL 4: ");
                            //System.out.println("home_menu : stdIDnew_home :  " + SchoolidKey + StdDivIdKey);
                            //System.out.println("homeMessage:  " + SchoolidKey + StdDivIdKey);
                            //System.out.println("SHANIL 5: ");
                            //System.out.println("home1");
                            //System.out.println("background");
                            Intent mIntent = new Intent(CampusWallet.getContext(), HomeActivity.class);
                            //System.out.println("SHANIL 6: ");
                            Constants.HomePopup = finalJSbody;
                            Constants.HomePopupTitle = finalJSTitle;
                            Constants.HomeFlag = "true";
                            Bundle b = new Bundle();
                            //System.out.println("SHANIL 6: CONTENT " + Constants.HomePopup);
                            //System.out.println("SHANIL 6: TITLE " + Constants.HomePopupTitle);
                            //System.out.println("SHANIL 6: FLAG " + Constants.HomeFlag);
                            b.putString("CONTENT", Constants.HomePopup);
                            b.putString("TITLE", Constants.HomePopupTitle);
                            mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                            mIntent.putExtras(b);
                            CampusWallet.getContext().startActivity(mIntent);
                        } else if (finalJSpushTypeLists.equalsIgnoreCase("notice board")) {
                            //System.out.println("SHANIL 9: ");
                            SchoolidKey = finalJsonobjct.optString("schoolID");
                            studentSchool = finalJsonobjct.optString("schoolName");
                            type = finalJsonobjct.optString("type");
                            if (SchoolidKey.equals(SchoolID) && (studentStandard.equalsIgnoreCase(StandardName))) {
                                //System.out.println("SHANIL 10: ");
                                Intent intent = new Intent(CampusWallet.getContext(), NoticeBoardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }
                            else {
                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }
                        } else if (finalJSpushTypeLists.equalsIgnoreCase("notice board std")) {
                            //System.out.println("SHANIL 9: ");
                            SchoolidKey = finalJsonobjct.optString("schoolID");
                            studentSchool = finalJsonobjct.optString("schoolName");
                            type = finalJsonobjct.optString("type");
                            //System.out.println("SHANIL NEW : "+SchoolidKey+"   "+SchoolID+"  "+studentStandard+"  "+StandardName);
                            if (SchoolidKey.equals(SchoolID) && (studentStandard.equals(StandardName))) {
                                //System.out.println("SHANIL 10: ");
                                Intent intent = new Intent(CampusWallet.getContext(), NoticeBoardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }
                            else {
                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }
                        }
                        else if (finalJSpushTypeLists.equalsIgnoreCase("calender")) {
                            //System.out.println("SHANIL 11: ");
                            SchoolidKey = finalJsonobjct.optString("schoolID");
                            studentSchool = finalJsonobjct.optString("schoolName");
                            type = finalJsonobjct.optString("type");
                            //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
                            if (SchoolidKey.equals(SchoolID) && (studentStandard.equals(StandardName))) {
                                //System.out.println("SHANIL 12: ");
                                Intent intent = new Intent(CampusWallet.getContext(), CalendarNew.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            } else {
                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }
                        }
                        else if (finalJSpushTypeLists.equalsIgnoreCase("School Bus Notification")) {
                            //System.out.println("SHANIL 11: BusSystem");
                            SchoolidKey = finalJsonobjct.optString("schoolID");
                            if (SchoolidKey.equals(SchoolID))
                            {
                                Intent intent = new Intent(CampusWallet.getContext(), ParentMapsActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }
                            else
                            {
                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }

                        }
                        else if (finalJSpushTypeLists.equalsIgnoreCase("calender std")) {
                            //System.out.println("SHANIL 11: ");
                            SchoolidKey = finalJsonobjct.optString("schoolID");
                            studentSchool = finalJsonobjct.optString("schoolName");
                            type = finalJsonobjct.optString("type");
                            //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
                            if (SchoolidKey.equals(SchoolID) && (studentStandard.equals(StandardName))) {
                                //System.out.println("SHANIL 12: ");
                                Intent intent = new Intent(CampusWallet.getContext(), CalendarNew.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            } else {
                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }
                        }
                        else if (finalJSpushTypeLists.equalsIgnoreCase("Activity")) {
                            //System.out.println("Abhinand 11: ");
                            SchoolidKey = finalJsonobjct.optString("schoolID");
                            studentSchool = finalJsonobjct.optString("schoolName");
                            StudIDKEY = finalJsonobjct.optString("studentID");
                            type = finalJsonobjct.optString("type");
                            //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
                            if (SchoolidKey.equals(SchoolID) && (studentStandard.equals(StandardName))&&
                                    (StudIDKEY.equals(StudID))) {
                                //System.out.println("Abhinand 12: ");
                                Intent intent = new Intent(CampusWallet.getContext(), GalleryActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);

                            } else {
                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }
                        }
                        else if (finalJSpushTypeLists.equalsIgnoreCase("Leave request Approved")) {
                            //System.out.println("SHANIL 11: ");
                            SchoolidKey = finalJsonobjct.optString("schoolID");
                            PhoneNo = finalJsonobjct.optString("phoneNo");
                            studentName = finalJsonobjct.optString("studentName");
                            studentSchool = finalJsonobjct.optString("schoolName");
                            StudentCode = finalJsonobjct.optString("studentCode");
                            type = finalJsonobjct.optString("type");
                            StudIDKEY = finalJsonobjct.optString("studentID");
                            StdDivIdKey = finalJsonobjct.getString("standardDivisionID");
                            //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
                            if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey))) {
                                //System.out.println("SHANIL xyz: ");
                                Intent intent = new Intent(CampusWallet.getContext(), LeaveRequestActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);

                            } else {
                                //System.out.println("SHANIL zyx: ");
                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }
                        }  else if (finalJSpushTypeLists.equalsIgnoreCase("Bus Tracking")) {
                            //System.out.println("SHANIL 9: ");
                            SchoolidKey = finalJsonobjct.optString("schoolID");
//                                    PhoneNo = data.optString("phoneNo");
//                                    studentName = data.optString("studentName");
                            studentSchool = finalJsonobjct.optString("schoolName");
//                                    StudentCode = data.optString("studentCode");
                            type = finalJsonobjct.optString("type");
//                                    StudIDKEY = data.optString("studentID");
//                                    StdDivIdKey = data.getString("standardDivisionID");
//                                    StdDivIdKey = data.getString("standardDivisionID");
                            if (SchoolidKey.equals(SchoolID) ) {
                                //System.out.println("SHANIL 10: ");
                                Intent intent = new Intent(CampusWallet.getContext(), ParentMapsActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }
                            else {
                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }}
                        else if (finalJSpushTypeLists.equalsIgnoreCase("Inbox message")) {
                            SchoolidKey = finalJsonobjct.optString("schoolID");
                            PhoneNo = finalJsonobjct.optString("phoneNo");
                            studentName = finalJsonobjct.optString("studentName");
                            studentSchool = finalJsonobjct.optString("schoolName");
                            StudentCode = finalJsonobjct.optString("studentCode");
                            type = finalJsonobjct.optString("type");
                            StudIDKEY = finalJsonobjct.optString("studentID");
                            StdDivIdKey = finalJsonobjct.getString("standardDivisionID");
                            //System.out.println("SHANIL 11: ");
                            String msg_type = " ";
                            msg_type = finalJsonobjct.getString("messageTypeID");
                            //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
                            if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey))) {
                                //System.out.println("SHANIL xyz1: ");
                                Intent intent = new Intent(CampusWallet.getContext(), E_Diary_Activity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle bundle = new Bundle();
                                bundle.putString("MessageTo", "RequestFromParent");
                                intent.putExtras(bundle);
                                CampusWallet.getContext().startActivity(intent);
                            } else {
                                //System.out.println("SHANIL zyx2: ");
                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }
                        } else if (finalJSpushTypeLists.equalsIgnoreCase("Messages")) {
                            //System.out.println("SHANIL 11: ");
                            SchoolidKey = finalJsonobjct.optString("schoolID");
                            PhoneNo = finalJsonobjct.optString("phoneNo");
                            studentName = finalJsonobjct.optString("studentName");
                            studentSchool = finalJsonobjct.optString("schoolName");
                            StudentCode = finalJsonobjct.optString("studentCode");
                            type = finalJsonobjct.optString("type");
                            StudIDKEY = finalJsonobjct.optString("studentID");
                            StdDivIdKey = finalJsonobjct.getString("standardDivisionID");
                            String SubjectID = finalJsonobjct.optString("subjectID");
                            String SubjectName = finalJsonobjct.optString("subjectName");

                            System.out.println("ABHINAND  :: SUBJECT ID: "+SubjectID);
                            System.out.println("ABHINAND  :: SUBJECT NAME: "+SubjectName);
                            //System.out.println("SubjectId " + SubjectID);
                            //System.out.println("StandardDivId1111 " + StandardDivId);
                            //System.out.println("SchoolID111 " + SchoolID);
                            //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
                            if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey))) {
                                //System.out.println("SHANIL 12testing: ");
                                if (SubjectID.equals("0")) {
                                    Intent mIntent = new Intent(CampusWallet.getContext(), TeachermessageDetailsActivity.class);
                                    Bundle b = new Bundle();
                                    //System.out.println("SubjectId " + SubjectID);
                                    b.putString("SubjectId", SubjectID);
                                    b.putString("SubjectName", SubjectName);
                                    b.putBoolean("Flagteacher",true);
                                    mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mIntent.putExtras(b);
                                    CampusWallet.getContext().startActivity(mIntent);
                                } else {
                                    Intent mIntent = new Intent(CampusWallet.getContext(), TeachermessageDetailsActivity.class);
                                    Bundle b = new Bundle();
                                    //System.out.println("SubjectId " + SubjectID);
                                    b.putString("SubjectId", SubjectID);
                                    b.putString("SubjectName", SubjectName);
                                    b.putBoolean("Flagteacher",false);
                                    mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mIntent.putExtras(b);
                                    CampusWallet.getContext().startActivity(mIntent);
                                }
                            } else {
                                //System.out.println("SHANIL 13: ");
                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }
                        }
                        else if (finalJSpushTypeLists.equalsIgnoreCase("Messages all")) {
                            //System.out.println("SHANIL 11: ");
                            SchoolidKey = finalJsonobjct.optString("schoolID");
                            PhoneNo = finalJsonobjct.optString("phoneNo");
                            studentName = finalJsonobjct.optString("studentName");
                            studentSchool = finalJsonobjct.optString("schoolName");
                            StudentCode = finalJsonobjct.optString("studentCode");
                            type = finalJsonobjct.optString("type");
                            StudIDKEY = finalJsonobjct.optString("studentID");
                            try {
                                StdDivIdKey = finalJsonobjct.getString("standardDivisionID");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String SubjectID = finalJsonobjct.optString("subjectID");
                            String SubjectName = finalJsonobjct.optString("subjectName");
                            //System.out.println("SubjectId " + SubjectID);
                            //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
                            if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey))) {
                                //System.out.println("SHANIL 12: ");
                                if (SubjectID.equals("0")) {
                                    Intent mIntent = new Intent(CampusWallet.getContext(), TeachermessageDetailsActivity.class);
                                    Bundle b = new Bundle();
                                    //System.out.println("SubjectId " + SubjectID);
                                    b.putString("SubjectId", SubjectID);
                                    b.putString("SubjectName", SubjectName);
                                    b.putBoolean("Flagteacher",true);
                                    mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mIntent.putExtras(b);
                                    CampusWallet.getContext().startActivity(mIntent);
                                } else {
                                    Intent mIntent = new Intent(CampusWallet.getContext(), TeachermessageDetailsActivity.class);
                                    Bundle b = new Bundle();
                                    //System.out.println("SubjectId " + SubjectID);
                                    b.putString("SubjectId", SubjectID);
                                    b.putString("SubjectName", SubjectName);
                                    b.putBoolean("Flagteacher",false);
                                    mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mIntent.putExtras(b);
                                    CampusWallet.getContext().startActivity(mIntent);
                                }

                            }
                            else {
                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }
                        }
                        else if (finalJSpushTypeLists.equalsIgnoreCase("Transaction")) {
                            //System.out.println("SHANIL 14: ");
                            SchoolidKey = finalJsonobjct.optString("schoolID");
                            PhoneNo = finalJsonobjct.optString("phoneNo");
                            studentName = finalJsonobjct.optString("studentName");
                            studentSchool = finalJsonobjct.optString("schoolName");
                            StudentCode = finalJsonobjct.optString("studentCode");
                            type = finalJsonobjct.optString("type");
                            StudIDKEY = finalJsonobjct.optString("studentID");
                            StdDivIdKey = finalJsonobjct.getString("standardDivisionID");
                            TransTypId = finalJsonobjct.optString("transactionTypeID");
                            TrnsTypName = finalJsonobjct.optString("transactionTypeName");
                            StudIDKEY = finalJsonobjct.optString("studentID");
                            //System.out.println("Canteentrnsactionss : " + finalJSpushTypeLists);
                            //System.out.println("Stud Details  : " + StudID + "  " + StudIDKEY);
                            if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey)) && (StudID.equals(StudIDKEY))) {
                                //System.out.println("SHANIL 15: ");
                                Intent mIntent = new Intent(CampusWallet.getContext(), CardTransactionActivity.class);
                                Bundle b = new Bundle();
                                //System.out.println("transactionTypeID " + TransTypId);
                                b.putString("transactionTypeID", TransTypId);
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                mIntent.putExtras(b);
                                CampusWallet.getContext().startActivity(mIntent);
                            } else {
                                //System.out.println("SHANIL 22: ");
                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }

                        }
                        else if (finalJSpushTypeLists.equalsIgnoreCase("adds"))
                        {
                            SchoolidKey = finalJsonobjct.optString("schoolID");
                            PhoneNo = finalJsonobjct.optString("phoneNo");
                            studentName = finalJsonobjct.optString("studentName");
                            studentSchool = finalJsonobjct.optString("schoolName");
                            StudentCode = finalJsonobjct.optString("studentCode");
                            type = finalJsonobjct.optString("type");
                            StudIDKEY = finalJsonobjct.optString("studentID");
                            StdDivIdKey = finalJsonobjct.getString("standardDivisionID");
                            //System.out.println("SHANIL 25: ");
                            //System.out.println("home_menu : stdIDnew_home :  " + SchoolidKey + StdDivIdKey);
                            //System.out.println("homeMessage:  " + SchoolidKey + StdDivIdKey);
                            //System.out.println("SHANIL 26: ");
                            //System.out.println("home1");
                            String weburl= finalJsonobjct.getString("weburl");
                            String imageurl= finalJsonobjct.getString("imageurl");
                            String chillarAddsID= finalJsonobjct.getString("chillarAddsID");
                            //System.out.println("background");
                            Intent mIntent = new Intent(CampusWallet.getContext(), HomeActivity.class);
                            //System.out.println("SHANIL 27: ");
                            Constants.HomePopup = finalJSbody;
                            Constants.HomePopupTitle = finalJSTitle;
                            Constants.HomeFlag = "true1";
                            Constants.onesignal_weburl=weburl;
                            Constants.onesignal_imageurl=imageurl;
                            Constants.onesignal_chillarAddsID=chillarAddsID;
                            Bundle b = new Bundle();
                            //System.out.println("SHANIL 27: CONTENT " + Constants.HomePopup);
                            //System.out.println("SHANIL 27: TITLE " + Constants.HomePopupTitle);
                            //System.out.println("SHANIL 27: FLAG " + Constants.HomeFlag);
                            b.putString("CONTENT", Constants.HomePopup);
                            b.putString("TITLE", Constants.HomePopupTitle);
                            b.putString("IMGURL", Constants.onesignal_weburl);
                            b.putString("WEBURL", Constants.onesignal_imageurl);
                            b.putString("chillarAddsID", Constants.onesignal_chillarAddsID);
                            mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                            mIntent.putExtras(b);
                            CampusWallet.getContext().startActivity(mIntent);
                        }
                        else if (finalJSpushTypeLists.equalsIgnoreCase("success_recharge")) {
                            //System.out.println("SHANIL 29: ");
                            String trn_type = " ";
                            trn_type = finalJsonobjct.getString("transaction_type");
                            SchoolidKey = finalJsonobjct.optString("schoolID");
                            PhoneNo = finalJsonobjct.optString("phoneNo");
                            studentName = finalJsonobjct.optString("studentName");
                            studentSchool = finalJsonobjct.optString("schoolName");
                            StudentCode = finalJsonobjct.optString("studentCode");
                            type = finalJsonobjct.optString("type");
                            StudIDKEY = finalJsonobjct.optString("studentID");
                            StdDivIdKey = finalJsonobjct.getString("standardDivisionID");
                            //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
                            if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey))) {
                                //System.out.println("SHANIL xyz1: ");
                                Intent intent = new Intent(CampusWallet.getContext(), PaymentHistoryActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle bundle=new Bundle();
                                bundle.putString("pos","1");
                                intent.putExtras(bundle);
                                CampusWallet.getContext().startActivity(intent);

                            } else {Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                CampusWallet.getContext().startActivity(intent);
                            }
                        }
                        else {
                            //System.out.println("SHANIL 23: ");
                        }
                    } else {
                        //System.out.println("SHANIL 24: ");
                        Intent intent = new Intent(CampusWallet.getContext(), HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                        CampusWallet.getContext().startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView Content;
        final TextView MessageBody;
        final ImageView iconimage;
        final RelativeLayout mainLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            Content = itemView.findViewById(R.id.content);
            MessageBody = itemView.findViewById(R.id.message);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            iconimage = itemView.findViewById(R.id.iconimage);
        }
    }
}
