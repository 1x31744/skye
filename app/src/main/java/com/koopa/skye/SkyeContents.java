package com.koopa.skye;

public class SkyeContents {

    String message, time;
    boolean messageSentByUser;

    public SkyeContents(String message, String time, boolean messageSentByUser) {
        this.message = message;
        this.time = time;
        this.messageSentByUser = messageSentByUser;
    }
}
