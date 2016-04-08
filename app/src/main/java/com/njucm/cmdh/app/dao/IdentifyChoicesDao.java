package com.njucm.cmdh.app.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.njucm.cmdh.app.domain.IdentifyChoices;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table IDENTIFY_CHOICES.
*/
public class IdentifyChoicesDao extends AbstractDao<IdentifyChoices, Long> {

    public static final String TABLENAME = "IDENTIFY_CHOICES";

    /**
     * Properties of entity IdentifyChoices.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Identifychoiceid = new Property(1, Integer.class, "identifychoiceid", false, "IDENTIFYCHOICEID");
        public final static Property Identifychoicevalue = new Property(2, Integer.class, "identifychoicevalue", false, "IDENTIFYCHOICEVALUE");
        public final static Property Scriptdescribe = new Property(3, String.class, "scriptdescribe", false, "SCRIPTDESCRIBE");
    };


    public IdentifyChoicesDao(DaoConfig config) {
        super(config);
    }
    
    public IdentifyChoicesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'IDENTIFY_CHOICES' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'IDENTIFYCHOICEID' INTEGER," + // 1: identifychoiceid
                "'IDENTIFYCHOICEVALUE' INTEGER," + // 2: identifychoicevalue
                "'SCRIPTDESCRIBE' TEXT);"); // 3: scriptdescribe
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'IDENTIFY_CHOICES'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, IdentifyChoices entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer identifychoiceid = entity.getIdentifychoiceid();
        if (identifychoiceid != null) {
            stmt.bindLong(2, identifychoiceid);
        }
 
        Integer identifychoicevalue = entity.getIdentifychoicevalue();
        if (identifychoicevalue != null) {
            stmt.bindLong(3, identifychoicevalue);
        }
 
        String scriptdescribe = entity.getScriptdescribe();
        if (scriptdescribe != null) {
            stmt.bindString(4, scriptdescribe);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public IdentifyChoices readEntity(Cursor cursor, int offset) {
        IdentifyChoices entity = new IdentifyChoices( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // identifychoiceid
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // identifychoicevalue
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // scriptdescribe
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, IdentifyChoices entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIdentifychoiceid(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setIdentifychoicevalue(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setScriptdescribe(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(IdentifyChoices entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(IdentifyChoices entity) {
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
