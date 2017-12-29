package ozanturcan.com.doorsidenoti;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ozanturcan.com.doorsidenoti.Operations.FirebaseAuthOperations;

public class LoginActivity extends FirebaseAuthOperations implements  View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        // Views
        sField_email = (EditText) findViewById(R.id.field_email);
        sField_password = (EditText) findViewById(R.id.field_password);

        // Buttons
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_Google_button);
        sField_email = (EditText) findViewById(R.id.field_email);
        sField_password = (EditText) findViewById(R.id.field_password);
        findViewById(R.id.sign_in_Google_button).setOnClickListener(this);
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged( @NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        // [START config_signin]
        // Configure Google Sign In
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }
    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            updateUI(currentUser);
            Toast.makeText(getApplicationContext(), "Current user exist ", Toast.LENGTH_SHORT).show();
            intenttoNextForm();
        }
    }
    // [END on_start_check_user]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    @Override
    public void onClick(View view) {
        Intent intent;
        int id = view.getId();

        if (id == R.id.email_sign_in_button) {
            if (!validateForm()) {
                return;
            } else
                FirebaseSimpleSignInMethod(sField_email.getText().toString(), sField_password.getText().toString());

        } else if (id == R.id.verify_email_button) {
            // Disable button
            findViewById(R.id.verify_email_button).setEnabled(false);
            sendEmailVerification();

        }else if (id == R.id.sign_in_Google_button) {

            RC_SIGN_IN = 0;
            signInWithGoogle();
        }
    }

    /// Using For GoogleSign
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount acct = result.getSignInAccount();

            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



   /// Activity Controller

    private boolean validateForm() {
        boolean valid = true;

        String email = sField_email.getText().toString();
        if (TextUtils.isEmpty(email)) {
            sField_email.setError("Required.");
            valid = false;
        } else {
            sField_email.setError(null);
        }

        String password = sField_password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            sField_password.setError("Required.");
            valid = false;
        } else {
            sField_password.setError(null);
        }

        return valid;
    }




}


/*
TODO: if Login Succes activitiy inten to MainActivity but İf we wanna back again App was taken Crash report
Todo : Account girişlerinde datalar nereden alınmalı firebasAuth mu FirebaseDatabase mi ? İlk alış dışında (algoritma eksik )
*/
