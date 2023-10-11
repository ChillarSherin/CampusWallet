package com.chillarcards.campuswallet.Diary.LeaveRequest;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.LayoutCreateLeaverequestDlgBinding;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.utils.Clipboard_Utils;
import com.chillarcards.campuswallet.utils.CommonSSLConnection;

public class CreateLeaveRequestDialog extends DialogFragment {

    public static String TAG = "CreateLeaveRequestDialog";
    public static Calendar FmCalendar;
    public static Calendar TmCalendar;
    private int mYear, mMonth, mDay;
    private String dateFrom = "",dateTo = "";
    CallBack callback;
    FirebaseAnalytics mFirebaseAnalytics;
    private String blockCharacterSet = "¿¡》《¤▪☆♧♢♡♤■□●○~#^|$%&*!&()€¥•_[]=£><@-+/`√π÷×¶∆¢°{}©®™✓:;?'\"\\";
    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };
    final String tag_json_object = "c_leaveRequest";
    public CreateLeaveRequestDialog() {
    }
    @SuppressLint("ValidFragment")
    public CreateLeaveRequestDialog(CallBack callback) {
        this.callback=callback;
    }


    private LayoutCreateLeaverequestDlgBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = LayoutCreateLeaverequestDlgBinding.inflate(inflater, container, false);

        Toolbar toolbar = binding.getRoot().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        toolbar.setTitle(getActivity().getResources().getString(R.string.request_a_leave_header));
        binding.ProgressBarLeave.setVisibility(View.GONE);
        mFirebaseAnalytics=FirebaseAnalytics.getInstance(getActivity());
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.tvreason.getWindowToken(), 0);

        binding.tvreason.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        binding.tvreason.setLongClickable(false);
        binding.tvreason.setTextIsSelectable(false);
        if (android.os.Build.VERSION.SDK_INT < 11) {
            binding.tvreason.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

                @Override
                public void onCreateContextMenu(ContextMenu menu, View v,
                                                ContextMenu.ContextMenuInfo menuInfo) {
                    // TODO Auto-generated method stub
                    menu.clear();
                }
            });
        } else {
            binding.tvreason.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    // TODO Auto-generated method stub
                    return false;
                }

                public void onDestroyActionMode(ActionMode mode) {
                    // TODO Auto-generated method stub

                }

                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    // TODO Auto-generated method stub
                    return false;
                }

                public boolean onActionItemClicked(ActionMode mode,
                                                   MenuItem item) {
                    // TODO Auto-generated method stub
                    return false;
                }
            });
        }

        binding.tvreason.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(binding.tvreasonLL.getCounterMaxLength())});
        binding.tvreason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= binding.tvreasonLL.getCounterMaxLength())
                    binding.tvreasonLL.setError( getActivity().getResources().getString(R.string.max_length_error)+ binding.tvreasonLL.getCounterMaxLength());
                else
                    binding.tvreasonLL.setError(null);
            }
        });
        binding.tvreason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clipboard_Utils.copyToClipboard(getActivity(), "");
            }
        });
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Leave_Request_Send_Button_Clicked),new Bundle());
                submit();
            }
        });
        binding.btncancel.setOnClickListener(v -> {
            mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Leave_Request_Cancel_Button_Clicked),new Bundle());
            cancel();
        });

        binding.tvfrm.setOnClickListener(v -> {
            showDatePickerDialog(binding.tvfrm);
        });

        binding.tvto.setOnClickListener(v -> {
            ToshowDatePickerDialog(binding.tvto);
        });


        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        TmCalendar = Calendar.getInstance();
        FmCalendar = Calendar.getInstance();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if ( context instanceof CallBack ) {
            callback = (CallBack) context;
        } else {
            throw new RuntimeException(context.getClass().getSimpleName()
                    + " must implement Callback");
        }
    }

    public void cancel()
    {
        dismiss();
    }
    public void submit(){
        PrefManager prefManager = new PrefManager(getActivity());
        String userPhone = prefManager.getUserPhone();
        String studentId = prefManager.getStudentId();
        String reason = binding.tvreason.getText().toString().trim();

        if(!dateFrom.equals("")){

            if(!dateTo.equals("")){

                if(!reason.equals("")){
                    if (CompireDates(dateFrom,dateTo)) {
                        submitRequest(userPhone, studentId, dateFrom, dateTo, reason);
                    }
                    else {
                        binding.tvto.setError(getActivity().getResources().getString(R.string.todate_less_startdate_error));
                    }

                }else{
                    binding.tvreason.setError(getActivity().getResources().getString(R.string.reson_for_leave));
//                    Toast.makeText(getActivity(), "Enter reason for leave!", Toast.LENGTH_SHORT).show();
                }

            }else{
                binding.tvto.setError(getActivity().getResources().getString(R.string.enter_to_date));
//                Toast.makeText(getActivity(), "Enter to date!", Toast.LENGTH_SHORT).show();
            }

        }else{
            binding.tvfrm.setError(getActivity().getResources().getString(R.string.enter_from_date));
//            Toast.makeText(getActivity(), "Enter from date!", Toast.LENGTH_SHORT).show();
        }



    }
    public boolean CompireDates(String Fromdate,String Todate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date strDate = sdf.parse(Fromdate);
            Date endDate = sdf.parse(Todate);
            if (endDate.after(strDate) || endDate.equals(strDate)) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }

    }


    private void submitRequest(String userPhone, String studentId, String dateFrom, String dateTo, String reason) {

        binding.ProgressBarLeave.setVisibility(View.VISIBLE);
        String URL, parameters;
        try {
            reason= URLEncoder.encode(reason, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        parameters = "phoneNo=" +userPhone+"&studentID="+studentId+"&leaveRequestFromDate="+dateFrom+"&leaveRequestToDate="+dateTo+"&leaveRequestReason="+reason;

        URL = Constants.BASE_URL + "c_leaveRequest.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                //System.out.println("CHECK---> Response " + jsonObject);

//                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
                binding.ProgressBarLeave.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject1 = new JSONObject(jsonObject);
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("status");
                    String code =jsonObject2.getString("code");

                    if(code.equals("200")){

                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.sent_for_review), Toast.LENGTH_SHORT).show();
                        callback.message(code);
                        dismiss();


                    }else{
                        String message = jsonObject2.getString("message");
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                binding.ProgressBarLeave.setVisibility(View.GONE);
                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
                VolleyLog.d("Object Error : ", volleyError.getMessage());
                volleyError.printStackTrace();
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       // RequestQueue requestQueue = Volley.newRequestQueue(getActivity(), commonSSLConnection.getHulkstack(getActivity()));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
        //requestQueue.add(jsonObjectRequestLogin);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }



    private void showDatePickerDialog(View v) {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        FmCalendar.set(Calendar.YEAR, year);
                        FmCalendar.set(Calendar.MONTH, monthOfYear);
                        FmCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDateButtonText();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    private void ToshowDatePickerDialog(View v) {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year, monthOfYear, dayOfMonth) -> {

                    TmCalendar.set(Calendar.YEAR, year);
                    TmCalendar.set(Calendar.MONTH, monthOfYear);
                    TmCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    ToupdateDateButtonText();
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }


    public  void updateDateButtonText() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFrom = dateFormat.format(FmCalendar.getTime());
        Date fromdat = null;
        Date CurrentDate = null;

        Date today=new Date();
        String StrToday=dateFormat.format(today);
        try {
            fromdat=dateFormat.parse(dateFrom);
            CurrentDate=dateFormat.parse(StrToday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (CurrentDate.before(fromdat) || CurrentDate.equals(fromdat)) {
            binding.tvfrm.setText(dateFrom);
        }
        else {
            binding.tvfrm.setError(getActivity().getResources().getString(R.string.valid_date));
            Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.valid_date),Toast.LENGTH_SHORT).show();
        }

    }

    public void ToupdateDateButtonText() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateTo = dateFormat.format(TmCalendar.getTime());
        Date todat = null;
        Date CurrentDate = null;

        Date today=new Date();
        String StrToday=dateFormat.format(today);
        try {
            todat=dateFormat.parse(dateTo);
            CurrentDate=dateFormat.parse(StrToday);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (CurrentDate.before(todat) || CurrentDate.equals(todat)) {
            binding.tvto.setText(dateTo);
        }
        else {
            binding.tvto.setError(getActivity().getResources().getString(R.string.valid_date));
            Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.valid_date),Toast.LENGTH_SHORT).show();
        }

    }

}
