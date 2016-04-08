package com.njucm.cmdh.app.utils;

import android.content.Context;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.dao.ConsitituteIdentifyResultDao;
import com.njucm.cmdh.app.dao.DaoSession;
import com.njucm.cmdh.app.domain.ConsitituteIdentifyResult;


import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Mesogene on 5/16/15.
 */
public class ConstituteIdentifyResultDBHelper {
    private static Context mContext;
    private static ConstituteIdentifyResultDBHelper instance;
    private ConsitituteIdentifyResultDao consitituteIdentifyResultDao;
    public static ConstituteIdentifyResultDBHelper getInstance(Context context,String databaseName){
        if(instance==null){
            instance = new ConstituteIdentifyResultDBHelper();
            if(mContext==null){
                mContext = context;
            }
            DaoSession daoSession = MyApplication.getDaoSession(mContext,databaseName);
            instance.consitituteIdentifyResultDao = daoSession.getConsitituteIdentifyResultDao();
        }
        return instance;
    }
    /**
     * 增加体质辨识结果记录
     */
    public void addConsitituteIdentifyResult(ConsitituteIdentifyResult item){consitituteIdentifyResultDao.insert(item);}
    /**
     * 获取体质辨识结果记录表
     */
    public List<ConsitituteIdentifyResult> getConsitituteIdentifyResultList(){
        QueryBuilder<ConsitituteIdentifyResult> qb = consitituteIdentifyResultDao.queryBuilder();
        return qb.list();
    }

        /**
         * 按ID获取体质辨识结果记录
         */
    public List<ConsitituteIdentifyResult> getConsitituteIdentifyResultList(int identifyresultid){
        QueryBuilder<ConsitituteIdentifyResult> qb = consitituteIdentifyResultDao.queryBuilder().where(ConsitituteIdentifyResultDao.Properties.Identifyresultid.eq(identifyresultid));
        return qb.list();
    }
    /**
     * 删除体质辨识结果记录
     */
    public void deleteConsitituteIdentifyResultList(int Id){
        QueryBuilder<ConsitituteIdentifyResult> qb = consitituteIdentifyResultDao.queryBuilder();
        DeleteQuery<ConsitituteIdentifyResult>db = qb.where(ConsitituteIdentifyResultDao.Properties.Id.eq(Id)).buildDelete();
        db.executeDeleteWithoutDetachingEntities();
    }
    /**
     * 清空全表
     */
    public void clearConsitituteIdentifyResult(){
        consitituteIdentifyResultDao.deleteAll();
    }

}
