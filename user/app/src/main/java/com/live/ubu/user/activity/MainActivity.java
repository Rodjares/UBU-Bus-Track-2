package com.live.ubu.user.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.Language;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.live.ubu.user.R;
import com.live.ubu.user.model.MyLocation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.refactor.lib.colordialog.PromptDialog;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ChildEventListener {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth auth;
    private Marker driverMarker, userMarker;
    private GoogleMap map;
    private float zoomLevel = 15;
    private String TAG = "MY_APP";
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng driverCurrentLocation;
    private LatLng userCurrentLocation;

    private BottomSheetDialog bottomSheetDialog;
    private TextView tvDistance;
    private TextView tvTime;
    private Button btnCheckIN;
    private DatabaseReference myRef2;
    private String distance;
    private String duration;
    private String summary;
    private boolean chk = false;
    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (!checkLocationPermission()) {
            requestLocationPermission();
        } else {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();

            mGoogleApiClient.connect();

            // Call Location Services
            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!enabled) {
                // call to enable gps
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }

            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            LocationCallback mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    Log.d(TAG, "onLocationResult: " + locationResult.toString());
                    onNewLocation(locationResult.getLastLocation());
                }
            };

            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, Looper.myLooper());

        }

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Location");
//        myRef.removeValue();
        myRef2 = database.getReference("UserCheckIn");
        myRef.addChildEventListener(this);

        final View bottomSheetView = getLayoutInflater().inflate(R.layout.maker_bottom_sheet, null);
        bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
        bottomSheetDialog.setContentView(bottomSheetView);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        tvDistance = bottomSheetView.findViewById(R.id.tv_bottom_sheet_distance);
        tvTime = bottomSheetView.findViewById(R.id.tv_bottom_sheet_time);
        btnCheckIN = bottomSheetView.findViewById(R.id.btn_bottom_sheet_check_in);
        btnCheckIN.setEnabled(false);
        btnCheckIN.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_light));
        btnCheckIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Check in", Toast.LENGTH_SHORT).show();
//                bottomSheetDialog.hide();

                if (btnCheckIN.getText().toString().equalsIgnoreCase("เช็คอิน")) {
                    btnCheckIN.setText("เช็คเอาท์");
                } else {
                    btnCheckIN.setText("เช็คอิน");
                }

                final PromptDialog dialog = new PromptDialog(MainActivity.this)
                        .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                        .setAnimationEnable(true)
                        .setTitleText(getString(R.string.fail))
                        .setPositiveListener(getString(R.string.ok), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                                bottomSheetDialog.hide();
                            }
                        });
                if (auth.getCurrentUser() != null) {
                    String userName = auth.getCurrentUser().getDisplayName();
                    Map<String, Object> map = new HashMap<>();
                    map.put("Time", System.currentTimeMillis());
                    map.put("UserName", userName);
                    map.put("Status", btnCheckIN.getText().toString());
                    map.put("UserUid", auth.getCurrentUser().getUid());
                    map.put("DriverLocation", driverCurrentLocation);
                    map.put("UserLocation", userCurrentLocation);
                    map.put("Distance", distance);
                    map.put("Duration", duration);
                    map.put("Summary", summary);
                    myRef2.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                                    .setTitleText(getString(R.string.success)).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                                    .setTitleText(getString(R.string.fail)).show();
                        }
                    });


                }
            }
        });
    }

    public void onNewLocation(Location location) {
        userCurrentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        if (userMarker == null) {
            userMarker = map.addMarker(new MarkerOptions()
                    .position(userCurrentLocation));
        } else {
            zoomLevel = map.getCameraPosition().zoom;
        }

        userMarker.setPosition(userCurrentLocation);
        userMarker.setTitle("Your Current Location");
        userMarker.setTag("user");
        map.moveCamera(CameraUpdateFactory.newLatLng(userCurrentLocation));
        map.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel));

        direction();
    }

    private void direction() {
        if (driverCurrentLocation != null && userCurrentLocation != null) {
            String serverKey = getString(R.string.google_maps_key);
            LatLng origin = driverCurrentLocation;
            LatLng destination = userCurrentLocation;
            GoogleDirection.withServerKey(serverKey)
                    .from(origin)
                    .to(destination)
                    .language(Language.THAI)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            // Do something here
                            if (direction.isOK()) {
                                Route route = direction.getRouteList().get(0);
                                summary = route.getSummary();
                                Leg leg = route.getLegList().get(0);
                                Info distanceInfo = leg.getDistance();
                                Info durationInfo = leg.getDuration();
                                distance = "ระยะทาง " + distanceInfo.getText();
                                duration = "ระยะเวลา " + durationInfo.getText();
                                tvDistance.setText(distance);
                                tvTime.setText(duration);

                                if (Integer.parseInt(distanceInfo.getValue()) <= 50 && driverCurrentLocation != null && userCurrentLocation != null) {
                                    btnCheckIN.setEnabled(true);
                                    btnCheckIN.setBackgroundColor(getResources().getColor(R.color.green));
                                } else {
                                    btnCheckIN.setEnabled(false);
                                    btnCheckIN.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_light));
                                }

//                                Toast.makeText(MainActivity.this, "distance: "+distance+"\nduration: "+duration, Toast.LENGTH_SHORT).show();
                                driverMarker.setTitle("distance: " + distance + " duration: " + duration);


                                if (auth.getCurrentUser() != null
                                        && btnCheckIN.getText().toString().equalsIgnoreCase("เช็คเอาท์")
                                        && userMarker != null
                                        && driverMarker != null
                                        ) {
                                    String userName = auth.getCurrentUser().getDisplayName();
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("Time", System.currentTimeMillis());
                                    map.put("UserName", userName);
                                    map.put("Status", btnCheckIN.getText().toString());
                                    map.put("UserUid", auth.getCurrentUser().getUid());
                                    map.put("DriverLocation", driverCurrentLocation);
                                    map.put("UserLocation", userCurrentLocation);
                                    map.put("Distance", distance);
                                    map.put("Duration", duration);
                                    map.put("Summary", summary);
                                    myRef2.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                          //complete
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                                }


                            } else {
                                //  Toast.makeText(MainActivity.this, direction.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onDirectionSuccess: " + direction.getErrorMessage());
                            }
                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {
                            // Do something here
                            Log.d(TAG, "onDirectionFailure: " + t.getLocalizedMessage());

                        }
                    });
        }
    }


    private void requestLocationPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()){
                    finish();
                    startActivity(getIntent());
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();
    }


    public boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(false);
        LatLng latLng = new LatLng(15.174743, 104.872518);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
            return;
        }
        map.setMyLocationEnabled(true);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.d(TAG, "onChildAdded: " + dataSnapshot);
        for (DataSnapshot d : dataSnapshot.getChildren()) {
            if (d.getKey().equalsIgnoreCase("driverlocation")) {
                MyLocation myLocation = d.getValue(MyLocation.class);
                if (myLocation != null) {
                    double la = Double.parseDouble(myLocation.getLatitude());
                    double lo = Double.parseDouble(myLocation.getLongitude());
                    driverCurrentLocation = new LatLng(la, lo);
                }
            }
        }


        if (driverMarker == null) {
            driverMarker = map.addMarker(new MarkerOptions()
                    .position(driverCurrentLocation)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name)));
        }

        driverMarker.setPosition(driverCurrentLocation);
        driverMarker.setTitle("Driver Current Location");
        driverMarker.setTag("driver");
        direction();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + databaseError.getMessage());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(!item.isChecked());
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showSheet(View view) {
        bottomSheetDialog.show();
    }

}
