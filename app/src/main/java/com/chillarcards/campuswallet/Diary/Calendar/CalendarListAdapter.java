package com.chillarcards.campuswallet.Diary.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chillarcards.campuswallet.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import com.chillarcards.campuswallet.networkmodels.calendarevent.Code;

@SuppressWarnings("ALL")
public class CalendarListAdapter extends RecyclerView.Adapter<CalendarListAdapter.ViewHolder> {


    private List<Code> mData = new ArrayList<>();

    private final int rowLayout;
    private final Context mContext;
    private final Activity activity;
    private final String dates;
    private int date1;
    private int month1;
    private int year1;
    private String [] datearray;
    FirebaseAnalytics mFirebaseAnalytics;

    public CalendarListAdapter(List<Code> content,String date, Activity activity, Context context,FirebaseAnalytics mFirebaseAnalytics) {
        this.mData = content;
        this.activity = activity;
        this.rowLayout = R.layout.item_calendar_event;
        this.mContext = context;
        this.dates=date;
        this.mFirebaseAnalytics=mFirebaseAnalytics;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);

        //System.out.println("calendar date : "+dates);

        datearray=dates.split("-");
        date1= Integer.parseInt(datearray[0]);
        month1= Integer.parseInt(datearray[1]);
        year1= Integer.parseInt(datearray[2]);


        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        try {
            holder.desc.setText(mData.get(position).getMessageContent());

            if (mData.get(position).getMessageCreatorType().equals("Principal")){

                holder.desc.setTextColor(mContext.getResources().getColor(R.color.green));

            }else if(mData.get(position).getMessageCreatorType().equals("Teacher")){
                holder.desc.setTextColor(mContext.getResources().getColor(R.color.blue));
            }else {
                holder.desc.setTextColor(mContext.getResources().getColor(R.color.black));
            }

            holder.calendarimgvw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try
                    {
                        mFirebaseAnalytics.logEvent(mContext.getResources().getString(R.string.Calendar_Event_added),new Bundle());
                        Intent calIntent = new Intent(Intent.ACTION_EDIT);
                    calIntent.setData(CalendarContract.Events.CONTENT_URI);
                    calIntent.putExtra(CalendarContract.Events.TITLE, String.valueOf(mData.get(position).getMessageContent()));

                    GregorianCalendar calDate = new GregorianCalendar(year1,month1,date1);
                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                            calDate.getTimeInMillis());
                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                            calDate.getTimeInMillis());
                    activity.startActivity(calIntent);
                    activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }catch (Exception e)
                    {

                    }
                }
            });


        } catch (Exception ignored) {

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView desc;
        final ImageView calendarimgvw;


        public ViewHolder(View itemView) {
            super(itemView);

            desc = (TextView) itemView.findViewById(R.id.TvCalendarContent);
            calendarimgvw=(ImageView)itemView.findViewById(R.id.calendarimagview);

        }
    }

}
