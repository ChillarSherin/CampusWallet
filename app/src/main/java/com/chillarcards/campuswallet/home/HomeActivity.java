package com.chillarcards.campuswallet.home;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.LayoutHomeActivityBinding;
import com.chillarcards.campuswallet.utils.BottomNavigationViewHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.chillarcards.campuswallet.Diary.Attendance.AttendanceActivity;
import com.chillarcards.campuswallet.Diary.Calendar.CalendarNew;
import com.chillarcards.campuswallet.Diary.EDiary.E_Diary_Activity;
import com.chillarcards.campuswallet.Diary.Gallary.GalleryActivity;
import com.chillarcards.campuswallet.Diary.LeaveRequest.LeaveRequestActivity;
import com.chillarcards.campuswallet.Diary.NoticeBoard.NoticeBoardActivity;
import com.chillarcards.campuswallet.Diary.Result.ResultActivity;
import com.chillarcards.campuswallet.Diary.TimeTable.TimeTableActivity;
import com.chillarcards.campuswallet.NotificationCenter.DatabaseHandler;
import com.chillarcards.campuswallet.NotificationCenter.NotificationCenterActivityNW;
import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.home.drawer.DataModel;
import com.chillarcards.campuswallet.home.drawer.DrawerItemCustomAdapter;
import com.chillarcards.campuswallet.home.drawer.FoodOrderFragment;
import com.chillarcards.campuswallet.home.drawer.HelpHodel;
import com.chillarcards.campuswallet.networkmodels.home.ActiveNewsletter;
import com.chillarcards.campuswallet.networkmodels.home.Code;
import com.chillarcards.campuswallet.networkmodels.home.Home;
import com.chillarcards.campuswallet.networkmodels.home.Modules;
import com.chillarcards.campuswallet.networkmodels.home.Payment;
import com.chillarcards.campuswallet.networkmodels.home.Status;
import com.chillarcards.campuswallet.networkmodels.home.StudentData;
import com.chillarcards.campuswallet.payments.PaymentsActivity;
import com.chillarcards.campuswallet.payments.Preorder.PreOrderSelection;
import com.chillarcards.campuswallet.payments.Transactions.TransactionSelectionActivity;
import com.chillarcards.campuswallet.studentselection.StudentListActivity;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;
import com.chillarcards.campuswallet.utils.MyCustomPagerAdapter;
import io.github.douglasjunior.androidSimpleTooltip.OverlayView;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

//import com.survicate.surveys.Survicate;

public class HomeActivity extends CustomConnectionBuddyActivity implements CallPermissionCallback,WhatsAppCallback{

    MyCustomPagerAdapter myCustomPagerAdapter;
    private ArrayList<Integer> images =new ArrayList<>();
    final String tag_json_object = "r_home";
    Fragment fragment1 = null;
    Fragment fragment2 = null;
    Fragment fragment3 = null;
    Fragment fragment4 = null;
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = null;
    Dialog dialogWhstapp;

    private static final int MENU_ITEM_ID_PAYMENTS =1;
    private static final int MENU_ITEM_ID_DIARY =2;
    private static final int MENU_ITEM_ID_NOTIF =3;
    private static final int MENU_ITEM_ID_TRACKING =4;
    private static final int MENU_ITEM_ID_FOOD =5;
    private int mMenuSet = 0;

    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 100;
    private static String[] PERMISSIONS_PHONECALL = {Manifest.permission.CALL_PHONE};

    PrefManager prefManager;
    Activity activity;

    public static final String SCREEN_KEY = "purchaseSuccess";

    Dialog OtpDialog;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 5000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000; // time in milliseconds between successive task executions.
    private Dialog OtpDialoglogout;
    private String[] mNavigationDrawerItemTitles;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    androidx.appcompat.app.ActionBarDrawerToggle mDrawerToggle;
    
    boolean exit=false;
    String Content,title,weburl,imgurl,chillarAddsID;
    ArrayList<HelpHodel> helpHodels;
    DatabaseHandler databaseHandler;
    
    private LayoutHomeActivityBinding binding;
    Button ReloadBTN;
    TextView ErrorMessageTV,CodeErrorTV;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.layout_home_activity);
        binding = LayoutHomeActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);

        
        //        Survicate.init(this);
        activity=this;
        DatabaseHandler db = new DatabaseHandler(activity);
        db.getContactsCount();
        prefManager = new PrefManager(HomeActivity.this);
        String phoneNum = prefManager.getUserPhone();
        String studentId = prefManager.getStudentId();

        logUser();
        getVersionName();
        navigationDrawer(false);
        helpHodels=new ArrayList<>();
        binding.commonSeatchIV.setVisibility(View.GONE);
//        Survicate.enterScreen(SCREEN_KEY);

        binding.navDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.drawerLayout.isDrawerOpen(GravityCompat.END)){
                    binding.drawerLayout.closeDrawer(GravityCompat.END);
                }else{
                    binding.drawerLayout.openDrawer(GravityCompat.END);
                }


            }
        });
        Constants.BASE_URL=prefManager.getInnerApiBaseUrl();
        Constants.BASE_IMAGE_URL=prefManager.getBaseImageUrl();
        Constants.BASE_ORDER_URL=prefManager.getBaseOrderUrl();
        Constants.BASE_URL_NL=prefManager.getBaseUrlNl();
        Constants.BASE_URL_XPAY=prefManager.getBaseUrlXpay();
        if (prefManager.getCheckNewSwitch().equalsIgnoreCase(""))
        {
            prefManager.setCheckNewSwitch("Done");
            Intent i = new Intent(HomeActivity.this, StudentListActivity.class);
            startActivity(i);
            finish();
        }


        setProgressBarVisible();
        LoadHome(phoneNum,studentId);


        binding.bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        showTooltip(binding.common_seatchIV,R.string.search_tooltip);
        binding.commonSeatchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimpleSearchDialogCompat(HomeActivity.this, getString(R.string.search_module),
                        getString(R.string.whatareyou_looking), null, helpHodels,
                        new SearchResultListener<HelpHodel>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog,
                                                   HelpHodel item, int position) {

                                Searchnavigation(item);

                                dialog.dismiss();
                            }
                        }).show();
//                simpleSearchDialogCompat
            }
        });


    }

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
//        Crashlytics.setUserIdentifier(prefManager.getUserPhone());
//        Crashlytics.setUserEmail(prefManager.getUserEmail());
//        Crashlytics.setUserName(prefManager.getUserName());

    }

    public void Searchnavigation(HelpHodel item)
    {
        if (item.getTitle().equalsIgnoreCase(getResources().getString(R.string.card_recharge_caps)))
        {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Card_Recharge_Search_Menu_Selected),new Bundle());
            Intent i =new Intent(activity,PaymentsActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle b= new Bundle();
            b.putString("categoryId",item.getTransactionID());
            b.putString("categoryName",item.getTransactionCategory());
            b.putString("fromLowbalance","0");
            b.putString("rechargeAmount","0");
            i.putExtras(b);
            startActivity(i);
        }
        else if (item.getTitle().equalsIgnoreCase(getResources().getString(R.string.transactions_caps)))
        {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Transactions_Search_menu_Selected),new Bundle());
            Intent i6 =new Intent(activity,TransactionSelectionActivity.class);
            i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i6);
        }
        else if (item.getTitle().equalsIgnoreCase(getResources().getString(R.string.fee_payments_caps)))
        {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Fee_Payment_Search_Menu_Selected),new Bundle());
            Intent i2 =new Intent(activity,PaymentsActivity.class);
            i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle b2= new Bundle();
            b2.putString("categoryId",item.getTransactionID());
            b2.putString("categoryName",item.getTransactionCategory());
            b2.putString("fromLowbalance","0");
            b2.putString("rechargeAmount","0");
            i2.putExtras(b2);
            startActivity(i2);
        }
        else if (item.getTitle().equalsIgnoreCase(getResources().getString(R.string.miscellanious_caps)))
        {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Miscellaneous_Payment_Search_Menu_Selected),new Bundle());
            Intent i3 =new Intent(activity,PaymentsActivity.class);
            i3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle b3= new Bundle();
            b3.putString("categoryId",item.getTransactionID());
            b3.putString("categoryName",item.getTransactionCategory());
            b3.putString("fromLowbalance","0");
            b3.putString("rechargeAmount","0");
            i3.putExtras(b3);
            startActivity(i3);
        }
        else if (item.getTitle().equalsIgnoreCase(getResources().getString(R.string.trust_payments_caps)))
        {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Trust_Payment_Search_Menu_Selected),new Bundle());
            Intent i4 =new Intent(activity,PaymentsActivity.class);
            i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle b4= new Bundle();
            b4.putString("categoryId",item.getTransactionID());
            b4.putString("categoryName",item.getTransactionCategory());
            b4.putString("fromLowbalance","0");
            b4.putString("rechargeAmount","0");
            i4.putExtras(b4);
            startActivity(i4);
        }
        else if (item.getTitle().equalsIgnoreCase(getResources().getString(R.string.pre_order_caps)))
        {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Pre_Order_Search_Menu_Selected),new Bundle());
            Intent i5 =new Intent(activity,PreOrderSelection.class);
            i5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle b5= new Bundle();
            b5.putString("categoryId",item.getTransactionID());
            b5.putString("categoryName",item.getTransactionCategory());
            i5.putExtras(b5);
            startActivity(i5);
        }
        else if (item.getTitle().equalsIgnoreCase(getResources().getString(R.string.calender_header)))
        {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Calendar_Search_Menu_Selected),new Bundle());
            Intent calendar=new Intent(activity, CalendarNew.class);
            calendar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(calendar);
        }
        else if (item.getTitle().equalsIgnoreCase(getResources().getString(R.string.ediary_header)))
        {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.E_Dairy_Search_Menu_Selected),new Bundle());
            Intent edairy=new Intent(activity, E_Diary_Activity.class);
            startActivity(edairy);

        }
        else if (item.getTitle().equalsIgnoreCase(getResources().getString(R.string.timetable_header)))
        {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Time_Table_Search_Menu_Selected),new Bundle());
            Intent timetable=new Intent(activity, TimeTableActivity.class);
            startActivity(timetable);
        }
        else if (item.getTitle().equalsIgnoreCase(getResources().getString(R.string.leaverequest_header)))
        {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Leave_Request_Search_Menu_Selected),new Bundle());
            Intent leave=new Intent(activity, LeaveRequestActivity.class);
            startActivity(leave);
        }
        else if (item.getTitle().equalsIgnoreCase(getResources().getString(R.string.noticeboard_header)))
        {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Notice_Board_Search_Menu_Selected),new Bundle());
            Intent noticeboard=new Intent(activity, NoticeBoardActivity.class);
            startActivity(noticeboard);
        }
        else if (item.getTitle().equalsIgnoreCase(getResources().getString(R.string.results_header)))
        {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Result_Search_Menu_Selected),new Bundle());
            Intent result=new Intent(activity, ResultActivity.class);
            startActivity(result);
        }
        else if (item.getTitle().equalsIgnoreCase(getResources().getString(R.string.attendance_header)))
        {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Attendance_Search_Menu_Selected),new Bundle());
            Intent attendance=new Intent(activity, AttendanceActivity.class);
            startActivity(attendance);
        }
        else if (item.getTitle().equalsIgnoreCase(getResources().getString(R.string.gallery_header)))
        {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Gallery_Search_Menu_Selected),new Bundle());
            Intent Activity=new Intent(activity, GalleryActivity.class);
            startActivity(Activity);
        }

        else if (item.getTitle().equalsIgnoreCase(getResources().getString(R.string.callus)))
        {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Callus_Search_Menu_Selected),new Bundle());
            callPermission();
        }
        else if (item.getTitle().equalsIgnoreCase(getResources().getString(R.string.chat_us_nav)))
        {
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Chatus_Search_Menu_Selected),new Bundle());
            whatsAppChat();
        }
    }

    @Override
    public void onBackPressed() {
        try {

            if (exit) {


                //  finish(); // finish activity
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            } else {

                Snackbar snackbar1 = Snackbar.make(binding.drawerLayout, getResources().getString(R.string.exit_app), Snackbar.LENGTH_LONG);
                snackbar1.show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }


        } catch (Exception e) {

            if (exit) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            } else {

                Snackbar snackbar1 = Snackbar.make(binding.drawerLayout, getResources().getString(R.string.exit_app), Snackbar.LENGTH_LONG);
                snackbar1.show();
//
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Bundle b=getIntent().getExtras();
        if (b!=null)
        {
        if (Constants.HomeFlag.equals("true")){

            Content=b.getString("CONTENT");

            title=b.getString("TITLE");

            Onesignalpopup();
//            //System.out.println("Content : "+Content);

        }

        else if (Constants.HomeFlag.equals("true1"))
        {

            Content=b.getString("CONTENT");

            title=b.getString("TITLE");
            weburl=b.getString("IMGURL");
            imgurl=b.getString("WEBURL");
            chillarAddsID=b.getString("chillarAddsID");

            //AddsOnesignalpopup();
//            //System.out.println("Content : "+Content);

            OneSignalPopupDialog dialog = new OneSignalPopupDialog();
            Bundle args = new Bundle();
            args.putString("Title", title);
            args.putString("Content", Content);
            args.putString("ImageUrl", imgurl);
            args.putString("weburl", weburl);
            args.putString("chillarAddsID", chillarAddsID);
            dialog.setArguments(args);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            dialog.show(ft, OneSignalPopupDialog.TAG);

        }
        else
        {
//            //System.out.println("Else : ");
        }
        }
//        LoadHomeBalanceUpdate();
        binding.CurrentBalanceTV.setText(getResources().getString(R.string.wallet_balance_home)+prefManager.getWalletBalance());
    }



    private void Onesignalpopup() {

        OtpDialog = new Dialog(this);
        OtpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        OtpDialog.setContentView(R.layout.layout_onesignal_content_home);
        OtpDialog.setCanceledOnTouchOutside(false);
        OtpDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        //System.out.println("Eldhossss "+title);
        Button Ok = (Button) OtpDialog.findViewById(R.id.buttonOK);
        TextView ContentText = (TextView) OtpDialog.findViewById(R.id.Tv);
        TextView title1 = (TextView) OtpDialog.findViewById(R.id.Title);

        try {
            OtpDialog.show();
        }catch (Exception e){

        }
        ContentText.setText(Content);
        title1.setText(title);
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.HomeFlag="false";
                OtpDialog.dismiss();

            }
        });

    }

    public void showTooltip(View v,int messageString)
    {
        new SimpleTooltip.Builder(this)
                .anchorView(v)
                .text(messageString)
                .gravity(Gravity.START)
                .animated(true)
                .transparentOverlay(false)
                .highlightShape(OverlayView.HIGHLIGHT_SHAPE_OVAL)
                .overlayOffset(0)
                .build()
                .show();
    }

    private void getVersionName(){

        String versionName = "";
        try {
            versionName = getApplicationContext().getPackageManager()
                    .getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
            mFirebaseAnalytics.setUserProperty(getResources().getString(R.string.UP_AppVersion),versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        prefManager.setVersionCode(versionName);

    }
    public int compareVersionNames(String oldVersionName, String newVersionName) {
        int res = 0;

        //System.out.println("Chillrrrr:Old:  "+ oldVersionName+ " New: "+newVersionName);

        String[] oldNumbers = oldVersionName.split("\\.");
        String[] newNumbers = newVersionName.split("\\.");

        // To avoid IndexOutOfBounds
        int maxIndex = Math.min(oldNumbers.length, newNumbers.length);

        for (int i = 0; i < maxIndex; i ++) {
            int oldVersionPart = Integer.valueOf(oldNumbers[i]);
            int newVersionPart = Integer.valueOf(newNumbers[i]);

            if (oldVersionPart < newVersionPart) {
                res = -1;
                break;
            } else if (oldVersionPart > newVersionPart) {
                res = 1;
                break;
            }
        }

        // If versions are the same so far, but they have different length...
        if (res == 0 && oldNumbers.length != newNumbers.length) {
            res = (oldNumbers.length > newNumbers.length)?1:-1;
        }

        return res;
    }

    public void showDialogPopup(){

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
        alertDialogBuilder.setMessage(getResources().getString(R.string.major_update));
        alertDialogBuilder.setCancelable(false);
        final String appName = "com.chillarcards.campuswallet";


        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.update_now),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try{
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appName)));
                        }catch(Exception e){
                            e.printStackTrace();

                        }

                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                });


        try {
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private DataModel[] getDatamodel(boolean history)
    {
        DataModel[] RtndrawerItem=null;
//        if (history) {
//            DataModel[] drawerItem = new DataModel[6];
//            drawerItem[0] = new DataModel(R.mipmap.ic_switch, getResources().getString(R.string.select_student));
//            drawerItem[1] = new DataModel(R.mipmap.ic_history, getResources().getString(R.string.history_nav));
//            drawerItem[2] = new DataModel(R.mipmap.ic_call_us, getResources().getString(R.string.call_us_nav));
//            drawerItem[3] = new DataModel(R.mipmap.ic_chat, getResources().getString(R.string.chat_us_nav));
//            drawerItem[4] = new DataModel(R.mipmap.ic_logout, getResources().getString(R.string.log_out));
//            drawerItem[5] = new DataModel(R.mipmap.ic_logout, getResources().getString(R.string.version_nav));
//            RtndrawerItem=drawerItem;
//
//        }
//        else
//        {
            DataModel[] drawerItem = new DataModel[5];
            drawerItem[0] = new DataModel(R.mipmap.ic_switch, getResources().getString(R.string.select_student));
            drawerItem[1] = new DataModel(R.mipmap.ic_call_us, getResources().getString(R.string.call_us_nav));
            drawerItem[2] = new DataModel(R.mipmap.ic_chat, getResources().getString(R.string.chat_us_nav));
            drawerItem[3] = new DataModel(R.mipmap.ic_logout, getResources().getString(R.string.log_out));
            drawerItem[4] = new DataModel(R.mipmap.ic_logout, getResources().getString(R.string.version_nav));
            RtndrawerItem=drawerItem;
//        }
        return  RtndrawerItem;
    }


    private void navigationDrawer(boolean history) {
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.item_custom_drawer_layout,
                getDatamodel(history),binding.drawerLayout,this,prefManager.getVersionCode(),history,mFirebaseAnalytics,this);
        binding.rightDrawer.setAdapter(adapter);
        binding.rightDrawer.setOnItemClickListener(new DrawerItemClickListener());
       // binding.rightDrawer.notify();
    }


    private void LoadHome(String phoneNum, String s) {
        try {


        String URL, parameters;
        parameters = "phoneNo=" + phoneNum+"&studentID="+s+"&current_version=2.7";
        URL = Constants.BASE_URL + "r_student_home_page.php?" + parameters.replaceAll(" ", "%20");
        System.out.println("CHECK---> URL " + URL);
            final CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
            mFirebaseAnalytics.setUserProperty(getResources().getString(R.string.UP_ApiVersion),"3.0.3");



        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {

                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
                setProgressBarGone();

                Gson gson = new Gson();

                Home home = gson.fromJson(jsonObject,Home.class);
                Status status=home.getStatus();
                String code= status.getCode();
                if(code.equals("200")){

                    Code code1=home.getCode();

//                    //onesignal tags for 3.0 or higher APIs
//                    List <List<OnesignalKey_>> onesignalKey_=code1.getOnesignalKeys();
//                    for (int i=0;i<onesignalKey_.size();i++)
//                    {
//                        List<OnesignalKey_> onesignalKey_s=onesignalKey_.get(i);
//                        for (int j=0;j<onesignalKey_s.size();j++)
//                        {
//
//                            OneSignal.sendTag(onesignalKey_s.get(j).getKey(),String.valueOf(onesignalKey_s.get(j).getValue()));
//                            //System.out.println("Setting OneSignla Tag:-> HomeScreen:-> Key : "+onesignalKey_s.get(j).getKey()+" Value : "+String.valueOf(onesignalKey_s.get(j).getValue()));
//                        }
//                    }


                    /*
                    * Fetch Modules
                    * */
                    Modules modules=code1.getModules();
                    List<String> moduleDiary= modules.getDiary();
                    ArrayList<String> moduleDiaryNew = new ArrayList<String>();
                    moduleDiaryNew.addAll(moduleDiary);
                    List<Payment> modulePayments= modules.getPayment();
                    ArrayList<Payment> modulePaymentsNew = new ArrayList<Payment>();
                    modulePaymentsNew.addAll(modulePayments);
                    List<Object> moduleNotifications= modules.getNotifications();
                    int moduleTracking= modules.getTracking();
                    int mobileorder= modules.getOrder();
                    if (modulePayments.size()!=0)
                    {
                        navigationDrawer(true);
                    }

                    //Search modules
                    helpHodels.clear();
                    helpHodels.add(new HelpHodel(getResources().getString(R.string.callus),"",""));
                    helpHodels.add(new HelpHodel(getResources().getString(R.string.chat_us_nav),"",""));
                    for (Payment payment: modulePaymentsNew)

                    {
                        helpHodels.add(new HelpHodel(GetpaymentHeader(payment.getTransactionCategory())
                                ,payment.getTransactionCategoryID(),payment.getTransactionCategory()));
                    }
                    for (String diary: moduleDiaryNew)
                    {
                        helpHodels.add(new HelpHodel(GetpaymentHeader(diary),"",""));
                    }
                    binding.commonSeatchIV.setVisibility(View.VISIBLE);

                    /*
                    * Fetch Student Data
                    * */
                    StudentData studentData=code1.getStudentData();
                    String studentName = studentData.getStudentName();
                    String schoolName = studentData.getSchoolName();
                    String studentClass = studentData.getStandard() + ":"+ studentData.getStandardDivision();

                    String AppVersion=code1.getApp_version();

                    if(compareVersionNames(prefManager.getVersionCode(),AppVersion)<0){


//                        //System.out.println("Chillrrrr: New version available.");
                        showDialogPopup();


                    }


                    /*
                    * Fetch Banner Images
                    * */
                    final List<ActiveNewsletter> activeNewsletter= code1.getActiveNewsletters();
                    // checcking payment (Currentbalance exists or not)
                    String currentbalance_Flag=code1.getCurrentBalanceExist();
                    String CurrentBalance="",AwaitingBalance="";

                    if (currentbalance_Flag.equalsIgnoreCase("1"))
                    {
                        CurrentBalance=code1.getCurrentBalance();
                        AwaitingBalance=code1.getBalanceAwaitingToDownload();
                        prefManager.setWalletBalance(CurrentBalance);
                        prefManager.setAwatingBalance(AwaitingBalance);
                        mFirebaseAnalytics.setUserProperty(getResources().getString(R.string.UP_WalletBalance),CurrentBalance);
                        mFirebaseAnalytics.setUserProperty(getResources().getString(R.string.UP_PendingDownload),AwaitingBalance);
                    }
                    String SchoolType=code1.getSchoolMachineType();
                    mFirebaseAnalytics.setUserProperty(getResources().getString(R.string.UP_MachineType),SchoolType);
                    boolean MachineTypeBoth;
                    if (SchoolType.equalsIgnoreCase("Offline POS"))
                    {
                        MachineTypeBoth=true;
                    }
                    else
                    {
                        MachineTypeBoth=false;
                    }

                    if (currentbalance_Flag.equalsIgnoreCase("1"))
                    {
                        binding.CurrentBalanceTV.setVisibility(View.VISIBLE);
                        binding.AwaitingBalanceTV.setVisibility(View.VISIBLE);
                        binding.CurrentBalanceTV.setText(getResources().getString(R.string.wallet_balance_home)+prefManager.getWalletBalance());
                        binding.AwaitingBalanceTV.setText(getResources().getString(R.string.pending_download_home)+prefManager.getAwatingBalance());
                        //            binding.CurrentBalanceTV1.setVisibility(View.VISIBLE);
                        //            binding.AwaitingBalanceTV1.setVisibility(View.VISIBLE);
                        //            binding.CurrentBalanceTV1.setText(context.getResources().getString(R.string.wallet_balance_home)+CurrentBalance);
                        //            binding.AwaitingBalanceTV1.setText(context.getResources().getString(R.string.pending_download_home)+AwaitingBalance);
                        if (MachineTypeBoth)
                        {
                            binding.CurrentBalanceTV.setVisibility(View.VISIBLE);
                            binding.AwaitingBalanceTV.setVisibility(View.VISIBLE);
                        //                binding.CurrentBalanceTV1.setVisibility(View.VISIBLE);
                        //                binding.AwaitingBalanceTV1.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            binding.CurrentBalanceTV.setVisibility(View.VISIBLE);
                            binding.AwaitingBalanceTV.setVisibility(View.GONE);
                            //                binding.CurrentBalanceTV1.setVisibility(View.VISIBLE);
                            //                binding.AwaitingBalanceTV1.setVisibility(View.GONE);
                            //                            ConstraintLayout.LayoutParams layoutParams=(ConstraintLayout.LayoutParams)binding.CurrentBalanceTV.getLayoutParams();
                            //                            layoutParams.rightMargin=16;
                            //                            layoutParams.leftMargin=16;
                            //                            layoutParams.topMargin=8;
                            //                            layoutParams.bottomMargin=16;
                            //                            binding.CurrentBalanceTV.setLayoutParams(layoutParams);
                            //                ConstraintLayout.LayoutParams layoutParams1=(ConstraintLayout.LayoutParams)binding.CurrentBalanceTV1.getLayoutParams();
                            //                layoutParams1.rightMargin=9;
                            //                layoutParams1.leftMargin=21;
                            //                layoutParams1.topMargin=8;
                            //                layoutParams1.bottomMargin=16;
                            //                binding.CurrentBalanceTV1.setLayoutParams(layoutParams1);

                        }
                    }
                    else {
                        binding.CurrentBalanceTV.setVisibility(View.GONE);
                        binding.AwaitingBalanceTV.setVisibility(View.GONE);
//            binding.CurrentBalanceTV1.setVisibility(View.GONE);
//            binding.AwaitingBalanceTV1.setVisibility(View.GONE);
                    }


                    myCustomPagerAdapter = new MyCustomPagerAdapter(HomeActivity.this,
                            activeNewsletter,currentbalance_Flag,CurrentBalance,AwaitingBalance,MachineTypeBoth);
                    binding.viewPager.setAdapter(myCustomPagerAdapter);
                    binding.viewPager.setScrollDurationFactor(10);
                    //ViewPagerCustomDuration viewPagerCustomDuration.setScrollDurationFactor
//                    binding.viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
//                        @Override
//                        public void transformPage(@NonNull View page, float position) {
//                            page.setRotationY(position * -30);
//                        }
//                    });

                    /*After setting the adapter use the timer */
                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable() {
                        public void run() {
                            if (currentPage == activeNewsletter.size()) {
                                currentPage = 0;
                            }
                            binding.viewPager.setCurrentItem(currentPage++, true);
                        }
                    };

                    timer = new Timer(); // This will create a new Thread
                    timer .schedule(new TimerTask() { // task to be scheduled

                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    }, 1, PERIOD_MS);

                    int size1=moduleDiary.size();
                    int size2=modulePayments.size();

                    if(size1 == 0 && size2 ==0){
                        mMenuSet=1;
                    }else if(size1 == 0 && size2 >0){
                        mMenuSet=2;
                    }else if(size1 > 0 && size2 ==0){
                        mMenuSet=3;
                    } if(size1 >0 && size2 >0){
                        mMenuSet=4;
                    }

//                    //System.out.println("CampusWallet mMenuSet: "+mMenuSet);
                    BottomNavigationViewHelper.removeShiftMode(binding.bottomNavigationView);
                    Menu menu = binding.bottomNavigationView.getMenu();
                    Bundle Firebase_bundle = new Bundle();
                    Firebase_bundle.putString("","");
                    if(mMenuSet==1){
                        if(mobileorder ==1){
                            menu.add(Menu.NONE, MENU_ITEM_ID_FOOD, Menu.NONE,getString(R.string.str_menu_five)).setIcon(R.drawable.ic_lunch).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);

                            fragment3 = new FoodOrderFragment();
                            Bundle bundle2 = new Bundle();
                            bundle2.putStringArrayList("moduleDiaryNew", moduleDiaryNew);
                            bundle2.putString("studentName",studentName);
                            bundle2.putString("schoolName",schoolName);
                            bundle2.putString("studentClass",studentClass);
                            fragment3.setArguments(bundle2);

                            fm.beginTransaction().add(R.id.main_container,fragment1, "1").commitAllowingStateLoss();
                        }
                        menu.add(Menu.NONE, MENU_ITEM_ID_NOTIF, Menu.NONE,getString(R.string.str_menu_three)).setIcon(R.drawable.ic_notification_grey).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                       if(moduleTracking ==1){
                           menu.add(Menu.NONE, MENU_ITEM_ID_TRACKING, Menu.NONE,getString(R.string.str_menu_four)).setIcon(R.drawable.ic_tracking_grey).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                       }

//                        fm.beginTransaction().add(R.id.main_container,fragment1, "1").commitAllowingStateLoss();
//                        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
                        mFirebaseAnalytics.logEvent(getResources().getString(R.string.Notification_Center_Module_Tab_Selected),Firebase_bundle);
                    }else if(mMenuSet==2){
                        menu.add(Menu.NONE, MENU_ITEM_ID_PAYMENTS, Menu.NONE,getString(R.string.str_menu_one)).setIcon(R.drawable.ic_recharge_grey).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                        if(mobileorder ==1){
                            menu.add(Menu.NONE, MENU_ITEM_ID_FOOD, Menu.NONE,getString(R.string.str_menu_five)).setIcon(R.drawable.ic_lunch).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                        }
                        menu.add(Menu.NONE, MENU_ITEM_ID_NOTIF, Menu.NONE,getString(R.string.str_menu_three)).setIcon(R.drawable.ic_notification_grey).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                        if(moduleTracking ==1){
                            menu.add(Menu.NONE, MENU_ITEM_ID_TRACKING, Menu.NONE,getString(R.string.str_menu_four)).setIcon(R.drawable.ic_tracking_grey).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                        }

//                        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

                        Gson gson1 = new Gson();
                        String paymentModules = gson1.toJson(modulePaymentsNew);

                        fragment1 = new PaymentsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("modulePaymentsNew", paymentModules);
                        bundle.putString("studentName",studentName);
                        bundle.putString("schoolName",schoolName);
                        bundle.putString("studentClass",studentClass);
                        fragment1.setArguments(bundle);

                        fragment3 = new FoodOrderFragment();
                        Bundle bundle2 = new Bundle();
                        bundle2.putStringArrayList("moduleDiaryNew", moduleDiaryNew);
                        bundle2.putString("studentName",studentName);
                        bundle2.putString("schoolName",schoolName);
                        bundle2.putString("studentClass",studentClass);
                        fragment3.setArguments(bundle2);

                        mFirebaseAnalytics.logEvent(getResources().getString(R.string.Payment_Module_Tab_Selected),Firebase_bundle);
                        active = fragment1;
                        if(fragment3!=null) {
                            fm.beginTransaction().add(R.id.main_container, fragment3, "2").hide(fragment3).commitAllowingStateLoss();
                        }
                        fm.beginTransaction().add(R.id.main_container,fragment1, "1").commitAllowingStateLoss();



                    }else if(mMenuSet==3){
                        menu.add(Menu.NONE, MENU_ITEM_ID_DIARY, Menu.NONE,getString(R.string.str_menu_two)).setIcon(R.drawable.ic_diary_grey).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                        if(mobileorder ==1){
                            menu.add(Menu.NONE, MENU_ITEM_ID_FOOD, Menu.NONE,getString(R.string.str_menu_five)).setIcon(R.drawable.ic_lunch).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                        }
                        menu.add(Menu.NONE, MENU_ITEM_ID_NOTIF, Menu.NONE,getString(R.string.str_menu_three)).setIcon(R.drawable.ic_notification_grey).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                        if(moduleTracking ==1){
                            menu.add(Menu.NONE, MENU_ITEM_ID_TRACKING, Menu.NONE,getString(R.string.str_menu_four)).setIcon(R.drawable.ic_tracking_grey).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                        }

//                        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

                        fragment2 = new DiaryFragment();
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("moduleDiaryNew", moduleDiaryNew);
                        bundle.putString("studentName",studentName);
                        bundle.putString("schoolName",schoolName);
                        bundle.putString("studentClass",studentClass);
                        fragment2.setArguments(bundle);

                        fragment3 = new FoodOrderFragment();
                        Bundle bundle2 = new Bundle();
                        bundle2.putStringArrayList("moduleDiaryNew", moduleDiaryNew);
                        bundle2.putString("studentName",studentName);
                        bundle2.putString("schoolName",schoolName);
                        bundle2.putString("studentClass",studentClass);
                        fragment3.setArguments(bundle2);

                        mFirebaseAnalytics.logEvent(getResources().getString(R.string.Diary_module_Tab_Selected),Firebase_bundle);
                        active = fragment2;
                        if(fragment3!=null) {
                            fm.beginTransaction().add(R.id.main_container, fragment3, "2").hide(fragment3).commitAllowingStateLoss();
                        }
                        fm.beginTransaction().add(R.id.main_container,fragment2, "1").commitAllowingStateLoss();

                    }else if(mMenuSet==4){
                        menu.add(Menu.NONE, MENU_ITEM_ID_PAYMENTS, Menu.NONE,getString(R.string.str_menu_one)).setIcon(R.drawable.ic_recharge_grey).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                        menu.add(Menu.NONE, MENU_ITEM_ID_DIARY, Menu.NONE,getString(R.string.str_menu_two)).setIcon(R.drawable.ic_diary_grey).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                                                if(mobileorder ==1){
                        menu.add(Menu.NONE, MENU_ITEM_ID_FOOD, Menu.NONE,getString(R.string.str_menu_five)).setIcon(R.drawable.ic_lunch).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                        }
                        menu.add(Menu.NONE, MENU_ITEM_ID_NOTIF, Menu.NONE,getString(R.string.str_menu_three)).setIcon(R.drawable.ic_notification_grey).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);

                        if(moduleTracking ==1){
                            menu.add(Menu.NONE, MENU_ITEM_ID_TRACKING, Menu.NONE,getString(R.string.str_menu_four)).setIcon(R.drawable.ic_tracking_grey).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS|MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                        }

//                        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

                        Gson gson1 = new Gson();
                        String paymentModules = gson1.toJson(modulePaymentsNew);


                        fragment1 = new PaymentsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("modulePaymentsNew", paymentModules);
                        bundle.putString("studentName",studentName);
                        bundle.putString("schoolName",schoolName);
                        bundle.putString("studentClass",studentClass);
                        fragment1.setArguments(bundle);

//                        //System.out.println("modulePaymentsNew home : "+modulePaymentsNew.size());

                        fragment2 = new DiaryFragment();
                        Bundle bundle1 = new Bundle();
                        bundle1.putStringArrayList("moduleDiaryNew", moduleDiaryNew);
                        bundle1.putString("studentName",studentName);
                        bundle1.putString("schoolName",schoolName);
                        bundle1.putString("studentClass",studentClass);
                        fragment2.setArguments(bundle1);

////
                        fragment3 = new FoodOrderFragment();
                        Bundle bundle2 = new Bundle();
                        bundle2.putStringArrayList("moduleDiaryNew", moduleDiaryNew);
                        bundle2.putString("studentName",studentName);
                        bundle2.putString("schoolName",schoolName);
                        bundle2.putString("studentClass",studentClass);
                        fragment3.setArguments(bundle2);

                        mFirebaseAnalytics.logEvent(getResources().getString(R.string.Payment_Module_Tab_Selected),Firebase_bundle);
                        active = fragment1;
                        if(fragment2!=null) {
                            fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commitAllowingStateLoss();
                        }
                        if(fragment3!=null) {
                            fm.beginTransaction().add(R.id.main_container, fragment3, "2").hide(fragment3).commitAllowingStateLoss();
                        }
                        fm.beginTransaction().add(R.id.main_container,fragment1, "1").commitAllowingStateLoss();


                    }

                }
                else if(code.equals("505")){

                     Intent i=new Intent(HomeActivity.this, StudentListActivity.class);
                     i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(i);
                     finish();

                }
                else
                    {

                    setProgressBarGone();
                    ReloadBTN.setText(getResources().getString(R.string.reload));
                    findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
                    ErrorMessageTV.setText(getResources().getString(R.string.error_message_errorlayout));
                    CodeErrorTV.setText(code);

                    ReloadBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            startActivity(getIntent());
                        }
                    });
                    String message = status.getMessage();
                    Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ReloadBTN.setText(getResources().getString(R.string.reload));
                findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
                ErrorMessageTV.setText(getResources().getString(R.string.error_message_admin));
                CodeErrorTV.setText(getResources().getString(R.string.error_message_errorlayout));

                ReloadBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        startActivity(getIntent());
                    }
                });
                volleyError.printStackTrace();
                setProgressBarGone();

            }

        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(HomeActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//            requestQueue.add(jsonObjectRequestLogin);
        }catch (Exception e)
        {
            ReloadBTN.setText(getResources().getString(R.string.reload));
            findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
            ErrorMessageTV.setText(getResources().getString(R.string.error_message_admin));
            CodeErrorTV.setText(getResources().getString(R.string.error_message_errorlayout));

            ReloadBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(getIntent());
                }
            });
            setProgressBarGone();
        }
    }

    private void LoadHomeBalanceUpdate(String phoneNum, String s) {
        try {


            String URL, parameters;
            parameters = "phoneNo=" + phoneNum+"&studentID="+s+"&current_version=2.7";
            URL = Constants.BASE_URL + "r_student_home_page.php?" + parameters.replaceAll(" ", "%20");
//            //System.out.println("CHECK---> URL " + URL);
            final CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
            mFirebaseAnalytics.setUserProperty(getResources().getString(R.string.UP_ApiVersion),"3.0.3");



            StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String jsonObject) {

                    CampusWallet.getInstance().cancelPendingRequests(tag_json_object);


                    Gson gson = new Gson();

                    Home home = gson.fromJson(jsonObject,Home.class);
                    Status status=home.getStatus();
                    String code= status.getCode();
                    if(code.equals("200")){

                        Code code1=home.getCode();
                        // checcking payment (Currentbalance exists or not)
                        String currentbalance_Flag=code1.getCurrentBalanceExist();
                        String CurrentBalance="",AwaitingBalance="";
                        if (currentbalance_Flag.equalsIgnoreCase("1"))
                        {
                            CurrentBalance=code1.getCurrentBalance();
                            AwaitingBalance=code1.getBalanceAwaitingToDownload();
                            prefManager.setWalletBalance(CurrentBalance);
                            prefManager.setAwatingBalance(AwaitingBalance);
                            mFirebaseAnalytics.setUserProperty(getResources().getString(R.string.UP_WalletBalance),CurrentBalance);
                            mFirebaseAnalytics.setUserProperty(getResources().getString(R.string.UP_PendingDownload),AwaitingBalance);
                        }
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {


                }

            });
            jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(HomeActivity.this));
            CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//            requestQueue.add(jsonObjectRequestLogin);
        }catch (Exception e)
        {

        }
    }

public String GetpaymentHeader(String categoryName)
{
    String catName="";
    switch(categoryName) {
        case "card_recharge":
            catName= getResources().getString(R.string.card_recharge_caps);

            break;
        case "card-transfer":
            catName=getResources().getString(R.string.transactions_caps);
            break;

        case "fee_payment":
            catName=getResources().getString(R.string.fee_payments_caps);
            break;
        case "miscellaneous_payments":
            catName=getResources().getString(R.string.miscellanious_caps);
            break;
        case "trust_payments":
            catName=getResources().getString(R.string.trust_payments_caps);
            break;
        case "preorder":
            catName=getResources().getString(R.string.pre_order_caps);
            break;
        case "calendar":
            catName=getResources().getString(R.string.calender_header);
            break;
        case "e-diary":
            catName=getResources().getString(R.string.ediary_header);
            break;
        case "timetable":
            catName=getResources().getString(R.string.timetable_header);
            break;
        case "leave-request":
            catName=getResources().getString(R.string.leaverequest_header);
            break;
        case "notice-board":
            catName=getResources().getString(R.string.noticeboard_header);
            break;
        case "result":
            catName=getResources().getString(R.string.results_header);
            break;
        case "attendance":
            catName=getResources().getString(R.string.attendance_header);
            break;
        case "Activity":
            catName=getResources().getString(R.string.gallery_header);
            break;


    }
    return catName;
}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
//        Survicate.leaveScreen(SCREEN_KEY);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle Firebase_bundle = new Bundle();
            Firebase_bundle.putString("","");
            switch (item.getItemId()) {
                case MENU_ITEM_ID_PAYMENTS:
                    if(active!=null) {
                        fm.beginTransaction().hide(active).show(fragment1).commitAllowingStateLoss();
                    }
                    else {
                        fm.beginTransaction().show(fragment1).commitAllowingStateLoss();
                    }

                    mFirebaseAnalytics.logEvent(getResources().getString(R.string.Payment_Module_Tab_Selected),Firebase_bundle);
                    active = fragment1;
                    return true;
                case MENU_ITEM_ID_DIARY:
                    if(active!=null) {
                        fm.beginTransaction().hide(active).show(fragment2).commitAllowingStateLoss();
                    }
                    else
                    {
                        fm.beginTransaction().show(fragment2).commitAllowingStateLoss();
                    }

                    mFirebaseAnalytics.logEvent(getResources().getString(R.string.Diary_module_Tab_Selected),Firebase_bundle);
                    active = fragment2;
                    return true;
                case MENU_ITEM_ID_FOOD:
                    if(active!=null) {
                        fm.beginTransaction().hide(active).show(fragment3).commitAllowingStateLoss();
                    }
                    else
                    {
                        fm.beginTransaction().show(fragment3).commitAllowingStateLoss();
                    }

                    mFirebaseAnalytics.logEvent(getResources().getString(R.string.Order_module_Tab_Selected),Firebase_bundle);
                    active = fragment3;
                    return true;
                case MENU_ITEM_ID_NOTIF:
//                    Toast.makeText(HomeActivity.this, "Click on " + getString(R.string.str_menu_three), Toast.LENGTH_SHORT).show();
                    mFirebaseAnalytics.logEvent(getResources().getString(R.string.Notification_Center_Module_Tab_Selected),Firebase_bundle);
                    Intent i=new Intent(HomeActivity.this, NotificationCenterActivityNW.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(i);
                    return true;
                case MENU_ITEM_ID_TRACKING:
//                    hidden due to complications in playstore policies - not using feature as of 21st JUN 2022
//                    mFirebaseAnalytics.logEvent(getResources().getString(R.string.Bus_tracking_Module_Tab_Selected),Firebase_bundle);
//                    Intent i1=new Intent(HomeActivity.this, ParentMapsActivity.class);
//                    i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                    startActivity(i1);
                    return true;

                default:
                    break;
            }
            return false;
        }
    };


    public void setProgressBarVisible(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.progressBar.setVisibility(View.VISIBLE);

    }

    public void setProgressBarGone(){

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.progressBar.setVisibility(View.GONE);

    }

    @Override
    public void callSupport() {
        callPermission();
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                           int[] grantResults) {
//        if (requestCode == PERMISSIONS_REQUEST_PHONE_CALL) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                callPermission();
//            } else {
//                callPermission();
//            }
//        }
//    }
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
//        } else {
            //Open call function
            String number = getResources().getString(R.string.customercare_number);
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//        }
    }

    @Override
    public void chatSupport() {
        whatsAppChat();
    }
    public void whatsAppChat()
    {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = null;
            try {
                url = "https://api.whatsapp.com/send?phone=" + getResources().getString(R.string.customercare_number) + "&text=" + URLEncoder.encode("", "UTF-8");
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            this.startActivity(i);

        } catch (ActivityNotFoundException e) {
            showWhatsapp();
            e.printStackTrace();
        }
    }

    private void showWhatsapp() {

        dialogWhstapp = new Dialog(HomeActivity.this, android.R.style.Theme_Dialog);
        dialogWhstapp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogWhstapp.setContentView(R.layout.whatsapp_layout);
        dialogWhstapp.setCanceledOnTouchOutside(true);
        dialogWhstapp.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialogWhstapp.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogWhstapp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RelativeLayout close = dialogWhstapp.findViewById(R.id.exit);
        Button CallButton = dialogWhstapp.findViewById(R.id.quitBtn);
        TextView ContentTxt = dialogWhstapp.findViewById(R.id.popupcontent);
        ContentTxt.setText(getResources().getString(R.string.contact_customer_care) + getResources().getString(R.string.customercare_number));
        try {
            dialogWhstapp.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogWhstapp.dismiss();

            }
        });

        CallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogWhstapp.dismiss();
                callPermission();
            }
        });

    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Navigation_Menu_Clicked),new Bundle());
        }

    }

    private void selectItem(int position) {
//        //System.out.println("NEw: dsd");

        switch (position) {
            case 0:
//                //System.out.println("NEw: dsd 1");
                binding.drawerLayout.closeDrawer(GravityCompat.END);
                binding.rightDrawer.setItemChecked(position, false);
                binding.rightDrawer.setSelection(position);
                break;
            case 1:
//                //System.out.println("NEw: dsd 2");
                binding.drawerLayout.closeDrawer(GravityCompat.END);
                break;
            case 2:
//                //System.out.println("NEw: dsd 3");
                break;

            default:
//                //System.out.println("NEw: dsd 4");
                break;
        }

    }



}
