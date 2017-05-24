package me.apexjcl.lynxenger.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.ObjectServerError;
import io.realm.SyncCredentials;
import io.realm.SyncUser;
import me.apexjcl.lynxenger.BuildConfig;
import me.apexjcl.lynxenger.LynxengerApplication;
import me.apexjcl.lynxenger.R;
import me.apexjcl.lynxenger.realm.UserManager;
import me.apexjcl.lynxenger.realm.models.contactRealm.Contact;

/**
 * Created by apex on 22/05/2017.
 */

public class LoginActivity extends Activity implements SyncUser.Callback {

    private EditText mUsername;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Check if session exists
        if (UserManager.isSessionAvailable()) {
            finish();
            launchHome();
            return;
        }
        init();
    }

    private void init() {
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
    }

    public void login(View view) {
        if (!checkCredentials()) {
            return;
        }
        Toast.makeText(this, R.string.button_login_action, Toast.LENGTH_LONG).show();
        SyncUser.loginAsync(SyncCredentials.usernamePassword(
                mUsername.getText().toString(),
                mPassword.getText().toString(),
                false
        ), LynxengerApplication.ROS_AUTH_URL, this);
    }

    private boolean checkCredentials() {
        return mUsername != null && mUsername.length() > 0 && mPassword != null && mPassword.length() > 0;
    }

    @Override
    public void onSuccess(SyncUser user) {
        Contact.initContactsRealm(user, mUsername.getText().toString());
        UserManager.setActiveUser(user);
        Toast.makeText(this, R.string.success, Toast.LENGTH_LONG).show();
        launchHome();
    }

    private void launchHome() {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public void onError(ObjectServerError error) {
        if (BuildConfig.DEBUG)
            error.printStackTrace();
        Toast.makeText(this, R.string.error_login, Toast.LENGTH_LONG).show();
    }
}
