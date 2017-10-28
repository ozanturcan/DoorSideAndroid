package ozanturcan.com.doorsidenoti;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        sField_email = (EditText) findViewById(R.id.field_email);
        sField_password = (EditText) findViewById(R.id.field_password);
        sVerify_email_button = (Button) findViewById(R.id.verify_email_button);

        // Views
        sField_email = (EditText) findViewById(R.id.field_email);
        sField_password = (EditText) findViewById(R.id.field_password);

        // Buttons

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
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
            Toast.makeText(getApplicationContext(), "Current user Giriş yapılmış", Toast.LENGTH_SHORT).show();

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
            FirebaseSignInMethod(sField_email.getText().toString(), sField_password.getText().toString());


        } else if (id == R.id.sign_out_button) {
            signOut();
        } else if (id == R.id.verify_email_button) {
            // Disable button
            findViewById(R.id.verify_email_button).setEnabled(false);
            sendEmailVerification();
        }else if (id == R.id.sign_in_button) {

            RC_SIGN_IN = 0;
            signInWithGoogle();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();


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

    // Firebase Sign-in start



}


/*
TODO: if Login Succes activitiy inten to MainActivity but İf we wanna back again App was taken
 Crash report
*/
