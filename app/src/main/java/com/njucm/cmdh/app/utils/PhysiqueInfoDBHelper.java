package com.njucm.cmdh.app.utils;

import android.content.Context;

import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.dao.DaoSession;
import com.njucm.cmdh.app.dao.PhysiqueInfoDao;
import com.njucm.cmdh.app.domain.PhysiqueInfo;

import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Mesogene on 5/13/15.
 */
public class PhysiqueInfoDBHelper {
    private static Context mContext;
    private static PhysiqueInfoDBHelper instance;
    private PhysiqueInfoDao physiqueInfoDao;
    public PhysiqueInfoDBHelper(){}
    public static  PhysiqueInfoDBHelper getInstance(Context context,String databaseName) {
        if (instance == null) {
            instance = new PhysiqueInfoDBHelper();
            if (mContext == null) {
                mContext = context;
            }

            // ?????
            DaoSession daoSession = MyApplication.getDaoSession(mContext, databaseName);
            instance.physiqueInfoDao = daoSession.getPhysiqueInfoDao();
        }
        return instance;
    }
    /**
     * 增加体质信息
     */
    public void addPhysiqueInfo(PhysiqueInfo item){
        physiqueInfoDao.insert(item);
    }
    /**
     * 获取体质信息表
     */
    public List<PhysiqueInfo>getPhysiqueInfoList(){
        QueryBuilder<PhysiqueInfo> qb = physiqueInfoDao.queryBuilder();
        return qb.list();
    }
    /**
     * 加载所有体质信息
     */
    public List<PhysiqueInfo>getPhysiqueInfo(){
        return physiqueInfoDao.loadAll();
    }
    /**
     * 按体质ID获取信息
     */
    public List<PhysiqueInfo>getPhysiqueInfoList(int physiqueinfoid){
        QueryBuilder<PhysiqueInfo> qb = physiqueInfoDao.queryBuilder().where(PhysiqueInfoDao.Properties.Physiqueinfoid.eq(physiqueinfoid));
        return qb.list();
    }
    /**
     * 删除体质信息
     */
    public void deletePhysiqueInfo(int Id){
        QueryBuilder<PhysiqueInfo> qb = physiqueInfoDao.queryBuilder();
        DeleteQuery<PhysiqueInfo> db = qb.where(PhysiqueInfoDao.Properties.Physiqueinfoid.eq(Id)).buildDelete();
        db.executeDeleteWithoutDetachingEntities();
    }
    /**
     * 清空体质信息
     */
    public void clearPhysiqueInfo(){
        physiqueInfoDao.deleteAll();
    }
}
