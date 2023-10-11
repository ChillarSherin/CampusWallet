//package com.chillarcards.campuswallet.BusTracking;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.drawable.Drawable;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.SystemClock;
//
//import androidx.annotation.DrawableRes;
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.fragment.app.FragmentActivity;
//import androidx.core.content.ContextCompat;
//
//import android.util.Log;
//import android.view.View;
//import android.view.animation.BounceInterpolator;
//import android.view.animation.Interpolator;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.chillarcards.campuswallet.R;
//import com.directions.route.Route;
//import com.directions.route.RouteException;
//import com.directions.route.Routing;
//import com.directions.route.RoutingListener;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptor;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.model.Polyline;
//import com.google.android.gms.maps.model.PolylineOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//import java.util.StringTokenizer;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import com.chillarcards.campuswallet.application.CampusWallet;
//import com.chillarcards.campuswallet.application.Constants;
//import com.chillarcards.campuswallet.application.PrefManager;
//import com.chillarcards.campuswallet.utils.CommonSSLConnection;
//
//public class ParentMapsActivity extends FragmentActivity implements OnMapReadyCallback, RoutingListener, LocationListener {
//
//    private static final String TAG = "ParentMapsActivity";
//    private GoogleMap mMap;
//    private FirebaseAuth mAuth;
//    //Marker stopmark;
//    List<FirebaseReceive> receivedData;
//    private List<Polyline> polylines;
//    private List<LatLng> mWaypoints;
//    private List<LatLng> mRoutePoints;
//    private static final int[] COLORS = new int[]{R.color.red};
//    LatLng start, stop, waypoint;
//    LatLng end;
//    LocationManager locationManager;
//    MarkerOptions Soptions = new MarkerOptions();
//    MarkerOptions Eoptions = new MarkerOptions();
//    Marker marker, stopmark;
//    private RequestQueue mRequestQueue;
//    //    private StringRequest mStringRequest;
//    String parentPh, s_Id, Studentname, RouteID, BusID;
//    List<StopLocations> arrLocationData = new ArrayList<>();
//    TextView InRouteTV;
//    TextView InstopTV;
//    TextView InstopTimeTV;
//    TextView OutStopTV;
//    TextView OutStopTimeTV;
//    TextView OutRouteTV;
//    TextView backInstopTV;
//    TextView backInstopTimeTV;
//    TextView backOutStopTV;
//    TextView backOutStopTimeTV;
//    LinearLayout detailsSTOPLL;
//    Timer timer;
//    CustomTimerTask cTimer;
//
//    private ArrayList<LatLng> points; //added
//    Polyline line; //added
//    @BindView(R.id.HeaderTV)
//    TextView binding.HeaderTV;
//    @BindView(R.id.BackIV)
//    ImageView binding.BackIV;
//    PrefManager prefManager;
//    String phoneNum, studentId;
//    Activity activity;
//    final String tag_json_object = "r_route_stops";
//    final String tag_json_object_one = "r_studentBusStop";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.parrent_map_root);
//        ButterKnife.bind(this);
////        Fabric.with(this, new Answers());
//        activity = this;
//        prefManager = new PrefManager(ParentMapsActivity.this);
//        binding.HeaderTV.setText(getResources().getString(R.string.bustracking_header));
//        binding.BackIV.setVisibility(View.VISIBLE);
//        binding.BackIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//        points = new ArrayList<LatLng>(); //added
//
//
//        parentPh = prefManager.getUserPhone();
//        s_Id = prefManager.getStudentId();
//        Studentname = prefManager.getStudentName();
//        //studentsListVolley();
//        polylines = new ArrayList<>();
//        receivedData = new ArrayList<>();
//        receivedData.clear();
//        mWaypoints = new ArrayList<>();
//        mWaypoints.clear();
//        mRoutePoints = new ArrayList<>();
//        mRoutePoints.clear();
//        //cTimer=new CustomTimerTask(ParentMapsActivity.this);
//        if (ContextCompat.checkSelfPermission(getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getApplicationContext(),
//                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
//                            android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
//
//        }
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        getHandles();
//        mAuth = FirebaseAuth.getInstance();
//        anonimusUser();
//        // getMesages();
//    }
//
//    void getLocation() {
//        try {
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
////
//        SharedPreferences prefs = getSharedPreferences("Chillar", MODE_PRIVATE);
//        String startLongi = prefs.getString("start_longi", "");
//        String startLatti = prefs.getString("start_latti", "");
//        if (!startLatti.equals("") || !startLongi.equals("")) {
//            double startLattiD = Double.parseDouble(startLatti);
//            double startLongiD = Double.parseDouble(startLongi);
//
//            if (receivedData.size() != 0) {
//                for (int i = 0; i < receivedData.size(); i++) {
//                    String mylat = receivedData.get(i).getLattitude();
//                    String mylong = receivedData.get(i).getLongitude();
//                    if (!mylat.equals("") || mylong.equals("")) {
//                        double mstopLattiD = Double.parseDouble(mylat);
//                        double mstopLongiD = Double.parseDouble(mylong);
//                        mWaypoints.add(new LatLng(mstopLattiD, mstopLongiD));
//                    }
//
//                }
//                if (mWaypoints.size() > 1) {
//                    Routing routing = new Routing.Builder()
//                            .travelMode(Routing.TravelMode.DRIVING)
//                            .withListener(this)
//                            //  .alternativeRoutes(false)
//                            .waypoints(mWaypoints)
//                            .build();
//                    routing.execute();
//                }
//            }
//
//
//        }
//        //start = new LatLng(10.0065536, 76.3656098);
//
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        //updateUI(currentUser);
//    }
//
//    public void anonimusUser() {
//        mAuth.signInAnonymously()
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInAnonymously:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            // updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInAnonymously:failure", task.getException());
//                            //   Toast.makeText(ParentMapsActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
//                            // updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//    }
//
//    public void getMesages() {
//        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("BusLocation");
//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//
//                // receivedData.clear();
//                String Longi, Latti, Bus_ID, uID, Route;
//                if (dataSnapshot.child(BusID).hasChild("1")) {
//                    DataSnapshot messageSnapshot = dataSnapshot.child(BusID).child("1");
//                    Bus_ID = (String) messageSnapshot.child("Bus_ID").getValue();
//                    Longi = (String) messageSnapshot.child("Longitude").getValue();
//                    Latti = (String) messageSnapshot.child("Lattitude").getValue();
//                    Route = (String) messageSnapshot.child("RouteID").getValue();
//                    uID = (String) messageSnapshot.child("Uid").getValue();
//                    // RouteID=r
//                    prefManager.setStudentStartLatti(Latti);
//                    prefManager.setStudentStartLongi(Longi);
//                    //receivedData.add(new FirebaseReceive(uID,Longi,Latti,Bus_ID,Route));
//                }
//                if (dataSnapshot.child(BusID).hasChild("2")) {
//                    DataSnapshot messageSnapshot = dataSnapshot.child(BusID).child("2");
//                    Bus_ID = (String) messageSnapshot.child("Bus_ID").getValue();
//                    Longi = (String) messageSnapshot.child("Longitude").getValue();
//                    Latti = (String) messageSnapshot.child("Lattitude").getValue();
//                    Route = (String) messageSnapshot.child("RouteID").getValue();
//                    uID = (String) messageSnapshot.child("Uid").getValue();
//                    receivedData.add(new FirebaseReceive(uID, Longi, Latti, Bus_ID, Route));
//
////                    Toast.makeText(ParentMapsActivity.this, ""+Latti+" , "+Longi, Toast.LENGTH_SHORT).show();
//                    double latitude = Double.parseDouble(Latti);
//                    double longitude = Double.parseDouble(Longi);
//
//                    LatLng latLng = new LatLng(latitude, longitude); //you already have this
//
//                    points.add(latLng); //added
//
//
//                }
//                for (int i = 0; i < receivedData.size(); i++) {
//                    String mylat = receivedData.get(i).getLattitude();
//                    String mylong = receivedData.get(i).getLongitude();
//                    if (!mylat.equals("") || mylong.equals("")) {
//                        double mstopLattiD = Double.parseDouble(mylat);
//                        double mstopLongiD = Double.parseDouble(mylong);
//                        mWaypoints.add(new LatLng(mstopLattiD, mstopLongiD));
//                    }
//
//                }
//                if (mWaypoints.size() != 0) {
//                    final Handler handler = new Handler();
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            animateMarker(mWaypoints.get(mWaypoints.size() - 1), mWaypoints.get(mWaypoints.size() - 1), false);
//                        }
//                    });
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//
//    }
//
//
////    private void redrawLine(){
////
//////        mMap.clear();  //clears all Markers and Polylines
////
////        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
////        for (int i = 0; i < points.size(); i++) {
////            LatLng point = points.get(i);
////            options.add(point);
////        }
////
//////        addMarker(); //add Marker in current position
////
////        line = mMap.addPolyline(options); //add Polyline
////    }
//
//    public void DrawRute() {
//        //arrLocationData
//        if (arrLocationData.size() != 0) {
//            if (polylines.size() > 0) {
//                for (Polyline poly : polylines) {
//                    poly.remove();
//                }
//            }
//            for (int i = 0; i < arrLocationData.size(); i++) {
//                String startLongi = arrLocationData.get(i).getLongitude();
//                String startLatti = arrLocationData.get(i).getLatitude();
//                if ((!startLatti.equals("") || !startLongi.equals(""))
//                        && (!startLatti.equalsIgnoreCase("null") || !startLongi.equalsIgnoreCase("null"))) {
//
//                    String status = arrLocationData.get(i).getStatus();
//                    double startLattiD = Double.parseDouble(startLatti);
//                    double startLongiD = Double.parseDouble(startLongi);
//
//                    //System.out.println("DOUBLE Latti"+startLattiD);
//                    //System.out.println("DOUBLE Longi"+startLongiD);
//
//                    mRoutePoints.add(new LatLng(startLattiD, startLongiD));
////                if(!status.equals("1")){
////                    break;
////                }
//
//                }
//            }
//            //  mRoutePoints.add()
//
//            if (mRoutePoints.size() != 0) {
//                for (int i = 0; i < mRoutePoints.size(); i++) {
//                    if (i != 0) {
//                        if (i != mRoutePoints.size() - 1) {
//                            Routing routing = new Routing.Builder()
//                                    .travelMode(Routing.TravelMode.DRIVING)
//                                    .withListener(this)
//                                    .alternativeRoutes(false)
//                                    //.optimize(true)
//                                    //.waypoints(mWaypoints)
//                                    //.waypoints()
//                                    //.optimize(true)
//                                    //.travelMode(Routing.TravelMode.DRIVING)
//                                    .avoid(Routing.AvoidKind.FERRIES)
//                                    .waypoints(mRoutePoints.get(i), mRoutePoints.get(i + 1))
//                                    .build();
//                            routing.execute();
//                        }
//                    } else {
//                        if (mRoutePoints.size() > 1) {
//                            Routing routing = new Routing.Builder()
//                                    .travelMode(Routing.TravelMode.DRIVING)
//                                    .withListener(this)
//                                    .alternativeRoutes(false)
//                                    //.optimize(true)
//                                    //.waypoints(mWaypoints)
//                                    //.waypoints()
//                                    // .optimize(true)
//                                    //.travelMode(Routing.TravelMode.DRIVING)
//                                    .avoid(Routing.AvoidKind.FERRIES)
//                                    .waypoints(mRoutePoints.get(i), mRoutePoints.get(i + 1))
//                                    .build();
//                            routing.execute();
//                        }
//
//                    }
//                }
//            }
//
//
//        }
//
//    }
//
//    public void changeMap() {
//        SharedPreferences prefs = getSharedPreferences("Chillar", MODE_PRIVATE);
////    String startLongi = prefs.getString("start_longi", "");
////    String startLatti = prefs.getString("start_latti", "");
//
//        String startLongi = receivedData.get(0).getLattitude();
//        String startLatti = receivedData.get(0).getLongitude();
//
//        if (!startLatti.equals("") || !startLongi.equals("")) {
//            double startLattiD = Double.parseDouble(startLatti);
//            double startLongiD = Double.parseDouble(startLongi);
//            // start = new LatLng(startLattiD, startLongiD);
//
//            if (receivedData.size() != 0) {
//
//                for (int i = 0; i < receivedData.size(); i++) {
//                    String mylat = receivedData.get(i).getLattitude();
//                    String mylong = receivedData.get(i).getLongitude();
//                    if (!mylat.equals("") || mylong.equals("")) {
//                        double mstopLattiD = Double.parseDouble(mylat);
//                        double mstopLongiD = Double.parseDouble(mylong);
//                        mWaypoints.add(new LatLng(mstopLattiD, mstopLongiD));
//                    }
//
//                }
//                if (receivedData.size() > 10) {
//                    String MidLatti = receivedData.get(receivedData.size() - 5).getLattitude();
//                    String MidLongi = receivedData.get(receivedData.size() - 5).getLongitude();
//                    double midstopLattiD = Double.parseDouble(MidLatti);
//                    double midstopLongiD = Double.parseDouble(MidLongi);
//                    waypoint = new LatLng(midstopLattiD, midstopLongiD);
//                } else {
//                    waypoint = new LatLng(mWaypoints.get(mWaypoints.size() - 1).latitude, mWaypoints.get(mWaypoints.size() - 1).longitude);
//                }
//                if (mWaypoints.size() > 1) {
//                    Routing routing = new Routing.Builder()
//                            .travelMode(Routing.TravelMode.DRIVING)
//                            .withListener(this)
//                            //.alternativeRoutes(false)
//                            //.waypoints(mWaypoints)
//                            //.waypoints()
//                            .optimize(true)
//                            .waypoints(mWaypoints.get(0), waypoint, mWaypoints.get(mWaypoints.size() - 1))
//                            .build();
//                    routing.execute();
//                }
//
//            }
//
//
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
////        mMap.clear();
//        parentPh = prefManager.getUserPhone();
//        s_Id = prefManager.getStudentId();
//        Studentname = prefManager.getStudentName();
//
//
//        studentsListVolley();
//
//
//        RouteID = prefManager.getStudentRouteID();
//        BusID = prefManager.getStudentBusID();
//        getMesages();
//    }
//
//    @Override
//    public void onRoutingFailure(RouteException e) {
//        if (e != null) {
//            //     Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//        } else {
//            //      Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onRoutingStart() {
//        // mMap.clear();
//    }
//
//    public void animateMarker(final LatLng startPosition, final LatLng toPosition,
//                              final boolean hideMarker) {
//        if (marker != null) {
//            marker.remove();
//        }
//
//        marker = mMap.addMarker(new MarkerOptions()
//                .position(startPosition)
////                .title(mCarParcelableListCurrentLation.get(position).mCarName)
////                .snippet(mCarParcelableListCurrentLation.get(position).mAddress)
//                .icon(bitmapDescriptorFromVector(ParentMapsActivity.this, R.mipmap.ic_bus_tracking)));
//
//
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mWaypoints.get(mWaypoints.size() - 1), 16.0f));
//
//    }
//
//    @Override
//    public void onRoutingSuccess(ArrayList<Route> route, int shortIndex) {
//
//
//        if (polylines.size() > 0) {
//            for (Polyline poly : polylines) {
//                //  poly.remove();
//            }
//        }
//        //System.out.println("Routing Success Start");
//        polylines = new ArrayList<>();
//        //add route(s) to the map.
//        for (int i = 0; i < route.size(); i++) {
//
//            //In case of more than 5 alternative routes
//            int colorIndex = i % COLORS.length;
//
//            PolylineOptions polyOptions = new PolylineOptions();
//            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
//            polyOptions.width(10 + i * 3);
//            polyOptions.addAll(route.get(i).getPoints());
//            Polyline polyline = mMap.addPolyline(polyOptions);
//            polylines.add(polyline);
//
//            //System.out.println("Routing Success End");
//            // Toast.makeText(getApplicationContext(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
//        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
//        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
//        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        vectorDrawable.draw(canvas);
//        return BitmapDescriptorFactory.fromBitmap(bitmap);
//    }
//
//    @Override
//    public void onRoutingCancelled() {
//
//    }
//
//    public void eraseRoute() {
//        for (Polyline line : polylines) {
//            line.remove();
//        }
//        polylines.clear();
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        try {
//            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//            Constants.Lattitude = location.getLatitude() + "";
//            Constants.Longitude = location.getLongitude() + "";
//
//        } catch (Exception e) {
//
//        }
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//
//    }
//
//    public void getHandles() {
//        InRouteTV = findViewById(R.id.InRouteTV);
//        InstopTV = findViewById(R.id.InstopTV);
//        OutStopTV = findViewById(R.id.OutStopTV);
//        detailsSTOPLL = findViewById(R.id.detailsSTOPLL);
//
//
//    }
//
//    public void sethandles(InOutModel inout, int num) {
//        if (num == 2) {
//            StudentStopDetails inData = new StudentStopDetails();
//            StudentStopDetails outData = new StudentStopDetails();
//            inData = inout.getGoingIn();
//            outData = inout.getGoingOut();
//            InRouteTV.setText(inData.getRoute());
//            InstopTV.setText(inData.getEndtime() + "-" + inData.getEndstop() + "");
//            if (inData.getStartstop().equalsIgnoreCase("") || inData.getStartstop().equalsIgnoreCase("null")) {
//                OutStopTV.setText(inData.getStarttime());
//            } else {
//                OutStopTV.setText(inData.getStarttime() + "-" + inData.getStartstop() + "");
//            }
//
//        } else if (num == 1) {
//            StudentStopDetails inData = new StudentStopDetails();
//            inData = inout.getGoingIn();
//            InRouteTV.setText(inData.getRoute());
//            InstopTV.setText(inData.getStarttime() + "-" + inData.getStartstop() + "");
//            if (inData.getEndstop().equalsIgnoreCase("") || inData.getEndstop().equalsIgnoreCase("null")) {
//                OutStopTV.setText(inData.getEndtime());
//            } else {
//                OutStopTV.setText(inData.getEndtime() + "-" + inData.getEndstop() + "");
//            }
//        }
//
//    }
//
//    private void studentsListVolley() {
//
//        //RequestQueue initialized
//        // mRequestQueue = Volley.newRequestQueue(this);
//
////        String studentListurl = Constants.AppUrl_bus +"r_student_bus_stop.php?phoneNo="+parentPh+
////                "&studentID="+s_Id;
//        String studentListurl = Constants.BASE_URL + "r_studentBusStop.php?phoneNo=" + parentPh +
//                "&studentID=" + s_Id;
//        //String Request initialized
//        //System.out.println("StudentBusStop URL: "+studentListurl);
//        CommonSSLConnection commonSSLConnection = new CommonSSLConnection();
//        StringRequest mStringRequestOne = new StringRequest(Request.Method.GET, studentListurl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                //Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
//                ParseStudentsList(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                Log.i(TAG, "Error :" + error.toString());
//            }
//        });
////        mRequestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(ParentMapsActivity.this));
//        mStringRequestOne.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
////        mRequestQueue = Volley.newRequestQueue(this/*, commonSSLConnection.getHulkstack(ParentMapsActivity.this)*/);
////        CampusWallet.getInstance().addToRequestQueue(mStringRequest, tag_json_object);
////        mRequestQueue = Volley.newRequestQueue(this);
//        CampusWallet.getInstance().addToRequestQueue(mStringRequestOne, tag_json_object_one);
////        requestQueue.add(mStringRequest);
//
////        mRequestQueue.add(mStringRequest);
//    }
//
//    public void ParseStudentsList(String response) {
//        if (!response.equals("")) {
//            //System.out.println("StudentBusStop DATA : "+response);
//            try {
//                JSONObject dataObject = new JSONObject(response);
//                String status = dataObject.optString("status");
//                String code = dataObject.optString("code");
//
//                JSONObject statusObject = new JSONObject(status);
//                String scode = statusObject.optString("code");
//                String smessage = statusObject.optString("message");
//                if (scode.equals("200") && smessage.equals("success")) {
//                    JSONObject studentARR = new JSONObject(code);
//                    String In = studentARR.optString("instop");
//                    String Out = studentARR.optString("outstop");
//                    String data = studentARR.optString("data");
//
//
//                    JSONObject BusIdOBJ = new JSONObject(data);
//                    String ExactrouteID = BusIdOBJ.optString("routeID");
//                    String inbusID = BusIdOBJ.optString("busID");
//
//                    prefManager.setStudentBusID(inbusID);
//                    prefManager.setStudentRouteID(ExactrouteID);
//
//                    InOutModel inoutdata = new InOutModel();
//                    StudentStopDetails Indata;
//                    StudentStopDetails Outdata;
//                    String stopstatus = studentARR.optString("stopstatus");
//                    String stop_data = studentARR.optString("stop_data");
//                    if (stopstatus.equalsIgnoreCase("Absent")) {
//                        InRouteTV.setText(getResources().getString(R.string.waiting));
//                        detailsSTOPLL.setVisibility(View.GONE);
//                    } else {
//
//                        JSONArray InstopARR = new JSONArray(stop_data);
//                        if (InstopARR.length() != 0) {
//                            for (int i = 0; i < InstopARR.length(); i++) {
//                                JSONObject InstopOBJ = InstopARR.getJSONObject(i);
//                                String route = InstopOBJ.optString("route");
//                                String routeID = InstopOBJ.optString("routeID");
//                                String startstopID = InstopOBJ.optString("startstopID");
//                                String startstop = InstopOBJ.optString("startstop");
//                                String startlatitude = InstopOBJ.optString("startlatitude");
//                                String startlongitude = InstopOBJ.optString("startlongitude");
//                                String starttime = InstopOBJ.optString("starttime");
////                                String [] starttimesplit=starttime.split(" ");
////                                starttime=starttimesplit[1];
//                                if (starttime.equalsIgnoreCase("null") || starttime.equalsIgnoreCase("")) {
//                                    starttime = getResources().getString(R.string.waiting);
//                                    startstop = "";
//                                } else {
//                                    StringTokenizer tk = new StringTokenizer(starttime);
//
//                                    String date = tk.nextToken();  // <---  yyyy-mm-dd
//                                    String time = tk.nextToken();  // <---  hh:mm:ss
//                                    starttime = time;
//                                }
//                                String endstopID = InstopOBJ.optString("endstopID");
//                                String endstop = InstopOBJ.optString("endstop");
//                                String endlatitude = InstopOBJ.optString("endlatitude");
//                                String endlongitude = InstopOBJ.optString("endlongitude");
//                                String endtime = InstopOBJ.optString("endtime");
////                                String [] endtimesplit=starttime.split(" ");
////                                endtime=endtimesplit[1];
//                                if (endtime.equalsIgnoreCase("null") || endtime.equalsIgnoreCase("")) {
//                                    endtime = getResources().getString(R.string.waiting);
//                                    endstop = "";
//                                } else {
//                                    StringTokenizer tk1 = new StringTokenizer(endtime);
//
//                                    //System.out.println("END TIME : " + endtime);
//
//                                    String date1 = tk1.nextToken();  // <---  yyyy-mm-dd
//                                    String time1 = tk1.nextToken();  // <---  hh:mm:ss
//                                    endtime = time1;
//                                }
//
//
//                                if (stopstatus.equalsIgnoreCase("instop")) {
//                                    route = "To School";
//                                    Indata = new StudentStopDetails(true, route, routeID, startstopID, startstop, startlatitude,
//                                            startlongitude, starttime, endstopID, endstop, endlatitude, endlongitude, endtime);
//                                    inoutdata.setGoingIn(Indata);
//                                    detailsSTOPLL.setVisibility(View.VISIBLE);
//                                    sethandles(inoutdata, 1);
//                                } else {
//                                    route = "From School";
//                                    Indata = new StudentStopDetails(true, route, routeID, startstopID, startstop, startlatitude,
//                                            startlongitude, starttime, endstopID, endstop, endlatitude, endlongitude, endtime);
//                                    inoutdata.setGoingIn(Indata);
//                                    detailsSTOPLL.setVisibility(View.VISIBLE);
//                                    sethandles(inoutdata, 2);
//                                }
//
//
//                            }
//
//                        }
//
//                    }
//
//                    LocationsStudentsListVolley();
//                    //sethandles(inoutdata);
////
//                } else {
////                    ErrorImage.setBackgroundResource(R.drawable.server_issue);
////                    Txt_Content.setText(R.string.something_went_wrong);
////                    Reload.setText(R.string.GO_BACK);
////                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
////                    Reload.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////
////
////                            onBackPressed();
////
////                        }
////                    });
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
////                ErrorImage.setBackgroundResource(R.drawable.server_issue);
////                Txt_Content.setText(R.string.something_went_wrong);
////                Reload.setText(R.string.GO_BACK);
////                findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
////                Reload.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////
////
////                        onBackPressed();
////
////                    }
////                });
//            }
//        }
//    }
//
//    public void ParseStudentsListOLD(String response) {
//        if (!response.equals("")) {
//            //System.out.println("StudentBusStop DATA : "+response);
//            try {
//                JSONObject dataObject = new JSONObject(response);
//                String status = dataObject.optString("status");
//                String code = dataObject.optString("code");
//                JSONObject statusObject = new JSONObject(status);
//                String scode = statusObject.optString("code");
//                String smessage = statusObject.optString("message");
//                if (scode.equals("200") && smessage.equals("success")) {
//                    JSONObject studentARR = new JSONObject(code);
//                    String In = studentARR.optString("instop");
//                    String Out = studentARR.optString("outstop");
//                    String data = studentARR.optString("data");
//
//
//                    JSONObject BusIdOBJ = new JSONObject(data);
//                    String ExactrouteID = BusIdOBJ.optString("routeID");
//                    String inbusID = BusIdOBJ.optString("busID");
//
//                    prefManager.setStudentBusID(inbusID);
//                    prefManager.setStudentRouteID(ExactrouteID);
//
//
//                    InOutModel inoutdata = new InOutModel();
//                    StudentStopDetails Indata;
//                    StudentStopDetails Outdata;
//                    String stopstatus = studentARR.optString("stopstatus");
//                    String stop_data = studentARR.optString("stop_data");
//                    if (stopstatus.equalsIgnoreCase("Absent")) {
//
//                    }
//                    if (stopstatus.equalsIgnoreCase("instop")) {
//                        JSONObject InstopOBJ = new JSONObject(In);
//                        String route = InstopOBJ.optString("route");
//                        String routeID = InstopOBJ.optString("routeID");
//                        String startstopID = InstopOBJ.optString("startstopID");
//                        String startstop = InstopOBJ.optString("startstop");
//                        String startlatitude = InstopOBJ.optString("startlatitude");
//                        String startlongitude = InstopOBJ.optString("startlongitude");
//                        String starttime = InstopOBJ.optString("starttime");
//                        String endstopID = InstopOBJ.optString("endstopID");
//                        String endstop = InstopOBJ.optString("endstop");
//                        String endlatitude = InstopOBJ.optString("endlatitude");
//                        String endlongitude = InstopOBJ.optString("endlongitude");
//                        String endtime = InstopOBJ.optString("endtime");
//
//                        Indata = new StudentStopDetails(true, route, routeID, startstopID, startstop, startlatitude,
//                                startlongitude, starttime, endstopID, endstop, endlatitude, endlongitude, endtime);
//                        inoutdata.setGoingIn(Indata);
//                        sethandles(inoutdata, 1);
//                    }
//                    if (stopstatus.equalsIgnoreCase("outstop")) {
//                        JSONObject InstopOBJ = new JSONObject(In);
//                        JSONObject OutstopOBJ = new JSONObject(Out);
//
//                        String route = InstopOBJ.optString("route");
//                        String routeID = InstopOBJ.optString("routeID");
//                        String startstopID = InstopOBJ.optString("startstopID");
//                        String startstop = InstopOBJ.optString("startstop");
//                        String startlatitude = InstopOBJ.optString("startlatitude");
//                        String startlongitude = InstopOBJ.optString("startlongitude");
//                        String starttime = InstopOBJ.optString("starttime");
//                        String endstopID = InstopOBJ.optString("endstopID");
//                        String endstop = InstopOBJ.optString("endstop");
//                        String endlatitude = InstopOBJ.optString("endlatitude");
//                        String endlongitude = InstopOBJ.optString("endlongitude");
//                        String endtime = InstopOBJ.optString("endtime");
//
//                        Indata = new StudentStopDetails(true, route, routeID, startstopID, startstop, startlatitude,
//                                startlongitude, starttime, endstopID, endstop, endlatitude, endlongitude, endtime);
//
//                        String Eroute = OutstopOBJ.optString("route");
//                        String ErouteID = OutstopOBJ.optString("routeID");
//                        String EstartstopID = OutstopOBJ.optString("startstopID");
//                        String Estartstop = OutstopOBJ.optString("startstop");
//                        String Estartlatitude = OutstopOBJ.optString("startlatitude");
//                        String Estartlongitude = OutstopOBJ.optString("startlongitude");
//                        String Estarttime = OutstopOBJ.optString("starttime");
//                        String EendstopID = OutstopOBJ.optString("endstopID");
//                        String Eendstop = OutstopOBJ.optString("endstop");
//                        String Eendlatitude = OutstopOBJ.optString("endlatitude");
//                        String Eendlongitude = OutstopOBJ.optString("endlongitude");
//                        String Eendtime = OutstopOBJ.optString("endtime");
//
//                        Outdata = new StudentStopDetails(false, Eroute, ErouteID, EstartstopID, Estartstop, Estartlatitude,
//                                Estartlongitude, Estarttime, EendstopID, Eendstop, Eendlatitude, Eendlongitude, Eendtime);
//                        inoutdata.setGoingIn(Indata);
//                        inoutdata.setGoingOut(Outdata);
//                        sethandles(inoutdata, 2);
//                    }
//
////
//                } else {
////                    ErrorImage.setBackgroundResource(R.drawable.server_issue);
////                    Txt_Content.setText(R.string.something_went_wrong);
////                    Reload.setText(R.string.GO_BACK);
////                    findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
////                    Reload.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////
////
////                            onBackPressed();
////
////                        }
////                    });
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
////                ErrorImage.setBackgroundResource(R.drawable.server_issue);
////                Txt_Content.setText(R.string.something_went_wrong);
////                Reload.setText(R.string.GO_BACK);
////                findViewById(R.id.ErrorLayoutnew).setVisibility(View.VISIBLE);
////                Reload.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////
////
////                        onBackPressed();
////
////                    }
////                });
//            }
//        }
//    }
//
//    private void LocationsStudentsListVolley() {
//
//        //RequestQueue initialized
//        //  mRequestQueue = Volley.newRequestQueue(this);
//
//        RouteID = prefManager.getStudentRouteID();
//
//        String studentListurl = Constants.BASE_URL + "r_route_stops.php?phoneNo=" + parentPh +
//                "&studentID=" + s_Id +
//                "&schoolBusRouteID=" + RouteID;
//        //System.out.println("StopLocations : "+studentListurl);
//        CommonSSLConnection commonSSLConnection = new CommonSSLConnection();
//        //String Request initialized
//        StringRequest mStringRequest = new StringRequest(Request.Method.GET, studentListurl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                //Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
//                ParseLocationStudentsList(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                Log.i(TAG, "Error :" + error.toString());
//            }
//        });
////        mRequestQueue = Volley.newRequestQueue(this, commonSSLConnection.getHulkstack(ParentMapsActivity.this));
//        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
////        mRequestQueue = Volley.newRequestQueue(this/*, commonSSLConnection.getHulkstack(ParentMapsActivity.this)*/);
//        CampusWallet.getInstance().addToRequestQueue(mStringRequest, tag_json_object);
////        requestQueue.add(jsonObjectRequestLogin);
////        mRequestQueue.add(mStringRequest);
//    }
//
//    public void ParseLocationStudentsList(String response) {
//        if (!response.equals("")) {
//            //System.out.println("StopLocations : "+response);
//            try {
//                JSONObject dataObject = new JSONObject(response);
//                String status = dataObject.optString("status");
//                String code = dataObject.optString("code");
//                JSONObject statusObject = new JSONObject(status);
//                String scode = statusObject.optString("code");
//                String smessage = statusObject.optString("message");
//                if (scode.equals("200") && smessage.equals("success")) {
//                    arrLocationData.clear();
//                    JSONArray studentARR = new JSONArray(code);
//                    if (stopmark != null) {
//                        stopmark.remove();
//                    }
//                    for (int i = 0; i < studentARR.length(); i++) {
////                        StopLocations studentData=new StopLocations();
//                        JSONObject studentObject = studentARR.getJSONObject(i);
//                        String route = studentObject.optString("route");
//                        String routeID = studentObject.optString("routeID");
//                        String stopID = studentObject.optString("stopID");
//                        String stop = studentObject.optString("stop");
//                        String inOrOut = studentObject.optString("inOrOut");
//                        String latitude = studentObject.optString("latitude");
//                        String longitude = studentObject.optString("longitude");
//                        String busNo = studentObject.optString("busNo");
//                        String status1 = studentObject.optString("status");
//
//                        int pin;
//
//
//                        arrLocationData.add(new StopLocations(route, routeID, stopID, stop, inOrOut, latitude, longitude, busNo, status1));
//                        if (!latitude.equals("") || !longitude.equals("")) {
//                            if (!latitude.equalsIgnoreCase("null") || !longitude.equalsIgnoreCase("null")) {
//                                // Creating an instance of MarkerOptions
//                                MarkerOptions markerOptions = new MarkerOptions();
//
//// Setting latitude and longitude for the marker
//
//                                if (!status1.equals("1")) {
//
//                                    pin = R.drawable.mylocpins;
//
//                                } else {
//                                    pin = R.drawable.house;
//                                }
//
//                                markerOptions.position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)))
//                                        .title(stop).visible(true).icon(bitmapDescriptorFromVector(ParentMapsActivity.this, pin));
//
//
//                                mMap.addMarker(markerOptions).showInfoWindow();
//                            }
//                        }
//                    }
//
//                    DrawRute();
//
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
////
//            }
//        }
//    }
//
//
//    class CustomTimerTask extends TimerTask {
//        private Context context;
//        private Handler mHandler = new Handler();
//
//        // Write Custom Constructor to pass Context
//        public CustomTimerTask(Context con) {
//            this.context = con;
//        }
//
//        @Override
//        public void run() {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            final Handler handler = new Handler();
//                            final long start = SystemClock.uptimeMillis();
//                            final long duration = 5000;
//
//                            final Interpolator interpolator = new BounceInterpolator();
//
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    long elapsed = SystemClock.uptimeMillis() - start;
//                                    float t = Math.max(
//                                            1 - interpolator.getInterpolation((float) elapsed
//                                                    / duration), 0);
//                                    marker.setAnchor(0.5f, 1.0f + 2 * t);
//
//                                    if (t > 0.0) {
//                                        // Post again 16ms later.
//                                        handler.postDelayed(this, 16);
//                                    }
//                                }
//                            });
//                        }
//                    });
//                }
//            }).start();
//
//        }
//
//    }
//
//
//    private String getDirectionsUrl(LatLng origin, LatLng dest) {
//
//        // Origin of route
//        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
//
//        // Destination of route
//        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
//
//        // Sensor enabled
//        String sensor = "sensor=false";
//        String mode = "mode=driving";
//        String key = "key=API_KEY";
//        // Building the parameters to the web service
//        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" + key;
//
//        // Output format
//        String output = "json";
//
//        // Building the url to the web service
//        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
//        Log.w("ParentMapActivity", url);
//
//
//        return url;
//    }
//
//    /**
//     * A method to download json data from url
//     */
//    private String downloadUrl(String strUrl) throws IOException {
//        String data = "";
//        InputStream iStream = null;
//        HttpURLConnection urlConnection = null;
//        try {
//            URL url = new URL(strUrl);
//
//            urlConnection = (HttpURLConnection) url.openConnection();
//
//            urlConnection.connect();
//
//            iStream = urlConnection.getInputStream();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
//
//            StringBuffer sb = new StringBuffer();
//
//            String line = "";
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//
//            data = sb.toString();
//
//            br.close();
//
//        } catch (Exception e) {
//            Log.d("Exception", e.toString());
//        } finally {
//            iStream.close();
//            urlConnection.disconnect();
//        }
//        return data;
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        CampusWallet.getInstance().cancelPendingRequests(tag_json_object);
//        CampusWallet.getInstance().cancelPendingRequests(tag_json_object_one);
//    }
//}
