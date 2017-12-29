package ozanturcan.com.doorsidenoti.Operations;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import ozanturcan.com.doorsidenoti.MainActivity;
import ozanturcan.com.doorsidenoti.Models.UserInformation;
import ozanturcan.com.doorsidenoti.R;

/**
 * Created by Legend on 27.10.2017.
 */

public class  FirebaseAuthOperations extends AppCompatActivity {

    protected int RC_SIGN_IN;
    protected FirebaseAuth mAuth;
    protected EditText sField_email;
    protected EditText sField_password;
    protected FirebaseAuth.AuthStateListener mAuthListener;
    protected Button sVerify_email_button;
    protected GoogleApiClient mGoogleApiClient;
    protected static final String TAG = "SignInActivity";
    private FirebaseDatabaseOperations firebaseDatabaseOperations = new FirebaseDatabaseOperations().getAnInnerClass();

    protected void FirebaseSimpleSignInMethod(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            updateUI(currentUser);
                            Toast.makeText(getApplicationContext(), "User Password login is Success", Toast.LENGTH_SHORT).show();
                            intenttoNextForm();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }
    protected void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    protected void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(getApplicationContext(), "Google Sign is Success", Toast.LENGTH_SHORT).show();

                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            updateUI(currentUser);

                            intenttoNextForm();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
    protected void intenttoNextForm() {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

    }
    protected void updateUI(FirebaseUser user) {
        UserInformation MyUserInfo = new UserInformation().getAnInnerClass();
        if (user != null){

            MyUserInfo.setPersonId(user.getUid());
            MyUserInfo.setPersonName(user.getDisplayName());
            MyUserInfo.setPersonEmail(user.getEmail());
            MyUserInfo.setPersonPhoto(user.getPhotoUrl());


            firebaseDatabaseOperations.AppendUserInfoToDatabase();



        }else
        {
            MyUserInfo = null;
        }
    }
    protected void signOut() {
        mAuth.signOut();
        updateUI(null);
        Toast.makeText(getApplicationContext(), "Sign Out", Toast.LENGTH_SHORT).show();

    }
    protected void sendEmailVerification() {


        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // [START_EXCLUDE]
                            // Re-enable button
                            findViewById(R.id.verify_email_button).setEnabled(true);

                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                        "Verification email sent to " + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG, "sendEmailVerification", task.getException());
                                Toast.makeText(getApplicationContext(),
                                        "Failed to send verification email.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            // [END_EXCLUDE]
                        }
                    });
            // [END send_email_verification]
        }
    }

}
