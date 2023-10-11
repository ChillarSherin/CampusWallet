package com.chillarcards.campuswallet.Diary.EDiary;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.chillarcards.campuswallet.Diary.LeaveRequest.CallBack;
import com.chillarcards.campuswallet.application.CampusWallet;
import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.utils.Clipboard_Utils;
import com.chillarcards.campuswallet.utils.NoMenuEditText;

@SuppressLint("ValidFragment")
public class MessageToteacherDialog extends DialogFragment {
    public static final String TAG = "MessageToteacherDialog";
    NoMenuEditText MessageToteacherET;
    TextInputLayout MessageToteacherETLL;
    NoMenuEditText SubjectET;
    TextInputLayout SubjectSPLL;
    Button TeacherMessageCancelBTN;
    Button TeacherMessageSendBTN;
    ProgressBar ProgressBarMrssage;
    CallBack callback;
    String ReasonID;
    FirebaseAnalytics mFirebaseAnalytics;
    final String tag_json_object = "c_parent_message_to_teacher";
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

    public MessageToteacherDialog(CallBack callback) {
//        ReasonID = reasonID;
        this.callback=callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_send_teacher_message, container, false);
        MessageToteacherET= view.findViewById(R.id.MessageToteacherET);
        MessageToteacherETLL= view.findViewById(R.id.MessageToteacherETLL);
        SubjectET= view.findViewById(R.id.SubjectSP);
        SubjectSPLL= view.findViewById(R.id.SubjectSPLL);
        TeacherMessageCancelBTN= view.findViewById(R.id.TeacherMessageCancelBTN);
        TeacherMessageSendBTN= view.findViewById(R.id.TeacherMessageSendBTN);
        ProgressBarMrssage= view.findViewById(R.id.ProgressBarMrssage);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.message("");
                dismiss();
            }
        });
        ProgressBarMrssage.setVisibility(View.GONE);
        mFirebaseAnalytics=FirebaseAnalytics.getInstance(getActivity());
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(SubjectET.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(MessageToteacherET.getWindowToken(), 0);
        SubjectET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        SubjectET.setLongClickable(false);
        SubjectET.setTextIsSelectable(false);
        if (android.os.Build.VERSION.SDK_INT < 11) {
            SubjectET.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

                @Override
                public void onCreateContextMenu(ContextMenu menu, View v,
                                                ContextMenu.ContextMenuInfo menuInfo) {
                    // TODO Auto-generated method stub
                    menu.clear();
                }
            });
        } else {
            SubjectET.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

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
        MessageToteacherET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        MessageToteacherET.setLongClickable(false);
        MessageToteacherET.setTextIsSelectable(false);
        if (android.os.Build.VERSION.SDK_INT < 11) {
            MessageToteacherET.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

                @Override
                public void onCreateContextMenu(ContextMenu menu, View v,
                                                ContextMenu.ContextMenuInfo menuInfo) {
                    // TODO Auto-generated method stub
                    menu.clear();
                }
            });
        } else {
            MessageToteacherET.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

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
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
//        toolbar.setTitle("REQUEST A LEAVE");
        SubjectET.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(SubjectSPLL.getCounterMaxLength())});
        SubjectET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= SubjectSPLL.getCounterMaxLength())
                    SubjectSPLL.setError("Max character length is " + SubjectSPLL.getCounterMaxLength());
                else
                    SubjectSPLL.setError(null);
            }
        });

        SubjectET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clipboard_Utils.copyToClipboard(getActivity(), "");
            }
        });
        MessageToteacherET.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(MessageToteacherETLL.getCounterMaxLength())});
        MessageToteacherET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= MessageToteacherETLL.getCounterMaxLength())
                    MessageToteacherETLL.setError("Max character length is " + MessageToteacherETLL.getCounterMaxLength());
                else
                    MessageToteacherETLL.setError(null);
            }
        });

        MessageToteacherET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clipboard_Utils.copyToClipboard(getActivity(), "");
            }
        });
        TeacherMessageSendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Message_To_Teacher_Send_Button_Clicked),new Bundle());
                submit();
            }
        });
        TeacherMessageCancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(getActivity().getResources().getString(R.string.Message_To_Teacher_Cancel_Button_Clicked),new Bundle());
                Cancel();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if ( context instanceof CallBack) {
            callback = (CallBack) context;
        } else {
            throw new RuntimeException(context.getClass().getSimpleName()
                    + " must implement Callback");
        }
    }
    public void submit() {

//        ProgressBarMrssage.setVisibility(View.VISIBLE);
        PrefManager prefManager = new PrefManager(getActivity());
        String userPhone = prefManager.getUserPhone();
        String studentId = prefManager.getStudentId();
        String reason=MessageToteacherET.getText().toString().trim();
        //System.out.println("REASON TEXT : "+reason);
        String subject=SubjectET.getText().toString().trim();
        if (!reason.equalsIgnoreCase(""))
        {
            if( !subject.equalsIgnoreCase("")){
//                if (MessageToteacherET.getText().toString().trim().length()>1000)
//                {
//                    MessageToteacherET.setError("Reason text length is limited to 1000 ");
//                }
//                else
//                {
                    submitRequest(userPhone,studentId,ReasonID,reason,subject);
//                }

        }
        else
            {
                SubjectET.setError(getActivity().getResources().getString(R.string.field_cannot_empty));
            }

        }
        else {
            MessageToteacherET.setError(getActivity().getResources().getString(R.string.field_cannot_empty));
        }
//        String reason = etReason.getText().toString();
    }

    public void Cancel() {
        callback.message("");
        dismiss();
    }
    private void submitRequest(String userPhone, String studentId, String typeID, String reason,String Subject) {

        ProgressBarMrssage.setVisibility(View.VISIBLE);
        String URL, parameters;
        try {
            reason= URLEncoder.encode(reason, "utf-8");
            Subject= URLEncoder.encode(Subject, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//        parameters = "phoneNo=" +userPhone+"&studentID="+studentId+"&message_type_Id="+typeID+"&reason="+reason+"&subject="+Subject;
        parameters = "phoneNo=" +userPhone+"&studentID="+studentId+"&subject="+Subject+"&reason="+reason;

        URL = Constants.BASE_URL + "c_parent_message_to_teacher.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        //CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                //System.out.println("CHECK---> Response " + jsonObject);

                ProgressBarMrssage.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject1 = new JSONObject(jsonObject);
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("status");
                    String code =jsonObject2.getString("code");

                    if(code.equals("200")){

                        Toast.makeText(getActivity().getApplicationContext(),getActivity().getResources().getString(R.string.sent_for_review) , Toast.LENGTH_SHORT).show();
                        callback.message(code);
                        dismiss();


                    }else{
                        String message = jsonObject2.getString("message");
                        Toast.makeText(getActivity().getApplicationContext(), message.toString(), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ProgressBarMrssage.setVisibility(View.GONE);
                CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
                VolleyLog.d("Object Error : ", volleyError.getMessage());
                volleyError.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       // RequestQueue requestQueue = Volley.newRequestQueue(getActivity(), commonSSLConnection.getHulkstack(getActivity()));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
       // requestQueue.add(jsonObjectRequestLogin);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }
}
