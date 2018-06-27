package com.live.ubu.user.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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
import com.live.ubu.user.R;



public class RoutePathActivity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap map;
    private String TAG = "MY_APP";

    final LatLng UBU = new LatLng(15.118404, 104.899381);
    final LatLng P1 = new LatLng(15.128788, 104.897316);
    final LatLng P2 = new LatLng(15.135073, 104.895260);
    final LatLng P3 = new LatLng(15.137149, 104.895837);
    final LatLng P4 = new LatLng(15.142015, 104.894037);
    final LatLng P5 = new LatLng(15.147956, 104.892791);
    final LatLng P6 = new LatLng(15.167488, 104.881884);
    final LatLng P7 = new LatLng(15.171003, 104.877723);
    final LatLng P8 = new LatLng(15.174156, 104.872858);
    final LatLng P9 = new LatLng(15.182141, 104.869295);
    final LatLng P10 = new LatLng(15.187567, 104.867965);
    final LatLng P11 = new LatLng(15.192143, 104.867320);
    final LatLng P12 = new LatLng(15.193572, 104.866258);
    final LatLng P13 = new LatLng(15.195353, 104.865357);
    final LatLng P14 = new LatLng(15.196109, 104.865346);
    final LatLng P15 = new LatLng(15.198542, 104.862814);
    final LatLng P16 = new LatLng(15.198925, 104.861409);
    final LatLng P17 = new LatLng(15.201865, 104.862160);
    final LatLng P18 = new LatLng(15.204691, 104.863769);
    final LatLng P19 = new LatLng(15.205737, 104.864080);
    final LatLng P20 = new LatLng(15.209650, 104.863629);
    final LatLng P21 = new LatLng(15.215895, 104.857873);
    final LatLng P22 = new LatLng(15.217831, 104.857186);
    final LatLng P23 = new LatLng(15.225065, 104.857407);
    final LatLng P24 = new LatLng(15.227156, 104.856989);
    final LatLng P25 = new LatLng(15.228864, 104.856453);
    final LatLng P26 = new LatLng(15.231493, 104.855906);
    final LatLng P27 = new LatLng(15.231507, 104.854966);
    final LatLng P28 = new LatLng(15.231884, 104.851720);
    final LatLng P29 = new LatLng(15.232022, 104.849926);
    final LatLng P30 = new LatLng(15.232555, 104.846609);
    final LatLng P31 = new LatLng(15.233790, 104.846729);
    final LatLng P32 = new LatLng(15.244763, 104.847501);
    final LatLng P33 = new LatLng(15.250225, 104.840792);
    final LatLng P34 = new LatLng(15.250065, 104.840667);
    final LatLng P35 = new LatLng(15.254416, 104.829295);
    final LatLng P36 = new LatLng(15.242158, 104.822060);
    final LatLng P37 = new LatLng(15.239763, 104.821636);
    final LatLng P38 = new LatLng(15.234815, 104.822280);
    final LatLng P39 = new LatLng(15.233417, 104.822162);
    final LatLng P40 = new LatLng(15.224963, 104.819759);
    final LatLng P41 = new LatLng(15.218589, 104.816502);
    final LatLng P42 = new LatLng(15.216156, 104.814206);
    final LatLng P43 = new LatLng(15.213837, 104.812908);
    final LatLng P44 = new LatLng(15.212760, 104.812908);
    final LatLng P45 = new LatLng(15.209520, 104.813788);
    final LatLng P46 = new LatLng(15.196440, 104.813601);
    final LatLng P47 = new LatLng(15.194628, 104.814320);
    final LatLng P48 = new LatLng(15.190197, 104.818740);
    final LatLng P49 = new LatLng(15.178595, 104.830827);
    final LatLng P50 = new LatLng(15.177663, 104.832436);
    final LatLng P51 = new LatLng(15.175515, 104.857407);
    final LatLng P52 = new LatLng(15.174546, 104.872048);
    final LatLng P53 = new LatLng(15.174631, 104.872607);
    final LatLng P54 = new LatLng(15.122723, 104.910518);
    final LatLng P55 = new LatLng(15.122056, 104.909790);
    final LatLng P56 = new LatLng(15.120772, 104.911099);
    final LatLng P57 = new LatLng(15.120316, 104.911410);
    final LatLng P58 = new LatLng(15.119891, 104.911453);
    final LatLng P59 = new LatLng(15.119932, 104.908642);
    final LatLng P60 = new LatLng(15.119828, 104.908342);
    final LatLng P61 = new LatLng(15.119849, 104.906025);
    final LatLng P62 = new LatLng(15.120255, 104.905900);
    final LatLng P63 = new LatLng(15.120306, 104.905471);
    final LatLng P64 = new LatLng(15.120539, 104.905271);
    final LatLng P65 = new LatLng(15.120487, 104.902369);
    final LatLng P66 = new LatLng(15.119073, 104.901516);
    final LatLng P67 = new LatLng(15.119073, 104.901516);
    final LatLng P68 = new LatLng(15.118690, 104.900899);
    final LatLng P69 = new LatLng(15.117576, 104.901106);
    final LatLng P70 = new LatLng(15.117597, 104.901730);
    final LatLng P71 = new LatLng(15.117361, 104.902156);
    final LatLng P72 = new LatLng(15.117340, 104.902679);
    final LatLng P73 = new LatLng(15.117128, 104.902891);
    final LatLng P74 = new LatLng(15.116649, 104.902918);

    //Bus Stops
    final LatLng S1 = new LatLng(15.122288, 104.911787); // งานยานพาหนะ
    final LatLng S2 = new LatLng(15.119896, 104.909664); // Rx
    final LatLng S3 = new LatLng(15.119834, 104.906896); // CLB5
    final LatLng S4 = new LatLng(15.119818, 104.906124); // โรงอาหารกลาง 2
    final LatLng S5 = new LatLng(15.120253, 104.905706); // โรงอาหารกลาง 1 , Law
    final LatLng S6 = new LatLng(15.120496, 104.904697); // En
    final LatLng S7 = new LatLng(15.117319, 104.902736); // ตึดอธิการหลังเก่า
    final LatLng S8 = new LatLng(15.118507, 104.900262); // หน้า ม.
    final LatLng S9 = new LatLng(15.173309, 104.873433); // ตลาดเจริญศรี
    final LatLng S10 = new LatLng(15.198998, 104.861342); // สถานีรถไฟ
    final LatLng S11 = new LatLng(15.229184, 104.856317); // ทุ่งศรีเมือง
    final LatLng S12 = new LatLng(15.244406, 104.847434); // SK

    private Polyline polyline;
    private Marker ms1, ms2, ms3, ms4, ms5, ms6, ms7, ms8, ms9, ms10, ms11, ms12;
    private BottomSheetDialog bottomSheetDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        View bottomSheetView = getLayoutInflater().inflate(R.layout.maker_bottom_sheet, null);
        bottomSheetDialog = new BottomSheetDialog(RoutePathActivity.this);
        bottomSheetDialog.setContentView(bottomSheetView);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(false);
        LatLng latLng = new LatLng(15.174743, 104.872518);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        polyline = map.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        UBU,
                        P1,
                        P2,
                        P3,
                        P4,
                        P5,
                        P6,
                        P7,
                        P8,
                        P9,
                        P10,
                        P11,
                        P12,
                        P13,
                        P14,
                        P15,
                        P16,
                        P17,
                        P18,
                        P19,
                        P20,
                        P21,
                        P22,
                        P23,
                        P24,
                        P25,
                        P26,
                        P27,
                        P28,
                        P29,
                        P30,
                        P31,
                        P32,
                        P33,
                        P34,
                        P35,
                        P36,
                        P37,
                        P38,
                        P39,
                        P40,
                        P41,
                        P42,
                        P43,
                        P44,
                        P45,
                        P46,
                        P47,
                        P48,
                        P49,
                        P50,
                        P51,
                        P52,
                        P53,
                        P54,
                        P55,
                        P56,
                        P57,
                        P58,
                        P59,
                        P60,
                        P61,
                        P62,
                        P63,
                        P64,
                        P65,
                        P66,
                        P67,
                        P68,
                        P69,
                        P70,
                        P71,
                        P72,
                        P73,
                        P74,
                        P70,
                        P68,
                        UBU));
        polyline.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        polyline.setJointType(JointType.ROUND);
        polyline.setEndCap(new RoundCap());
        polyline.setStartCap(new RoundCap());

        // Bus Stops
        ms1 = map.addMarker(new MarkerOptions().position(S1).title("งานยานพาหนะ").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop)));
        ms2 = map.addMarker(new MarkerOptions().position(S2).title("คณะเภสัชศาสตร์").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop)));
        ms3 = map.addMarker(new MarkerOptions().position(S3).title("อาคารเรียนรวม 5").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop)));
        ms4 = map.addMarker(new MarkerOptions().position(S4).title("โรงอาหารกลาง 2").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop)));
        ms5 = map.addMarker(new MarkerOptions().position(S5).title("โรงอาหารกลาง 1 , คณะนิติศาสตร์").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop)));
        ms6 = map.addMarker(new MarkerOptions().position(S6).title("คณะวิศวกรรมศาสตร์").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop)));
        ms7 = map.addMarker(new MarkerOptions().position(S7).title("ตึกอธิการบดีหลังเก่า").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop)));
        ms8 = map.addMarker(new MarkerOptions().position(S8).title("หน้า ม.").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop)));
        ms9 = map.addMarker(new MarkerOptions().position(S9).title("ตลาดเจริญศรี").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop)));
        ms10 = map.addMarker(new MarkerOptions().position(S10).title("สถานีรถไฟ").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop)));
        ms11 = map.addMarker(new MarkerOptions().position(S11).title("ทุ่งศรีเมือง").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop)));
        ms12 = map.addMarker(new MarkerOptions().position(S12).title("ยิ่งเจริญปาร์ค (SK)").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop)));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(!item.isChecked());
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_polyline_check) {
            if (item.isChecked()) {
                polyline.setVisible(true);
            } else {
                polyline.setVisible(false);
            }
            return true;
        } else if (id == R.id.action_bus_stop_check) {
            if (item.isChecked()) {
                ms1.setVisible(true);
                ms2.setVisible(true);
                ms3.setVisible(true);
                ms4.setVisible(true);
                ms5.setVisible(true);
                ms6.setVisible(true);
                ms7.setVisible(true);
                ms8.setVisible(true);
                ms9.setVisible(true);
                ms10.setVisible(true);
                ms11.setVisible(true);
                ms12.setVisible(true);
            } else {
                ms1.setVisible(false);
                ms2.setVisible(false);
                ms3.setVisible(false);
                ms4.setVisible(false);
                ms5.setVisible(false);
                ms6.setVisible(false);
                ms7.setVisible(false);
                ms8.setVisible(false);
                ms9.setVisible(false);
                ms10.setVisible(false);
                ms11.setVisible(false);
                ms12.setVisible(false);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showSheet(View view) {
        bottomSheetDialog.show();
    }

}
