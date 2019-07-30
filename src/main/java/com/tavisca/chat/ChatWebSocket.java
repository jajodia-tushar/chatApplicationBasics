package com.tavisca.chat;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/endpoint")
public class ChatWebSocket {

        @OnOpen
        public void onOpen(Session session){
            System.out.println("Open Connection: "+session.getId());
        }

        @OnClose
        public void onClose(Session session){
            System.out.println("Connection Closed: "+session.getId());
        }

        @OnMessage
        public void onMessage(String message,Session session){
            System.out.println("Received Message from "+session.getId()+"  Message is "+ message);

            try {
                session.getBasicRemote().sendText(message.toUpperCase());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



}
