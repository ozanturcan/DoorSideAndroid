package ozanturcan.com.doorsidenoti.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Legend on 27.10.2017.
 */

public class DeviceInformations {

    static private DeviceInformations thisClass = new DeviceInformations();

    private String DeviceNumber;
    private String DeviceTokens;
    private String DeviceAddress;
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
