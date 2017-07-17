package com.example.maobuidinh.realm.app;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by mao.buidinh on 7/17/2017.
 */

public class Application extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }
}
