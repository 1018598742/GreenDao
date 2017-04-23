package com.fta.greendaotest;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

/**
 * 文件描述：
 * 作者： Created by fta on 2017/4/23.
 */

public class App extends Application {
    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = true;//是否加密
    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,ENCRYPTED ?"notes-db-encrypted" :"notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret"):helper.getWritableDb();//加密使用的加密密码
        daoSession =  new DaoMaster(db).newSession();
    }
    public DaoSession getDaoSession(){
        return daoSession;
    }
}
