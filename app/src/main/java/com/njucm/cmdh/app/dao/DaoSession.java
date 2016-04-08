package com.njucm.cmdh.app.dao;

import android.database.sqlite.SQLiteDatabase;

import com.njucm.cmdh.app.domain.AverageAmountStd;
import com.njucm.cmdh.app.domain.ConsitituteIdentifyResult;
import com.njucm.cmdh.app.domain.DietaryRecords;
import com.njucm.cmdh.app.domain.EatingAnalysis;
import com.njucm.cmdh.app.domain.EatingPreferRecords;
import com.njucm.cmdh.app.domain.ExercisePreferRecords;
import com.njucm.cmdh.app.domain.FoodFeature;
import com.njucm.cmdh.app.domain.HealthKnowledgeType;
import com.njucm.cmdh.app.domain.HealthSuggType;
import com.njucm.cmdh.app.domain.HealthSuggestionCombileSimplify;
import com.njucm.cmdh.app.domain.HealthSuggestions;
import com.njucm.cmdh.app.domain.HealthSuggestionsMapp;
import com.njucm.cmdh.app.domain.IdentificationIssuess;
import com.njucm.cmdh.app.domain.IdentifyChoices;
import com.njucm.cmdh.app.domain.PhysiqueInfo;
import com.njucm.cmdh.app.domain.SleepInfoRecords;
import com.njucm.cmdh.app.domain.SleepPreferRecords;
import com.njucm.cmdh.app.domain.SportInfoRecords;
import com.njucm.cmdh.app.domain.TCMHealthknowledge;
import com.njucm.cmdh.app.domain.User;
import com.njucm.cmdh.app.domain.UserAnswerRecords;
import com.njucm.cmdh.app.domain.UserExerciseFeature;
import com.njucm.cmdh.app.domain.UserHealthknowage;
import com.njucm.cmdh.app.domain.UserKnowledgeMapp;
import com.njucm.cmdh.app.domain.UserSleepFeature;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig healthknowledgetypeDaoConfig;
    private final DaoConfig tcmhealthknowledgeDaoConfig;
    private final DaoConfig PhysiqueInfoDaoConfig;
    private final DaoConfig IdentifyChoicesDaoConfig;
    private final DaoConfig IdentificationIssuessDaoConfig;
    private final DaoConfig UserAnswerRecordsDaoConfig;
    private final DaoConfig ConsitituteIdentifyResultDaoConfig;
    private final DaoConfig userKnowledgeMappDaoConfig;
    private final DaoConfig healthSuggestionsMappDaoConfig;
    private final DaoConfig healthSuggTypeDaoConfig;
    private final DaoConfig healthSuggestionsDaoConfig;
    private final DaoConfig userDaoConfig;
    private final DaoConfig userExerciseFeatureDaoConfig;
    private final DaoConfig userSleepFeatureDaoConfig;
    private final DaoConfig foodFeatureDaoConfig;
    private final DaoConfig dietaryRecordsDaoConfig;
    private final DaoConfig eatingAnalysisDaoConfig;
    private final DaoConfig sportInfoRecordsDaoConfig;
    private final DaoConfig sleepInfoRecordsDaoConfig;
    private final DaoConfig eatingPreferRecordsDaoConfig;
    private final DaoConfig sleepPreferRecordsDaoConfig;
    private final DaoConfig exercisePreferRecordsDaoConfig;
    private final DaoConfig averageAmountStdDaoConfig;
    private final DaoConfig userHealthknowageDaoConfig;
    private final DaoConfig healthSuggestionCombileSimplifyDaoConfig;

    private final UserDao userDao;
    private final UserExerciseFeatureDao userExerciseFeatureDao;
    private final UserSleepFeatureDao userSleepFeatureDao;
    private final FoodFeatureDao foodFeatureDao;
    private final DietaryRecordsDao dietaryRecordsDao;
    private final EatingAnalysisDao eatingAnalysisDao;
    private final SportInfoRecordsDao sportInfoRecordsDao;
    private final SleepInfoRecordsDao sleepInfoRecordsDao;
    private final EatingPreferRecordsDao eatingPreferRecordsDao;
    private final SleepPreferRecordsDao sleepPreferRecordsDao;
    private final ExercisePreferRecordsDao exercisePreferRecordsDao;
    private final AverageAmountStdDao averageAmountStdDao;
    private final PhysiqueInfoDao PhysiqueInfoDao;
    private final IdentifyChoicesDao IdentifyChoicesDao;
    private final IdentificationIssuessDao IdentificationIssuessDao;
    private final UserAnswerRecordsDao UserAnswerRecordsDao;
    private final ConsitituteIdentifyResultDao ConsitituteIdentifyResultDao;
    private final HealthKnowledgeTypeDao healthKnowledgeTypeDao;
    private final TCMHealthknowledgeDao TCMHealthknowledgeDao;
    private final UserKnowledgeMappDao userKnowledgeMappDao;
    private final HealthSuggestionsMappDao healthSuggestionsMappDao;
    private final HealthSuggTypeDao healthSuggTypeDao;
    private final HealthSuggestionsDao healthSuggestionsDao;
    private final UserHealthknowageDao userHealthknowageDao;
    private final HealthSuggestionCombileSimplifyDao healthSuggestionCombileSimplifyDao;
    

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        healthknowledgetypeDaoConfig = daoConfigMap.get(HealthKnowledgeTypeDao.class).clone();
        healthknowledgetypeDaoConfig.initIdentityScope(type);

        tcmhealthknowledgeDaoConfig = daoConfigMap.get(TCMHealthknowledgeDao.class).clone();
        tcmhealthknowledgeDaoConfig.initIdentityScope(type);

        healthKnowledgeTypeDao = new HealthKnowledgeTypeDao(healthknowledgetypeDaoConfig, this);
        TCMHealthknowledgeDao = new TCMHealthknowledgeDao(tcmhealthknowledgeDaoConfig, this);

        PhysiqueInfoDaoConfig = daoConfigMap.get(PhysiqueInfoDao.class).clone();
        PhysiqueInfoDaoConfig.initIdentityScope(type);

        IdentifyChoicesDaoConfig = daoConfigMap.get(IdentifyChoicesDao.class).clone();
        IdentifyChoicesDaoConfig.initIdentityScope(type);

        IdentificationIssuessDaoConfig = daoConfigMap.get(IdentificationIssuessDao.class).clone();
        IdentificationIssuessDaoConfig.initIdentityScope(type);

        UserAnswerRecordsDaoConfig = daoConfigMap.get(UserAnswerRecordsDao.class).clone();
        UserAnswerRecordsDaoConfig.initIdentityScope(type);

        ConsitituteIdentifyResultDaoConfig = daoConfigMap.get(ConsitituteIdentifyResultDao.class).clone();
        ConsitituteIdentifyResultDaoConfig.initIdentityScope(type);

        userKnowledgeMappDaoConfig = daoConfigMap.get(UserKnowledgeMappDao.class).clone();
        userKnowledgeMappDaoConfig.initIdentityScope(type);

        healthSuggestionsMappDaoConfig = daoConfigMap.get(HealthSuggestionsMappDao.class).clone();
        healthSuggestionsMappDaoConfig.initIdentityScope(type);

        healthSuggTypeDaoConfig = daoConfigMap.get(HealthSuggTypeDao.class).clone();
        healthSuggTypeDaoConfig.initIdentityScope(type);

        healthSuggestionsDaoConfig = daoConfigMap.get(HealthSuggestionsDao.class).clone();
        healthSuggestionsDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        userExerciseFeatureDaoConfig = daoConfigMap.get(UserExerciseFeatureDao.class).clone();
        userExerciseFeatureDaoConfig.initIdentityScope(type);

        userSleepFeatureDaoConfig = daoConfigMap.get(UserSleepFeatureDao.class).clone();
        userSleepFeatureDaoConfig.initIdentityScope(type);

        foodFeatureDaoConfig = daoConfigMap.get(FoodFeatureDao.class).clone();
        foodFeatureDaoConfig.initIdentityScope(type);

        dietaryRecordsDaoConfig = daoConfigMap.get(DietaryRecordsDao.class).clone();
        dietaryRecordsDaoConfig.initIdentityScope(type);

        eatingAnalysisDaoConfig = daoConfigMap.get(EatingAnalysisDao.class).clone();
        eatingAnalysisDaoConfig.initIdentityScope(type);

        sportInfoRecordsDaoConfig = daoConfigMap.get(SportInfoRecordsDao.class).clone();
        sportInfoRecordsDaoConfig.initIdentityScope(type);

        sleepInfoRecordsDaoConfig = daoConfigMap.get(SleepInfoRecordsDao.class).clone();
        sleepInfoRecordsDaoConfig.initIdentityScope(type);

        eatingPreferRecordsDaoConfig = daoConfigMap.get(EatingPreferRecordsDao.class).clone();
        eatingPreferRecordsDaoConfig.initIdentityScope(type);

        sleepPreferRecordsDaoConfig = daoConfigMap.get(SleepPreferRecordsDao.class).clone();
        sleepPreferRecordsDaoConfig.initIdentityScope(type);

        exercisePreferRecordsDaoConfig = daoConfigMap.get(ExercisePreferRecordsDao.class).clone();
        exercisePreferRecordsDaoConfig.initIdentityScope(type);

        averageAmountStdDaoConfig = daoConfigMap.get(AverageAmountStdDao.class).clone();
        averageAmountStdDaoConfig.initIdentityScope(type);

        userHealthknowageDaoConfig = daoConfigMap.get(UserHealthknowageDao.class).clone();
        userHealthknowageDaoConfig.initIdentityScope(type);

        healthSuggestionCombileSimplifyDaoConfig = daoConfigMap.get(HealthSuggestionCombileSimplifyDao.class).clone();
        healthSuggestionCombileSimplifyDaoConfig.initIdentityScope(type);
        

        PhysiqueInfoDao = new PhysiqueInfoDao(PhysiqueInfoDaoConfig, this);
        IdentifyChoicesDao = new IdentifyChoicesDao(IdentifyChoicesDaoConfig, this);
        IdentificationIssuessDao = new IdentificationIssuessDao(IdentificationIssuessDaoConfig, this);
        UserAnswerRecordsDao = new UserAnswerRecordsDao(UserAnswerRecordsDaoConfig, this);
        ConsitituteIdentifyResultDao = new ConsitituteIdentifyResultDao(ConsitituteIdentifyResultDaoConfig, this);
        userKnowledgeMappDao = new UserKnowledgeMappDao(userKnowledgeMappDaoConfig, this);
        healthSuggestionsMappDao = new HealthSuggestionsMappDao(healthSuggestionsMappDaoConfig, this);
        healthSuggTypeDao = new HealthSuggTypeDao(healthSuggTypeDaoConfig, this);
        healthSuggestionsDao = new HealthSuggestionsDao(healthSuggestionsDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);
        userExerciseFeatureDao = new UserExerciseFeatureDao(userExerciseFeatureDaoConfig, this);
        userSleepFeatureDao = new UserSleepFeatureDao(userSleepFeatureDaoConfig, this);
        foodFeatureDao = new FoodFeatureDao(foodFeatureDaoConfig, this);
        dietaryRecordsDao = new DietaryRecordsDao(dietaryRecordsDaoConfig, this);
        eatingAnalysisDao = new EatingAnalysisDao(eatingAnalysisDaoConfig, this);
        sportInfoRecordsDao = new SportInfoRecordsDao(sportInfoRecordsDaoConfig, this);
        sleepInfoRecordsDao = new SleepInfoRecordsDao(sleepInfoRecordsDaoConfig, this);
        eatingPreferRecordsDao = new EatingPreferRecordsDao(eatingPreferRecordsDaoConfig, this);
        sleepPreferRecordsDao = new SleepPreferRecordsDao(sleepPreferRecordsDaoConfig, this);
        exercisePreferRecordsDao = new ExercisePreferRecordsDao(exercisePreferRecordsDaoConfig, this);
        averageAmountStdDao = new AverageAmountStdDao(averageAmountStdDaoConfig, this);
        userHealthknowageDao = new UserHealthknowageDao(userHealthknowageDaoConfig, this);
        healthSuggestionCombileSimplifyDao = new HealthSuggestionCombileSimplifyDao(healthSuggestionCombileSimplifyDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(UserExerciseFeature.class, userExerciseFeatureDao);
        registerDao(UserSleepFeature.class, userSleepFeatureDao);
        registerDao(FoodFeature.class, foodFeatureDao);
        registerDao(DietaryRecords.class, dietaryRecordsDao);
        registerDao(EatingAnalysis.class, eatingAnalysisDao);
        registerDao(SportInfoRecords.class, sportInfoRecordsDao);
        registerDao(SleepInfoRecords.class, sleepInfoRecordsDao);
        registerDao(EatingPreferRecords.class, eatingPreferRecordsDao);
        registerDao(SleepPreferRecords.class, sleepPreferRecordsDao);
        registerDao(ExercisePreferRecords.class, exercisePreferRecordsDao);
        registerDao(AverageAmountStd.class, averageAmountStdDao);
        registerDao(PhysiqueInfo.class, PhysiqueInfoDao);
        registerDao(IdentifyChoices.class, IdentifyChoicesDao);
        registerDao(IdentificationIssuess.class, IdentificationIssuessDao);
        registerDao(UserAnswerRecords.class, UserAnswerRecordsDao);
        registerDao(ConsitituteIdentifyResult.class, ConsitituteIdentifyResultDao);
        registerDao(UserHealthknowage.class, userHealthknowageDao);

        registerDao(HealthKnowledgeType.class, healthKnowledgeTypeDao);
        registerDao(TCMHealthknowledge.class, TCMHealthknowledgeDao);
        registerDao(UserKnowledgeMapp.class, userKnowledgeMappDao);
        registerDao(HealthSuggestionsMapp.class, healthSuggestionsMappDao);
        registerDao(HealthSuggType.class, healthSuggTypeDao);
        registerDao(HealthSuggestions.class, healthSuggestionsDao);
        registerDao(HealthSuggestionCombileSimplify.class, healthSuggestionCombileSimplifyDao);

    }



    public void clear() {
        healthknowledgetypeDaoConfig.getIdentityScope().clear();
        tcmhealthknowledgeDaoConfig.getIdentityScope().clear();
        PhysiqueInfoDaoConfig.getIdentityScope().clear();
        IdentifyChoicesDaoConfig.getIdentityScope().clear();
        IdentificationIssuessDaoConfig.getIdentityScope().clear();
        UserAnswerRecordsDaoConfig.getIdentityScope().clear();
        ConsitituteIdentifyResultDaoConfig.getIdentityScope().clear();
        userKnowledgeMappDaoConfig.getIdentityScope().clear();
        healthSuggestionsMappDaoConfig.getIdentityScope().clear();
        healthSuggTypeDaoConfig.getIdentityScope().clear();
        healthSuggestionsDaoConfig.getIdentityScope().clear();
        userDaoConfig.getIdentityScope().clear();
        userExerciseFeatureDaoConfig.getIdentityScope().clear();
        userSleepFeatureDaoConfig.getIdentityScope().clear();
        foodFeatureDaoConfig.getIdentityScope().clear();
        dietaryRecordsDaoConfig.getIdentityScope().clear();
        eatingAnalysisDaoConfig.getIdentityScope().clear();
        sportInfoRecordsDaoConfig.getIdentityScope().clear();
        sleepInfoRecordsDaoConfig.getIdentityScope().clear();
        eatingPreferRecordsDaoConfig.getIdentityScope().clear();
        sleepPreferRecordsDaoConfig.getIdentityScope().clear();
        exercisePreferRecordsDaoConfig.getIdentityScope().clear();
        averageAmountStdDaoConfig.getIdentityScope().clear();
        userHealthknowageDaoConfig.getIdentityScope().clear();
        healthSuggestionCombileSimplifyDaoConfig.getIdentityScope().clear();
    }

    public HealthKnowledgeTypeDao getHealthKnowledgeTypeDao() {
        return healthKnowledgeTypeDao;
    }

    public TCMHealthknowledgeDao getTCMHealthknowledgeDao() {
        return TCMHealthknowledgeDao;
    }

    public PhysiqueInfoDao getPhysiqueInfoDao() {
        return PhysiqueInfoDao;
    }

    public IdentifyChoicesDao getIdentifyChoicesDao() {
        return IdentifyChoicesDao;
    }

    public IdentificationIssuessDao getIdentificationIssuessDao() {
        return IdentificationIssuessDao;
    }

    public UserAnswerRecordsDao getUserAnswerRecordsDao() {
        return UserAnswerRecordsDao;
    }

    public ConsitituteIdentifyResultDao getConsitituteIdentifyResultDao() {
        return ConsitituteIdentifyResultDao;
    }

    public UserKnowledgeMappDao getUserKnowledgeMappDao() {
        return userKnowledgeMappDao;
    }

    public HealthSuggestionsMappDao getHealthSuggestionsMappDao() {
        return healthSuggestionsMappDao;
    }

    public HealthSuggTypeDao getHealthSuggTypeDao() {
        return healthSuggTypeDao;
    }

    public HealthSuggestionsDao getHealthSuggestionsDao() {
        return healthSuggestionsDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public UserExerciseFeatureDao getUserExerciseFeatureDao() {
        return userExerciseFeatureDao;
    }

    public UserSleepFeatureDao getUserSleepFeatureDao() {
        return userSleepFeatureDao;
    }

    public FoodFeatureDao getFoodFeatureDao() {
        return foodFeatureDao;
    }

    public DietaryRecordsDao getDietaryRecordsDao() {
        return dietaryRecordsDao;
    }

    public EatingAnalysisDao getEatingAnalysisDao() {
        return eatingAnalysisDao;
    }

    public SportInfoRecordsDao getSportInfoRecordsDao() {
        return sportInfoRecordsDao;
    }

    public SleepInfoRecordsDao getSleepInfoRecordsDao() {
        return sleepInfoRecordsDao;
    }

    public EatingPreferRecordsDao getEatingPreferRecordsDao() {
        return eatingPreferRecordsDao;
    }

    public SleepPreferRecordsDao getSleepPreferRecordsDao() {
        return sleepPreferRecordsDao;
    }

    public ExercisePreferRecordsDao getExercisePreferRecordsDao() {
        return exercisePreferRecordsDao;
    }

    public AverageAmountStdDao getAverageAmountStdDao() {
        return averageAmountStdDao;
    }

    public UserHealthknowageDao getUserHealthknowageDao() {
        return userHealthknowageDao;
    }

    public HealthSuggestionCombileSimplifyDao getHealthSuggestionCombileSimplifyDao() {
        return healthSuggestionCombileSimplifyDao;
    }

}