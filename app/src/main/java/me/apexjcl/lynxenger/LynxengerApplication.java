package me.apexjcl.lynxenger;

import android.app.Application;

import io.realm.Realm;

import static me.apexjcl.lynxenger.BuildConfig.OBJECT_SERVER_IP;
import static me.apexjcl.lynxenger.BuildConfig.OBJECT_SERVER_PORT;
import static me.apexjcl.lynxenger.BuildConfig.OBJECT_SERVER_PROTOCOL;

/**
 * Created by apex on 22/05/2017.
 */

public class LynxengerApplication extends Application {

    public static final String ROS_BASE_URL = OBJECT_SERVER_IP + OBJECT_SERVER_PORT;
    public static final String ROS_AUTH_URL = OBJECT_SERVER_PROTOCOL + ROS_BASE_URL + "/auth";
    public static final String ROS_CONTACTS_REALM_URL = "realm://" + ROS_BASE_URL + "/lynxenger/contacts";
    public static final String ROS_PERSONAL_REALM_URL = "realm://" + ROS_BASE_URL + "/~/lynxengerChat";
    public static final long SCHEMA_VERSION = BuildConfig.SCHEMA_VERSION;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
