package me.apexjcl.lynxenger.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import me.apexjcl.lynxenger.R;

/**
 * Created by apex on 22/05/2017.
 */

public class LoginActivity extends Activity {

    private EditText mUsername;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Check if session exists
        init();
    }

    private void init() {
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
    }

    public void login(View view) {

    }
}
