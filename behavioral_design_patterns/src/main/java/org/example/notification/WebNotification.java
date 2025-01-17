package org.example.notification;

public class WebNotification implements Observer {

    @Override
    public void update(String message) {
        System.out.println("WebNotification: " + message);
    }
}
