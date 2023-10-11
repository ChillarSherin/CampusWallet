package com.chillarcards.campuswallet.application;

import java.util.ArrayList;
import java.util.List;

import com.chillarcards.campuswallet.NewPreOrder.AllItems.Event;
import com.chillarcards.campuswallet.NewPreOrder.DummyOrderItems;
import com.chillarcards.campuswallet.payments.Preorder.Sales_Item;

public class Constants {
    /******************************** Live API     3.0  **********************************/
//    public static String BASE_URL = "https://campuswallet.chillarcards.com/parent_app/api_3.0/android/";
//    public static String BASE_IMAGE_URL = "http://campuswallet.chillarcards.com/uploads/";
//    public static String BASE_URL_NL = "http://campuswallet.chillarcards.com/uploads/newsletters/";
//    public static String BASE_URL_XPAY = "https://campuswallet.chillarcards.com/xpay/api_1.0/";

    /******************************** Live API     3.1  **********************************/
//    public static String BASE_URL = "http://campuswallet.chillarcards.com/parent_app/api_3.2/android/";
//    public static String BASE_URL_ORIGINAL = "http://campuswallet.chillarcards.com/parent_app/api_3.2/android/";
//    public static String BASE_ORDER_URL = "http://campuswalletdev.chillarcards.com/client_order_api/v1_0/android/";
//    public static String BASE_IMAGE_URL = "http://campuswalletdev.chillarcards.com/uploads/";
//    public static String BASE_URL_NL = "http://campuswalletdev.chillarcards.com/uploads/newsletters/";
//    public static String BASE_URL_XPAY = "http://campuswalletdev.chillarcards.com/xpay/api_1.0/";

    /******************************** Live API     3.3  **********************************/
//    public static String BASE_URL = "http://campuswallet.chillarcards.com/parent_app/api_3.3/android/";
//    public static String BASE_URL_ORIGINAL = "http://campuswallet.chillarcards.com/parent_app/api_3.3/android/";
//    public static String BASE_ORDER_URL = "http://campuswalletdev.chillarcards.com/client_order_api/v1_0/android/";
//    public static String BASE_IMAGE_URL = "http://campuswalletdev.chillarcards.com/uploads/";
//    public static String BASE_URL_NL = "http://campuswalletdev.chillarcards.com/uploads/newsletters/";
//    public static String BASE_URL_XPAY = "http://campuswalletdev.chillarcards.com/xpay/api_1.0/";


    /******************************** Live API     3.4  **********************************/
    public static String BASE_URL = "http://cwo.chillarpayments.com/parent_app/api_3.4/android/";
    public static String BASE_URL_ORIGINAL = "http://cwo.chillarpayments.com/parent_app/api_3.4/android/";
    public static String BASE_ORDER_URL = "http://cw.chillarpayments.com/client_order_api/v1_0/android/";
    public static String BASE_IMAGE_URL = "http://cw.chillarpayments.com/uploads/";
    public static String BASE_URL_NL = "http://cw.chillarpayments.com/uploads/newsletters/";
    public static String BASE_URL_XPAY = "http://cw.chillarpayments.com/xpay/api_1.0/";

    /******************************** AWS SERVER     3.3  **********************************/
//    public static String BASE_URL = "http://campuswallet.chillarcards.com/parent_app/api_3.3/android/";
//    public static String BASE_URL_ORIGINAL = "http://campuswallet.chillarcards.com/parent_app/api_3.3/android/";
//    public static String BASE_ORDER_URL = "http://campuswalletdev.chillarcards.com/client_order_api/v1_0/android/";
//    public static String BASE_IMAGE_URL = "http://campuswalletdev.chillarcards.com/uploads/";
//    public static String BASE_URL_NL = "http://campuswalletdev.chillarcards.com/uploads/newsletters/";
//    public static String BASE_URL_XPAY = "http://campuswalletdev.chillarcards.com/xpay/api_1.0/";


    /******************************** LIVE API     2.9  **********************************/
//    public static String BASE_URL = "https://campuswallet.chillarcards.com/parent_app/api_2.9/android/";
//    public static String BASE_IMAGE_URL = "http://campuswallet.chillarcards.com/uploads/";
//    public static String BASE_URL_NL = "http://campuswallet.chillarcards.com/uploads/newsletters/";
//    public static String BASE_URL_XPAY = "https://campuswallet.chillarcards.com/xpay/api_1.0/";

    /******************************** Dev API     2.9  **********************************/
//    public static String BASE_URL = "http://campuswalletdev.chillarcards.com/parent_app/api_2.9/android/";
//    public static String BASE_IMAGE_URL = "http://campuswalletdev.chillarcards.com/uploads/";
//    public static String BASE_URL_NL = "http://campuswalletdev.chillarcards.com/uploads/newsletters/";
//    public static String BASE_URL_XPAY = "http://campuswalletdev.chillarcards.com/xpay/api_1.0/";

    public static String SingleImageURL="";
    public static String Lattitude="";
    public static String Longitude="";
    public static String daySelected;
    public static ArrayList<Sales_Item> sales_items= new ArrayList<>();
    public static ArrayList<String> Items= new ArrayList<>();
    public static ArrayList<String> preorderItemTypeTimingItemIDnew = new ArrayList<>();
    public static ArrayList<String> Quantity= new ArrayList<>();
    public static ArrayList<String> Amount= new ArrayList<>();
    public static ArrayList<String> PriceList= new ArrayList<>();
    public static String TotalAmount;
    public static int Qty;
    public static String HomePopup;
    public static String HomePopupTitle;
    public static String HomeFlag;
    public static String onesignal_weburl;
    public static String onesignal_imageurl;
    public static String onesignal_chillarAddsID;

    public static List<DummyOrderItems> CartItems=new ArrayList<>();
    public static boolean from_Low_balance=false;
    public static int from_old_preorder=0;
}
