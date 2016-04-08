package com.njucm.cmdh.app.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.njucm.cmdh.app.domain.UserSleepFeature;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table USER_SLEEP_FEATURE.
*/
public class UserSleepFeatureDao extends AbstractDao<UserSleepFeature, Long> {

    public static final String TABLENAME = "USER_SLEEP_FEATURE";

    /**
     * Properties of entity UserSleepFeature.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Sleepfeatureid = new Property(1, Integer.class, "sleepfeatureid", false, "SLEEPFEATUREID");
        public final static Property Airhumidity = new Property(2, Double.class, "airhumidity", false, "AIRHUMIDITY");
        public final static Property Ambienttemperature = new Property(3, Double.class, "ambienttemperature", false, "AMBIENTTEMPERATURE");
        public final static Property Ambientnoise = new Property(4, Double.class, "ambientnoise", false, "AMBIENTNOISE");
        public final static Property Bedtimehabits = new Property(5, String.class, "bedtimehabits", false, "BEDTIMEHABITS");
        public final static Property Goodbedtimehabits = new Property(6, String.class, "goodbedtimehabits", false, "GOODBEDTIMEHABITS");
        public final static Property Siestahabit = new Property(7, String.class, "siestahabit", false, "SIESTAHABIT");
        public final static Property Averagesleeptime = new Property(8, Double.class, "averagesleeptime", false, "AVERAGESLEEPTIME");
        public final static Property Sleephabitdetemination = new Property(9, String.class, "sleephabitdetemination", false, "SLEEPHABITDETEMINATION");
        public final static Property Sleepindex = new Property(10, String.class, "sleepindex", false, "SLEEPINDEX");
        public final static Property Sleephabitanalysis = new Property(11, String.class, "sleephabitanalysis", false, "SLEEPHABITANALYSIS");
        public final static Property Temp_locationinfoid = new Property(12, Integer.class, "temp_locationinfoid", false, "TEMP_LOCATIONINFOID");
    };


    public UserSleepFeatureDao(DaoConfig config) {
        super(config);
    }
    
    public UserSleepFeatureDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'USER_SLEEP_FEATURE' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'SLEEPFEATUREID' INTEGER," + // 1: sleepfeatureid
                "'AIRHUMIDITY' REAL," + // 2: airhumidity
                "'AMBIENTTEMPERATURE' REAL," + // 3: ambienttemperature
                "'AMBIENTNOISE' REAL," + // 4: ambientnoise
                "'BEDTIMEHABITS' TEXT," + // 5: bedtimehabits
                "'GOODBEDTIMEHABITS' TEXT," + // 6: goodbedtimehabits
                "'SIESTAHABIT' TEXT," + // 7: siestahabit
                "'AVERAGESLEEPTIME' REAL," + // 8: averagesleeptime
                "'SLEEPHABITDETEMINATION' TEXT," + // 9: sleephabitdetemination
                "'SLEEPINDEX' TEXT," + // 10: sleepindex
                "'SLEEPHABITANALYSIS' TEXT," + // 11: sleephabitanalysis
                "'TEMP_LOCATIONINFOID' INTEGER);"); // 12: temp_locationinfoid
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'USER_SLEEP_FEATURE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, UserSleepFeature entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer sleepfeatureid = entity.getSleepfeatureid();
        if (sleepfeatureid != null) {
            stmt.bindLong(2, sleepfeatureid);
        }
 
        Double airhumidity = entity.getAirhumidity();
        if (airhumidity != null) {
            stmt.bindDouble(3, airhumidity);
        }
 
        Double ambienttemperature = entity.getAmbienttemperature();
        if (ambienttemperature != null) {
            stmt.bindDouble(4, ambienttemperature);
        }
 
        Double ambientnoise = entity.getAmbientnoise();
        if (ambientnoise != null) {
            stmt.bindDouble(5, ambientnoise);
        }
 
        String bedtimehabits = entity.getBedtimehabits();
        if (bedtimehabits != null) {
            stmt.bindString(6, bedtimehabits);
        }
 
        String goodbedtimehabits = entity.getGoodbedtimehabits();
        if (goodbedtimehabits != null) {
            stmt.bindString(7, goodbedtimehabits);
        }
 
        String siestahabit = entity.getSiestahabit();
        if (siestahabit != null) {
            stmt.bindString(8, siestahabit);
        }
 
        Double averagesleeptime = entity.getAveragesleeptime();
        if (averagesleeptime != null) {
            stmt.bindDouble(9, averagesleeptime);
        }
 
        String sleephabitdetemination = entity.getSleephabitdetemination();
        if (sleephabitdetemination != null) {
            stmt.bindString(10, sleephabitdetemination);
        }
 
        String sleepindex = entity.getSleepindex();
        if (sleepindex != null) {
            stmt.bindString(11, sleepindex);
        }
 
        String sleephabitanalysis = entity.getSleephabitanalysis();
        if (sleephabitanalysis != null) {
            stmt.bindString(12, sleephabitanalysis);
        }
 
        Integer temp_locationinfoid = entity.getTemp_locationinfoid();
        if (temp_locationinfoid != null) {
            stmt.bindLong(13, temp_locationinfoid);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public UserSleepFeature readEntity(Cursor cursor, int offset) {
        UserSleepFeature entity = new UserSleepFeature( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // sleepfeatureid
            cursor.isNull(offset + 2) ? null : cursor.getDouble(offset + 2), // airhumidity
            cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3), // ambienttemperature
            cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4), // ambientnoise
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // bedtimehabits
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // goodbedtimehabits
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // siestahabit
            cursor.isNull(offset + 8) ? null : cursor.getDouble(offset + 8), // averagesleeptime
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // sleephabitdetemination
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // sleepindex
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // sleephabitanalysis
            cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12) // temp_locationinfoid
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UserSleepFeature entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSleepfeatureid(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setAirhumidity(cursor.isNull(offset + 2) ? null : cursor.getDouble(offset + 2));
        entity.setAmbienttemperature(cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3));
        entity.setAmbientnoise(cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4));
        entity.setBedtimehabits(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setGoodbedtimehabits(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSiestahabit(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setAveragesleeptime(cursor.isNull(offset + 8) ? null : cursor.getDouble(offset + 8));
        entity.setSleephabitdetemination(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setSleepindex(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setSleephabitanalysis(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setTemp_locationinfoid(cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(UserSleepFeature entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(UserSleepFeature entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
