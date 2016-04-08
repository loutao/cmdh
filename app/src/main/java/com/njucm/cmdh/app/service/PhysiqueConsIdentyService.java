package com.njucm.cmdh.app.service;

import android.content.Context;

import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.dao.ConsitituteIdentifyResultDao;
import com.njucm.cmdh.app.dao.DaoSession;
import com.njucm.cmdh.app.dao.HealthKnowledgeTypeDao;
import com.njucm.cmdh.app.dao.IdentificationIssuessDao;
import com.njucm.cmdh.app.dao.IdentifyChoicesDao;
import com.njucm.cmdh.app.dao.PhysiqueInfoDao;
import com.njucm.cmdh.app.dao.TCMHealthknowledgeDao;
import com.njucm.cmdh.app.dao.UserAnswerRecordsDao;
import com.njucm.cmdh.app.domain.PhysiqueInfo;
import com.njucm.cmdh.app.domain.TCMHealthknowledge;
import com.njucm.cmdh.app.utils.HBContants;

import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Mesogene on 4/3/15.
 */
public class PhysiqueConsIdentyService {
    private static Context mContext;
    private static PhysiqueConsIdentyService instance;


    private TCMHealthknowledgeDao TCMHealthknowledgeDAO;
    private PhysiqueInfoDao physiqueInfoDao;
    private ConsitituteIdentifyResultDao consitituteIdentifyResultDao;
    private IdentificationIssuessDao identificationIssuessDao;
    private UserAnswerRecordsDao userAnswerRecordsDao;
    private IdentifyChoicesDao identifyChoicesDao;
    private HealthKnowledgeTypeDao healthKnowledgeTypeDao;


    public PhysiqueConsIdentyService() {
    }

    public static PhysiqueConsIdentyService getInstance(Context context, String databaseName) {
        if (instance == null) {
            instance = new PhysiqueConsIdentyService();
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
     * 增加养生知识记录
     */
    public void addToPhysiqueInfoTable(PhysiqueInfo item) {
        physiqueInfoDao.insert(item);
    }

    /**
     * 获取养生知识列表
     */
    public List<PhysiqueInfo> getPhysiqueInfoList() {
        QueryBuilder<PhysiqueInfo> qb = physiqueInfoDao.queryBuilder();
        return qb.list();
    }

    /**
     * 加载所有养生知识
     */
    public List<PhysiqueInfo> getAllPhysiqueInfo() {
        return physiqueInfoDao.loadAll();// 加载所有
    }

    /**
     * 判断是否存在
     */
    public boolean isSaved(int Id) {
        QueryBuilder<PhysiqueInfo> qb = physiqueInfoDao.queryBuilder();
        qb.where(PhysiqueInfoDao.Properties.Id.eq(Id));
        qb.buildCount().count();
        return qb.buildCount().count() > 0 ? true : false;// ?????
    }

    /**
     * 删除养生知识记录
     */
    public void deletePhysiqueInfoList(int Id) {
        QueryBuilder<PhysiqueInfo> qb = physiqueInfoDao.queryBuilder();
        DeleteQuery<PhysiqueInfo> bd = qb.where(PhysiqueInfoDao.Properties.Id.eq(Id)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
    }

    /**
     * 清空养生知识表
     */
    public void clearPhysiqueInfo() {
        physiqueInfoDao.deleteAll();
    }

    /**
     * 获取养生知识ID
     */
    public Long getPhysiqueInfoId(int physiqueinfoId) {
        QueryBuilder<PhysiqueInfo> qb = physiqueInfoDao.queryBuilder();
        qb.where(PhysiqueInfoDao.Properties.Id.eq(physiqueinfoId));
        if (qb.list().size() > 0) {
            return qb.list().get(0).getId();
        } else {
            return null;
        }
    }

    /**
     * ????
     */
    public List<PhysiqueInfo> getIphRegionList(int physiqueinfoId) {
        QueryBuilder<PhysiqueInfo> qb = physiqueInfoDao.queryBuilder();
        qb.where(qb.and(PhysiqueInfoDao.Properties.Id.eq(physiqueinfoId), PhysiqueInfoDao.Properties.Physicaltypename.eq(HBContants.CITYINFO_IR)));
        qb.orderAsc(PhysiqueInfoDao.Properties.Id);// ????
        return qb.list();
    }
}
