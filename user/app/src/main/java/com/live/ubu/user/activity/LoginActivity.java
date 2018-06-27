package com.live.ubu.user.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.live.ubu.user.R;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 4321;
    private final String TAG = "My_APP";
    private RelativeLayout coordinatorLayout;
    private FirebaseAuth auth;
    private ProgressBar simpleProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        simpleProgressBar = findViewById(R.id.simpleProgressBar);


        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            goToMainActivity();
        } else {
            // not signed in
            showSnackbar(R.string.not_singin_yet);
        }
    }

    public void goToMainActivity() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    public void goToSigning(View view) {
        simpleProgressBar.setVisibility(View.VISIBLE);
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build()
                        )).build(),
                RC_SIGN_IN);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            // Successfully signed in
            if (resultCode == RESULT_OK) {
                if (auth.getCurrentUser().getEmail() != null && auth.getCurrentUser().getEmail().contains("@ubu.ac.th")) {
                    requestLocationPermission();
                } else {
                    //delete account bs current user not member of ubu
                    AuthUI.getInstance()
                            .delete(this)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        showSnackbar(R.string.current_user_not_member_of_ubu);
                                    } else {
                                        // Deletion failed
                                    }
                                }
                            });
                }
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.no_internet_connection);
                    return;
                }

                showSnackbar(R.string.unknown_error);
                Log.e(TAG, "Sign-in error: ", response.getError());
            }

            simpleProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void showSnackbar(int resId) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, getString(resId), Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    private void requestLocationPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (!report.getGrantedPermissionResponses().isEmpty()){
                    goToMainActivity();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();
    }
}
