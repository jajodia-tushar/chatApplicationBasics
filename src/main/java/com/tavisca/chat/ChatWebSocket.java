package com.tavisca.chat;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/endpoint")
public class ChatWebSocket {

    static Thread thread;
    private ChatHelper chatHelper;

        public ChatWebSocket(){
            this.chatHelper = new ChatHelper();

        }

        @OnOpen
        public void onOpen(Session session){
            if(this.thread == null) {
                this.thread = new Thread(new MyRunnable());
                this.thread.start();
            }
            System.out.println("Open Connection: "+session.getId());
            ChatHelper.sessionMap.put(session.getId(),session);
        }

        @OnClose
        public void onClose(Session session){
            System.out.println("Connection Closed: "+session.getId());
            ChatHelper.sessionMap.remove(session.getId());
        }

        @OnMessage
        public void onMessage(String message,Session session){
            System.out.println("Received Message from "+session.getId()+"  Message is "+ message);
            ChatHelper.sendMessage(message,session.getId());
        }
}

class MyRunnable implements Runnable{
    @Override
    public void run() {
        while (true) {
            for (Session session : ChatHelper.sessionMap.values()) {
                try {
                    session.getBasicRemote().sendText("-----10 Seconds Over-----");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

