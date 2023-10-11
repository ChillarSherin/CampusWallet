//package com.chillarcards.campuswallet.OneSignal;
//
//import android.app.ActivityManager;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Build;
//
//import com.onesignal.OSNotification;
////import com.onesignal.OneSignal;
//
//import org.json.JSONObject;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import com.chillarcards.campuswallet.NotificationCenter.Contact;
//import com.chillarcards.campuswallet.NotificationCenter.DatabaseHandler;
//import com.chillarcards.campuswallet.application.CampusWallet;
//import com.chillarcards.campuswallet.application.PrefManager;
//
//import static android.content.Context.MODE_PRIVATE;
//
////This will be called when a notification is received while your app is running.
//public class MyNotificationReceivedHandler  implements OneSignal.NotificationReceivedHandler {
//
//
//    String Content,title;
//
//    String datakeys,StudID,payment;
//    String SchoolID,SchoolName, StandardDivId,StandardName,SchoolidKey, StdDivIdKey,TrnsTypName,TransTypId,StudIDKEY;
//    String PhoneNo, studentDiv,studentName, studentSchool,studentStandard,StudentCode,type;
//
//    DatabaseHandler db = new DatabaseHandler(CampusWallet.getContext());
//    PrefManager prefManager;
//
//
//    @Override
//    public void notificationReceived(OSNotification notification) {
//        JSONObject data = notification.payload.additionalData;
//        JSONObject contents = notification.payload.toJSONObject();
//        String customKey;
//        System.out.println("ONESIGNAL 1 "+data);
//        System.out.println("ONESIGNAL 1 "+contents);
//        prefManager = new PrefManager(CampusWallet.getContext());
//
//
//        SchoolID = prefManager.getSChoolID();
//        StandardDivId =  prefManager.getStudentStandardDivision();
//        StandardName =  prefManager.getStudentName();
//        StudID =  prefManager.getStudentId();
//        SchoolName =  prefManager.getSChoolName();
//
//
//
//        Date currentTime = Calendar.getInstance().getTime();
//        String clicked="0";
//        String NotificationID,Studentid,Schoolid,StandardNamePush;
//        int messageID;
//        NotificationID= (contents.optString("notificationID"));
//        Studentid= (data.optString("studentID"));
//        Schoolid= (data.optString("schoolID"));
//        StandardNamePush= (data.optString("standardName"));
//        messageID= (data.optInt("messageID"));
//        //System.out.println("ONESIGNAL 3 "+NotificationID);
//        //System.out.println("ONESIGNAL 3Studentid "+Studentid);
//        //System.out.println("ONESIGNAL 3Schoolid "+Schoolid);
//        try {
//
//            if (data != null) {
//                //While sending a Push notification from OneSignal dashboard
//                // you can send an addtional data named "customkey" and retrieve the value of it and do necessary operation
////                customKey = data.optString("customkey", null);
////                if (customKey != null)
////                    //System.out.println("ONESIGNAL 4 " + customKey);
////
////
////                {
//                    //System.out.println("ONESIGNAL 3Studentid customKey "+Studentid);
//                    //System.out.println("ONESIGNAL 3Schoolid NEW"+Schoolid);
//                    db.addContact(new Contact(NotificationID,currentTime.toString(), contents.toString(),clicked,Schoolid,Studentid,StandardNamePush,messageID));
////                    final List<Contact> contacts = db.getAllContacts(StudID,SchoolID,StandardName);
////
////
////
////                    for (Contact cn : contacts) {
////                        String log = "Id: " + cn.getID() + " ,DateTime: " + cn.getdatetime() + " ,Datas: " + cn.getDatas()+" SchoolId : "+cn.getSchoolID()+" StudentID :  "+cn.getStudentID();;
////                        // Writing Contacts to log
////                        System.out.println("ONESIGNAL DB Student Data:  " + log);
////                        System.out.println("ONESIGNAL DB Student Data Size " + contacts.size());
////                        System.out.println("ONESIGNAL DB Student Data Length " + contents.length());
////
////                    }
//
//                }
////            }
//
//        }catch (Exception e){
//
//        }
//    }
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
//
//}