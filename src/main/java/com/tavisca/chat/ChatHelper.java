package com.tavisca.chat;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatHelper {

    public static HashMap<String, Session> sessionMap =
            new HashMap<>();

    public static void sendMessage(String message, String senderId){
        Pattern pattern = Pattern.compile("@(.*):(.*)");
        Matcher matcher = pattern.matcher(message);
        if(matcher.find()){
            String receiverID = matcher.group(1);
            String messageToBeSent = matcher.group(2);
            Session session = sessionMap.get(receiverID);
            try {
                session.getBasicRemote().sendText("This message is from "+senderId+" and the message is -> "+messageToBeSent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
