package com.zhenqiangli.androidbignerdcriminalintent.data;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhenqiangli on 7/15/17.
 */

public class Crime {
    private UUID id;
    private String title;
    private Date date;
    private boolean solved;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public boolean isSolved() {
        return solved;
    }

    public Crime() {
        this("", false);
    }

    public Crime(String title, boolean solved) {
        this.title = title;
        this.solved = solved;
        id = UUID.randomUUID();
        date = new Date();
    }

    @Override
    public String toString() {
        return title + "/" + date;
    }
}
