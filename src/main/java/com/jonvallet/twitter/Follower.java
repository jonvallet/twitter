package com.jonvallet.twitter;

import java.util.Objects;

public class Follower {
    public final String user;
    public final String follower;

    public Follower(String user, String follower) {
        this.user = user;
        this.follower = follower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follower follower1 = (Follower) o;
        return Objects.equals(user, follower1.user) &&
                Objects.equals(follower, follower1.follower);
    }

    @Override
    public int hashCode() {

        return Objects.hash(user, follower);
    }
}
