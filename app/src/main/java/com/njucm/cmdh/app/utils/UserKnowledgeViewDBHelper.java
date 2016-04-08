package com.njucm.cmdh.app.utils;

import android.content.Context;

import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.dao.DaoSession;
import com.njucm.cmdh.app.dao.TCMHealthknowledgeDao;
import com.njucm.cmdh.app.dao.UserHealthknowageDao;
import com.njucm.cmdh.app.domain.UserHealthknowage;

import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Mesogene on 4/21/15.
 */
public class UserKnowledgeViewDBHelper {

    private static Context mContext;
    private static UserKnowledgeViewDBHelper instance;
    private UserHealthknowageDao userHealthknowageDao;
    public static UserKnowledgeViewDBHelper getInstance(Context context,String databaseName)
    {
        if (instance == null)
        {
            instance = new UserKnowledgeViewDBHelper();
            if (mContext == null)
            {
                mContext = context;
            }

            DaoSession daoSession = MyApplication.getDaoSession(mContext, databaseName);
            instance.userHealthknowageDao = daoSession.getUserHealthknowageDao();
        }
        return instance;
    }

    /**
     * 增加个人健康建议记录
     */
    public void addUserHealthknowage(UserHealthknowage item){
        userHealthknowageDao.insert(item);
    }

    /**
     * 获取个人健康建议列表
     */
    public List<UserHealthknowage> getUserHealthknowageList(String type){
        QueryBuilder<UserHealthknowage> qb = userHealthknowageDao.queryBuilder().where(UserHealthknowageDao.Properties.Healthknowltypename.eq(type));
        return qb.list();
    }

    /**
     * 删除养生知识记录
     */
    public void deleteTcmhealthknowledgeList(int Id) {
        QueryBuilder<UserHealthknowage> qb = userHealthknowageDao.queryBuilder();
        DeleteQuery<UserHealthknowage> bd = qb.where(TCMHealthknowledgeDao.Properties.Id.eq(Id)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
    }

    /**
     * 清空全表
     */
    public void clearUserHealthknowledge(){
        userHealthknowageDao.deleteAll();
    }


    public List<UserHealthknowage> getUserHealthknowageListByType(String type){
        QueryBuilder<UserHealthknowage> qb = userHealthknowageDao.queryBuilder().where(UserHealthknowageDao.Properties.Healthknowltypename.eq(type));
        return qb.list();
    }

    public void deleteUserHealthknowageByType(String type){
        QueryBuilder<UserHealthknowage> qb = userHealthknowageDao.queryBuilder();
        DeleteQuery<UserHealthknowage> bd = qb.where(UserHealthknowageDao.Properties.Healthknowltypename.eq(type)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
    }

    /**
     * @param type  在3种类型中选择一种修改
     * @param collectState  收藏过就用true，没有收藏过就用false
     */
    public void updateCollectedState(String type, boolean collectState){
        QueryBuilder<UserHealthknowage> qb = userHealthknowageDao.queryBuilder();
        qb.where(UserHealthknowageDao.Properties.Healthknowltypename.eq(type));
        List<UserHealthknowage> list = qb.list();
        UserHealthknowage tempitem = list.get(0);
        tempitem.setIsCollected(collectState);
        userHealthknowageDao.insertOrReplaceInTx(tempitem);
    }

    public boolean getItemCollectedStatus(String type){
        QueryBuilder qb = userHealthknowageDao.queryBuilder();
        qb.where(UserHealthknowageDao.Properties.Healthknowltypename.eq(type));
        List<UserHealthknowage> list = qb.list();
        UserHealthknowage tempitem = list.get(0);
        boolean isCollected = tempitem.getIsCollected();
        return isCollected;
    }

    public Date getItemTime(String type){
        QueryBuilder qb = userHealthknowageDao.queryBuilder();
        qb.where(UserHealthknowageDao.Properties.Healthknowltypename.eq(type));
        List<UserHealthknowage> list = qb.list();
        UserHealthknowage tempitem = list.get(0);
        Date time = tempitem.getHealthknowledgetime();
        return time;
    }
}
