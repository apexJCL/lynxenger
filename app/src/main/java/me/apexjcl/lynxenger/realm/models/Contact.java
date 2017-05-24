package me.apexjcl.lynxenger.realm.models;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by apex on 22/05/2017.
 */

public class Contact extends RealmObject {

    private String user_id;
    private String username;
    private Date created_on;

}
