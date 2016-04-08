package com.njucm.cmdh.app.utils;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.dao.DaoSession;
import com.njucm.cmdh.app.dao.HealthSuggestionCombileSimplifyDao;
import com.njucm.cmdh.app.dao.TCMHealthknowledgeDao;
import com.njucm.cmdh.app.domain.HealthSuggestionCombileSimplify;
import com.njucm.cmdh.app.domain.UserHealthknowage;

import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import retrofit.http.Query;

/**
 * Created by Mesogene on 5/12/15.
 */
public class HealthSuggestionCombileSimplifyDBHelper {

    private static Context mContext;
    private static HealthSuggestionCombileSimplifyDBHelper instance;
    private HealthSuggestionCombileSimplifyDao healthSuggestionCombileSimplifyDao;
    public static HealthSuggestionCombileSimplifyDBHelper getInstance(Context context,String databaseName)
    {
        if (instance == null)
        {
            instance = new HealthSuggestionCombileSimplifyDBHelper();
            if (mContext == null)
            {
                mContext = context;
            }

            DaoSession daoSession = MyApplication.getDaoSession(mContext, databaseName);
            instance.healthSuggestionCombileSimplifyDao = daoSession.getHealthSuggestionCombileSimplifyDao();
        }
        return instance;
    }

    /**
     * 添加健康建议收藏
     */
    public void addHealthSuggestionSimplify(HealthSuggestionCombileSimplify item){
        healthSuggestionCombileSimplifyDao.insert(item);
    }

    /**
     * 根据id删除健康建议
     */
    public void deleteHealthSuggestionSimplify(int recommendid){
        QueryBuilder<HealthSuggestionCombileSimplify> qb = healthSuggestionCombileSimplifyDao.queryBuilder();
        DeleteQuery<HealthSuggestionCombileSimplify> bd = qb.where(HealthSuggestionCombileSimplifyDao.Properties.Healthsuggestcode.eq(recommendid)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
    }

    /**
     * 根据recommendid来显示收藏或者没有收藏
     */
    public Boolean findCollectStatusById(int recommendid){
        QueryBuilder qb = healthSuggestionCombileSimplifyDao.queryBuilder();
        qb.where(HealthSuggestionCombileSimplifyDao.Properties.Recommendedconditionsmappid.eq(recommendid));
        List<HealthSuggestionCombileSimplify> list = qb.list();
        if(list.size()!=0)
            return true;
        else
            return false;
    }

    /**
     * 获取数据库所有数据
     */
    public List<HealthSuggestionCombileSimplify> getrecommendidList(){
        QueryBuilder<HealthSuggestionCombileSimplify> queryBuilder = healthSuggestionCombileSimplifyDao.queryBuilder();
        return queryBuilder.list();
    }

}
