package me.apexjcl.lynxenger.realm.models.lynxenger;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by apex on 24/05/2017.
 */

public class Chat extends RealmObject {

    @PrimaryKey
    private String userId; // User ID, can't change
    private String username; // Username to display, it can change
    private RealmList<Message> messages;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RealmList<Message> getMessages() {
        return messages;
    }

    public void setMessages(RealmList<Message> messages) {
        this.messages = messages;
    }
}
