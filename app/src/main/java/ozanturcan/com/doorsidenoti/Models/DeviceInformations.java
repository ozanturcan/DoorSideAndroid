package ozanturcan.com.doorsidenoti.Models;

/**
 * Created by Legend on 27.10.2017.
 */

public class DeviceInformations {

    private String DeviceNumber;
    private String DeviceTokens;
    private String DeviceAddress;
    private String DeviceMessages;
    private String DeviceOwner;

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

    public String getDeviceMessages() {
        return DeviceMessages;
    }

    public void setDeviceMessages(String deviceMessages) {
        DeviceMessages = deviceMessages;
    }

    public String getDeviceOwner() {
        return DeviceOwner;
    }

    public void setDeviceOwner(String deviceOwner) {
        DeviceOwner = deviceOwner;
    }
}
