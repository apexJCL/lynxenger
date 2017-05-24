package me.apexjcl.lynxenger.realm.models.contactRealm;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.SyncConfiguration;
import io.realm.SyncUser;
import io.realm.permissions.PermissionChange;
import me.apexjcl.lynxenger.BuildConfig;
import me.apexjcl.lynxenger.LynxengerApplication;

/**
 * Defines a user of the application on the Contact's Realm that all users have access to
 * Created by apex on 22/05/2017.
 */

public class Contact extends RealmObject {

    private String userId;
    private String username;
    private Date createdOn;
    private Date lastLoggedIn;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(Date lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public static void initContactsRealm(final SyncUser user, final String username) {
        if (!user.isAdmin())
            return;
        user.getManagementRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Boolean mayRead = true;
                Boolean mayWrite = true;
                Boolean mayManage = true;
                PermissionChange change = new PermissionChange(LynxengerApplication.ROS_CONTACTS_REALM_URL, user.getIdentity(), mayRead, mayWrite, mayManage);
                realm.insert(change);
            }
        });
        SyncConfiguration configuration = new SyncConfiguration.Builder(user, LynxengerApplication.ROS_CONTACTS_REALM_URL).schemaVersion(BuildConfig.SCHEMA_VERSION).build();
        Realm r = Realm.getInstance(configuration); // Retrieves remote contacts realm
        long count = r.where(Contact.class).equalTo("userId", user.getIdentity()).count();
        if (count == 0)
            r.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Contact c = realm.createObject(Contact.class);
                    c.setUserId(user.getIdentity());
                    c.setCreatedOn(new Date());
                    c.setUsername(username);
                    c.setLastLoggedIn(new Date());
                }
            });
        r.close();
    }
}
