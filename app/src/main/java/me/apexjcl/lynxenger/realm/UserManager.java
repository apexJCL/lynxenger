package me.apexjcl.lynxenger.realm;

import io.realm.SyncUser;

/**
 * Created by apex on 22/05/2017.
 */

public class UserManager {

    public static boolean isSessionAvailable() {
        SyncUser user = SyncUser.currentUser();
        return user != null && user.isValid();
    }

}
