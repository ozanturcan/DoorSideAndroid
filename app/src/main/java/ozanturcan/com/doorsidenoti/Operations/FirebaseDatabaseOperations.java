package ozanturcan.com.doorsidenoti.Operations;

import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ozanturcan.com.doorsidenoti.Models.UserInformation;

/**
 * Created by Legend on 29.10.2017.
 */

public class FirebaseDatabaseOperations {

    static private FirebaseDatabaseOperations thisClass = new FirebaseDatabaseOperations();
    private String TAG = "FirebaseDatabase";

    public FirebaseDatabaseOperations getAnInnerClass(){
        return thisClass;
    }
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private UserInformation UserInfo = new UserInformation().getAnInnerClass();


//    public void FireSetData(){
//        myRef = FirebaseDatabase.getInstance().getReference();
//
//        // Write a message to the database
//         myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");
//
//
//
//    }
//    public void FireGetData(){
//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            public static final String TAG = "Firebase Database";
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//    }


        // Using With UpdateUI fonk in FirebaseAuthOperation
    public void AppendUserInfoToDatabase() {


        mDatabase.child("users")
                .child(UserInfo.getPersonId())
                .setValue(UserInfo.getAllUserValue())
        .addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {

                    Log.d(TAG,"writeNewUserError");
                }
            }
        });
    }


    public void GetCurrentUserDetail(){
         DatabaseReference Database =  FirebaseDatabase.getInstance().getReference("users").child(UserInfo.getPersonId());
        Database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


          dataSnapshot.child("PersonName").getValue().toString();



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void SendMessageToBoard (String sBoardID)
    {

    }

    public void CheckMessagesFromBoard (){

    }


    public void getMessageWithToken(){}
}
