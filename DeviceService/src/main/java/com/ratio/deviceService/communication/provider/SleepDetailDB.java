package com.ratio.deviceService.communication.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mesogene on 2015/5/24.
 */
public class SleepDetailDB extends DataBaseHelper
{
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SLEEP = "sleepvalue";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_USERID = "userid";
    public static final String DATABASE_TABLE = "sleepdetail";
    public static final String createTableSql = "create table  IF NOT EXISTS sleepdetail(_id integer primary key autoincrement not null,userid NVARCHAR(100) not null,time integer  not null,sleepvalue integer  not null, type integer )";
    public static final String[] dispColumns = { "_id", "userid", "time", "sleepvalue", "type" };

    public SleepDetailDB(Context paramContext)
    {
        super(paramContext);
    }

    public void beginTransaction()
    {
        db.beginTransaction();
    }

    public boolean deleteAll()
    {
        return db.delete("sleepdetail", null, null) > 0;
    }

    public int deleteBetweenTime(String paramString, long paramLong1, long paramLong2)
    {
        return db.delete("sleepdetail", "userid='" + paramString + "' and " + "time" + " >= " + paramLong1 + " and " + "time" + " < " + paramLong2, null);
    }

    public int deleteByUserId(String paramString, long paramLong)
    {
        return db.delete("sleepdetail", "userid='" + paramString + "' and " + "time" + " = " + paramLong, null);
    }

    public boolean deleteByUserId(String paramString)
    {
        return db.delete("sleepdetail", "userid='" + paramString + "'", null) > 0;
    }

    public void endTransaction()
    {
        db.endTransaction();
    }

    public SleepDetailTB get(String paramString, long paramLong)
    {
        paramString = "userid ='" + paramString + "' and " + "time" + " = " + paramLong;
        Cursor localCursor = db.query("sleepdetail", dispColumns, paramString, null, null, null, "time ASC");
        if (localCursor == null)
            return null;
        Object localObject = null;
        SleepDetailTB localSleepDetailTB = null;
        try
        {
            if (localCursor.moveToFirst())
            {
                localSleepDetailTB = new SleepDetailTB();
                localSleepDetailTB.ID = localCursor.getInt(localCursor.getColumnIndex("_id"));
                localSleepDetailTB.userid = localCursor.getString(localCursor.getColumnIndex("userid"));
                localSleepDetailTB.time = localCursor.getLong(localCursor.getColumnIndex("time"));
                localSleepDetailTB.type = localCursor.getInt(localCursor.getColumnIndex("type"));
                localSleepDetailTB.sleepvalue = localCursor.getInt(localCursor.getColumnIndex("sleepvalue"));
            }
            localCursor.close();
            return localSleepDetailTB;
        }
        catch (IllegalStateException localIllegalStateException)
        {
            while (true)
                localCursor.close();
        }
        finally
        {
            localCursor.close();
        }
//        throw new Throwable();
    }

    public List<SleepDetailTB> get(String paramString, long paramLong1, long paramLong2)
    {
        paramString = "userid ='" + paramString + "' and " + "time" + " < " + paramLong2 + " and " + "time" + " >= " + paramLong1;
        Cursor localCursor = db.query("sleepdetail", dispColumns, paramString, null, null, null, "time ASC");
        if (localCursor == null)
            return null;
        SleepDetailTB localSleepDetailTB = null;
        ArrayList localArrayList = null;
        try
        {
            if (localCursor.moveToFirst())
            {
                localArrayList = new ArrayList(localCursor.getCount());
                boolean bool;
                do
                {
                    localSleepDetailTB = new SleepDetailTB();
                    localSleepDetailTB.ID = localCursor.getInt(localCursor.getColumnIndex("_id"));
                    localSleepDetailTB.userid = localCursor.getString(localCursor.getColumnIndex("userid"));
                    localSleepDetailTB.time = localCursor.getLong(localCursor.getColumnIndex("time"));
                    localSleepDetailTB.type = localCursor.getInt(localCursor.getColumnIndex("type"));
                    localSleepDetailTB.sleepvalue = localCursor.getInt(localCursor.getColumnIndex("sleepvalue"));
                    localArrayList.add(localSleepDetailTB);
                    bool = localCursor.moveToNext();
                }
                while (bool);
            }
            localCursor.close();
            return localArrayList;
        }
        catch (IllegalStateException localIllegalStateException)
        {
            while (true)
                localCursor.close();
        }
        finally
        {
            localCursor.close();
        }
//        throw paramString;
    }

    public long insert(SleepDetailTB paramSleepDetailTB)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("userid", paramSleepDetailTB.userid);
        localContentValues.put("time", Long.valueOf(paramSleepDetailTB.time));
        localContentValues.put("type", Integer.valueOf(paramSleepDetailTB.type));
        localContentValues.put("sleepvalue", Integer.valueOf(paramSleepDetailTB.sleepvalue));
        return db.insert("sleepdetail", null, localContentValues);
    }

    public void setTransactionSuccessful()
    {
        db.setTransactionSuccessful();
    }

    public int update(SleepDetailTB paramSleepDetailTB)
    {
        String str = "time = " + paramSleepDetailTB.time;
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("userid", paramSleepDetailTB.userid);
        localContentValues.put("time", Long.valueOf(paramSleepDetailTB.time));
        localContentValues.put("type", Integer.valueOf(paramSleepDetailTB.type));
        localContentValues.put("sleepvalue", Integer.valueOf(paramSleepDetailTB.sleepvalue));
        return db.update("sleepdetail", localContentValues, str, null);
    }
}
