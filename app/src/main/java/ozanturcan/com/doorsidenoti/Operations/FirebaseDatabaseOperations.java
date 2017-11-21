package ozanturcan.com.doorsidenoti.Operations;

import android.media.session.MediaSession;
import android.net.Uri;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
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
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;

import ozanturcan.com.doorsidenoti.Models.DeviceInformations;
import ozanturcan.com.doorsidenoti.Models.MessagesDetails;
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

    private DeviceInformations deviceInfo = new DeviceInformations().getAnInnerClass();
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


    public void GetCurrentUserInfo(){
        DatabaseReference Database =  FirebaseDatabase.getInstance().getReference("users").child(UserInfo.getPersonId());
        Database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String PersonName   = (String) dataSnapshot.child("PersonName").getValue();
                String PersonPhoto  = (String) dataSnapshot.child("PersonPhoto").getValue();
                String PersonEmail  = (String) dataSnapshot.child("PersonEmail").getValue();
                String PersonID     = (String) dataSnapshot.child("PersonID").getValue();
                String DeviceID     = (String) dataSnapshot.child("DeviceID").getValue();

                UserInfo.setPersonId(PersonID);
                UserInfo.setPersonName(PersonName);
                UserInfo.setPersonEmail(PersonEmail);
                UserInfo.setPersonPhoto(Uri.parse(PersonPhoto));
                UserInfo.setPersonDeviceIDs(DeviceID);






            }
            @Override
            public void onCancelled(DatabaseError dataSnapshot) {

            }
        });
    }

    public void SendMessageToBoard (String sBoardID)
    {

    }

    public void     getAllMessagesFromUser ()
    {
        String DeviceNumber = (String) UserInfo.getPersonDeviceIDs();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Devices").child("1").child("TokenProps");
        final String sMessages,sSender,sEmail,sTimestamp,sPhone;

        final Map <String, List<MessagesDetails>> TokenProps = new HashMap<String, List<MessagesDetails>>();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp : dataSnapshot.getChildren()){
                    //TokenProps Main Token1 Token 2
                    List<MessagesDetails> MesasgeList = new ArrayList<MessagesDetails>();
                    for (DataSnapshot dspcChild : dsp.getChildren()) {
                        //Message 1 Message 2

                        MessagesDetails currentMessageDetails = new MessagesDetails();

                        Map<String ,String> MmessageDetailsMap = new HashMap<String, String>();

                        for (DataSnapshot childChild : dspcChild.getChildren())
                        {
                            //Message Phone Sender Mail Subject timestamp
                            MmessageDetailsMap.put(childChild.getKey(), String.valueOf(childChild.getValue()));

                        }
                        currentMessageDetails.setMessageDetailsMap(MmessageDetailsMap);
//                            SecondChild.put(dspcChild.getKey(),MesasgeList);
                        currentMessageDetails.setMessageSToken(dspcChild.getKey());

                        MesasgeList.add(currentMessageDetails);


                    }
                    TokenProps.put(dsp.getKey(), MesasgeList); //add result into array list
                }
                    deviceInfo.setDeviceMessages(TokenProps);
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void getMessageWithToken(){}
}
