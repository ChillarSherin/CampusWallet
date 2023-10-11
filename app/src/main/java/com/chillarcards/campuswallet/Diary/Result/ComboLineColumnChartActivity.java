package com.chillarcards.campuswallet.Diary.Result;

        import android.app.Activity;
        import android.os.Bundle;
        import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.DefaultItemAnimator;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import android.util.Log;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.ArrayAdapter;
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
        import com.chillarcards.campuswallet.databinding.ActivityComboLineColumnChartBinding;
        import com.github.mikephil.charting.animation.Easing;
        import com.github.mikephil.charting.components.Legend;
        import com.github.mikephil.charting.components.XAxis;
        import com.github.mikephil.charting.data.Entry;
        import com.github.mikephil.charting.data.LineData;
        import com.github.mikephil.charting.data.LineDataSet;
        import com.github.mikephil.charting.highlight.Highlight;
        import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
        import com.github.mikephil.charting.listener.ChartTouchListener;
        import com.github.mikephil.charting.listener.OnChartGestureListener;
        import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
        import com.google.gson.Gson;

        import java.util.ArrayList;
        import java.util.List;


        import com.chillarcards.campuswallet.application.CampusWallet;
        import com.chillarcards.campuswallet.application.Constants;
        import com.chillarcards.campuswallet.application.CustomConnectionBuddyActivity;
        import com.chillarcards.campuswallet.application.PrefManager;
        import com.chillarcards.campuswallet.networkmodels.ExamDetails.ExamlistCode;
        import com.chillarcards.campuswallet.networkmodels.ExamDetails.ExamlistDetails;
        import com.chillarcards.campuswallet.networkmodels.ExamDetails.ExamlistStatus;
        import com.chillarcards.campuswallet.utils.CommonSSLConnection;


@SuppressWarnings("ALL")
public class ComboLineColumnChartActivity extends CustomConnectionBuddyActivity implements OnChartGestureListener,
        OnChartValueSelectedListener {

    private final List<String> sub_name = new ArrayList<>();
    private float offset;
    private boolean flipped;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    PrefManager prefManager;

    private final List<String> examID = new ArrayList<>();
    private final List<String> examDate = new ArrayList<>();
    private final List<String> examName = new ArrayList<>();
    private final List<String> examTotalMark = new ArrayList<>();
    private final List<String> class_avg  = new ArrayList<>();
    private final List<String> student_score = new ArrayList<>();
    private final List<ExamlistCode> ExamlistCodeList = new ArrayList<>();

    String s_Id,parentPh,SubId;
    Activity activity;

    Button Reload;
    TextView Txt_Content;
    ImageView ErrorImage;
    private ExamDetailsAdapter mAdapter;

    Button ReloadBTN,GoBackBTN;
    TextView ErrorMessageTV,CodeErrorTV,NodataTV;
    final String tag_json_object = "r_exam_data";
    ActivityComboLineColumnChartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
      //  setContentView(R.layout.activity_combo_line_column_chart);

        binding = ActivityComboLineColumnChartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ReloadBTN = binding.getRoot().findViewById(R.id.ReloadBTN);
        ErrorMessageTV=binding.getRoot().findViewById(R.id.ErrorMessageTV);
        CodeErrorTV=binding.getRoot().findViewById(R.id.CodeErrorTV);
        GoBackBTN =binding.getRoot().findViewById(R.id.GoBackBTN);
        NodataTV =binding.getRoot().findViewById(R.id.NodataTV);
        
        binding.HeaderTV.setText(getResources().getString(R.string.results_header));
        binding.BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.progress.setVisibility(View.VISIBLE);
        binding.recyclerviewitemSRL.setColorSchemeResources(R.color.colorAccent);

        binding.linechart.setVisibility(View.GONE);
        binding.linechart.setOnChartGestureListener(this);
        binding.linechart.setOnChartValueSelectedListener(this);
        binding.linechart.setDrawGridBackground(false);


        // add data


        // get the legend (only possible after setting data)
        Legend l = binding.linechart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // no description text
        binding.linechart.setDescription(getResources().getString(R.string.result_chart));
        binding.linechart.setNoDataTextDescription(getResources().getString(R.string.provide_data_chart));

        // enable touch gestures
        binding.linechart.setTouchEnabled(true);

        // enable scaling and dragging
        binding.linechart.setDragEnabled(true);
        binding.linechart.setScaleEnabled(true);


        binding.linechart.getAxisRight().setEnabled(false);
        XAxis xAxis = binding.linechart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setSpaceBetweenLabels(0);

        xAxis.setXOffset(12);
        xAxis.setTextSize(10);
        xAxis.setDrawGridLines(false);
        binding.linechart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
        binding.linechart.invalidate();



    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            SubId = bundle.getString("SubId");
        }
        prefManager = new PrefManager(this);
        parentPh = prefManager.getUserPhone();  //getting phone number from shared preference
        s_Id = prefManager.getStudentId();
        ExamDetailsListsPHP();
        binding.recyclerviewitemSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ExamDetailsListsPHP();
                binding.recyclerviewitemSRL.setRefreshing(false);
            }
        });
    }

    private ArrayList<String> setXAxisValues(){

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("");
        for (int i=0;i<examName.size();i++){

            xVals.add(examName.get(i));

            //System.out.println("Count name : "+examName.size()+"     "+examName.get(i));

        }
        xVals.add("");

        //System.out.println("Count name1 : "+xVals.size());

        return xVals;
    }


    private ArrayList<Entry> setYAxisValues(){


        ArrayList<Entry> yVals = new ArrayList<Entry>();

        yVals.add(new Entry(Integer.parseInt(String.valueOf(0)), 0));
        for (int i=0;i<class_avg.size();i++){

            int finalValue = (int) (Math.round(Float.parseFloat(class_avg.get(i))));
//            yVals.add(new Entry(Integer.parseInt(String.valueOf(finalValue)), i+1));
            yVals.add(new Entry(calculatePercentage(Float.parseFloat(class_avg.get(i)),Float.parseFloat(examTotalMark.get(i))), i+1));
            //System.out.println("Count name2 : "+class_avg.size());
            //System.out.println("Count finalValue : "+finalValue);
        }
//        yVals.add(new Entry(Integer.parseInt(String.valueOf(0)), 0));
        //System.out.println("Count name3 : "+yVals.size());

        return yVals;
    }

    private ArrayList<Entry> setYAxisValues2(){
        ArrayList<Entry> y2Vals = new ArrayList<Entry>();

        y2Vals.add(new Entry(Float.parseFloat(String.valueOf(0)), 0));
        for (int i=0;i<student_score.size();i++){

            y2Vals.add(new Entry(calculatePercentage(Float.parseFloat(student_score.get(i)),(Float.parseFloat(examTotalMark.get(i)))), i+1));
            //System.out.println("Count name3 : "+student_score.size());
        }
//        y2Vals.add(new Entry(Integer.parseInt(String.valueOf(0)), 0));
        //System.out.println("Count name4 : "+y2Vals.size());
        return y2Vals;
    }


    private void setData() {
        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yVals = setYAxisValues();
        ArrayList<Entry> y2Vals = setYAxisValues2();

        LineDataSet set1,set2;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, getResources().getString(R.string.chart_class_avg));
        set2 = new LineDataSet(y2Vals, getString(R.string.chart_student_score));

//        set1.setFillAlpha(110);
        set2.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        //   set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(getResources().getColor(R.color.blue));
        set1.setCircleColor(getResources().getColor(R.color.blue));
        set1.setLineWidth(3f);
        set1.setCircleRadius(4f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(12f);
        set1.setDrawFilled(false);

        set2.setColor(getResources().getColor(R.color.duskYellow));
        set2.setCircleColor(getResources().getColor(R.color.duskYellow));
        set2.setLineWidth(3f);
        set2.setCircleRadius(4f);
        set2.setDrawCircleHole(false);
        set2.setValueTextSize(12f);
        set2.setDrawFilled(false);


        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets
        dataSets.add(set2); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        binding.linechart.setData(data);

    }


    @Override
    public void onChartGestureStart(MotionEvent me,
                                    ChartTouchListener.ChartGesture
                                            lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me,
                                  ChartTouchListener.ChartGesture
                                          lastPerformedGesture) {

        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            // or highlightTouch(null) for callback to onNothingSelected(...)
            binding.linechart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2,
                             float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: "
                + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + binding.linechart.getLowestVisibleXIndex()
                + ", high: " + binding.linechart.getHighestVisibleXIndex());

        Log.i("MIN MAX", "xmin: " + binding.linechart.getXChartMin()
                + ", xmax: " + binding.linechart.getXChartMax()
                + ", ymin: " + binding.linechart.getYChartMin()
                + ", ymax: " + binding.linechart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    public float calculatePercentage(float obtained, float total) {
        return obtained * 100 / total;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
    }
    @Override
    public void onBackPressed() {

       super.onBackPressed();
    }
    private void ExamDetailsListsPHP() {

        binding.progress.setVisibility(View.VISIBLE);
        parentPh = prefManager.getUserPhone();  //getting phone number from shared preference
        s_Id = prefManager.getStudentId();


        String URL, parameters;
        parameters = "phoneNo=" + parentPh + "&studentID=" + s_Id+"&standardSubjectID="+SubId;
        URL = Constants.BASE_URL  + "r_exam_data.php?" + parameters.replaceAll(" ", "%20");
        //System.out.println("CHECK---> URL " + URL);
        CommonSSLConnection commonSSLConnection=new CommonSSLConnection();
        StringRequest jsonObjectRequestLogin = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonObject) {
                binding.progress.setVisibility(View.GONE);
                //System.out.println("CHECK---> Response " + jsonObject);
                //findViewById(R.id.progressLayout).setVisibility(View.GONE);
                Gson gson = new Gson();

                ExamlistDetails model = gson.fromJson(jsonObject, ExamlistDetails.class);
                ExamlistStatus status = model.getStatus();
                String code = status.getCode();

                if (code.equals("200")) {
                    examID.clear();
                    examDate.clear();
                    examName.clear();
                    examTotalMark.clear();
                    class_avg.clear();
                    student_score.clear();
                    binding.linechart.setVisibility(View.VISIBLE);
                    List<ExamlistCode> relationship  = model.getCode();


                    if (relationship.size()>0) {
                        findViewById(R.id.NodataLL).setVisibility(View.GONE);
                        findViewById(R.id.ErrorLL).setVisibility(View.GONE);
                        for (int i = 0; i < relationship.size(); i++) {

                            ExamlistCode relncode = relationship.get(i);

                            examID.add(relncode.getExamID());
                            examDate.add(relncode.getExamDate());
                            examName.add(relncode.getExamName());
                            examTotalMark.add(relncode.getExamTotalMark());
                            class_avg.add(relncode.getClassAvg());
                            student_score.add(String.valueOf(relncode.getStudentScore()));
                            ExamlistCodeList.add(relncode);


                            //System.out.println("SHANIL4 : " + (relncode.getExamID()));
                            //System.out.println("SHANIL5 : " + (relncode.getExamDate()));
                            binding.progress.setVisibility(View.GONE);
                        }
                        setData();
                        if (examID.size() > 0) {
                            //System.out.println("SHANIL6 ");
                            //System.out.println("WorkingaDAPTER " + examID.size());
                            binding.recyclerviewitem.setVisibility(View.VISIBLE);
                            binding.recyclerviewitem.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                            binding.recyclerviewitem.setItemAnimator(new DefaultItemAnimator());
                            binding.recyclerviewitem.setNestedScrollingEnabled(false);
                            mAdapter = new ExamDetailsAdapter(examID, examDate, examName, examTotalMark,
                                    class_avg, student_score, activity, getApplicationContext(),mFirebaseAnalytics);
                            binding.recyclerviewitem.setAdapter(mAdapter);


                        }
                    }
                    else {

                        NodataTV.setText(getResources().getString(R.string.no_data_found));
                        GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                        findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                        GoBackBTN.setVisibility(View.GONE);
                    }
                    }else if(code.equals("400")){
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
                    }else if (code.equals("204")){
                    NodataTV.setText(getResources().getString(R.string.no_data_found));
                    GoBackBTN.setText(getResources().getString(R.string.go_back));
//                        ErrorImage.setBackgroundResource(R.drawable.nodata);
                    findViewById(R.id.NodataLL).setVisibility(View.VISIBLE);
                    GoBackBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                           onBackPressed();

                        }
                    });
                    }else if (status.equals("401")){

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

                    }else if (code.equals("500")){

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

                else {
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
                String message = status.getMessage();
                Toast.makeText(ComboLineColumnChartActivity.this, message, Toast.LENGTH_SHORT).show();
            }


        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            binding.progress.setVisibility(View.GONE);
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
                Toast.makeText(ComboLineColumnChartActivity.this, getResources().getString(R.string.network_error_try), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequestLogin.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(ComboLineColumnChartActivity.this));
        CampusWallet.getInstance().addToRequestQueue(jsonObjectRequestLogin, tag_json_object);
//        requestQueue.add(jsonObjectRequestLogin);

    }


}