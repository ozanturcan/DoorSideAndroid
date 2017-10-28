package ozanturcan.com.doorsidenoti.Models;

import android.net.Uri;

import java.util.List;

/**
 * Created by Legend on 27.10.2017.
 */

public class UserInformation {

    static private UserInformation thisClass = new UserInformation();

    private  String personName;
    private  String personTitle ;
    private  String personFamilyName;
    private  String personEmail;
    private  String personId;
    private  List<Integer> personDeviceIDs;
    private  Uri personPhoto;

    public UserInformation getAnInnerClass(){
        return thisClass;
    }


    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonTitle() {
        return personTitle;
    }

    public void setPersonTitle(String personTitle) {
        this.personTitle = personTitle;
    }

    public String getPersonFamilyName() {
        return personFamilyName;
    }

    public void setPersonFamilyName(String personFamilyName) {
        this.personFamilyName = personFamilyName;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;

    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public List<Integer> getPersonDeviceIDs() {
        return personDeviceIDs;
    }

    public void setPersonDeviceIDs(List<Integer> personDeviceIDs) {
        this.personDeviceIDs = personDeviceIDs;
    }

    public Uri getPersonPhoto() {
        return personPhoto;
    }

    public void setPersonPhoto(Uri personPhoto) {
        this.personPhoto = personPhoto;
    }
}
