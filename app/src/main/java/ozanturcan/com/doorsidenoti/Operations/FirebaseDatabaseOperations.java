package ozanturcan.com.doorsidenoti.Operations;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ozanturcan.com.doorsidenoti.Models.DeviceInformations;
import ozanturcan.com.doorsidenoti.Models.MessagesDetails;
import ozanturcan.com.doorsidenoti.Models.UserInformation;

/**
 * Created by Legend on 29.10.2017.
 */

public class FirebaseDatabaseOperations extends AppCompatActivity{

    static private FirebaseDatabaseOperations thisClass = new FirebaseDatabaseOperations();
    private String TAG = "FirebaseDatabase";

    public FirebaseDatabaseOperations getAnInnerClass() {
        return thisClass;
    }
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private UserInformation UserInfo = new UserInformation().getAnInnerClass();
    Context context; // before onCreate in MainActivity

    private DeviceInformations deviceInfo = new DeviceInformations().getAnInnerClass();

    // Using With UpdateUI fonk in FirebaseAuthOperation
    public void AppendUserInfoToDatabase() {
        DatabaseReference Database = FirebaseDatabase.getInstance().getReference("users").child(UserInfo.getPersonId());
        Database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null){
                    mDatabase.child("users")
                            .child(UserInfo.getPersonId())
                            .setValue(UserInfo.getAllUserValue())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) {

                                        Log.d(TAG, "writeNewUserError");
                                    }
                                }
                            });
                };
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void GetCurrentUserInfo() {
        DatabaseReference Database = FirebaseDatabase.getInstance().getReference("users").child(UserInfo.getPersonId());
        Database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String PersonName = (String) dataSnapshot.child("PersonName").getValue();
                String PersonPhoto = (String) dataSnapshot.child("PersonPhoto").getValue();
                String PersonEmail = (String) dataSnapshot.child("PersonEmail").getValue();
                String DeviceID = String.valueOf(dataSnapshot.child("DeviceID").getValue());
                String PersonID = (String) dataSnapshot.child("PersonID").getValue();

                UserInfo.setPersonId(PersonID);
                UserInfo.setPersonName(PersonName);
                UserInfo.setPersonEmail(PersonEmail);
                UserInfo.setPersonDeviceIDs(DeviceID);
                UserInfo.setPersonPhoto(Uri.parse(PersonPhoto));

            }

            @Override
            public void onCancelled(DatabaseError dataSnapshot) {

            }
        });
    }


    public void getBoard() {



    }

    public void SendMessage(final String sendingMessage, final String Token) {

       final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Devices").child(UserInfo.getPersonDeviceIDs());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    databaseReference.child("CurrentMessage")
                            .setValue(sendingMessage)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) {

                                        Log.d(TAG, "Your Message was sended");
                                    }
                                    else{

                                    }
                                }
                            });
                    databaseReference.child("CurrentToken")
                            .setValue(Token)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) {

                                        Log.d(TAG, "writeNewUserError");
                                    }
                                }
                            });
                };
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getAllMessagesFromUser() {
        String DeviceNumber = (String) UserInfo.getPersonDeviceIDs();
        final DatabaseReference reference = mDatabase.child("Devices").child(UserInfo.getPersonDeviceIDs()).child("TokenProps");
        final String sMessages, sSender, sEmail, sTimestamp, sPhone;

        final Map<String, List<MessagesDetails>> TokenProps = new HashMap<String, List<MessagesDetails>>();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    //TokenProps Main Token1 Token 2
                    List<MessagesDetails> MesasgeList = new ArrayList<MessagesDetails>();
                    for (DataSnapshot dspcChild : dsp.getChildren()) {
                        //Message 1 Message 2

                        MessagesDetails currentMessageDetails = new MessagesDetails();

                        Map<String, String> MmessageDetailsMap = new HashMap<String, String>();

                        for (DataSnapshot childChild : dspcChild.getChildren()) {
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

    public void getUnseenMessagesFromUser() {
        String DeviceNumber = (String) UserInfo.getPersonDeviceIDs();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Devices").child(UserInfo.getPersonDeviceIDs()).child("TokenProps");
        final String sMessages, sSender, sEmail, sTimestamp, sPhone;

        final Map<String, List<MessagesDetails>> TokenProps = new HashMap<String, List<MessagesDetails>>();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    //TokenProps Main Token1 Token 2
                    List<MessagesDetails> MesasgeList = new ArrayList<MessagesDetails>();
                    for (DataSnapshot dspcChild : dsp.getChildren()) {
                        //Message 1 Message 2
                        String a = (String) dspcChild.child("Seen").getValue();
                        if (new String(a).equals("True")) {

                            continue;
                        } else if (new String(a).equals("False")) {
                            MessagesDetails currentMessageDetails = new MessagesDetails();

                            Map<String, String> MmessageDetailsMap = new HashMap<String, String>();

                            for (DataSnapshot childChild : dspcChild.getChildren()) {

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
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void getMessageWithToken(){}
}
