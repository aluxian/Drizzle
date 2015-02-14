package com.aluxian.drizzle.api.models;

import java.util.Date;

public final class Like {

    public final int id;
    public final Date createdAt;
    public final User user;

    public Like(int id, Date createdAt, User user) {
        this.id = id;
        this.createdAt = createdAt;
        this.user = user;
    }

}