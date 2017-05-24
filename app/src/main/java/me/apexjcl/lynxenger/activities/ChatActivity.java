package me.apexjcl.lynxenger.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import io.realm.Realm;
import io.realm.SyncConfiguration;
import me.apexjcl.lynxenger.BuildConfig;
import me.apexjcl.lynxenger.R;
import me.apexjcl.lynxenger.adapters.ChatAdapter;
import me.apexjcl.lynxenger.realm.UserManager;
import me.apexjcl.lynxenger.realm.handlers.ChatHandler;
import me.apexjcl.lynxenger.realm.models.lynxenger.Chat;
import me.apexjcl.lynxenger.realm.models.lynxenger.Message;

public class ChatActivity extends Activity implements Realm.Transaction.OnSuccess, Realm.Transaction.OnError {

    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";

    private SyncConfiguration mChatConfiguration;
    private Chat mChat;
    private Realm mRealm;
    private String mUserId;
    private String mUsername;
    private String mMyUserId;

    private EditText mMessage;
    private RecyclerView mMessageRecycler;
    private ChatAdapter mChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mUserId = getIntent().getStringExtra(USER_ID);
        if (mUserId == null) {
            finish();
            return;
        }
        mUsername = getIntent().getStringExtra(USERNAME);
        getActionBar().setTitle(mUsername);
        mMessage = (EditText) findViewById(R.id.message);
        mMessageRecycler = (RecyclerView) findViewById(R.id.chat_recycler);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mChatConfiguration = UserManager.getChatConfiguration();
        mRealm = Realm.getInstance(mChatConfiguration);
        if (!ChatHandler.doesChatExists(mRealm, mUserId)) {
            ChatHandler.initChat(mRealm, mUserId, mUsername);
        }
        mChat = mRealm.where(Chat.class).equalTo("userId", mUserId).findFirst();
        mChatAdapter = new ChatAdapter(mChat.getMessages(), true);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mChatAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRealm.removeAllChangeListeners();
        mRealm.close();
    }

    public void sendMessage(View view) {
        if (!validMessage()) {
            mMessage.setError(getString(R.string.error_empty_message));
        }
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Chat c = realm.where(Chat.class).equalTo("userId", mUserId).findFirst();
                Message m = realm.createObject(Message.class);
                m.setSentBy(mMyUserId);
                m.setSentOn(new Date());
                m.setContent(mMessage.getText().toString());
                c.getMessages().add(m);
            }
        }, this, this);
    }

    private boolean validMessage() {
        return mMessage != null && mMessage.length() > 0;
    }

    @Override
    public void onError(Throwable error) {
        if (BuildConfig.DEBUG)
            error.printStackTrace();
    }

    @Override
    public void onSuccess() {

    }
}
