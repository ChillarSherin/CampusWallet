//package com.chillarcards.campuswallet.OneSignal;
//
//import android.app.ActivityManager;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//
////import com.onesignal.OSNotificationAction;
////import com.onesignal.OSNotificationOpenResult;
////import com.onesignal.OneSignal;
//
//import org.json.JSONObject;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import com.chillarcards.campuswallet.BusTracking.ParentMapsActivity;
//import com.chillarcards.campuswallet.Diary.Calendar.CalendarNew;
//import com.chillarcards.campuswallet.Diary.EDiary.E_Diary_Activity;
//import com.chillarcards.campuswallet.Diary.EDiary.TeachermessageDetailsActivity;
//import com.chillarcards.campuswallet.Diary.Gallary.GalleryActivity;
//import com.chillarcards.campuswallet.Diary.LeaveRequest.LeaveRequestActivity;
//import com.chillarcards.campuswallet.Diary.NoticeBoard.NoticeBoardActivity;
//import com.chillarcards.campuswallet.Diary.TimeTable.SubjectMsgTeacher;
//import com.chillarcards.campuswallet.NotificationCenter.Contact;
//import com.chillarcards.campuswallet.NotificationCenter.DatabaseHandler;
//import com.chillarcards.campuswallet.Payment.History.PaymentHistoryActivity;
//import com.chillarcards.campuswallet.application.CampusWallet;
//import com.chillarcards.campuswallet.application.Constants;
//import com.chillarcards.campuswallet.application.PrefManager;
//import com.chillarcards.campuswallet.home.HomeActivity;
//import com.chillarcards.campuswallet.payments.Transactions.OnlineTransactionStatusActivity;
//import com.chillarcards.campuswallet.payments.cardtransaction.CardTransactionActivity;
//import com.chillarcards.campuswallet.studentselection.StudentListActivity;
//
//import static android.content.Context.MODE_PRIVATE;
//
//@SuppressWarnings("ALL")
//public class  MyNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
//
//    String Content,title;
//
//    String datakeys,StudID;
//    String SchoolID,SchoolName, StandardDivId,StandardName,SchoolidKey, StdDivIdKey,TrnsTypName,TransTypId,StudIDKEY;
//    String PhoneNo, studentDiv,studentName, studentSchool,studentStandard,StudentCode,type;
//    PrefManager prefManager;
//
//    // This fires when a notification is opened by tapping on it.
//
//    DatabaseHandler db = new DatabaseHandler(CampusWallet.getContext());
//    @Override
//    public void notificationOpened(OSNotificationOpenResult result) {
//
//        //System.out.println("ONESIGNAL 8 ");
//
//
//
//        try {
//            prefManager=new PrefManager(CampusWallet.getContext());
//
//            /*shared preference.......................................................*/
//            SchoolID = prefManager.getSChoolID();
//            StandardDivId =  prefManager.getStudentStandardDivisionID();
//            StandardName =  prefManager.getStudentStandard();
//            StudID =  prefManager.getStudentId();
//            SchoolName =  prefManager.getSChoolName();
//            //System.out.println("SHANIL 1: ");
//
//            //System.out.println("ONESIGNAL 9 ALL DETAILS Application : " + "studentID : " + String.valueOf(StudID) + "  SchoolID : " + String.valueOf(SchoolID)
////                    + "  standardDivisionID : " + String.valueOf(StandardDivId));
//
//            //System.out.println("ONESIGNAL 10 STDIVid : " + StandardDivId);
//            //System.out.println("ONESIGNAL 11 CODMOB SchoolID : " + SchoolID);
//            //System.out.println("ONESIGNAL 12 CODMOB StudID : " + StudID);
//
//
//            Date currentTime = Calendar.getInstance().getTime();
//
//            OSNotificationAction.ActionType actionType = result.action.type;
//            JSONObject data = result.notification.payload.additionalData;
//            JSONObject contents = result.notification.payload.toJSONObject();
//
//            String Clicked="1";
//            String NotificationID;
//            NotificationID= (contents.optString("notificationID"));
//            //System.out.println("ONESIGNAL 13 MAIN CONTENTS : " + contents);
//            //System.out.println("ONESIGNAL 14 MAIN CONTENTS2 : " + currentTime.toString()+"   "+ contents.toString());
//            //System.out.println("ONESIGNAL 15 notificationId 3 "+NotificationID);
//
//            db.UpdateTable((NotificationID));
//
//
//
//            //If we send notification with action buttons we need to specidy the button id's and retrieve it to
//            //do the necessary operation.
//            if (actionType == OSNotificationAction.ActionType.ActionTaken) {
//                Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);
//                if (result.action.actionID.equals("more")) {
//
//                    if (data != null) {
//
//                        //System.out.println("SHANIL 2: ");
//                        Content = contents.optString("body");
//                        title = contents.optString("title");
//                        //System.out.println("contents : " + Content);
//
//
//                        //System.out.println("data : " + data);
//
//
//                        if (data != null) {
//
//
//                            //System.out.println("SHANIL 3: ");
//                            datakeys = data.optString("pushType");
//
//
//                            type = data.optString("type");
//
//                            if (type.equals("school")) {
//                                studentStandard = data.optString("standardName");
//                                studentDiv = data.optString("standardDivisionName");
//                            } else if (type.equals("college")) {
//                                studentStandard = data.optString("courseName");
//                                studentDiv = data.optString("semesterName");
//                            }
//
//
//                            //System.out.println("schoolid : stdID :  " + SchoolidKey + StdDivIdKey);
//                            //System.out.println("schoolidold : stdID :  " + SchoolID + StandardDivId);
//                            //System.out.println("datakey : " + datakeys);
//                            //System.out.println("PhoneNo : " + PhoneNo);
//                            //System.out.println("studentDiv : " + studentDiv);
//                            //System.out.println("studentName : " + studentName);
//                            //System.out.println("studentSchool : " + studentSchool);
//                            //System.out.println("studentStandard : " + studentStandard);
//                            //System.out.println("StudentCode : " + StudentCode);
//                            //System.out.println("type : " + type);
//                            //System.out.println("StudIDKEY : " + StudIDKEY);
//
//
//                            //System.out.println("keyvalue : " + datakeys);
//
//                            if (datakeys.equalsIgnoreCase("home_status")) {
//                                SchoolidKey = data.optString("schoolID");
//                                PhoneNo = data.optString("phoneNo");
//                                studentName = data.optString("studentName");
//                                studentSchool = data.optString("schoolName");
//                                StudentCode = data.optString("studentCode");
//                                type = data.optString("type");
//                                StudIDKEY = data.optString("studentID");
//                                StdDivIdKey = data.getString("standardDivisionID");
//                                //System.out.println("SHANIL 4: ");
//                                //System.out.println("home_menu : stdIDnew_home :  " + SchoolidKey + StdDivIdKey);
//                                //System.out.println("homeMessage:  " + SchoolidKey + StdDivIdKey);
//                                //System.out.println("SHANIL 5: ");
//                                //System.out.println("home1");
//
//                                if (isAppIsInBackground(CampusWallet.getContext())) {
//                                    //System.out.println("background");
//                                    Intent mIntent = new Intent(CampusWallet.getContext(), HomeActivity.class);
//                                    //System.out.println("SHANIL 6: ");
//                                    Constants.HomePopup = Content;
//                                    Constants.HomePopupTitle = title;
//                                    Constants.HomeFlag = "true";
//
//                                    Bundle b = new Bundle();
//                                    //System.out.println("SHANIL 6: CONTENT " + Constants.HomePopup);
//                                    //System.out.println("SHANIL 6: TITLE " + Constants.HomePopupTitle);
//                                    //System.out.println("SHANIL 6: FLAG " + Constants.HomeFlag);
//                                    b.putString("CONTENT", Constants.HomePopup);
//                                    b.putString("TITLE", Constants.HomePopupTitle);
//
//                                    mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    mIntent.putExtras(b);
//                                    CampusWallet.getContext().startActivity(mIntent);
//                                } else {
//                                    //System.out.println("SHANIL 7: ");
//                                }
//
//
//                            } else if (datakeys.equalsIgnoreCase("Bus Tracking")) {
//                                //System.out.println("SHANIL 9: ");
//                                SchoolidKey = data.optString("schoolID");
////                                    PhoneNo = data.optString("phoneNo");
////                                    studentName = data.optString("studentName");
//                                studentSchool = data.optString("schoolName");
////                                    StudentCode = data.optString("studentCode");
//                                type = data.optString("type");
////                                    StudIDKEY = data.optString("studentID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
//                                if (SchoolidKey.equals(SchoolID)) {
//
//                                    //System.out.println("SHANIL 10: ");
//
//                                    Intent intent = new Intent(CampusWallet.getContext(), ParentMapsActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//
//                                }
//                                else {
//
//                                    Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//
//                                }}else if (datakeys.equalsIgnoreCase("notice board")) {
//                                //System.out.println("SHANIL 9: ");
//                                SchoolidKey = data.optString("schoolID");
//                                Integer messageID = data.optInt("messageID");
////                                    PhoneNo = data.optString("phoneNo");
////                                    studentName = data.optString("studentName");
//                                studentSchool = data.optString("schoolName");
////                                    StudentCode = data.optString("studentCode");
//                                type = data.optString("type");
////                                    StudIDKEY = data.optString("studentID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
//                                if (SchoolidKey.equals(SchoolID) /*&& (studentStandard.equals(StandardName))*/&&
//                                        (studentSchool.equals(SchoolName))) {
//
//                                    //System.out.println("SHANIL 10: ");
//
//                                    Intent intent = new Intent(CampusWallet.getContext(), NoticeBoardActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putInt("messageID",messageID);
//                                    intent.putExtras(bundle);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//
//                                }
//                                else {
//
//                                    Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//
//                                }
//                            }
//                            else if (datakeys.equalsIgnoreCase("Bus Tracking")) {
//                                //System.out.println("SHANIL 9: ");
//                                SchoolidKey = data.optString("schoolID");
////                                    PhoneNo = data.optString("phoneNo");
////                                    studentName = data.optString("studentName");
//                                studentSchool = data.optString("schoolName");
////                                    StudentCode = data.optString("studentCode");
//                                type = data.optString("type");
////                                    StudIDKEY = data.optString("studentID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
//                                if (SchoolidKey.equals(SchoolID) ) {
//
//                                    //System.out.println("SHANIL 10: ");
//
//                                    Intent intent = new Intent(CampusWallet.getContext(), ParentMapsActivity.class);
//
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//
//                                }
//                                else {
//
//                                    Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//
//                                }
//                            }else if (datakeys.equalsIgnoreCase("Activity")) {
//                                //System.out.println("Abhinand Activity: 1");
//                                SchoolidKey = data.optString("schoolID");
////                                    PhoneNo = data.optString("phoneNo");
////                                    studentName = data.optString("studentName");
//                                studentSchool = data.optString("schoolName");
////                                    StudentCode = data.optString("studentCode");
//                                type = data.optString("type");
//                                StudIDKEY = data.optString("studentID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
//                                StdDivIdKey = data.getString("standardDivisionID");
//                                if (SchoolidKey.equals(SchoolID) && (studentStandard.equals(StandardName))
//                                        && (StudIDKEY.equals(StudID))
//                                        && (StdDivIdKey.equals(StandardDivId))) {
//
//                                    //System.out.println("Abhinand Activity: 1 ");
//
//                                    Intent intent = new Intent(CampusWallet.getContext(), GalleryActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//
//                                }
//                                else {
//
//                                    Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//
//                                }
////
//
//                            }else if (datakeys.equalsIgnoreCase("notice board std")) {
//                                //System.out.println("SHANIL 9: ");
//                                SchoolidKey = data.optString("schoolID");
//                                Integer messageID = data.optInt("messageID");
////                                    PhoneNo = data.optString("phoneNo");
////                                    studentName = data.optString("studentName");
//                                studentSchool = data.optString("schoolName");
////                                    StudentCode = data.optString("studentCode");
//                                type = data.optString("type");
////                                    StudIDKEY = data.optString("studentID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
//                                //System.out.println("Abhinand Standard Check : code: studentStandard : "+studentStandard+ " : Pref :"+StandardName );
//                                //System.out.println("Abhinand School Check : code: SchoolidKey : "+SchoolidKey + " : Pref :"+ SchoolID);
//                                if (SchoolidKey.equals(SchoolID) && (studentStandard.equals(StandardName))) {
//                                    Intent intent = new Intent(CampusWallet.getContext(), NoticeBoardActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putInt("messageID",messageID);
//                                    intent.putExtras(bundle);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//                                }
//                                else {
//                                    Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//                                }
//                            }else if (datakeys.equalsIgnoreCase("calender")) {
//                                //System.out.println("SHANIL 11: ");
//                                SchoolidKey = data.optString("schoolID");
////                                    PhoneNo = data.optString("phoneNo");
//                                studentSchool = data.optString("schoolName");
////                                    StudentCode = data.optString("studentCode");
//                                type = data.optString("type");
////                                    StudIDKEY = data.optString("studentID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
////                                    StdDivIdKey = data.optString("standardDivisionID");
//                                //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
//                                if (SchoolidKey.equals(SchoolID) ) {
//                                    //System.out.println("SHANIL 12: ");
//                                    Intent intent = new Intent(CampusWallet.getContext(), CalendarNew.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//
//                                } else {
//                                    Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//
//                                }
//                            }
//                            else if (datakeys.equalsIgnoreCase("School Bus Notification")) {
//                                //System.out.println("SHANIL 11: Bus System Notification ");
//                                SchoolidKey = data.optString("schoolID");
//                                //System.out.println("Abhinand SchoolidKey : "+SchoolidKey);
////                                    PhoneNo = data.optString("phoneNo");
//                                studentSchool = data.optString("schoolName");
//                                //System.out.println("Abhinand studentSchool : "+studentSchool);
////                                    StudentCode = data.optString("studentCode");
//                                type = data.optString("type");
//                                //System.out.println("Abhinand type : "+type);
////                                    StudIDKEY = data.optString("studentID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
////                                    StdDivIdKey = data.optString("standardDivisionID");
//                                //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey +" : "+ StdDivIdKey);
//                                if (SchoolidKey.equals(SchoolID) ) {
//                                    //System.out.println("SHANIL 12: ");
//                                    Intent intent = new Intent(CampusWallet.getContext(), ParentMapsActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//
//                                } else {
//                                    Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//
//                                }
//                            }
//                            else if (datakeys.equalsIgnoreCase("calender std")) {
//                                //System.out.println("SHANIL 11: ");
//                                SchoolidKey = data.optString("schoolID");
////                                    PhoneNo = data.optString("phoneNo");
//                                studentSchool = data.optString("schoolName");
////                                    StudentCode = data.optString("studentCode");
//                                type = data.optString("type");
////                                    StudIDKEY = data.optString("studentID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
////                                    StdDivIdKey = data.optString("standardDivisionID");
//                                //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
//                                //System.out.println("Abhinand Standard Check : code: studentStandard : "+studentStandard+ " : Pref :"+StandardName );
//                                //System.out.println("Abhinand School Check : code: SchoolidKey : "+SchoolidKey + " : Pref :"+ SchoolID);
//                                if (SchoolidKey.equals(SchoolID) && (studentStandard.equals(StandardName))) {
//                                    //System.out.println("SHANIL 12: ");
//                                    Intent intent = new Intent(CampusWallet.getContext(), CalendarNew.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//
//                                } else {
//                                    Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//
//                                }
//                            } else if (datakeys.equalsIgnoreCase("Leave request Approved")) {
//                                //System.out.println("SHANIL 11: ");
//
//                                SchoolidKey = data.optString("schoolID");
//                                Integer messageID = data.optInt("messageID");
//                                PhoneNo = data.optString("phoneNo");
//                                studentName = data.optString("studentName");
//                                studentSchool = data.optString("schoolName");
//                                StudentCode = data.optString("studentCode");
//                                type = data.optString("type");
//                                StudIDKEY = data.optString("studentID");
//                                StdDivIdKey = data.getString("standardDivisionID");
//                                //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
//                                //System.out.println("Abhinand Standard Check : code: studentStandard : "+studentStandard+ " : Pref :"+StandardName );
//                                //System.out.println("Abhinand School Check : code: SchoolidKey : "+SchoolidKey + " : Pref :"+ SchoolID);
//                                if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey))) {
//                                    //System.out.println("SHANIL xyz: ");
//
//                                    Bundle bundle = new Bundle();
//                                    bundle.putInt("messageID",messageID);
//
//
//                                    Intent intent = new Intent(CampusWallet.getContext(), LeaveRequestActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    intent.putExtras(bundle);
//                                    CampusWallet.getContext().startActivity(intent);
//
//                                } else {
//
//                                    Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//                                }
//                            } else if (datakeys.equalsIgnoreCase("Inbox message")) {
//                                SchoolidKey = data.optString("schoolID");
//                                PhoneNo = data.optString("phoneNo");
//                                studentName = data.optString("studentName");
//                                studentSchool = data.optString("schoolName");
//                                StudentCode = data.optString("studentCode");
//                                type = data.optString("type");
//                                StudIDKEY = data.optString("studentID");
//                                StdDivIdKey = data.getString("standardDivisionID");
//                                //System.out.println("SHANIL 11: ");
//                                String msg_type = " ";
//                                msg_type = data.getString("messageTypeID");
//                                //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
//                                //System.out.println("Abhinand Standard Check : code: studentStandard : "+studentStandard+ " : Pref :"+StandardName );
//                                //System.out.println("Abhinand School Check : code: SchoolidKey : "+SchoolidKey + " : Pref :"+ SchoolID);
//                                if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey))) {
//                                    //System.out.println("SHANIL xyz1: ");
//
//                                    Intent intent = new Intent(CampusWallet.getContext(), E_Diary_Activity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putString("MessageTo", "RequestFromParent");
//                                    intent.putExtras(bundle);
//                                    CampusWallet.getContext().startActivity(intent);
//
//                                } else {
//                                    Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//
//
//                                }
//                            } else if (datakeys.equalsIgnoreCase("Messages")) {
//                                //System.out.println("SHANIL 11: ");
//                                SchoolidKey = data.optString("schoolID");
//                                Integer messageID  = data.optInt("messageID");
//                                PhoneNo = data.optString("phoneNo");
//                                studentName = data.optString("studentName");
//                                studentSchool = data.optString("schoolName");
//                                StudentCode = data.optString("studentCode");
//                                type = data.optString("type");
//                                StudIDKEY = data.optString("studentID");
//                                StdDivIdKey = data.getString("standardDivisionID");
//                                String SubjectID = data.optString("subjectID");
//                                String SubjectName = data.optString("subjectName");
//                                //System.out.println("SubjectId " + SubjectID);
//                                //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
//                                //System.out.println("Abhinand Standard Check : code: studentStandard : "+studentStandard+ " : Pref :"+StandardName );
//                                //System.out.println("Abhinand School Check : code: SchoolidKey : "+SchoolidKey + " : Pref :"+ SchoolID);
//                                if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey))&& (StudIDKEY.equals(StudID))) {
//                                    //System.out.println("SHANIL 12: ");
//                                    if (SubjectID.equals("0")) {
//                                        Intent mIntent = new Intent(CampusWallet.getContext(), TeachermessageDetailsActivity.class);
//                                        Bundle b = new Bundle();
//                                        //System.out.println("SubjectId " + SubjectID);
//                                        b.putString("SubjectId", SubjectID);
//                                        b.putString("SubjectName", SubjectName);
//                                        b.putInt("messageID", messageID);
//                                        b.putBoolean("Flagteacher",true);
//                                        mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        mIntent.putExtras(b);
//                                        CampusWallet.getContext().startActivity(mIntent);
//                                    } else {
//                                        Intent mIntent = new Intent(CampusWallet.getContext(), TeachermessageDetailsActivity.class);
//                                        Bundle b = new Bundle();
//                                        //System.out.println("SubjectId " + SubjectID);
//                                        b.putString("SubjectId", SubjectID);
//                                        b.putString("SubjectName", SubjectName);
//                                        b.putInt("messageID",messageID);
//                                        b.putBoolean("Flagteacher",false);
//                                        mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        mIntent.putExtras(b);
//                                        CampusWallet.getContext().startActivity(mIntent);
//
//
//                                    }
//
//                                } else {
//                                    Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//                                }
//                            }
//                            else if (datakeys.equalsIgnoreCase("Messages all")) {
//                                //System.out.println("SHANIL 11: ");
//                                SchoolidKey = data.optString("schoolID");
//                                Integer messageID  = data.optInt("messageID");
//                                PhoneNo = data.optString("phoneNo");
//                                studentName = data.optString("studentName");
//                                studentSchool = data.optString("schoolName");
//                                StudentCode = data.optString("studentCode");
//                                type = data.optString("type");
//                                StudIDKEY = data.optString("studentID");
//                                StdDivIdKey = data.getString("standardDivisionID");
//                                String SubjectID = data.optString("subjectID");
//                                String SubjectName = data.optString("subjectName");
//                                //System.out.println("SubjectId " + SubjectID);
//                                //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
//                                //System.out.println("Abhinand Standard Check : code: studentStandard : "+studentStandard+ " : Pref :"+StandardName );
//                                //System.out.println("Abhinand School Check : code: SchoolidKey : "+SchoolidKey + " : Pref :"+ SchoolID);
//                                if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey))) {
//                                    //System.out.println("SHANIL 12: ");
//                                    if (SubjectID.equals("0")) {
//                                        Intent mIntent = new Intent(CampusWallet.getContext(), TeachermessageDetailsActivity.class);
//                                        Bundle b = new Bundle();
//                                        //System.out.println("SubjectId " + SubjectID);
//                                        b.putString("SubjectId", SubjectID);
//                                        b.putString("SubjectName", SubjectName);
//                                        b.putInt("messageID", messageID);
//                                        b.putBoolean("Flagteacher",true);
//                                        mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        mIntent.putExtras(b);
//                                        CampusWallet.getContext().startActivity(mIntent);
//                                    } else {
//                                        Intent mIntent = new Intent(CampusWallet.getContext(), TeachermessageDetailsActivity.class);
//                                        Bundle b = new Bundle();
//                                        //System.out.println("SubjectId " + SubjectID);
//                                        b.putString("SubjectId", SubjectID);
//                                        b.putString("SubjectName", SubjectName);
//                                        b.putInt("messageID", messageID);
//                                        b.putBoolean("Flagteacher",false);
//                                        mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        mIntent.putExtras(b);
//                                        CampusWallet.getContext().startActivity(mIntent);
//
//
//                                    }
//
//                                } else {
//                                    Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//                                }
//                            }
//                            else if (datakeys.equalsIgnoreCase("Transaction")) {
//                                //System.out.println("SHANIL 14: ");
//                                SchoolidKey = data.optString("schoolID");
//                                PhoneNo = data.optString("phoneNo");
//                                studentName = data.optString("studentName");
//                                studentSchool = data.optString("schoolName");
//                                StudentCode = data.optString("studentCode");
//                                type = data.optString("type");
//                                StudIDKEY = data.optString("studentID");
//                                StdDivIdKey = data.getString("standardDivisionID");
//                                TransTypId = data.optString("transactionTypeID");
//                                TrnsTypName = data.optString("transactionTypeName");
//                                StudIDKEY = data.optString("studentID");
//                                //System.out.println("Canteentrnsactionss : " + datakeys);
//                                //System.out.println("Stud Details  : " + StudID + "  " + StudIDKEY);
//
//
//                                if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey)) && (StudID.equals(StudIDKEY))) {
//
//                                    Intent intent = new Intent(CampusWallet.getContext(), CardTransactionActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//                                } else {
//                                    Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//                                }
//                            }
//                            else if (datakeys.equalsIgnoreCase("adds"))
//                            {
//                                SchoolidKey = data.optString("schoolID");
//                                PhoneNo = data.optString("phoneNo");
//                                studentName = data.optString("studentName");
//                                studentSchool = data.optString("schoolName");
//                                StudentCode = data.optString("studentCode");
//                                type = data.optString("type");
//                                StudIDKEY = data.optString("studentID");
//                                StdDivIdKey = data.getString("standardDivisionID");
//
//                                //System.out.println("SHANIL 25: ");
//                                //System.out.println("home_menu : stdIDnew_home :  " + SchoolidKey + StdDivIdKey);
//                                //System.out.println("homeMessage:  " + SchoolidKey + StdDivIdKey);
//                                //System.out.println("SHANIL 26: ");
//                                //System.out.println("home1");
//
//                                String weburl=data.getString("weburl");
//                                String imageurl=data.getString("imageurl");
//                                String chillarAddsID=data.getString("chillarAddsID");
//
////                                    if (isAppIsInBackground(CampusWallet.getContext())) {
//                                //System.out.println("background");
//                                Intent mIntent = new Intent(CampusWallet.getContext(), HomeActivity.class);
//                                //System.out.println("SHANIL 27: ");
//                                Constants.HomePopup = Content;
//                                Constants.HomePopupTitle = title;
//                                Constants.HomeFlag = "true1";
//                                Constants.onesignal_weburl=weburl;
//                                Constants.onesignal_imageurl=imageurl;
//                                Constants.onesignal_chillarAddsID=chillarAddsID;
//
//
//                                Bundle b = new Bundle();
//                                //System.out.println("SHANIL 27: CONTENT " + Constants.HomePopup);
//                                //System.out.println("SHANIL 27: TITLE " + Constants.HomePopupTitle);
//                                //System.out.println("SHANIL 27: FLAG " + Constants.HomeFlag);
//                                b.putString("CONTENT", Constants.HomePopup);
//                                b.putString("TITLE", Constants.HomePopupTitle);
//                                b.putString("IMGURL", Constants.onesignal_weburl);
//                                b.putString("WEBURL", Constants.onesignal_imageurl);
//                                b.putString("chillarAddsID", Constants.onesignal_chillarAddsID);
//
//                                mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                mIntent.putExtras(b);
//                                CampusWallet.getContext().startActivity(mIntent);
////                                    } else {
////                                        //System.out.println("SHANIL 28: ");
////                                    }
//
//
//                            }
//
//                            else if (datakeys.equalsIgnoreCase("success_recharge")) {
//                                //System.out.println("SHANIL 29: ");
//                                String trn_type = " ";
//                                trn_type = data.getString("transaction_type");
//                                SchoolidKey = data.optString("schoolID");
//                                PhoneNo = data.optString("phoneNo");
//                                studentName = data.optString("studentName");
//                                studentSchool = data.optString("schoolName");
//                                StudentCode = data.optString("studentCode");
//                                type = data.optString("type");
//                                StudIDKEY = data.optString("studentID");
//                                StdDivIdKey = data.getString("standardDivisionID");
//                                //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
//                                if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey))) {
//                                    Intent intent = new Intent(CampusWallet.getContext(), OnlineTransactionStatusActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//                                } else {
//                                    Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    CampusWallet.getContext().startActivity(intent);
//                                }
//                            }
//                            else if (datakeys.equalsIgnoreCase("common")) {
//
//                                //System.out.println("SHANIL 23: ");
//                                Intent intent = new Intent(CampusWallet.getContext(), HomeActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            }
//
//                            if (actionType == OSNotificationAction.ActionType.ActionTaken)
//                                Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);
//
//
//
//
//
//                        } else {
//                            //System.out.println("SHANIL 24: ");
//                            Intent intent = new Intent(CampusWallet.getContext(), HomeActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            CampusWallet.getContext().startActivity(intent);
//
//                        }
//                    }else{
//                        Intent intent = new Intent(CampusWallet.getContext(), HomeActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        CampusWallet.getContext().startActivity(intent);
//                    }
//
//
//                } else {
//
//                    //Ignore. No action to be taken
//
//                }
//            }else
//            {
//
//                if (data != null) {
//
//                    //System.out.println("SHANIL 2: ");
//                    Content = contents.optString("body");
//                    title = contents.optString("title");
//                    //System.out.println("contents : " + Content);
//
//
//                    //System.out.println("data : " + data);
//
//
//                    if (data != null) {
//
//
//                        //System.out.println("SHANIL 3: ");
//                        datakeys = data.optString("pushType");
//
//                        type = data.optString("type");
//
//                        if (type.equals("school")) {
//                            studentStandard = data.optString("standardName");
//                            studentDiv = data.optString("standardDivisionName");
//                        } else if (type.equals("college")) {
//                            studentStandard = data.optString("courseName");
//                            studentDiv = data.optString("semesterName");
//                        }
//
//
//                        //System.out.println("schoolid : stdID :  " + SchoolidKey + StdDivIdKey);
//                        //System.out.println("schoolidold : stdID :  " + SchoolID + StandardDivId);
//                        //System.out.println("datakey : " + datakeys);
//                        //System.out.println("PhoneNo : " + PhoneNo);
//                        //System.out.println("studentDiv : " + studentDiv);
//                        //System.out.println("studentName : " + studentName);
//                        //System.out.println("studentSchool : " + studentSchool);
//                        //System.out.println("studentStandard : " + studentStandard);
//                        //System.out.println("StudentCode : " + StudentCode);
//                        //System.out.println("type : " + type);
//
//
//                        //System.out.println("keyvalue : " + datakeys);
//
//                        if (datakeys.equalsIgnoreCase("home_status")) {
//                            SchoolidKey = data.optString("schoolID");
//                            PhoneNo = data.optString("phoneNo");
//                            studentName = data.optString("studentName");
//                            studentSchool = data.optString("schoolName");
//                            StudentCode = data.optString("studentCode");
//                            type = data.optString("type");
//                            StudIDKEY = data.optString("studentID");
//                            StdDivIdKey = data.getString("standardDivisionID");
//                            //System.out.println("SHANIL 4: ");
//                            //System.out.println("home_menu : stdIDnew_home :  " + SchoolidKey + StdDivIdKey);
//                            //System.out.println("homeMessage:  " + SchoolidKey + StdDivIdKey);
//                            //System.out.println("SHANIL 5: ");
//                            //System.out.println("home1");
//
//                            if (isAppIsInBackground(CampusWallet.getContext())) {
//                                //System.out.println("background");
//                                Intent mIntent = new Intent(CampusWallet.getContext(), HomeActivity.class);
//                                //System.out.println("SHANIL 6: ");
//                                Constants.HomePopup = Content;
//                                Constants.HomePopupTitle = title;
//                                Constants.HomeFlag = "true";
//
//                                Bundle b = new Bundle();
//                                //System.out.println("SHANIL 6: CONTENT " + Constants.HomePopup);
//                                //System.out.println("SHANIL 6: TITLE " + Constants.HomePopupTitle);
//                                //System.out.println("SHANIL 6: FLAG " + Constants.HomeFlag);
//                                b.putString("CONTENT", Constants.HomePopup);
//                                b.putString("TITLE", Constants.HomePopupTitle);
//
//                                mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                mIntent.putExtras(b);
//                                CampusWallet.getContext().startActivity(mIntent);
//                            } else {
//                                //System.out.println("SHANIL 7: ");
//                            }
//
//
//                        } else if (datakeys.equalsIgnoreCase("Bus Tracking")) {
//                            //System.out.println("SHANIL 9: ");
//                            SchoolidKey = data.optString("schoolID");
////                                    PhoneNo = data.optString("phoneNo");
////                                    studentName = data.optString("studentName");
//                            studentSchool = data.optString("schoolName");
////                                    StudentCode = data.optString("studentCode");
//                            type = data.optString("type");
////                                    StudIDKEY = data.optString("studentID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
//                            if (SchoolidKey.equals(SchoolID) ) {
//
//                                //System.out.println("SHANIL 10: ");
//
//                                Intent intent = new Intent(CampusWallet.getContext(), ParentMapsActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            }
//                            else {
//
//                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            }}else if (datakeys.equalsIgnoreCase("notice board")) {
//                            //System.out.println("SHANIL 9: ");
//                            SchoolidKey = data.optString("schoolID");
////                                    PhoneNo = data.optString("phoneNo");
////                                    studentName = data.optString("studentName");
//                            studentSchool = data.optString("schoolName");
////                                    StudentCode = data.optString("studentCode");
//                            type = data.optString("type");
////                                    StudIDKEY = data.optString("studentID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
//                            if (SchoolidKey.equals(SchoolID) ) {
//
//                                //System.out.println("SHANIL 10: ");
//
//                                Intent intent = new Intent(CampusWallet.getContext(), NoticeBoardActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            }
//                            else {
//
//                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            }
//                        }
//                        else if (datakeys.equalsIgnoreCase("Bus Tracking")) {
//                            //System.out.println("SHANIL 9: ");
//                            SchoolidKey = data.optString("schoolID");
////                                    PhoneNo = data.optString("phoneNo");
////                                    studentName = data.optString("studentName");
//                            studentSchool = data.optString("schoolName");
////                                    StudentCode = data.optString("studentCode");
//                            type = data.optString("type");
////                                    StudIDKEY = data.optString("studentID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
//                            if (SchoolidKey.equals(SchoolID) ) {
//
//                                //System.out.println("SHANIL 10: ");
//
//                                Intent intent = new Intent(CampusWallet.getContext(), ParentMapsActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            }
//                            else {
//
//                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            }
//                        }else if (datakeys.equalsIgnoreCase("Activity")) {
//                            //System.out.println("Abhinand Activity: 1");
//                            SchoolidKey = data.optString("schoolID");
////                                    PhoneNo = data.optString("phoneNo");
////                                    studentName = data.optString("studentName");
//                            studentSchool = data.optString("schoolName");
////                                    StudentCode = data.optString("studentCode");
//                            type = data.optString("type");
//                            StudIDKEY = data.optString("studentID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
//                            StdDivIdKey = data.getString("standardDivisionID");
//                            if (SchoolidKey.equals(SchoolID) && (studentStandard.equals(StandardName))
//                                    && (StudIDKEY.equals(StudID))
//                                    && (StdDivIdKey.equals(StandardDivId))) {
//
//                                //System.out.println("Abhinand Activity: 1 ");
//
//                                Intent intent = new Intent(CampusWallet.getContext(), GalleryActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            }
//                            else {
//
//                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            }
////
//
//                        }else if (datakeys.equalsIgnoreCase("notice board std")) {
//                            //System.out.println("SHANIL 9: ");
//                            SchoolidKey = data.optString("schoolID");
//                            Integer messageID = data.optInt("messageID");
////                                    PhoneNo = data.optString("phoneNo");
////                                    studentName = data.optString("studentName");
//                            studentSchool = data.optString("schoolName");
////                                    StudentCode = data.optString("studentCode");
//                            type = data.optString("type");
////                                    StudIDKEY = data.optString("studentID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
//                            //System.out.println("Abhinand Standard Check : code: studentStandard : "+studentStandard+ " : Pref :"+StandardName );
//                            //System.out.println("Abhinand School Check : code: SchoolidKey : "+SchoolidKey + " : Pref :"+ SchoolID);
//                            if (SchoolidKey.equals(SchoolID) && (studentStandard.equals(StandardName))) {
//
//                                //System.out.println("SHANIL 10: ");
//
////                                Intent intent = new Intent(CampusWallet.getContext(), NoticeBoardActivity.class);
////                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
////                                CampusWallet.getContext().startActivity(intent);
//
//                                Intent intent = new Intent(CampusWallet.getContext(), NoticeBoardActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putInt("messageID",messageID);
//                                intent.putExtras(bundle);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            }
//                            else {
//                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//                            }
//                        }else if (datakeys.equalsIgnoreCase("calender")) {
//                            //System.out.println("SHANIL 11: ");
//                            SchoolidKey = data.optString("schoolID");
////                                    PhoneNo = data.optString("phoneNo");
//                            studentSchool = data.optString("schoolName");
////                                    StudentCode = data.optString("studentCode");
//                            type = data.optString("type");
////                                    StudIDKEY = data.optString("studentID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
////                                    StdDivIdKey = data.optString("standardDivisionID");
//                            //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
//                            if (SchoolidKey.equals(SchoolID) ) {
//                                //System.out.println("SHANIL 12: ");
//                                Intent intent = new Intent(CampusWallet.getContext(), CalendarNew.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            } else {
//                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            }
//                        }
//                        else if (datakeys.equalsIgnoreCase("School Bus Notification")) {
//                            //System.out.println("SHANIL 11: Bus System Notification ");
//                            SchoolidKey = data.optString("schoolID");
//                            //System.out.println("Abhinand SchoolidKey : "+SchoolidKey);
////                                    PhoneNo = data.optString("phoneNo");
//                            studentSchool = data.optString("schoolName");
//                            //System.out.println("Abhinand studentSchool : "+studentSchool);
////                                    StudentCode = data.optString("studentCode");
//                            type = data.optString("type");
//                            //System.out.println("Abhinand type : "+type);
////                                    StudIDKEY = data.optString("studentID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
////                                    StdDivIdKey = data.optString("standardDivisionID");
//                            //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey +" : "+ StdDivIdKey);
//                            if (SchoolidKey.equals(SchoolID) ) {
//                                //System.out.println("SHANIL 12: ");
//                                Intent intent = new Intent(CampusWallet.getContext(), ParentMapsActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            } else {
//                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            }
//                        }
//                        else if (datakeys.equalsIgnoreCase("calender std")) {
//                            //System.out.println("SHANIL 11: ");
//                            SchoolidKey = data.optString("schoolID");
////                                    PhoneNo = data.optString("phoneNo");
//                            studentSchool = data.optString("schoolName");
////                                    StudentCode = data.optString("studentCode");
//                            type = data.optString("type");
////                                    StudIDKEY = data.optString("studentID");
////                                    StdDivIdKey = data.getString("standardDivisionID");
////                                    StdDivIdKey = data.optString("standardDivisionID");
//                            //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
//                            //System.out.println("Abhinand Standard Check : code: studentStandard : "+studentStandard+ " : Pref :"+StandardName );
//                            //System.out.println("Abhinand School Check : code: SchoolidKey : "+SchoolidKey + " : Pref :"+ SchoolID);
//                            if (SchoolidKey.equals(SchoolID) && (studentStandard.equals(StandardName))) {
//                                //System.out.println("SHANIL 12: ");
//                                Intent intent = new Intent(CampusWallet.getContext(), CalendarNew.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            } else {
//                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            }
//                        } else if (datakeys.equalsIgnoreCase("Leave request Approved")) {
//                            //System.out.println("SHANIL 11: ");
//
//                            SchoolidKey = data.optString("schoolID");
//                            PhoneNo = data.optString("phoneNo");
//                            studentName = data.optString("studentName");
//                            studentSchool = data.optString("schoolName");
//                            StudentCode = data.optString("studentCode");
//                            type = data.optString("type");
//                            StudIDKEY = data.optString("studentID");
//                            StdDivIdKey = data.getString("standardDivisionID");
//                            //System.out.println("Abhinand : schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
//                            if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey))) {
//                                //System.out.println("SHANIL xyz: ");
//                                Intent intent = new Intent(CampusWallet.getContext(), LeaveRequestActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            } else {
//
//                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//                            }
//                        } else if (datakeys.equalsIgnoreCase("Inbox message")) {
//                            SchoolidKey = data.optString("schoolID");
//                            PhoneNo = data.optString("phoneNo");
//                            studentName = data.optString("studentName");
//                            studentSchool = data.optString("schoolName");
//                            StudentCode = data.optString("studentCode");
//                            type = data.optString("type");
//                            StudIDKEY = data.optString("studentID");
//                            StdDivIdKey = data.getString("standardDivisionID");
//                            //System.out.println("SHANIL 11: ");
//                            String msg_type = " ";
//                            msg_type = data.getString("messageTypeID");
//                            //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
//                            if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey))) {
//                                //System.out.println("SHANIL xyz1: ");
//
//                                Intent intent = new Intent(CampusWallet.getContext(), E_Diary_Activity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                Bundle bundle = new Bundle();
//                                bundle.putString("MessageTo", "RequestFromParent");
//                                intent.putExtras(bundle);
//                                CampusWallet.getContext().startActivity(intent);
//
//                            } else {
//                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//
//
//                            }
//                        } else if (datakeys.equalsIgnoreCase("Messages")) {
//                            //System.out.println("SHANIL 11: ");
//                            SchoolidKey = data.optString("schoolID");
//                            PhoneNo = data.optString("phoneNo");
//                            studentName = data.optString("studentName");
//                            studentSchool = data.optString("schoolName");
//                            StudentCode = data.optString("studentCode");
//                            type = data.optString("type");
//                            StudIDKEY = data.optString("studentID");
//                            StdDivIdKey = data.getString("standardDivisionID");
//                            String SubjectID = data.optString("subjectID");
//                            String SubjectName = data.optString("subjectName");
//                            //System.out.println("SubjectId " + SubjectID);
//                            //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
//                            if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey))&& (StudIDKEY.equals(StudID))) {
//                                //System.out.println("SHANIL 12: ");
//                                if (SubjectID.equals("0")) {
//                                    Intent mIntent = new Intent(CampusWallet.getContext(), TeachermessageDetailsActivity.class);
//                                    Bundle b = new Bundle();
//                                    //System.out.println("SubjectId " + SubjectID);
//                                    b.putString("SubjectId", SubjectID);
//                                    b.putString("SubjectName", SubjectName);
//                                    b.putBoolean("Flagteacher",true);
//                                    mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    mIntent.putExtras(b);
//                                    CampusWallet.getContext().startActivity(mIntent);
//                                } else {
//                                    Intent mIntent = new Intent(CampusWallet.getContext(), TeachermessageDetailsActivity.class);
//                                    Bundle b = new Bundle();
//                                    //System.out.println("SubjectId " + SubjectID);
//                                    b.putString("SubjectId", SubjectID);
//                                    b.putString("SubjectName", SubjectName);
//                                    b.putBoolean("Flagteacher",false);
//                                    mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    mIntent.putExtras(b);
//                                    CampusWallet.getContext().startActivity(mIntent);
//
//
//                                }
//
//                            } else {
//                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//                            }
//                        }
//                        else if (datakeys.equalsIgnoreCase("Messages all")) {
//                            //System.out.println("SHANIL 11: ");
//                            SchoolidKey = data.optString("schoolID");
//                            PhoneNo = data.optString("phoneNo");
//                            studentName = data.optString("studentName");
//                            studentSchool = data.optString("schoolName");
//                            StudentCode = data.optString("studentCode");
//                            type = data.optString("type");
//                            StudIDKEY = data.optString("studentID");
//                            StdDivIdKey = data.getString("standardDivisionID");
//                            String SubjectID = data.optString("subjectID");
//                            String SubjectName = data.optString("subjectName");
//                            //System.out.println("SubjectId " + SubjectID);
//                            //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
//                            //System.out.println("Abhinand Standard Check : code: studentStandard : "+studentStandard+ " : Pref :"+StandardName );
//                            //System.out.println("Abhinand School Check : code: SchoolidKey : "+SchoolidKey + " : Pref :"+ SchoolID);
//                            if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey))) {
//                                //System.out.println("SHANIL 12: ");
//                                if (SubjectID.equals("0")) {
//                                    Intent mIntent = new Intent(CampusWallet.getContext(), TeachermessageDetailsActivity.class);
//                                    Bundle b = new Bundle();
//                                    //System.out.println("SubjectId " + SubjectID);
//                                    b.putString("SubjectId", SubjectID);
//                                    b.putString("SubjectName", SubjectName);
//                                    b.putBoolean("Flagteacher",true);
//                                    mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    mIntent.putExtras(b);
//                                    CampusWallet.getContext().startActivity(mIntent);
//                                } else {
//                                    Intent mIntent = new Intent(CampusWallet.getContext(), TeachermessageDetailsActivity.class);
//                                    Bundle b = new Bundle();
//                                    //System.out.println("SubjectId " + SubjectID);
//                                    b.putString("SubjectId", SubjectID);
//                                    b.putString("SubjectName", SubjectName);
//                                    b.putBoolean("Flagteacher",false);
//                                    mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    mIntent.putExtras(b);
//                                    CampusWallet.getContext().startActivity(mIntent);
//
//
//                                }
//
//                            } else {
//                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//                            }
//                        }
//                        else if (datakeys.equalsIgnoreCase("Transaction")) {
//                            //System.out.println("SHANIL 14: ");
//                            SchoolidKey = data.optString("schoolID");
//                            PhoneNo = data.optString("phoneNo");
//                            studentName = data.optString("studentName");
//                            studentSchool = data.optString("schoolName");
//                            StudentCode = data.optString("studentCode");
//                            type = data.optString("type");
//                            StudIDKEY = data.optString("studentID");
//                            StdDivIdKey = data.getString("standardDivisionID");
//                            TransTypId = data.optString("transactionTypeID");
//                            TrnsTypName = data.optString("transactionTypeName");
//                            StudIDKEY = data.optString("studentID");
//                            //System.out.println("Canteentrnsactionss : " + datakeys);
//                            //System.out.println("Stud Details  : " + StudID + "  " + StudIDKEY);
//
//
//                            if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey)) && (StudID.equals(StudIDKEY))) {
//
//                                Intent intent = new Intent(CampusWallet.getContext(), CardTransactionActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//                            } else {
//                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//                            }
//                        }
//                        else if (datakeys.equalsIgnoreCase("adds"))
//                        {
//                            SchoolidKey = data.optString("schoolID");
//                            PhoneNo = data.optString("phoneNo");
//                            studentName = data.optString("studentName");
//                            studentSchool = data.optString("schoolName");
//                            StudentCode = data.optString("studentCode");
//                            type = data.optString("type");
//                            StudIDKEY = data.optString("studentID");
//                            StdDivIdKey = data.getString("standardDivisionID");
//
//                            //System.out.println("SHANIL 25: ");
//                            //System.out.println("home_menu : stdIDnew_home :  " + SchoolidKey + StdDivIdKey);
//                            //System.out.println("homeMessage:  " + SchoolidKey + StdDivIdKey);
//                            //System.out.println("SHANIL 26: ");
//                            //System.out.println("home1");
//
//                            String weburl=data.getString("weburl");
//                            String imageurl=data.getString("imageurl");
//                            String chillarAddsID=data.getString("chillarAddsID");
//
////                                    if (isAppIsInBackground(CampusWallet.getContext())) {
//                            //System.out.println("background");
//                            Intent mIntent = new Intent(CampusWallet.getContext(), HomeActivity.class);
//                            //System.out.println("SHANIL 27: ");
//                            Constants.HomePopup = Content;
//                            Constants.HomePopupTitle = title;
//                            Constants.HomeFlag = "true1";
//                            Constants.onesignal_weburl=weburl;
//                            Constants.onesignal_imageurl=imageurl;
//                            Constants.onesignal_chillarAddsID=chillarAddsID;
//
//
//                            Bundle b = new Bundle();
//                            //System.out.println("SHANIL 27: CONTENT " + Constants.HomePopup);
//                            //System.out.println("SHANIL 27: TITLE " + Constants.HomePopupTitle);
//                            //System.out.println("SHANIL 27: FLAG " + Constants.HomeFlag);
//                            b.putString("CONTENT", Constants.HomePopup);
//                            b.putString("TITLE", Constants.HomePopupTitle);
//                            b.putString("IMGURL", Constants.onesignal_weburl);
//                            b.putString("WEBURL", Constants.onesignal_imageurl);
//                            b.putString("chillarAddsID", Constants.onesignal_chillarAddsID);
//
//                            mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            mIntent.putExtras(b);
//                            CampusWallet.getContext().startActivity(mIntent);
////                                    } else {
////                                        //System.out.println("SHANIL 28: ");
////                                    }
//
//
//                        }
//
//                        else if (datakeys.equalsIgnoreCase("success_recharge")) {
//                            //System.out.println("SHANIL 29: ");
//                            String trn_type = " ";
//                            trn_type = data.getString("transaction_type");
//                            SchoolidKey = data.optString("schoolID");
//                            PhoneNo = data.optString("phoneNo");
//                            studentName = data.optString("studentName");
//                            studentSchool = data.optString("schoolName");
//                            StudentCode = data.optString("studentCode");
//                            type = data.optString("type");
//                            StudIDKEY = data.optString("studentID");
//                            StdDivIdKey = data.getString("standardDivisionID");
//                            //System.out.println("schoolidnew : stdIDnew :  " + SchoolidKey + StdDivIdKey);
//                            if (SchoolID.equals(SchoolidKey) && (StandardDivId.equals(StdDivIdKey))) {
//                                Intent intent = new Intent(CampusWallet.getContext(), OnlineTransactionStatusActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//                            } else {
//                                Intent intent = new Intent(CampusWallet.getContext(), StudentListActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                CampusWallet.getContext().startActivity(intent);
//                            }
//                        }
//                        else if (datakeys.equalsIgnoreCase("common")) {
//
//                            //System.out.println("SHANIL 23: ");
//                            Intent intent = new Intent(CampusWallet.getContext(), HomeActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            CampusWallet.getContext().startActivity(intent);
//
//                        }
//
//                        if (actionType == OSNotificationAction.ActionType.ActionTaken)
//                            Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);
//
//
//
//
//                    } else {
//                        //System.out.println("SHANIL 24: ");
//                        Intent intent = new Intent(CampusWallet.getContext(), HomeActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        CampusWallet.getContext().startActivity(intent);
//
//                    }
//
//
//
//                }else{
//                    Intent intent = new Intent(CampusWallet.getContext(), HomeActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    CampusWallet.getContext().startActivity(intent);
//                }
//
//            }
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//
//    }
//
//
//    private boolean isAppIsInBackground(Context context) {
//        boolean isInBackground = true;
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
//            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
//            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
//                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//                    for (String activeProcess : processInfo.pkgList) {
//                        if (activeProcess.equals(context.getPackageName())) {
//                            isInBackground = false;
//                        }
//                    }
//                }
//            }
//        } else {
//            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
//            ComponentName componentInfo = taskInfo.get(0).topActivity;
//            if (componentInfo.getPackageName().equals(context.getPackageName())) {
//                isInBackground = false;
//            }
//        }
//
//        return isInBackground;
//    }
//}