package com.njucm.cmdh.app.utils;

import android.content.Context;

import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.dao.DaoSession;
import com.njucm.cmdh.app.dao.IdentificationIssuessDao;
import com.njucm.cmdh.app.dao.TCMHealthknowledgeDao;
import com.njucm.cmdh.app.domain.IdentificationIssuess;
import com.njucm.cmdh.app.domain.TCMHealthknowledge;

import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Mesogene on 3/21/15.
 */
public class DBHelper {
    private static Context mContext;
    private static DBHelper instance;

    private TCMHealthknowledgeDao TCMHealthknowledgeDAO;

    public DBHelper()
    {
    }

    public static DBHelper getInstance(Context context,String databaseName)
    {
        if (instance == null)
        {
            instance = new DBHelper();
            if (mContext == null)
            {
                mContext = context;
            }

            // ?????
            DaoSession daoSession = MyApplication.getDaoSession(mContext, databaseName);
            instance.TCMHealthknowledgeDAO = daoSession.getTCMHealthknowledgeDao();

        }
        return instance;
    }

    /**
     * 增加养生知识记录
     */
    public void addToTcmhealthknowledgeTable(TCMHealthknowledge item) {
        TCMHealthknowledgeDAO.insert(item);
    }

    /**
     * 获取养生知识列表
     */
    public List<TCMHealthknowledge> getTcmhealthknowledgeList() {
        QueryBuilder<TCMHealthknowledge> qb = TCMHealthknowledgeDAO.queryBuilder();
        return qb.list();
    }
    /**
     * 加载所有养生知识
     */
    public List<TCMHealthknowledge> getTcmhealthknowledge() {
        return TCMHealthknowledgeDAO.loadAll();// 加载所有
    }

    /**
     * 判断是否存在
     */
    public boolean isSaved(int Id) {
        QueryBuilder<TCMHealthknowledge> qb = TCMHealthknowledgeDAO.queryBuilder();
        qb.where(TCMHealthknowledgeDao.Properties.Id.eq(Id));
        qb.buildCount().count();
        return qb.buildCount().count() > 0 ? true : false;// ?????
    }

    /**
     * 删除养生知识记录
     */
    public void deleteTcmhealthknowledgeList(int Id) {
        QueryBuilder<TCMHealthknowledge> qb = TCMHealthknowledgeDAO.queryBuilder();
        DeleteQuery<TCMHealthknowledge> bd = qb.where(TCMHealthknowledgeDao.Properties.Id.eq(Id)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
    }

    /**
     * 清空养生知识表
     */
    public void clearTcmhealthknowledge() {
        TCMHealthknowledgeDAO.deleteAll();
    }

    /**
     * 获取养生知识ID
     */
    public Long getHealthKnowledgeId(int healthknowltypeId) {
        QueryBuilder<TCMHealthknowledge> qb = TCMHealthknowledgeDAO.queryBuilder();
        qb.where(TCMHealthknowledgeDao.Properties.Id.eq(healthknowltypeId));
        if (qb.list().size() > 0) {
            return qb.list().get(0).getId();
        }
        else
        {
            return null;
        }
    }

    /**
     * ????
     */
    public List<TCMHealthknowledge> getIphRegionList(int healthknowledgeId) {
        QueryBuilder<TCMHealthknowledge> qb = TCMHealthknowledgeDAO.queryBuilder();
        qb.where(qb.and(TCMHealthknowledgeDao.Properties.Id.eq(healthknowledgeId), TCMHealthknowledgeDao.Properties.Healthknowledgetitle.eq(HBContants.CITYINFO_IR)));
        qb.orderAsc(TCMHealthknowledgeDao.Properties.Id);// ????
        return qb.list();
    }


}
