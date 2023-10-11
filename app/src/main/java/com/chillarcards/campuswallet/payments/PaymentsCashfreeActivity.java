//package com.chillarcards.campuswallet.payments;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.text.Editable;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.chillarcards.campuswallet.R;
//import com.gocashfree.cashfreesdk.CFPaymentService;
//import com.google.gson.Gson;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import butterknife.OnTextChanged;
//import com.chillarcards.campuswallet.NewPreOrder.CartListActivity;
//import com.chillarcards.campuswallet.application.CampusWallet;
//import com.chillarcards.campuswallet.application.Constants;
//import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
//import com.chillarcards.campuswallet.application.PrefManager;
//import com.chillarcards.campuswallet.networkmodels.ListSavedCards.CardsDetail;
//import com.chillarcards.campuswallet.networkmodels.ListSavedCards.SavedCards;
//import com.chillarcards.campuswallet.networkmodels.createpaymentnew.Cashfree;
//import com.chillarcards.campuswallet.networkmodels.createpaymentnew.Code;
//import com.chillarcards.campuswallet.networkmodels.createpaymentnew.Createpaymentnew;
//import com.chillarcards.campuswallet.networkmodels.createpaymentnew.Status;
//import com.chillarcards.campuswallet.payments.Preorder.PreOrderCartList_Activity;
//import com.chillarcards.campuswallet.utils.CommonSSLConnection;
//
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_APP_ID;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CARD_CVV;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CARD_HOLDER;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CARD_MM;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CARD_NUMBER;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CARD_YYYY;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_EMAIL;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_NAME;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_CUSTOMER_PHONE;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_AMOUNT;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_ID;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_ORDER_NOTE;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_PAYMENT_OPTION;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_UPI_VPA;
//import static com.gocashfree.cashfreesdk.CFPaymentService.PARAM_VENDOR_SPLIT;
//
//public class PaymentsCashfreeActivity extends CustomConnectionBuddyActivity implements SavedCardListener {
//
//    private static final int CARD_NUMBER_TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
//    private static final int CARD_NUMBER_TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
//    private static final int CARD_NUMBER_DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
//    private static final int CARD_NUMBER_DIVIDER_POSITION = CARD_NUMBER_DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
//    private static final char CARD_NUMBER_DIVIDER = '-';
//
//    private static final int CARD_DATE_TOTAL_SYMBOLS = 5; // size of pattern MM/YY
//    private static final int CARD_DATE_TOTAL_DIGITS = 4; // max numbers of digits in pattern: MM + YY
//    private static final int CARD_DATE_DIVIDER_MODULO = 3; // means divider position is every 3rd symbol beginning with 1
//    private static final int CARD_DATE_DIVIDER_POSITION = CARD_DATE_DIVIDER_MODULO - 1; // means divider position is every 2nd symbol beginning with 0
//    private static final char CARD_DATE_DIVIDER = '/';
//
//    private static final int CARD_CVC_TOTAL_SYMBOLS = 3;
//
//
//
//
//    final String tag_json_object1 = "c_online_transaction1";
//    final String tag_json_object2 = "u_online_transaction1";
//    final String tag_json_object3 = "saved_cards";
//
//    PrefManager prefManager;
//    Activity activity;
//    String parentPhone,studentId,trans_category,orderId,orderAmount,paymentMode,amount,paymentGateway;
//    String paymentItemId = "";
//
//    @BindView(R.id.recyclerView)
//    RecyclerView mRecyclerView;
//    @BindView(R.id.mProgressBar)
//    ProgressBar mProgressBar;
//    @BindView(R.id.savedCards)
//    RelativeLayout mRelativeLayout;
//    @BindView(R.id.cardDetailsLayout)
//    RelativeLayout cardDetailsLayout;
//    @BindView(R.id.netBankingLayout)
//    RelativeLayout netBankingLayout;
//    @BindView(R.id.cardNumberEditText)
//    EditText cardNumberEditText;
//    @BindView(R.id.cardNameEditText)
//    EditText cardNameEditText;
//    @BindView(R.id.cardDateEditText)
//    EditText cardDateEditText;
//    @BindView(R.id.cardCVCEditText)
//    EditText cardCVCEditText;
//    @BindView(R.id.upiIdEditText)
//    EditText upiIdEditText;
//    @BindView(R.id.txtAmount)
//    TextView txtAmount;
//    @BindView(R.id.saveCardCheckBox)
//    CheckBox saveCardCheckBox;
//    @BindView(R.id.ic_back)
//    ImageView ic_back;
//
//
//
//
//    SavedCardsAdapter savedCardsAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cashfree);
//
//        ButterKnife.bind(this);
//
//
//        prefManager = new PrefManager(PaymentsCashfreeActivity.this);
//        activity = this;
//        parentPhone = prefManager.getUserPhone();
//        studentId = prefManager.getStudentId();
//
//
//        mRelativeLayout.setVisibility(View.GONE);
//        cardDetailsLayout.setVisibility(View.GONE);
//        netBankingLayout.setVisibility(View.GONE);
//
//        Bundle b = getIntent().getExtras();
//        paymentMode = b.getString("paymentMode");
//        amount = b.getString("amount");
//        trans_category = b.getString("trans_category");
//        paymentGateway = b.getString("paymentGateway");
//        if(!trans_category.equalsIgnoreCase("2")){
//            paymentItemId =  b.getString("paymentItemId");
//        }
//
//        txtAmount.setText("Amount: "+amount);
//
//        if(paymentMode.equalsIgnoreCase("CC")||paymentMode.equalsIgnoreCase("DC")){
//            cardDetailsLayout.setVisibility(View.VISIBLE);
//        }else{
//            netBankingLayout.setVisibility(View.VISIBLE);
//        }
//
//        String paymentModeNew = paymentMode.equalsIgnoreCase("CC")?"credit":"debit";
//
//        if(paymentMode.equalsIgnoreCase("CC")||paymentMode.equalsIgnoreCase("DC")){
//            mProgressBar.setVisibility(View.VISIBLE);
//            LoadSavedCards(parentPhone,studentId,trans_category,paymentModeNew);
//        }
//
//
//    }
//
//    @OnClick(R.id.ic_back)
//    public void backPressed(){
//        finish();
//    }
//
//    @OnClick(R.id.btnPay)
//    public void btnPayClicked(){
//
//        if(paymentMode.equalsIgnoreCase("CC")||paymentMode.equalsIgnoreCase("DC")){
//            if(!cardNumberEditText.getText().toString().isEmpty()){
//                if(!cardDateEditText.getText().toString().isEmpty()){
//                    if(!cardCVCEditText.getText().toString().isEmpty()){
//                        if(!cardNameEditText.getText().toString().isEmpty()){
//
//                            if(validateCard(cardNumberEditText.getText().toString())) {
//
//                                if(huhnValidation(cardNumberEditText.getText().toString())) {
//
//                                    createTransaction(parentPhone, studentId, amount, trans_category, paymentGateway, paymentMode, paymentItemId, false, "", "");
//
//                                }else{
//                                    Toast.makeText(activity, "Enter a Valid Card number", Toast.LENGTH_SHORT).show();
//                                }
//                            }else{
//                                Toast.makeText(activity, "Enter a Valid Card number", Toast.LENGTH_SHORT).show();
//                            }
//                        }else{
//                            Toast.makeText(activity, "Enter Missing Fields", Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//                        Toast.makeText(activity, "Enter Missing Fields", Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Toast.makeText(activity, "Enter Missing Fields", Toast.LENGTH_SHORT).show();
//                }
//            }else{
//                Toast.makeText(activity, "Enter Missing Fields", Toast.LENGTH_SHORT).show();
//            }
//        }else{
//
//            if(!upiIdEditText.getText().toString().isEmpty()){
//                createTransaction(parentPhone, studentId, amount, trans_category, paymentGateway, paymentMode, paymentItemId, false, "", "");
//
//            }else{
//                Toast.makeText(activity, "Enter Missing Fields", Toast.LENGTH_SHORT).show();
//
//            }
//
//        }
//
//
//
//
//    }
//
//    @OnTextChanged(value = R.id.cardNumberEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
//    protected void onCardNumberTextChanged(Editable s) {
//
////        Log.d("DEBUG", "afterTextChanged : "+s);
////        String ccNum = s.toString();
////        if(ccNum.contains("-"))
////        ccNum.replace("-","");
////        for(String p:listOfPattern){
////            if(ccNum.matches(p)){
////                Log.d("DEBUG", "afterTextChanged : discover");
////                break;
////            }
////        }
//
//        if (!isInputCorrect(s, CARD_NUMBER_TOTAL_SYMBOLS, CARD_NUMBER_DIVIDER_MODULO, CARD_NUMBER_DIVIDER)) {
//            s.replace(0, s.length(), concatString(getDigitArray(s, CARD_NUMBER_TOTAL_DIGITS), CARD_NUMBER_DIVIDER_POSITION, CARD_NUMBER_DIVIDER));
//        }
//
//
//    }
//
//    @OnTextChanged(value = R.id.cardDateEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
//    protected void onCardDateTextChanged(Editable s) {
//        if (!isInputCorrect(s, CARD_DATE_TOTAL_SYMBOLS, CARD_DATE_DIVIDER_MODULO, CARD_DATE_DIVIDER)) {
//            s.replace(0, s.length(), concatString(getDigitArray(s, CARD_DATE_TOTAL_DIGITS), CARD_DATE_DIVIDER_POSITION, CARD_DATE_DIVIDER));
//        }
//    }
//
//    @OnTextChanged(value = R.id.cardCVCEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
//    protected void onCardCVCTextChanged(Editable s) {
//        if (s.length() > CARD_CVC_TOTAL_SYMBOLS) {
//            s.delete(CARD_CVC_TOTAL_SYMBOLS, s.length());
//        }
//    }
//
//    private boolean isInputCorrect(Editable s, int size, int dividerPosition, char divider) {
//        boolean isCorrect = s.length() <= size;
//        for (int i = 0; i < s.length(); i++) {
//            if (i > 0 && (i + 1) % dividerPosition == 0) {
//                isCorrect &= divider == s.charAt(i);
//            } else {
//                isCorrect &= Character.isDigit(s.charAt(i));
//            }
//        }
//        return isCorrect;
//    }
//
//    private String concatString(char[] digits, int dividerPosition, char divider) {
//        final StringBuilder formatted = new StringBuilder();
//
//        for (int i = 0; i < digits.length; i++) {
//            if (digits[i] != 0) {
//                formatted.append(digits[i]);
//                if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
//                    formatted.append(divider);
//                }
//            }
//        }
//
//        return formatted.toString();
//    }
//
//    private char[] getDigitArray(final Editable s, final int size) {
//        char[] digits = new char[size];
//        int index = 0;
//        for (int i = 0; i < s.length() && index < size; i++) {
//            char current = s.charAt(i);
//            if (Character.isDigit(current)) {
//                digits[index] = current;
//                index++;
//            }
//        }
//        return digits;
//    }
//
//
//
//    private void createTransaction(String phoneNum, String s, final String amount, final String trans_category, final String paymentGateway, final String paymentMode, String paymentItemId, boolean savedCard, String CVV, String cardId) {
//
//        mProgressBar.setVisibility(View.VISIBLE);
//        final String URL;
//        String parameters;
//        orderAmount="";
//        orderId="";
//        parameters = "phoneNo=" + phoneNum+"&studentID="+s+"&amount="+amount+"&transaction_category="+trans_category+"&paymentGateway="+paymentGateway+"&payment_modes="+paymentMode;
//
//        if(!trans_category.equals("2")&&!paymentItemId.equals("")){
//            parameters = parameters+ "&itemID="+paymentItemId;
//        }
//        URL = Constants.BASE_URL + "c_online_transaction.php?" + parameters.replaceAll(" ", "%20");
//        System.out.println("CHECK---> URL " + URL);
//        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
//        final StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String jsonObject) {
//
//                Log.i("CreatePayment",jsonObject);
//
//                CampusWallet.getInstance().cancelPendingRequests(tag_json_object1);
//                Gson gson = new Gson();
//
//                Createpaymentnew createpayment = gson.fromJson(jsonObject,Createpaymentnew.class);
//                Status status = createpayment.getStatus();
//                String code = status.getCode();
//
//                if(code.equals("200")){
////                    findViewById(R.id.ErrorLL).setVisibility(View.GONE);
//                    mProgressBar.setVisibility(View.GONE);
//                    Code code1 = createpayment.getCode();
//
//
//                    if(paymentGateway.equals("cashfree")){
//
//
//                        List<Cashfree> cashfree = code1.getCashfree();
//                        if(cashfree.size()>0){
//
//                            Cashfree cashfree1 = cashfree.get(0);
//
//
//                            String appId= cashfree1.getAppId();
//                            String token=cashfree1.getCftoken();
//                            orderAmount=cashfree1.getOrderAmount();
//                            String orderNotes=cashfree1.getOrderNotes();
//                            orderId=cashfree1.getOnlineTransactionID();
//                            String parentName=cashfree1.getParentName();
//                            String parentPhone=cashfree1.getParentPhone();
//                            String parentEmail=cashfree1.getParentEmail();
//                            String vendorSplit=cashfree1.getVendorSplit();
//
//
//                            String stage = "PROD";
//
//
//                            Map<String, String> params = new HashMap<>();
//
//                            params.put(PARAM_APP_ID, appId);
//                            params.put(PARAM_ORDER_ID, orderId);
//                            params.put(PARAM_ORDER_AMOUNT, orderAmount);
//                            params.put(PARAM_ORDER_NOTE, orderNotes);
//                            params.put(PARAM_CUSTOMER_NAME, parentName);
//                            params.put(PARAM_CUSTOMER_PHONE, parentPhone);
//                            params.put(PARAM_CUSTOMER_EMAIL, parentEmail);
//                            params.put(PARAM_VENDOR_SPLIT,vendorSplit);
//
//
//                            for (Map.Entry entry : params.entrySet()) {
//                                Log.d("CFSKDSample", entry.getKey() + " " + entry.getValue());
//                            }
//
//
//
//
//                            CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
//                            cfPaymentService.setOrientation(0);
//                            switch (paymentMode) {
//
//                                /***
//                                 * This method handles the payment gateway invocation (web flow).
//                                 *
//                                 * @param context Android context of the calling activity
//                                 * @param params HashMap containing all the parameters required for creating a payment order
//                                 * @param token Provide the token for the transaction
//                                 * @param stage Identifies if test or production service needs to be invoked. Possible values:
//                                 *              PROD for production, TEST for testing.
//                                 * @param color1 Background color of the toolbar
//                                 * @param color2 text color and icon color of toolbar
//                                 * @param hideOrderId If true hides order Id from the toolbar
//                                 */
//                                case "CC":
//                                case "DC":
//                                {
//
//                                    if(savedCard){
//
//                                        params.put(PARAM_PAYMENT_OPTION, "savedCard");
//                                        params.put("card_id", cardId);
//                                        params.put(PARAM_CARD_CVV, CVV);
//                                        cfPaymentService.doPayment(PaymentsCashfreeActivity.this, params, token, stage, "#784BD2", "#FFFFFF", false);
//
//
//                                    }else {
//                                        String cardExpiry = cardDateEditText.getText().toString();
//
//                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
//                                        simpleDateFormat.setLenient(false);
//                                        Date expiry = null;
//                                        try {
//                                            expiry = simpleDateFormat.parse(cardExpiry);
//                                        } catch (ParseException e) {
//                                            e.printStackTrace();
//                                        }
////                                        boolean expired = expiry.before(new Date());
////                                        if(!expired){
//                                            Calendar cal = Calendar.getInstance();
//                                            cal.setTime(expiry);
//                                            int month= cal.get(Calendar.MONTH);
//                                            int year = cal.get(Calendar.YEAR);
//                                            month =month+1;
//
//                                            System.out.println("CashFree: Expiry: "+month+" "+year);
//                                            String monthStr = month<10?"0"+month:""+month+"";
//                                            System.out.println("CashFree: Expiry Month: "+monthStr);
//
//
//
//                                            params.put(PARAM_PAYMENT_OPTION, "card");
//                                            params.put(PARAM_CARD_NUMBER, cardNumberEditText.getText().toString().replaceAll("-", ""));//Replace Card number
//                                            params.put(PARAM_CARD_MM, monthStr); // Card Expiry Month in MM
//                                            params.put(PARAM_CARD_YYYY, String.valueOf(year)); // Card Expiry Year in YYYY
//                                            params.put(PARAM_CARD_HOLDER, cardNameEditText.getText().toString()); // Card Holder name
//                                            params.put(PARAM_CARD_CVV, cardCVCEditText.getText().toString());
//                                            if (saveCardCheckBox.isChecked()) {
//                                                params.put("card_save", "1");
//                                            }
//
//                                            cfPaymentService.doPayment(PaymentsCashfreeActivity.this, params, token, stage, "#784BD2", "#FFFFFF", false);
//
////                                        }
//
//                                    }
//
//                                    //                 cfPaymentService.doPayment(MainActivity.this, params, token, stage);
//                                    break;
//                                }
//                                /***
//                                 * Same for all payment modes below.
//                                 *
//                                 * @param context Android context of the calling activity
//                                 * @param params HashMap containing all the parameters required for creating a payment order
//                                 * @param token Provide the token for the transaction
//                                 * @param stage Identifies if test or production service needs to be invoked. Possible values:
//                                 *              PROD for production, TEST for testing.
//                                 */
//                                case "UPI": {
//                                    params.put(PARAM_PAYMENT_OPTION, "upi");
//                                    params.put(PARAM_UPI_VPA, upiIdEditText.getText().toString());// Put correct upi vpa here
////                                cfPaymentService.selectUpiClient("com.google.android.apps.nbu.paisa.user");
//                                    cfPaymentService.doPayment(PaymentsCashfreeActivity.this, params, token, stage, "#784BD2", "#FFFFFF", false);
//                                    break;
//                                }
//                                case "AmazonPay": {
//                                    cfPaymentService.doAmazonPayment(PaymentsCashfreeActivity.this, params, token, stage);
//                                    break;
//                                }
//                                case "GooglePay": {
//                                    cfPaymentService.gPayPayment(PaymentsCashfreeActivity.this, params, token, stage);
//                                    break;
//                                }
//                                case "PhonePe": {
//                                    cfPaymentService.phonePePayment(PaymentsCashfreeActivity.this, params, token, stage);
//                                    break;
//                                }
//                            }
//
//
//                        }
//
//                    }
//
//
//
//
//                }else if(code.equals("500")) {
//
//                    mProgressBar.setVisibility(View.GONE);
//
//                    Toast.makeText(activity, status.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }else if(code.equals("201")) {
//                    mProgressBar.setVisibility(View.GONE);
//                    Toast.makeText(activity, status.getMessage(), Toast.LENGTH_SHORT).show();
//                }else
//                {
//                    mProgressBar.setVisibility(View.GONE);
//                    Toast.makeText(activity, status.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                volleyError.printStackTrace();
//                mProgressBar.setVisibility(View.GONE);
//
//            }
//        });
//        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
////        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(PaymentCreateActivity.this));
//        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object1);
////        requestQueue.add(jsonObjectRequestLogin);
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        //System.out.println("OnDestroy ");
//        CampusWallet.getInstance().cancelPendingRequests(tag_json_object1);
//        CampusWallet.getInstance().cancelPendingRequests(tag_json_object2);
//        CampusWallet.getInstance().cancelPendingRequests(tag_json_object3);
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //Same request code for all payment APIs.
////        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
//        Log.d("CashFree", "API Response : ");
//        //Prints all extras. Replace with app logic.
//        if (data != null) {
//            Bundle bundle = data.getExtras();
//
//            if(bundle.getString("txStatus").equals("SUCCESS")){
//                UpdateStatus(bundle.getString("referenceId"),"S",bundle.getString("orderId"),bundle.getString("orderAmount"));
//            }else{
//
//                UpdateStatus("","F",orderId,orderAmount);
//            }
////            if (bundle != null)
////                for (String key : bundle.keySet()) {
////                    if (bundle.getString(key) != null) {
////                        Log.d("CashFree", key + " : " + bundle.getString(key));
////                        Toast.makeText(getApplicationContext(), key + " : " + bundle.getString(key), Toast.LENGTH_SHORT).show();
////
////
////
////                    }
////                }
//        }
//    }
//
//    private void UpdateStatus(String testIdentifier, final String status, String onlineTransactionID, String orderAmount){
//
//        String URL, parameters = "";
//        if(testIdentifier.equals("")){
//            parameters = "phoneNo=" + parentPhone+"&studentID="+studentId+"&tran_id="+onlineTransactionID+"&transaction_category="+trans_category+"&tran_status="+status;
//        }else{
//            parameters = "phoneNo=" + parentPhone+"&studentID="+studentId+"&tran_id="+onlineTransactionID+"&transaction_category="+trans_category+"&tran_status="+status+"&identifier="+testIdentifier;
//        }
//
//        URL = Constants.BASE_URL + "u_online_transaction_status.php?" + parameters.replaceAll(" ", "%20");
////        System.out.println("CHECK---> URL " + URL);
//        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
//        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String jsonObject) {
//
//                CampusWallet.getInstance().cancelPendingRequests(tag_json_object2);
//                //System.out.println("onResponse : "+jsonObject);
//
//                if (Constants.from_Low_balance)
//                {
//                    if (status.equalsIgnoreCase("S")) {
//                        if (Constants.from_old_preorder==0)
//                        {
////                            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Pre_Order_Cart_Button_clicked),new Bundle());
//                            Intent i = new Intent(getApplicationContext(), CartListActivity.class);
//                            startActivity(i);
//                            activity.finish();
//                        }
//                        else {
//                            mFirebaseAnalytics.logEvent(getResources().getString(R.string.Pre_Order_Cart_Button_clicked),new Bundle());
//                            Intent i = new Intent(getApplicationContext(), PreOrderCartList_Activity.class);
//                            startActivity(i);
//                            activity.finish();
//                        }
//                        Toast.makeText(PaymentsCashfreeActivity.this,getResources().getString(R.string.success),Toast.LENGTH_SHORT).show();
//
//                    }
//                    else
//                    {
////                        Toast.makeText(PaymentRazorpay.this,getResources().getString(R.string.failed),Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(getApplicationContext(), PaymentReceiptActivity.class);
//                        Bundle b = new Bundle();
//                        b.putString("status", status);
//                        b.putString("onlineTransactionID", onlineTransactionID);
//                        b.putString("transaction_category", trans_category);
//                        b.putString("amount", orderAmount);
//                        b.putString("from", "1");
//                        i.putExtras(b);
//                        startActivity(i);
//                        activity.finish();
//                    }
//                }
//                else {
//                    Intent i = new Intent(getApplicationContext(), PaymentReceiptActivity.class);
//                    Bundle b = new Bundle();
//                    b.putString("status", status);
//                    b.putString("onlineTransactionID", onlineTransactionID);
//                    b.putString("transaction_category", trans_category);
//                    b.putString("amount", orderAmount);
//                    b.putString("from", "1");
//                    i.putExtras(b);
//                    startActivity(i);
//                    activity.finish();
//                }
//
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                volleyError.printStackTrace();
//                Toast.makeText(PaymentsCashfreeActivity.this,getResources().getString(R.string.failed),Toast.LENGTH_SHORT).show();
//            }
//        });
//        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
////        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(PaymentRazorpay.this));
//
//        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object2);
////        requestQueue.add(jsonObjectRequestLogin);
//
//    }
//
//    private void LoadSavedCards(String phoneNum, String s, final String category, String paymentModeNew) {
//
//
//
//        String URL, parameters;
//        parameters = "phoneNo=" + phoneNum+"&studentID="+s+"&transactionCategoryID="+category+"&cardType="+paymentModeNew;
//        URL = Constants.BASE_URL + "r_online_saved_cards.php?" + parameters.replaceAll(" ", "%20");
//        System.out.println("CHECK---> URL " + URL);
//        final CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
//        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String jsonObject) {
//
//                CampusWallet.getInstance().cancelPendingRequests(tag_json_object3);
//                mProgressBar.setVisibility(View.GONE);
//                Gson gson = new Gson();
//
//                SavedCards savedCards = gson.fromJson(jsonObject,SavedCards.class);
//                com.chillarcards.campuswallet.networkmodels.ListSavedCards.Status status = savedCards.getStatus();
//                String code = status.getCode();
//
//                if(code.equalsIgnoreCase("200")){
//
//                    List<CardsDetail> cardsDetails = new ArrayList<>();
//                    cardsDetails=savedCards.getCode().getCardsDetails();
//
//                    if(cardsDetails.size()>0){
//                        mRelativeLayout.setVisibility(View.VISIBLE);
//                        mRecyclerView.setHasFixedSize(true);
//                        mRecyclerView.setLayoutManager(new LinearLayoutManager(PaymentsCashfreeActivity.this, LinearLayoutManager.VERTICAL, false));
//                        mRecyclerView.setNestedScrollingEnabled(false);
//                        SavedCardsAdapter mAdapter = new SavedCardsAdapter(cardsDetails,PaymentsCashfreeActivity.this,PaymentsCashfreeActivity.this);
//                        mAdapter.notifyDataSetChanged();
//                        mRecyclerView.setAdapter(mAdapter);
//                    }else {
//                        mRelativeLayout.setVisibility(View.GONE);
//                    }
//
//
//
//
//
//
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//                volleyError.printStackTrace();
//                mProgressBar.setVisibility(View.GONE);
//
//
//            }
//        });
//        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
////        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(PaymentsActivity.this));
//        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object3);
////        requestQueue.add(jsonObjectRequestLogin);
//
//    }
//
//    public boolean validateCard(String cardNumber){
//        String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
//                "(?<mastercard>5[1-5][0-9]{14})|" +
//                "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
//                "(?<amex>3[47][0-9]{13})|" +
//                "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
//                "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";
//
//        Pattern pattern = Pattern.compile(regex);
//
//        //Strip all hyphens
//        String card = cardNumber.replaceAll("-", "");
//
//        //Match the card
//        Matcher matcher = pattern.matcher(card);
//
//        System.out.println(matcher.matches());
//
////        if(matcher.matches()) {
////            //If card is valid then verify which group it belong
////            System.out.println(matcher.group("mastercard"));
////        }
//
//        return matcher.matches();
//
//    }
//
//    public boolean huhnValidation(String cardNumber){
//
//        int sum = 0;
//        boolean alternate = false;
//        cardNumber = cardNumber.replaceAll("-", "");
//        for (int i = cardNumber.length() - 1; i >= 0; i--)
//        {
//            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
//            if (alternate)
//            {
//                n *= 2;
//                if (n > 9)
//                {
//                    n = (n % 10) + 1;
//                }
//            }
//            sum += n;
//            alternate = !alternate;
//        }
//        return (sum % 10 == 0);
//
//    }
//
//
//    @Override
//    public void onSavedCardSelected(CardsDetail mData) {
//
//        ConfirmCard(mData);
//    }
//
//
//    public void ConfirmCard(CardsDetail mData)
//    {
//        Dialog confimCardDlg = new Dialog(PaymentsCashfreeActivity.this);
//        confimCardDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        confimCardDlg.setContentView(R.layout.layout_saved_card_cvv_popup);
//        confimCardDlg.setCanceledOnTouchOutside(true);
//        confimCardDlg.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        confimCardDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        TextView confirm = (TextView) confimCardDlg.findViewById(R.id.confirmLogout);
//        TextView deny=(TextView) confimCardDlg.findViewById(R.id.cancelLogout);
//        TextView imageid=(TextView) confimCardDlg.findViewById(R.id.imageid);
//        EditText etCVV=(EditText) confimCardDlg.findViewById(R.id.etCVV);
//
//        imageid.setText(mData.getMaskedCard());
//
//        try {
//            confimCardDlg.show();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                confimCardDlg.dismiss();
//
//                if(!etCVV.getText().toString().isEmpty()){
//
//                    createTransaction(parentPhone,studentId,amount,trans_category,paymentGateway,paymentMode,paymentItemId,true ,
//                            etCVV.getText().toString(),mData.getCardId());
//
//
//                }else{
//                    Toast.makeText(activity, "Please Enter CVV", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
//        deny.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                confimCardDlg.dismiss();
//            }
//        });
//    }
//}
