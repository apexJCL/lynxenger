package me.apexjcl.lynxenger.realm;

import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncUser;
import me.apexjcl.lynxenger.BuildConfig;
import me.apexjcl.lynxenger.LynxengerApplication;

/**
 * Created by apex on 22/05/2017.
 */

public class UserManager {

    public static boolean isSessionAvailable() {
        SyncUser user = SyncUser.currentUser();
        return user != null;
    }

    public static void setActiveUser(SyncUser user) {
        SyncConfiguration configuration = new SyncConfiguration.Builder(
                user,
                LynxengerApplication.ROS_PERSONAL_REALM_URL
        ).schemaVersion(BuildConfig.SCHEMA_VERSION).build();
        Realm r = Realm.getDefaultInstance();
        Realm.setDefaultConfiguration(configuration);
        r.close();
    }


    public static SyncConfiguration getContactsConfiguration() {
        return new SyncConfiguration.Builder(SyncUser.currentUser(), LynxengerApplication.ROS_CONTACTS_REALM_URL).schemaVersion(BuildConfig.SCHEMA_VERSION).build();
    }

    public static SyncConfiguration getChatConfiguration() {
        return new SyncConfiguration.Builder(SyncUser.currentUser(), LynxengerApplication.ROS_PERSONAL_REALM_URL).schemaVersion(BuildConfig.SCHEMA_VERSION).build();
    }

    public static void logout() {
        SyncUser user = SyncUser.currentUser();
        user.logout();
    }
}
