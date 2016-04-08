package com.njucm.cmdh.app.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.njucm.cmdh.app.domain.PhysiqueInfo;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table PHYSIQUE_INFO.
 */
public class PhysiqueInfoDao extends AbstractDao<PhysiqueInfo, Long> {

    public static final String TABLENAME = "PHYSIQUE_INFO";

    /**
     * Properties of entity PhysiqueInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Physiqueinfoid = new Property(1, Integer.class, "physiqueinfoid", false, "PHYSIQUEINFOID");
        public final static Property Tablescoreheight = new Property(2, Double.class, "tablescoreheight", false, "TABLESCOREHEIGHT");
        public final static Property Tablescorelow = new Property(3, Double.class, "tablescorelow", false, "TABLESCORELOW");
        public final static Property Switchscoreheight = new Property(4, Double.class, "switchscoreheight", false, "SWITCHSCOREHEIGHT");
        public final static Property Switchscorelow = new Property(5, Double.class, "switchscorelow", false, "SWITCHSCORELOW");
        public final static Property Generacharacter = new Property(6, String.class, "generacharacter", false, "GENERACHARACTER");
        public final static Property ShapeFeature = new Property(7, String.class, "ShapeFeature", false, "SHAPE_FEATURE");
        public final static Property Commonmanifest = new Property(8, String.class, "commonmanifest", false, "COMMONMANIFEST");
        public final static Property Mentalcharacter = new Property(9, String.class, "mentalcharacter", false, "MENTALCHARACTER");
        public final static Property Incidencetendency = new Property(10, String.class, "incidencetendency", false, "INCIDENCETENDENCY");
        public final static Property Adaptivecapacity = new Property(11, String.class, "adaptivecapacity", false, "ADAPTIVECAPACITY");
        public final static Property Physicaltypename = new Property(12, String.class, "physicaltypename", false, "PHYSICALTYPENAME");
        public final static Property Physicaltypecode = new Property(13, Integer.class, "physicaltypecode", false, "PHYSICALTYPECODE");
        public final static Property Adjustmethod = new Property(14,String.class,"adjustmethod",false,"ADJUSTMETHOD");
        public final static Property Multiplepeople = new Property(15,String.class,"multiplepeople",false,"MULTIPLEPEOPLE");
        public final static Property Physicalinterpretation = new Property(16,String.class,"physicalinterpretation",false,"PHYSICALINTERPRETATION");
        public final static Property Physicalreason = new Property(17,String.class,"physicalreason",false,"PHYSICALREASON");
        public final static Property Physicaladjustmethod = new Property(18,String.class,"physicaladjustmethod",false,"PHYSICALADJUSTMETHOD");
        public final static Property foodallowtaboo = new Property(19,String.class,"foodallowtaboo",false,"FOODALLOWTABOO");
    };


    public PhysiqueInfoDao(DaoConfig config) {
        super(config);
    }

    public PhysiqueInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PHYSIQUE_INFO' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'PHYSIQUEINFOID' INTEGER," + // 1: physiqueinfoid
                "'TABLESCOREHEIGHT' REAL," + // 2: tablescoreheight
                "'TABLESCORELOW' REAL," + // 3: tablescorelow
                "'SWITCHSCOREHEIGHT' REAL," + // 4: switchscoreheight
                "'SWITCHSCORELOW' REAL," + // 5: switchscorelow
                "'GENERACHARACTER' TEXT," + // 6: generacharacter
                "'SHAPE_FEATURE' TEXT," + // 7: ShapeFeature
                "'COMMONMANIFEST' TEXT," + // 8: commonmanifest
                "'MENTALCHARACTER' TEXT," + // 9: mentalcharacter
                "'INCIDENCETENDENCY' TEXT," + // 10: incidencetendency
                "'ADAPTIVECAPACITY' TEXT," + // 11: adaptivecapacity
                "'PHYSICALTYPENAME' TEXT," + // 12: physicaltypename
                "'PHYSICALTYPECODE' INTEGER,"+ // 13: physicaltypecode
                "'ADJUSTMETHOD' TEXT,"+ //14:adjustmethod
                "'MULTIPLEPEOPLE' TEXT,"+ //15:multiplepeople
                "'PHYSICALINTERPRETATION' TEXT,"+ //16:physicalinterpretation
                "'PHYSICALREASON' TEXT,"+ //17:physicalreason
                "'PHYSICALADJUSTMETHOD' TEXT,"+ //18:physicaladjustmethod
                "'FOODALLOWTABOO' TEXT);"); //19:foodallowtaboo
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PHYSIQUE_INFO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, PhysiqueInfo entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        Integer physiqueinfoid = entity.getPhysiqueinfoid();
        if (physiqueinfoid != null) {
            stmt.bindLong(2, physiqueinfoid);
        }

        Double tablescoreheight = entity.getTablescoreheight();
        if (tablescoreheight != null) {
            stmt.bindDouble(3, tablescoreheight);
        }

        Double tablescorelow = entity.getTablescorelow();
        if (tablescorelow != null) {
            stmt.bindDouble(4, tablescorelow);
        }

        Double switchscoreheight = entity.getSwitchscoreheight();
        if (switchscoreheight != null) {
            stmt.bindDouble(5, switchscoreheight);
        }

        Double switchscorelow = entity.getSwitchscorelow();
        if (switchscorelow != null) {
            stmt.bindDouble(6, switchscorelow);
        }

        String generacharacter = entity.getGeneracharacter();
        if (generacharacter != null) {
            stmt.bindString(7, generacharacter);
        }

        String ShapeFeature = entity.getShapefeature();
        if (ShapeFeature != null) {
            stmt.bindString(8, ShapeFeature);
        }

        String commonmanifest = entity.getCommonmanifest();
        if (commonmanifest != null) {
            stmt.bindString(9, commonmanifest);
        }

        String mentalcharacter = entity.getMentalcharacter();
        if (mentalcharacter != null) {
            stmt.bindString(10, mentalcharacter);
        }

        String incidencetendency = entity.getIncidencetendency();
        if (incidencetendency != null) {
            stmt.bindString(11, incidencetendency);
        }

        String adaptivecapacity = entity.getAdaptivecapacity();
        if (adaptivecapacity != null) {
            stmt.bindString(12, adaptivecapacity);
        }

        String physicaltypename = entity.getPhysicaltypename();
        if (physicaltypename != null) {
            stmt.bindString(13, physicaltypename);
        }

        Integer physicaltypecode = entity.getPhysicaltypecode();
        if (physicaltypecode != null) {
            stmt.bindLong(14, physicaltypecode);
        }
        String adjustmethod = entity.getAdjustmethod();
        if(adjustmethod !=null){
            stmt.bindString(15,adjustmethod);
        }
        String multiplepeople = entity.getMultiplepeople();
        if(multiplepeople!=null){
            stmt.bindString(16,multiplepeople);
        }
        String physicalinterpretation = entity.getPhysicalinterpretation();
        if(physicalinterpretation!=null){
            stmt.bindString(17,physicalinterpretation);
        }
        String physicalreason =entity.getPhysicalreason();
        if(physicalreason!=null){
            stmt.bindString(18,physicalreason);
        }
        String physicaladjustmethod = entity.getPhysicaladjustmethod();
        if(physicaladjustmethod!=null){
            stmt.bindString(19,physicaladjustmethod);
        }
        String foodallowtaboo = entity.getFoodallowtaboo();
        if(foodallowtaboo!=null){
            stmt.bindString(20,foodallowtaboo);
        }

    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public PhysiqueInfo readEntity(Cursor cursor, int offset) {
        PhysiqueInfo entity = new PhysiqueInfo( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // physiqueinfoid
                cursor.isNull(offset + 2) ? null : cursor.getDouble(offset + 2), // tablescoreheight
                cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3), // tablescorelow
                cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4), // switchscoreheight
                cursor.isNull(offset + 5) ? null : cursor.getDouble(offset + 5), // switchscorelow
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // generacharacter
                cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // ShapeFeature
                cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // commonmanifest
                cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // mentalcharacter
                cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // incidencetendency
                cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // adaptivecapacity
                cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // physicaltypename
                cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13), // physicaltypecode
                cursor.isNull(offset + 14) ? null :cursor.getString(offset + 14),// adjustmethod
                cursor.isNull(offset + 15) ? null :cursor.getString(offset + 15),// multiplepeople
                cursor.isNull(offset + 16) ? null :cursor.getString(offset + 16),// physicalinterpretation
                cursor.isNull(offset + 17) ? null :cursor.getString(offset + 17),// physicalreason
                cursor.isNull(offset + 18) ? null :cursor.getString(offset + 18),// physicaladjustmethod
                cursor.isNull(offset + 19) ? null :cursor.getString(offset + 19) // foodallowtaboo
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, PhysiqueInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPhysiqueinfoid(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setTablescoreheight(cursor.isNull(offset + 2) ? null : cursor.getDouble(offset + 2));
        entity.setTablescorelow(cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3));
        entity.setSwitchscoreheight(cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4));
        entity.setSwitchscorelow(cursor.isNull(offset + 5) ? null : cursor.getDouble(offset + 5));
        entity.setGeneracharacter(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setShapefeature(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCommonmanifest(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setMentalcharacter(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setIncidencetendency(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setAdaptivecapacity(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setPhysicaltypename(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setPhysicaltypecode(cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13));
        entity.setAdjustmethod(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setMultiplepeople(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setPhysicalinterpretation(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setPhysicalreason(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setPhysicaladjustmethod(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setFoodallowtaboo(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));

    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(PhysiqueInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(PhysiqueInfo entity) {
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
