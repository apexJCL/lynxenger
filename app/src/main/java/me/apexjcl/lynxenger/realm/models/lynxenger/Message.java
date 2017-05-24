package me.apexjcl.lynxenger.realm.models.lynxenger;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by apex on 24/05/2017.
 */

public class Message extends RealmObject {

    private String sentBy;
    private String content;
    private Date sentOn;
    private Date seenAt;
    private boolean seen;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSentOn() {
        return sentOn;
    }

    public void setSentOn(Date sentOn) {
        this.sentOn = sentOn;
    }

    public Date getSeenAt() {
        return seenAt;
    }

    public void setSeenAt(Date seenAt) {
        this.seenAt = seenAt;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }
}
