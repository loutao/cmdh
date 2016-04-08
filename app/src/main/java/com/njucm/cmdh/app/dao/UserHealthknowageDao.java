package com.njucm.cmdh.app.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.njucm.cmdh.app.domain.UserHealthknowage;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;



// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table USER_HEALTHKNOWAGE.
*/
public class UserHealthknowageDao extends AbstractDao<UserHealthknowage, Long> {

    public static final String TABLENAME = "USER_HEALTHKNOWAGE";

    /**
     * Properties of entity UserHealthknowage.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Userknowledgemappid = new Property(1, Integer.class, "userknowledgemappid", false, "USERKNOWLEDGEMAPPID");
        public final static Property Temp_picturelistid = new Property(2, Integer.class, "temp_picturelistid", false, "TEMP_PICTURELISTID");
        public final static Property Healthknowledgetime = new Property(3, java.util.Date.class, "healthknowledgetime", false, "HEALTHKNOWLEDGETIME");
        public final static Property Userid = new Property(4, Integer.class, "userid", false, "USERID");
        public final static Property Username = new Property(5, String.class, "username", false, "USERNAME");
        public final static Property Healthknowltypeid = new Property(6, Integer.class, "healthknowltypeid", false, "HEALTHKNOWLTYPEID");
        public final static Property Healthknowltypename = new Property(7, String.class, "healthknowltypename", false, "HEALTHKNOWLTYPENAME");
        public final static Property Healthknowledgetitle = new Property(8, String.class, "healthknowledgetitle", false, "HEALTHKNOWLEDGETITLE");
        public final static Property Healthknowledgecontent = new Property(9, String.class, "healthknowledgecontent", false, "HEALTHKNOWLEDGECONTENT");
        public final static Property Healthknowledgecode = new Property(10, Integer.class, "healthknowledgecode", false, "HEALTHKNOWLEDGECODE");
        public final static Property IsCollected = new Property(11, Boolean.class, "isCollected", false, "IS_COLLECTED");
    };


    public UserHealthknowageDao(DaoConfig config) {
        super(config);
    }
    
    public UserHealthknowageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'USER_HEALTHKNOWAGE' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'USERKNOWLEDGEMAPPID' INTEGER," + // 1: userknowledgemappid
                "'TEMP_PICTURELISTID' INTEGER," + // 2: temp_picturelistid
                "'HEALTHKNOWLEDGETIME' INTEGER," + // 3: healthknowledgetime
                "'USERID' INTEGER," + // 4: userid
                "'USERNAME' TEXT," + // 5: username
                "'HEALTHKNOWLTYPEID' INTEGER," + // 6: healthknowltypeid
                "'HEALTHKNOWLTYPENAME' TEXT," + // 7: healthknowltypename
                "'HEALTHKNOWLEDGETITLE' TEXT," + // 8: healthknowledgetitle
                "'HEALTHKNOWLEDGECONTENT' TEXT," + // 9: healthknowledgecontent
                "'HEALTHKNOWLEDGECODE' INTEGER," + // 10: healthknowledgecode
                "'IS_COLLECTED' INTEGER DEFAULT 0);"); // 11: isCollected  这里加上自己的修改DEFAULT(0)
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'USER_HEALTHKNOWAGE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, UserHealthknowage entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer userknowledgemappid = entity.getUserknowledgemappid();
        if (userknowledgemappid != null) {
            stmt.bindLong(2, userknowledgemappid);
        }
 
        Integer temp_picturelistid = entity.getTemp_picturelistid();
        if (temp_picturelistid != null) {
            stmt.bindLong(3, temp_picturelistid);
        }
 
        java.util.Date healthknowledgetime = entity.getHealthknowledgetime();
        if (healthknowledgetime != null) {
            stmt.bindLong(4, healthknowledgetime.getTime());
        }
 
        Integer userid = entity.getUserid();
        if (userid != null) {
            stmt.bindLong(5, userid);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(6, username);
        }
 
        Integer healthknowltypeid = entity.getHealthknowltypeid();
        if (healthknowltypeid != null) {
            stmt.bindLong(7, healthknowltypeid);
        }
 
        String healthknowltypename = entity.getHealthknowltypename();
        if (healthknowltypename != null) {
            stmt.bindString(8, healthknowltypename);
        }
 
        String healthknowledgetitle = entity.getHealthknowledgetitle();
        if (healthknowledgetitle != null) {
            stmt.bindString(9, healthknowledgetitle);
        }
 
        String healthknowledgecontent = entity.getHealthknowledgecontent();
        if (healthknowledgecontent != null) {
            stmt.bindString(10, healthknowledgecontent);
        }
 
        Integer healthknowledgecode = entity.getHealthknowledgecode();
        if (healthknowledgecode != null) {
            stmt.bindLong(11, healthknowledgecode);
        }
 
        Boolean isCollected = entity.getIsCollected();
        if (isCollected != null) {
            stmt.bindLong(12, isCollected ? 1l: 0l);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public UserHealthknowage readEntity(Cursor cursor, int offset) {
        UserHealthknowage entity = new UserHealthknowage( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // userknowledgemappid
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // temp_picturelistid
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)), // healthknowledgetime
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // userid
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // username
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // healthknowltypeid
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // healthknowltypename
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // healthknowledgetitle
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // healthknowledgecontent
            cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10), // healthknowledgecode
            cursor.isNull(offset + 11) ? null : cursor.getShort(offset + 11) != 0 // isCollected
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UserHealthknowage entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserknowledgemappid(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setTemp_picturelistid(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setHealthknowledgetime(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
        entity.setUserid(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setUsername(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setHealthknowltypeid(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setHealthknowltypename(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setHealthknowledgetitle(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setHealthknowledgecontent(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setHealthknowledgecode(cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10));
        entity.setIsCollected(cursor.isNull(offset + 11) ? null : cursor.getShort(offset + 11) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(UserHealthknowage entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(UserHealthknowage entity) {
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
