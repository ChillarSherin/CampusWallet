package com.chillarcards.campuswallet.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.chillarcards.campuswallet.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@SuppressLint("ValidFragment")
public class ToDatePickerTVFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    Calendar mCalendar;
    Date date;
    Date date2;
    TextView Todate;
    Context cntext;
    GetFragmentDatepickerDate mcallback;

    public ToDatePickerTVFragment() {
    }


    public ToDatePickerTVFragment(Calendar mCalendar, Date date, Date date2, TextView todate, Context cntext, GetFragmentDatepickerDate mcallback) {
        this.mCalendar = mCalendar;
        this.date = date;
        this.date2 = date2;
        this.cntext = cntext;
        this.mcallback = mcallback;
        Todate = todate;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
        ToupdateDateButtonText();
    }

    private void ToupdateDateButtonText() {
        @SuppressLint("SimpleDateFormat")
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateForButton = dateFormat.format(mCalendar.getTime());
        try {
            date = dateFormat.parse(dateForButton);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date.before(date2) || date.equals(date2)) {
            Todate.setText(dateForButton);
            mcallback.onDateselected(dateForButton);

        } else {
            Toast.makeText(cntext, R.string.select_correct_date, Toast.LENGTH_SHORT).show();
        }

    }
}

