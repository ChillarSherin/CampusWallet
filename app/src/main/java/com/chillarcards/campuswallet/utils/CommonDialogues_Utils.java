package com.chillarcards.campuswallet.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

import com.chillarcards.campuswallet.R;

public class CommonDialogues_Utils {
    public void showDialogPopup(final Context  context){

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Your device does not support this download !.");
        alertDialogBuilder.setCancelable(true);


        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (context instanceof Activity) {
                            ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }
                });


        try {
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
