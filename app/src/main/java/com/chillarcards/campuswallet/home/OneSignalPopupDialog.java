package com.chillarcards.campuswallet.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import com.chillarcards.campuswallet.application.Constants;

public class OneSignalPopupDialog extends DialogFragment {

    public static String TAG = "OneSignalPopupDialog";

    public OneSignalPopupDialog() {
    }
    TextView title1;
    Button Ok;
    Button More;
    TextView ContentText;
    ImageView img;
    String Title,Content,ImageUrl,weburl,chillarAddsID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        Bundle bundle=getArguments();
        if(bundle!=null)
        {
            Title=bundle.getString("Title");
            Content=bundle.getString("Content");
            ImageUrl=bundle.getString("ImageUrl");
            weburl=bundle.getString("weburl");
            chillarAddsID=bundle.getString("chillarAddsID");
        }
//        //System.out.println("OneSignalPopupDialog : Title : "+ Title);
//        //System.out.println("OneSignalPopupDialog : Content : "+ Content);
//        //System.out.println("OneSignalPopupDialog : ImageUrl : "+ Constants.BASE_IMAGE_URL+ImageUrl);
//        //System.out.println("OneSignalPopupDialog : weburl : "+ weburl);
//        //System.out.println("OneSignalPopupDialog : chillarAddsID : "+ chillarAddsID);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_onesignal_content_home_adds, container, false);

        title1 = view.findViewById(R.id.Title);
        Ok = view.findViewById(R.id.buttonOK);
        More = view.findViewById(R.id.buttonmore);
        ContentText = view.findViewById(R.id.Tv);
        img = view.findViewById(R.id.one_img);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.HomeFlag="false";
                dismiss();
            }
        });
        toolbar.setTitle("INFORMATION");

        Ok.setOnClickListener(view1 -> submit());
        More.setOnClickListener(view1 -> More());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setGravity(View.TEXT_ALIGNMENT_CENTER);
        }
        ContentText.setText(Content);
        title1.setText(Title);
        Picasso.get()
                .load(Constants.BASE_IMAGE_URL+ImageUrl)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(img);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void submit(){
        Constants.HomeFlag="false";
        dismiss();
    }
    public void More(){

        Constants.HomeFlag="false";
        dismiss();

        Intent i=new Intent(getActivity().getApplicationContext(),AdsWebViewActivity.class);
        Bundle b=new Bundle();
        b.putString("mUrl",weburl);
        b.putString("chillarAddsID",chillarAddsID);
        i.putExtras(b);
        startActivity(i);

    }



}
