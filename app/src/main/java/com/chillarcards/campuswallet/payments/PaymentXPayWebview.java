package com.chillarcards.campuswallet.payments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.chillarcards.campuswallet.R;
import com.chillarcards.campuswallet.databinding.ActivityPaymentXpayWebviewBinding;

import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;

public class PaymentXPayWebview extends CustomConnectionBuddyActivity {

    PrefManager prefManager;
    String url = "";
    private ActivityPaymentXpayWebviewBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_payment_xpay_webview);
        binding = ActivityPaymentXpayWebviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefManager = new PrefManager(this);
        String phoneNo = prefManager.getUserPhone();
        String studentID = prefManager.getStudentId();



        Bundle b= getIntent().getExtras();
        String type = b.getString("trans_category");
        String amount = b.getString("amount");

        binding.HeaderTV.setText(setHeaderTV(type));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(type.equals("2")) {

            url = Constants.BASE_URL_XPAY+"c_card_recharge.php?amount="+amount+"&phoneNo="+phoneNo+"&studentID="+studentID+"&RequestDevice=2";
            //System.out.println("URL Webview: "+url);

        }else if(type.equals("3")) {
            String itemId = b.getString("paymentItemId");
            url = Constants.BASE_URL_XPAY+"c_fee_payment.php?amount="+amount+"&phoneNo="+phoneNo+"&studentID="+studentID+"&RequestDevice=2"+"&itemID="+itemId;
            //System.out.println("URL Webview: "+url);
        }else if(type.equals("4")) {
            String itemId = b.getString("paymentItemId");
            url = Constants.BASE_URL_XPAY+"c_misc_payment.php?amount="+amount+"&phoneNo="+phoneNo+"&studentID="+studentID+"&RequestDevice=2"+"&itemID="+itemId;
            //System.out.println("URL Webview: "+url);
        }else if(type.equals("5")) {
            String itemId = b.getString("paymentItemId");
            url = Constants.BASE_URL_XPAY+"c_trust_payment.php?amount="+amount+"&phoneNo="+phoneNo+"&studentID="+studentID+"&RequestDevice=2"+"&itemID="+itemId;
            //System.out.println("URL Webview: "+url);
        }
        //System.out.println("XPAY URL : "+url);

        binding.webView.setWebViewClient(new myWebClient());
        binding.webView.loadUrl(url);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.addJavascriptInterface(new WebViewJavaScriptInterface(this), "app");

    }
    public String setHeaderTV(String catName)
    {
        String headerName="";
        switch (catName)
        {
            case "2":
                headerName=getResources().getString(R.string.card_recharge_caps);
                break;
            case "3":
                headerName=getResources().getString(R.string.fee_payments_caps);
                break;
            case "4":
                headerName=getResources().getString(R.string.miscellanious_caps);
                break;
            case "5":
                headerName=getResources().getString(R.string.trust_payments_caps);
                break;
            default :
                headerName=getResources().getString(R.string.payement_title);
                break;

        }
        return headerName;
    }

    public class myWebClient extends WebViewClient
    {
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {

            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentXPayWebview.this);
            AlertDialog alertDialog = builder.create();
            String message = getResources().getString(R.string.ssl_certificate_error);
            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message = getResources().getString(R.string.certificate_authority_not_trusted);
                    break;
                case SslError.SSL_EXPIRED:
                    message = getResources().getString(R.string.certificate_expired);
                    break;
                case SslError.SSL_IDMISMATCH:
                    message =getResources().getString(R.string.certificate_hostname_mismatch);

                    break;
                case SslError.SSL_NOTYETVALID:
                    message =getResources().getString(R.string.certificate_not_valid);
                    break;
            }

            message += getResources().getString(R.string.do_you_want_to_continue);
            alertDialog.setTitle(getResources().getString(R.string.ssl_certificate_error));
            alertDialog.setMessage(message);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Ignore SSL certificate errors
                    handler.proceed();
                }
            });

            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    handler.cancel();
                }
            });
            try {
                alertDialog.show();
            }catch (Exception e){

            }

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }
    }

    // To handle &quot;Back&quot; key press event for WebView to go back to previous screen.
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)
//    {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
//            webView.goBack();
//        return true;
//    }
//        return super.onKeyDown(keyCode, event);
//    }

    /*
     * JavaScript Interface. Web code can access methods in here
     * (as long as they have the @JavascriptInterface annotation)
     */
    public class WebViewJavaScriptInterface{

        private Context context;

        /**
         * Need a reference to the context in order to sent a post message
         */
        public WebViewJavaScriptInterface(Context context){
            this.context = context;
        }

        /**
         * This method can be called from Android. @JavascriptInterface
         * required after SDK version 17.
         */
        @JavascriptInterface
        public void makeToast(String status,String transid){
            Toast.makeText(context, status+" "+transid, Toast.LENGTH_SHORT).show();

            //System.out.println("XPAY: "+status+" "+transid);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
