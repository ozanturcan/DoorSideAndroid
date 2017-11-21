package ozanturcan.com.doorsidenoti.Models;

import android.bluetooth.BluetoothClass;
import android.net.Uri;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.IgnoreExtraProperties;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Legend on 27.10.2017.
 */
@IgnoreExtraProperties
public class UserInformation {

    static private UserInformation thisClass = new UserInformation();

    private  String personName;
    private  String personEmail;
    private  String personId;
    private Uri personPhoto;
    private  String personDeviceIDs;




    public UserInformation() {

    }

    public UserInformation getAnInnerClass(){
        return thisClass;
    }

    public Map<String, String> getAllUserValue() {
        Map <String, String> AllUserValue;
        AllUserValue = new HashMap<String, String>();
        AllUserValue.put("PersonName", personName );
        AllUserValue.put("PersonEmail", personEmail );
        AllUserValue.put("PersonID", personId );
        AllUserValue.put("PersonPhoto", String.valueOf(personPhoto));
        Map ListDevice = new HashMap<String, String>();
        if (personDeviceIDs != null){

        AllUserValue.putAll(ListDevice);
        }
        return AllUserValue;
    }

    public String getPersonName() { return personName; }

    public void setPersonName(String personName) { this.personName = personName; }

    public String getPersonEmail() { return personEmail; }

    public void setPersonEmail(String personEmail) { this.personEmail = personEmail; }

    public String getPersonId() { return personId; }

    public void setPersonId(String personId) { this.personId = personId; }

    public Uri getPersonPhoto() {return personPhoto; }

    public void setPersonPhoto(Uri personPhoto) {this.personPhoto = personPhoto; }

    public String  getPersonDeviceIDs() { return personDeviceIDs; }

    public void setPersonDeviceIDs(String personDeviceIDs) { this.personDeviceIDs = personDeviceIDs; }


}
