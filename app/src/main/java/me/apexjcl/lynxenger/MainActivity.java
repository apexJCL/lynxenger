package me.apexjcl.lynxenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import me.apexjcl.lynxenger.activities.LoginActivity;
import me.apexjcl.lynxenger.realm.UserManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check if session exists
        if (!UserManager.isSessionAvailable()){
            launchLogin();
            finish();
            return;
        }
    }

    private void launchLogin(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
