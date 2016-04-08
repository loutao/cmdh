package com.ratio.deviceService.communication.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mesogene on 2015/5/24.
 */
public abstract class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Codoon_Accessory.db";
    public static final int DATABASE_VERSION = 4;
    static SQLiteDatabase db;
    private static SQLiteOpenHelper instance;
    public static DataBaseHelper mDataBaseHelper;
    private static AtomicInteger mOpenCounter = new AtomicInteger();

    public DataBaseHelper(Context paramContext) {
        super(paramContext, "Codoon_Accessory.db", null, 4);
        if (instance == null)
            instance = this;
    }

    public void close() {
        try {
            if (mOpenCounter.decrementAndGet() == 0)
                instance.close();
            return;
        } finally {
//            localObject = finally;
//            throw localObject;
        }
    }

    protected void closeDatabase() {
        try {
            if ((db != null) && (db.isOpen()) && (!db.isDbLockedByCurrentThread()) && (!db.isDbLockedByOtherThreads())) {
                db.close();
                db = null;
            }
            return;
        } finally {
//            localObject = finally;
//            throw localObject;
        }
    }

    protected SQLiteDatabase getDatabase() {
        try {
            if ((db != null) && (db.isOpen())) ;
            for (SQLiteDatabase localSQLiteDatabase = db; ; localSQLiteDatabase = db) {

                db = getWritableDatabase();
                return localSQLiteDatabase;
            }
        } finally {
        }
    }

    public boolean isColumnExist(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2) {
        boolean bool1 = false;
        boolean bool2 = false;
        if (paramString1 == null)
            bool2 = false;
        while (true) {

            try {
                StringBuilder localStringBuilder = new StringBuilder("select count(1) as c from sqlite_master where type ='table' and name ='");
                Cursor localCursor = paramSQLiteDatabase.rawQuery(paramString1.trim() + "' and sql like '%" + paramString2.trim() + "%'", null);
                if ((localCursor.moveToNext()) && (localCursor.getInt(0) > 0))
                    bool1 = true;
                localCursor.close();
                bool2 = bool1;
            } catch (Exception localException) {
                bool2 = bool1;
            }
            return bool2;
        }
    }

    public boolean isTableExist(String paramString) {
        boolean bool1 = false;
        boolean bool2 = false;
        if (paramString == null)
            bool2 = false;
        while (true)
        {

            try
            {
                StringBuilder localStringBuilder = new StringBuilder("select count(1) as c from sqlite_master where type ='table' and name ='");
                String str = paramString.trim() + "'";
                Cursor localCursor = getDatabase().rawQuery(str, null);
                if ((localCursor.moveToNext()) && (localCursor.getInt(0) > 0))
                    bool1 = true;
                localCursor.close();
            }
            catch (Exception localException)
            {
                bool2 = bool1;
            }
            return bool2;
        }
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
        paramSQLiteDatabase.execSQL("create table  IF NOT EXISTS sleepdetail(_id integer primary key autoincrement not null,userid NVARCHAR(100) not null,time integer  not null,sleepvalue integer  not null, type integer )");
        paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS day_heart_table (_id INTEGER PRIMARY KEY, date TEXT, userId TEXT, productID TEXT, data TEXT );");
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
        if (!isTableExist("sleepdetail"))
            paramSQLiteDatabase.execSQL("create table  IF NOT EXISTS sleepdetail(_id integer primary key autoincrement not null,userid NVARCHAR(100) not null,time integer  not null,sleepvalue integer  not null, type integer )");
        if (!isTableExist("day_heart_table"))
            paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS day_heart_table (_id INTEGER PRIMARY KEY, date TEXT, userId TEXT, productID TEXT, data TEXT );");
        if (!isColumnExist(paramSQLiteDatabase, "sleepdetail", "sleepvalue"))
            paramSQLiteDatabase.execSQL(" ALTER TABLE sleepdetail ADD Column sleepvalue integer NOT NULL default 0 ");
    }

    public void open() {
        try {
            if (mOpenCounter.incrementAndGet() == 1)
                db = instance.getWritableDatabase();
            return;
        } finally {
//            localObject =finally;
//            throw localObject;
        }
    }
}
