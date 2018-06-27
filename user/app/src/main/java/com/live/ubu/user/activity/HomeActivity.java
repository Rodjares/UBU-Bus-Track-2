package com.live.ubu.user.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.live.ubu.user.R;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private boolean chk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_sign_out) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                            finish();
                        }
                    });
            return true;
        }else if(id == R.id.menu_about){
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToMainMap(View view) {
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
    }

    public void goToRoutePath(View view) {
//        startActivity(new Intent(HomeActivity.this, RoutePathActivity.class));
        String[] imgs= {
                "https://firebasestorage.googleapis.com/v0/b/ubu-bus.appspot.com/o/1.jpg?alt=media&token=9cc503cd-fb10-475f-a863-4593538cea70",
                "https://firebasestorage.googleapis.com/v0/b/ubu-bus.appspot.com/o/2.jpg?alt=media&token=4fc5cc85-374c-46b1-bf85-749adf6511de"
        };

        View v = View.inflate(this,R.layout.image_overlay,null);
        final TextView tvIndicator = v.findViewById(R.id.tvIndicator);

        chk = false;
        ImageViewer.OnImageChangeListener listener = new ImageViewer.OnImageChangeListener() {
            @Override
            public void onImageChange(int position) {
                if(tvIndicator.getText().equals("1/2") && chk){
                    tvIndicator.setText("2/2");
                }else {
                    tvIndicator.setText("1/2");
                }
                chk = true;
            }
        };

        new ImageViewer.Builder<>(this, imgs)
                .setImageChangeListener(listener)
                .setOverlayView(v)
                .show();
    }

    public void goToDriver(View view) {
        startActivity(new Intent(HomeActivity.this, DriverActivity.class));
    }

    public void goToVehicle(View view) {
        startActivity(new Intent(HomeActivity.this, VehicleActivity.class));
    }

}
