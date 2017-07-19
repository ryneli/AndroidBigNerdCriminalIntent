package com.zhenqiangli.androidbignerdcriminalintent.data;

import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by zhenqiangli on 7/15/17.
 */

public class Crime {
    private static final String TAG = "Crime";
    private UUID id;
    private String title;
    private Date date;
    private boolean solved;

    public boolean isRequirePolice() {
        return requirePolice;
    }

    public void setRequirePolice(boolean requirePolice) {
        this.requirePolice = requirePolice;
    }

    private boolean requirePolice;

    public void setTitle(String title) {
        if (title == null || title.equals("")) {
            Log.d(TAG, "setTitle: " + title);
            return;
        }
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

    public String getSimpleDate() {
        return new SimpleDateFormat("MMMM dd, YYYY").format(date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSolved() {
        return solved;
    }

    public Crime() {
        this("", false, false);
    }

    public Crime(String title, boolean solved) {
        this(title, solved, false);
    }

    public Crime(String title, boolean solved, boolean requirePolice) {
        this.title = title;
        this.solved = solved;
        this.requirePolice = requirePolice;
        id = UUID.randomUUID();
        date = new Date();
    }

    @Override
    public String toString() {
        return title + "/" + date;
    }
}
