package com.jonvallet.twitter;

import java.util.Date;

public class Post {
    public final String user;
    public final String message;
    public final Date timestamp = new Date();

    public Post(String user, String message) {
        this.user = user;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", user, message, timestamp);
    }
}
