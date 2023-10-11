# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Codmob\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizations !class/merging/*
#com.facebook.imagepipeline
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**

-keep class com.paynimo.android.payment.** { *; }
-dontwarn com.paynimo.android.payment.**

-keepclassmembernames class * {
    java.lang.Class class$(java.lang.String);
    java.lang.Class class$(java.lang.String, boolean);
}

-keep class retrofit.** {
    <fields>;
    <methods>;
}
-dontwarn retrofit.**

# Gson specific classes
-keep class sun.misc.Unsafe {
    <fields>;
    <methods>;
}

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.** {
    <fields>;
    <methods>;
}

# #---------------End: proguard configuration for Gson  ----------
-keep class de.greenrobot.event.** {
    <fields>;
    <methods>;
}

-keep class com.squareup.** {
    <fields>;
    <methods>;
}

-keep class android.support.v4.** {
    <fields>;
    <methods>;
}

-keep class android.support.v7.** {
    <fields>;
    <methods>;
}

-keep class android.service.media.MediaBrowserService {
    <fields>;
    <methods>;
}
-dontwarn android.service.media.MediaBrowserService

-keep class org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement {
     <fields>;
     <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

-keep interface android.service.media.IMediaBrowserServiceCallbacks {
    <fields>;
    <methods>;
}

-keep class io.card.payment.** {
    <fields>;
    <methods>;
}

-keep class okio.** {
    <fields>;
    <methods>;
}

-keep class java.nio.file.** {
    <fields>;
    <methods>;
}
-dontwarn java.nio.file.**
-dontwarn com.mixpanel.**
-dontwarn org.apache.http.**
-dontwarn com.android.volley.toolbox.**
# volley
-dontwarn com.android.volley.jar.**
-keep class com.android.volley.**{*;}

-keep class com.squareup.** { *; }
-keep interface com.squareup.** { *; }
-dontwarn com.squareup.**
-dontnote

 -keep class com.parse.*{ *; }
 -dontwarn com.parse.**
 -dontwarn com.google.common.**
 -dontwarn org.hamcrest.**

 -dontwarn org.hamcrest.**
 -dontwarn android.test.**
 -dontwarn android.support.test.**

 -keep class org.hamcrest.** {
    *;
 }

 -keep class org.junit.** { *; }
 -dontwarn org.junit.**

 -keep class junit.** { *; }
 -dontwarn junit.**

 -keep class sun.misc.** { *; }
 -dontwarn sun.misc.**

 -keep class com.wooplr.spotlight.** { *; }
 -keep interface com.wooplr.spotlight.**
 -keep enum com.wooplr.spotlight.**

 -keepclassmembers class * {
     @android.webkit.JavascriptInterface <methods>;
 }

 -keepattributes JavascriptInterface
 -keepattributes *Annotation*

 -dontwarn com.razorpay.**
 -keep class com.razorpay.** {*;}

 -optimizations !method/inlining/*

 -keepclasseswithmembers class * {
   public void onPayment*(...);
 }

 -assumenosideeffects class System {
     public static *** out.println(...);
 }

 -keep class com.github.mikephil.charting.** { *; }
-dontwarn io.realm.**
-dontwarn com.neumob.**

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn java.nio.file.**
-keep class java.nio.file.**
-keepattributes Signature
-keepattributes *Annotation*

-keep class codmob.com.campuswallet.ChatMessage**
-keepclassmembers class codmob.com.campuswallet.ChatMessage** {*;}


-keep public class org.apache.commons.io.**
-keep public class com.google.gson.**
-keep public class com.google.gson.** {public private protected *;}

##---------------Begin: proguard configuration for Gson ----------
-keepattributes *Annotation*,Signature

# To support Enum type of class members
-keepclassmembers enum * { *; }
##---------------End: proguard configuration for Gson ----------

-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.*

-dontnote okhttp3.**, okio.**, retrofit2.**, pl.droidsonroids.**
-dontwarn com.facebook.drawee.view.DraweeView

-dontwarn io.card.**
-dontwarn android.**

-keep class com.facebook.FacebookSdk {
   boolean isInitialized();
}
-keep class com.facebook.appevents.AppEventsLogger {
   com.facebook.appevents.AppEventsLogger newLogger(android.content.Context);
   void logSdkEvent(java.lang.String, java.lang.Double, android.os.Bundle);
}
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**


-keepclassmembers class android.support.design.internal.BottomNavigationMenuView {
    boolean mShiftingMode;
}