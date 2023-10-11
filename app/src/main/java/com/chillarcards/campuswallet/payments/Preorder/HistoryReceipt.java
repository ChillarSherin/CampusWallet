package com.chillarcards.campuswallet.payments.Preorder;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chillarcards.campuswallet.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.chillarcards.campuswallet.application.Constants;
import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
import com.chillarcards.campuswallet.application.PrefManager;

public class HistoryReceipt extends CustomConnectionBuddyActivity {

    private static final String TAG = "HistoryReceiptActivity";
    WebView ReceiptWV;
    String TransID;
    String parentPh,s_Id;
    FloatingActionButton downloadBTN;
    Dialog OtpDialog;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_history_receipt_preorder);
        ReceiptWV=findViewById(R.id.receiptWV);
        downloadBTN=findViewById(R.id.downloadBTN);
        isStoragePermissionGranted();
        prefManager=new PrefManager(HistoryReceipt.this);

        TransID = getIntent().getExtras().getString("TransID");
        parentPh = prefManager.getUserPhone();
        s_Id= prefManager.getStudentId();

        ReceiptWV.getSettings().setJavaScriptEnabled(true);

        final Activity activity = this;
        ReceiptWV.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000);
            }
        });
        ReceiptWV.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });

        ReceiptWV.loadUrl(Constants.BASE_URL+"/r_online_payment_reciept.php?" +
                "phoneNo=" +parentPh+
                "&studentID=" +s_Id+
                "&onlineTransactionID="+TransID);
        downloadBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = takeScreenshot();
                saveBitmap(bitmap);
            }
        });


       // shareIt();

    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(R.id.receiptWV);
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        File imageFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Receipts");
        if (! imageFolder.exists())
        {
            imageFolder.mkdir();
        }

       File imagePath = new File(imageFolder.getAbsolutePath() +"/"+TransID+"_screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Custom_Dialogue();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
    public void Custom_Dialogue()
    {
        OtpDialog = new Dialog(this);
        OtpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        OtpDialog.setContentView(R.layout.download_receipt_layout);
        OtpDialog.setCanceledOnTouchOutside(false);
        OtpDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button Ok = (Button) OtpDialog.findViewById(R.id.buttonChangeOK);

        TextView ContentText = (TextView) OtpDialog.findViewById(R.id.Tv);

        TextView title1 = (TextView) OtpDialog.findViewById(R.id.Title);

        try {
            OtpDialog.show();
        }catch (Exception e){

        }
       // ContentText.setText(Content);


        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(HistoryReceipt.this,StudentList.class);
//                startActivity(i);
                HistoryReceipt.super.onBackPressed();
                finish();
                OtpDialog.dismiss();

            }
        });


    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
//    private void shareIt() {
//        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//        sharingIntent.setType("image/*");
//        String shareBody = "In Tweecher, My highest score with screen shot";
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Tweecher score");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//        sharingIntent.putExtra(Intent.EXTRA_STREAM, Constants.imagefile);
//
//        startActivity(Intent.createChooser(sharingIntent, "Share via"));
//
//        Intent i=new Intent(getApplicationContext(),FirstActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        Bundle bn=new Bundle();
//        bn.putString("Type","CwType");
//        i.putExtras(bn);
//        startActivity(i);
//        finish();
//
//    }
}
