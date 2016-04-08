package com.njucm.cmdh.app.utils;

import android.content.Context;

import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.dao.DaoSession;
import com.njucm.cmdh.app.dao.IdentificationIssuessDao;
import com.njucm.cmdh.app.domain.IdentificationIssuess;

import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Mesogene on 5/10/15.
 */
public class IdentificationIssuessDBHelper {
    private static Context mContext;
    private static IdentificationIssuessDBHelper instance;
    private IdentificationIssuessDao identificationIssuessDao;
    public IdentificationIssuessDBHelper(){}
    public static IdentificationIssuessDBHelper getInstance(Context context,String databaseName)
    {
        if (instance == null)
        {
            instance = new IdentificationIssuessDBHelper();
            if (mContext == null)
            {
                mContext = context;
            }

            // ?????
            DaoSession daoSession = MyApplication.getDaoSession(mContext, databaseName);

            instance.identificationIssuessDao = daoSession.getIdentificationIssuessDao();
        }
        return instance;
    }
    /**
     *增加问卷题目
     */
    public void addIdentificationIssuess(IdentificationIssuess item){
        identificationIssuessDao.insert(item);
    }
    /**
     *获取体质辨识问题表
     */
    public List<IdentificationIssuess> getTcmidentifyquestionList(){
        QueryBuilder<IdentificationIssuess> qb=identificationIssuessDao.queryBuilder();
        return qb.list();
    }
    /**
     * 加载所有问题
     */
    public List<IdentificationIssuess> getTcmidentifyquestion() {
        return identificationIssuessDao.loadAll();// 加载所有
    }
    /**
     * 按体质类型获取问题
     */
    public List<IdentificationIssuess> getTcmidentifyquestion(int temp_physiqueinfoid){
        QueryBuilder<IdentificationIssuess> qb = identificationIssuessDao.queryBuilder().where(IdentificationIssuessDao.Properties.Temp_physiqueinfoid.eq(temp_physiqueinfoid));
        return qb.list();
    }
    /**
     * 判断是否存在
     */
    public boolean isSaved(int Id) {
        QueryBuilder<IdentificationIssuess> qb = identificationIssuessDao.queryBuilder();
        qb.where(IdentificationIssuessDao.Properties.Id.eq(Id));
        qb.buildCount().count();
        return qb.buildCount().count() > 0 ? true : false;// ?????
    }
    /**
     * 删除问卷题目
     */
    public void deleteTcmidentifyquestionList(int Id){
        QueryBuilder<IdentificationIssuess> qb = identificationIssuessDao.queryBuilder();
        DeleteQuery<IdentificationIssuess> bd = qb.where(IdentificationIssuessDao.Properties.Id.eq(Id)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
    }
    /**
     * 清空问卷题目
     */
    public void clearTcmidentifyquestion(){identificationIssuessDao.deleteAll();}
    /**
     * 获取问卷题目ID
     */
    public Long getIdentifyquestionId(int identifyissueId){
        QueryBuilder<IdentificationIssuess> qb = identificationIssuessDao.queryBuilder();
        qb.where(IdentificationIssuessDao.Properties.Id.eq(identifyissueId));
        if(qb.list().size()>0){
            return qb.list().get(0).getId();
        }else{
            return null;
        }
    }
    public List<IdentificationIssuess> getTcmidentifyquestionList(int identifyissueId){
        QueryBuilder<IdentificationIssuess> qb = identificationIssuessDao.queryBuilder();
        qb.where(qb.and(IdentificationIssuessDao.Properties.Id.eq(identifyissueId),IdentificationIssuessDao.Properties.Identifyissueid.eq(HBContants.DATABASE_NAME_USERHEALTHKNOWLEDGE)));
        qb.orderAsc(IdentificationIssuessDao.Properties.Id);
        return qb.list();
    }

}
