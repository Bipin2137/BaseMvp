package com.dexter.basemvpwithoutdagger;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.dexter.basemvpwithoutdagger.database.db.CineWorldDb;
import com.dexter.basemvpwithoutdagger.utils.Constants;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by Khatr on 12/31/2017.
 */

public class CineWorldApp extends Application {

    private static CineWorldApp sInstance = null;
    private static CineWorldDb INSTANCE;
    private final static Object sLock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        initTimber();

        initLeakCanary();

        initRoomDatabase();
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static CineWorldDb initRoomDatabase() {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(sInstance, CineWorldDb.class, Constants.DATABASE_NAME).build();
            }
            return INSTANCE;
        }
    }
}
