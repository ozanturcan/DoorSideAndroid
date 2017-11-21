package ozanturcan.com.doorsidenoti.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Legend on 20.11.2017.
 */

public class MessagesDetails {


    private String sMessage;
    private String sSender;
    private String sEmail;
    private String sTimestamp;
    private String sPhone;
    private String sSubject;
    private String MessageSToken;
    private List<Map <String ,String>> MessageList;
    private Map <String ,String> MessageDetailsMap = new HashMap<String, String>();

    public String getMessageSToken() {
        return MessageSToken;
    }

    public void setMessageSToken(String messageSToken) {
        MessageSToken = messageSToken;
    }


    public String getsMessage() {
        return sMessage;
    }

    public String getsSender() {
        return sSender;
    }

    public String getsEmail() {
        return sEmail;
    }

    public String getsTimestamp() {
        return sTimestamp;
    }

    public String getsPhone() {
        return sPhone;
    }

    public String getsSubject() {
        return sSubject;
    }

    public List<Map<String, String>> getMessageList() {
        return MessageList;
    }

    public void setMessageList(List<Map<String, String>> messageList) {
        MessageList = messageList;
    }

    public Map<String, String> getMessageDetailsMap() {



        return MessageDetailsMap;
    }

    public void setMessageDetailsMap(Map<String, String> messageDetailsMap) {
        MessageDetailsMap = messageDetailsMap;
        sMessage = MessageDetailsMap.get("Message");
        sSender = MessageDetailsMap.get("Sender");
        sEmail = MessageDetailsMap.get("eMail");
        sTimestamp = MessageDetailsMap.get("Timestamp");
        sPhone = MessageDetailsMap.get("Phone");
        sSubject = MessageDetailsMap.get("Subject");
    }
}
