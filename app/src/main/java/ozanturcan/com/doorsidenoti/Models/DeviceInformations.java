package ozanturcan.com.doorsidenoti.Models;

import java.util.List;
import java.util.Map;

/**
 * Created by Legend on 27.10.2017.
 */

public class DeviceInformations {

    static private DeviceInformations thisClass = new DeviceInformations();

    private String DeviceNumber;

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getCurrentMessage() {
        return CurrentMessage;
    }

    public void setCurrentMessage(String currentMessage) {
        CurrentMessage = currentMessage;
    }

    public String getCurrentToken() {
        return CurrentToken;
    }

    public void setCurrentToken(String currentToken) {
        CurrentToken = currentToken;
    }

    private String DeviceTokens;
    private String DeviceAddress;
    private String DeviceId;
    private String CurrentMessage;
    private String CurrentToken;

    public static DeviceInformations getThisClass() {
        return thisClass;
    }

    public static void setThisClass(DeviceInformations thisClass) {
        DeviceInformations.thisClass = thisClass;
    }

    private Map <String, List<MessagesDetails>> DeviceMessages;
    private String DeviceOwner;
    public DeviceInformations() {

    }

    public DeviceInformations getAnInnerClass(){
        return thisClass;
    }

    public String getDeviceNumber() {
        return DeviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        DeviceNumber = deviceNumber;
    }

    public String getDeviceTokens() {
        return DeviceTokens;
    }

    public void setDeviceTokens(String deviceTokens) {
        DeviceTokens = deviceTokens;
    }

    public String getDeviceAddress() {
        return DeviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        DeviceAddress = deviceAddress;
    }

    public Map <String, List<MessagesDetails>> getDeviceMessages() {
        return DeviceMessages;
    }

    public void setDeviceMessages(Map <String, List<MessagesDetails>> deviceMessages) {
        DeviceMessages = deviceMessages;
    }

    public String getDeviceOwner() {
        return DeviceOwner;
    }

    public void setDeviceOwner(String deviceOwner) {
        DeviceOwner = deviceOwner;
    }
}
