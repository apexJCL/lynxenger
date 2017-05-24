package me.apexjcl.lynxenger.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncUser;
import me.apexjcl.lynxenger.R;
import me.apexjcl.lynxenger.adapters.ContactsAdapter;
import me.apexjcl.lynxenger.realm.UserManager;
import me.apexjcl.lynxenger.realm.models.contactRealm.Contact;

public class MainActivity extends Activity {

    private Realm r;
    private RecyclerView mContactsRecycler;
    private ContactsAdapter mContactsAdapter;

    private SyncConfiguration mContactsConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check if session exists
        if (savedInstanceState == null) {
            if (UserManager.isSessionAvailable())
                UserManager.setActiveUser(SyncUser.currentUser());
            else {
                finish();
                launchLogin();
                return;
            }
        }
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        r = Realm.getInstance(mContactsConfig);
        load();
    }

    @Override
    protected void onStop() {
        super.onStop();
        r.close();
    }

    private void init() {
        mContactsRecycler = (RecyclerView) findViewById(R.id.contacts_recycler);
        mContactsConfig = UserManager.getContactsConfiguration();
    }

    private void load() {
        mContactsAdapter = new ContactsAdapter(r.where(Contact.class).findAllAsync(), true);
        mContactsRecycler.setAdapter(mContactsAdapter);
        mContactsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void launchLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void startChat(String userId, String username) {
        Intent i = new Intent(this, ChatActivity.class);
        i.putExtra(ChatActivity.USER_ID, userId);
        i.putExtra(ChatActivity.USERNAME, username);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                UserManager.logout();
                launchLogin();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
