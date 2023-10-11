package com.chillarcards.campuswallet.Diary.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;

import com.chillarcards.campuswallet.databinding.ActivityAttendancePopupBinding;
import com.chillarcards.campuswallet.databinding.ActivityNewCalendarBinding;
import com.google.gson.Gson;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.calendarevent.Calendarevent;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;


@SuppressWarnings("ALL")
public class CalendarNew extends CustomConnectionBuddyActivity {

    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    Dialog dlg2;
    private String parentPh,s_Id;
    private RecyclerView mRecyclerView;
    private Activity activity;
    private String dateevnt,dateevnt2;
    PrefManager prefManager;

    final String tag_json_object = "r_calender";

    Calendar mCalendar;
    private int mYear, mMonth, mDay;

    @SuppressLint("SimpleDateFormat") final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
    private ActivityNewCalendarBinding binding;
    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_new_calendar);
        binding = ActivityNewCalendarBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);

        prefManager = new PrefManager(this);
        binding.HeaderTV.setText(getResources().getString(R.string.calender_header));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        caldroidFragment = new CaldroidFragment();
        activity = this;

        parentPh = prefManager.getUserPhone();
        s_Id= prefManager.getStudentId();

        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        Calendar c = Calendar.getInstance();
        int CurrYear = c.get(Calendar.YEAR);
        int CurrMonth = c.get(Calendar.MONTH)+1;

        defaultCalendarEvnts(CurrYear,CurrMonth);

        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
            caldroidFragment.setArguments(args);
        }



        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                dateevnt=formatter.format(date);
                dateevnt2=simpleDate.format(date);
                mFirebaseAnalytics.logEvent(getResources().getString(R.string.Calendar_Event_Date_Clicked),new Bundle());
                calendarEvents();
            }

            @Override
            public void onChangeMonth(int month, int year) {

                defaultCalendarEvnts(year,month);

            }

            @Override
            public void onLongClickDate(Date date, View view) {

            }

            @Override
            public void onCaldroidViewCreated()
            {
                if (caldroidFragment.getLeftArrowButton() != null) {

                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




    /**
     *
     * @param currYear
     * @param currMonth
     */
    private void defaultCalendarEvnts(int currYear, final int currMonth)
    {



        String URL, parameters;

        parameters = "phoneNo=" + parentPh + "&studentID=" + s_Id+"&year="+currYear+"&month="+currMonth;
        URL = Constants.BASE_URL  + "r_calender.php?" + parameters.replaceAll(" ", "%20");
//        //System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                binding.progbar.setVisibility(View.GONE);
//                //System.out.println("CHECK---> Response " + jsonObject);
                Gson gson = new Gson();

                com.chillarcards.campuswallet.networkmodels.calendar.Calendar calendar = gson.fromJson(jsonObject,com.chillarcards.campuswallet.networkmodels.calendar.Calendar.class);

                com.chillarcards.campuswallet.networkmodels.calendar.Status status = calendar.getStatus();
                String code = status.getCode();

                if (code.equals("200")) {
                    findViewById(R.id.ErrorLL).setVisibility(View.GONE);

                    List<com.chillarcards.campuswallet.networkmodels.calendar.Code> codes = calendar.getCode();

                    if(codes.size()>0){

                        for(int i = 0;i<codes.size();++i){

                            com.chillarcards.campuswallet.networkmodels.calendar.Code code1 = codes.get(i);

                            String date = code1.getMessageDueDate();
                            String eventType = code1.getType();

                            switch (eventType){
                                case "1":
                                    if (caldroidFragment != null) {
                                        setCustomResourceForDates(date);
                                        try {
                                            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                                            t.replace(R.id.calendar1, caldroidFragment);
                                            t.commitAllowingStateLoss();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                case "2":
                                    if (caldroidFragment != null) {

                                        setStudentForDates(date);
                                        try {
                                            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                                            t.replace(R.id.calendar1, caldroidFragment);
                                            t.commitAllowingStateLoss();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                case "3":
                                    if (caldroidFragment != null) {
                                        setBothForDates(date);
                                        try {
                                            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                                            t.replace(R.id.calendar1, caldroidFragment);
                                            t.commitAllowingStateLoss();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                    }
                                    break;
                                case "4":
                                    if (caldroidFragment != null) {

                                        setHolidays(date);
                                        try {
                                            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                                            t.replace(R.id.calendar1, caldroidFragment);
                                            t.commitAllowingStateLoss();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                    }
                                    break;
                                case "5":
                                    if (caldroidFragment != null) {

                                        setTeacherHolidays(date);
                                        try {
                                            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                                            t.replace(R.id.calendar1, caldroidFragment);
                                            t.commitAllowingStateLoss();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                    }
                                    break;
                                case "6":
                                    if (caldroidFragment != null) {
                                        setPrinciHolidays(date);

                                        try {
                                            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                                            t.replace(R.id.calendar1, caldroidFragment);
                                            t.commitAllowingStateLoss();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                case "7":
                                    if (caldroidFragment != null) {
                                        setBothHolidays(date);
                                        try {
                                            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                                            t.replace(R.id.calendar1, caldroidFragment);
                                            t.commitAllowingStateLoss();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                            }

                        }

                    }else{
                        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                        t.replace(R.id.calendar1, caldroidFragment);
                        t.commitAllowingStateLoss();
                        binding.progbar.setVisibility(View.GONE);

                    }

                }else{
                    ReloadBTN.setText(getResources().getString(R.string.go_back));
                    findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
                    ErrorMessageTV.setText(getResources().getString(R.string.error_message_errorlayout));
                    CodeErrorTV.setText(getResources().getString(R.string.code_attendance)+code);

                    ReloadBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            onBackPressed();

                        }
                    });
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                binding.progbar.setVisibility(View.GONE);
                VolleyLog.d("Object Error : ", volleyError.getMessage());
                ReloadBTN.setText(getResources().getString(R.string.go_back));
                findViewById(R.id.ErrorLL).setVisibility(View.VISIBLE);
                ErrorMessageTV.setText(getResources().getString(R.string.error_message_admin));
                CodeErrorTV.setText(getResources().getString(R.string.error_message_errorlayout));

                ReloadBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        onBackPressed();

                    }
                });

                Toast.makeText(CalendarNew.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(CalendarNew.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);

    }



    /**
     * To get specific messages
     */

    private void calendarEvents()
    {
        dlg2 = new Dialog(activity);
        dlg2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg2.setContentView(R.layout.popup_calendar_event);
        dlg2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView header = (TextView) dlg2.findViewById(R.id.header);
        final TextView noEvents = (TextView) dlg2.findViewById(R.id.noEvents);
        mRecyclerView = (RecyclerView) dlg2.findViewById(R.id.eventlistviewid);
        ImageView CloseEventpopupIV = (ImageView) dlg2.findViewById(R.id.CloseEventpopupIV);
        final ProgressBar mProgressbar = (ProgressBar) dlg2.findViewById(R.id.progress);
        header.setText(dateevnt2);
        dlg2.show();

        CloseEventpopupIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg2.dismiss();
            }
        });

        final String tag_json_object = "r_message_by_date";
        String URL, parameters;
        parameters = "phoneNo=" + parentPh + "&studentID=" + s_Id+"&date="+dateevnt;
        URL = Constants.BASE_URL  + "r_message_by_date.php?" + parameters.replaceAll(" ", "%20");
//        //System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
//                //System.out.println("CHECK---> Response " + jsonObject);
                Gson gson = new Gson();
                mProgressbar.setVisibility(View.GONE);

                Calendarevent calendarevent = gson.fromJson(jsonObject,Calendarevent.class);
                com.chillarcards.campuswallet.networkmodels.calendarevent.Status status = calendarevent.getStatus();
                String code = status.getCode();

                if(code.equals("200")){

                    List<com.chillarcards.campuswallet.networkmodels.calendarevent.Code> code1 = calendarevent.getCode();

                    if(code1.size()>0){

                        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        CalendarListAdapter mAdapter = new CalendarListAdapter(code1,dateevnt, activity, getApplicationContext(),mFirebaseAnalytics);
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setVisibility(View.VISIBLE);



                    }else{

                        mRecyclerView.setVisibility(View.GONE);
                        noEvents.setVisibility(View.VISIBLE);

                    }

                }else{

                    dlg2.dismiss();
//                    String message = status.getMessage();
                    String message = getResources().getString(R.string.no_events_found);
                    Toast.makeText(CalendarNew.this, message, Toast.LENGTH_SHORT).show();

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mProgressbar.setVisibility(View.GONE);
                VolleyLog.d("Object Error : ", volleyError.getMessage());

                Toast.makeText(CalendarNew.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(CalendarNew.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);



    }

    public Date getCurrentDate()
    {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateFrom = dateFormat.format(mCalendar.getTime());
        Date date=mCalendar.getTime();
        return date;
    }

    private Date simpleDate(String dateStr){

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        //String dateFrom = dateFormat.format(dateStr);
        Date date= null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        //System.out.println("Incoming Date : "+date.toString());
        return date;

//        Date date = new Date();
//        try {
//            date = simpleDate.parse(dateStr);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        return date;

    }

    /**
     * Setting color for specific dates
     * @param date
     */
    private void setCustomResourceForDates(String date) {

        Date blueDate = simpleDate(date);
        Date currentDate = getCurrentDate();

        if(currentDate.getDate()==blueDate.getDate()){

            if (caldroidFragment != null) {
                ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.duskYellow));
                caldroidFragment.setBackgroundDrawableForDate(drawable, currentDate);
            }

        }else {

            if (caldroidFragment != null)
            {
                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
                caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
                caldroidFragment.setTextColorForDate(R.color.white, blueDate);

            }

        }

    }
    /**
     * Setting color for specific dates
     */
    private void setStudentForDates(String date) {

        Date blueDate = simpleDate(date);

        Date currentDate = getCurrentDate();

        if(currentDate.getDate()==blueDate.getDate()){

            if (caldroidFragment != null) {
                ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.duskYellow));
                caldroidFragment.setBackgroundDrawableForDate(drawable, currentDate);
            }

        }else {


            if (caldroidFragment != null) {
                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
                ColorDrawable green = new ColorDrawable(getResources().getColor(R.color.green));
                ColorDrawable cyan = new ColorDrawable(Color.CYAN);
                caldroidFragment.setBackgroundDrawableForDate(green, blueDate);

                caldroidFragment.setTextColorForDate(R.color.white, blueDate);

            }
        }
    }
    /**
     * Setting color for specific dates
     */
    private void setBothForDates(String date) {

        Date blueDate = simpleDate(date);

        Date currentDate = getCurrentDate();

        if(currentDate.getDate()==blueDate.getDate()){

            if (caldroidFragment != null) {
                ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.duskYellow));
                caldroidFragment.setBackgroundDrawableForDate(drawable, currentDate);
            }

        }else {

            if (caldroidFragment != null) {


                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
                ColorDrawable green = new ColorDrawable(getResources().getColor(R.color.green));
                ColorDrawable cyan = new ColorDrawable(Color.CYAN);
                caldroidFragment.setBackgroundDrawableForDate(cyan, blueDate);
                caldroidFragment.setTextColorForDate(R.color.white, blueDate);
            }
        }
    }
    /**
     * Setting color for Holiday
     *
     * */
    private void setHolidays(String date) {

        Date blueDate = simpleDate(date);

        Date currentDate = getCurrentDate();


        if(currentDate.getDate()==blueDate.getDate()){

            if (caldroidFragment != null) {
                ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.duskYellow));
                caldroidFragment.setBackgroundDrawableForDate(drawable, currentDate);
            }

        }else {


            if (caldroidFragment != null)
            {
                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
                ColorDrawable green = new ColorDrawable(getResources().getColor(R.color.green));
                ColorDrawable Gray = new ColorDrawable(Color.WHITE);
                caldroidFragment.setBackgroundDrawableForDate(Gray, blueDate);
                caldroidFragment.setTextColorForDate(R.color.caldroid_light_red, blueDate);
            }
        }
    }

    /**
     * Setting color for Teacher Holiday
     */
    private void setTeacherHolidays(String date) {
        Date blueDate = simpleDate(date);

        Date currentDate = getCurrentDate();


        if(currentDate.getDate()==blueDate.getDate()){

            if (caldroidFragment != null) {
                ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.duskYellow));
                caldroidFragment.setBackgroundDrawableForDate(drawable, currentDate);
            }

        }else {

            if (caldroidFragment != null) {
                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
                ColorDrawable green = new ColorDrawable(getResources().getColor(R.color.green));
                ColorDrawable Gray = new ColorDrawable(Color.WHITE);
                caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
                caldroidFragment.setTextColorForDate(R.color.caldroid_light_red, blueDate);
            }
        }
    }

    /**
     * Setting color for Principal Holidays
     */
    private void setPrinciHolidays(String date) {

        Date blueDate = simpleDate(date);
        Date currentDate = getCurrentDate();


        if(currentDate.getDate()==blueDate.getDate()){

            if (caldroidFragment != null) {
                ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.duskYellow));
                caldroidFragment.setBackgroundDrawableForDate(drawable, currentDate);
            }

        }else {


            if (caldroidFragment != null) {
                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
                ColorDrawable green = new ColorDrawable(getResources().getColor(R.color.green));
                ColorDrawable Gray = new ColorDrawable(Color.WHITE);
                caldroidFragment.setBackgroundDrawableForDate(green, blueDate);

                caldroidFragment.setTextColorForDate(R.color.caldroid_light_red, blueDate);

            }
        }
    }
    /**
     * Setting color for Both Holidays
     */
    private void setBothHolidays(String date) {

        Date blueDate = simpleDate(date);

        Date currentDate = getCurrentDate();


        if(currentDate.getDate()==blueDate.getDate()){

            if (caldroidFragment != null) {
                ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.duskYellow));
                caldroidFragment.setBackgroundDrawableForDate(drawable, currentDate);
            }

        }else {


            if (caldroidFragment != null) {
                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
                ColorDrawable green = new ColorDrawable(getResources().getColor(R.color.green));
                ColorDrawable Gray = new ColorDrawable(Color.CYAN);
                caldroidFragment.setBackgroundDrawableForDate(Gray, blueDate);
                caldroidFragment.setTextColorForDate(R.color.caldroid_light_red, blueDate);
            }
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
        }
    }



}

