package me.apexjcl.lynxenger.realm.handlers;

import java.util.Date;

import io.realm.Realm;
import me.apexjcl.lynxenger.realm.models.lynxenger.Chat;
import me.apexjcl.lynxenger.realm.models.lynxenger.Message;

/**
 * Created by apex on 24/05/2017.
 */

public class ChatHandler {

    public static boolean doesChatExists(Realm chatRealm, String userId) {
        return chatRealm.where(Chat.class).equalTo("userId", userId).count() > 0;
    }

    public static void initChat(Realm chatRealm, final String userId, final String username) {
        chatRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Chat c = realm.createObject(Chat.class, userId);
                c.setUsername(username);
                realm.copyToRealmOrUpdate(c);
            }
        });
    }

    public static void sendMessage(Realm chatRealm, final String message, final String userId, Realm.Transaction.OnSuccess success, Realm.Transaction.OnError error) {
        chatRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Message m = realm.createObject(Message.class);
                m.setContent(message);
                m.setSentOn(new Date());
                m.setSentBy(userId);
                realm.copyToRealm(m);
            }
        }, success, error);
    }
}
