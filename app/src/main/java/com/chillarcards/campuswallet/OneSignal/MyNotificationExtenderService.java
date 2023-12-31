//package com.chillarcards.campuswallet.OneSignal;
//
//import androidx.core.app.NotificationCompat;
//import android.util.Log;
//
//import com.onesignal.NotificationExtenderService;
//import com.onesignal.OSNotificationDisplayedResult;
//import com.onesignal.OSNotificationReceivedResult;
//
//import java.math.BigInteger;
//
//public class MyNotificationExtenderService extends NotificationExtenderService {
//
//
//    @Override
//    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {
//        OverrideSettings overrideSettings = new OverrideSettings();
//        overrideSettings.extender = new NotificationCompat.Extender() {
//            @Override
//            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
//                // Sets the background notification color to Green on Android 5.0+ devices.
//                return builder.setColor(new BigInteger("87E487", 16).intValue());
//            }
//        };
//
//        OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);
//        Log.d("OneSignalExample", "Notification displayed with id: " + displayedResult.androidNotificationId);
//
//
//        return true;
//    }
//}