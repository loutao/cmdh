package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(3, "HealthSuggestionCombileSimplifyDao");
        addHealthSuggestions(schema);
        new DaoGenerator().generateAll(schema, args[0]);
    }

    public static void addUser(Schema schema) {
        /**
         * *普通用户表
         */
        Entity User = schema.addEntity("User");
        User.addIdProperty().primaryKey().autoincrement();
        User.addIntProperty("userid");
        User.addStringProperty("username");
        User.addStringProperty("usersex");
        User.addIntProperty("userage");
        User.addIntProperty("temp_sleepfeatureid");
        User.addIntProperty("temp_exercisefeatureid");
        User.addIntProperty("temp_eatingfeatureid");
        User.addIntProperty("temp_adminisareaid");
/**
 * 用户运动特征表*
 */
        Entity UserExerciseFeature = schema.addEntity("UserExerciseFeature");
        UserExerciseFeature.addIdProperty().primaryKey().autoincrement();
        UserExerciseFeature.addIntProperty("exercisefeatureid");
        UserExerciseFeature.addDoubleProperty("height");
        UserExerciseFeature.addDoubleProperty("weight");
        UserExerciseFeature.addDoubleProperty("steplength");
        UserExerciseFeature.addDoubleProperty("runsteplength");
        UserExerciseFeature.addDoubleProperty("standardweight");
        UserExerciseFeature.addDateProperty("datauptime");
        UserExerciseFeature.addStringProperty("exercisefeatureremarks");
        UserExerciseFeature.addStringProperty("motilityindex");
        UserExerciseFeature.addStringProperty("exercisehabitsdetermine");
        UserExerciseFeature.addStringProperty("exercisehabitanalysis");
        UserExerciseFeature.addDoubleProperty("averageexcitetime");
        UserExerciseFeature.addStringProperty("exercisetypeprefer");
/**
 * 用户睡眠特征表*
 */
        Entity UserSleepFeature = schema.addEntity("UserSleepFeature");
        UserSleepFeature.addIdProperty().primaryKey().autoincrement();
        UserSleepFeature.addIntProperty("sleepfeatureid");
        UserSleepFeature.addDoubleProperty("airhumidity");
        UserSleepFeature.addDoubleProperty("ambienttemperature");
        UserSleepFeature.addDoubleProperty("ambientnoise");
        UserSleepFeature.addStringProperty("bedtimehabits");
        UserSleepFeature.addStringProperty("goodbedtimehabits");
        UserSleepFeature.addStringProperty("siestahabit");
        UserSleepFeature.addDoubleProperty("averagesleeptime");
        UserSleepFeature.addStringProperty("sleephabitdetemination");
        UserSleepFeature.addStringProperty("sleepindex");
        UserSleepFeature.addStringProperty("sleephabitanalysis");
        UserSleepFeature.addIntProperty("temp_locationinfoid");
/**
 * 用户饮食特征表*
 */
        Entity FoodFeature = schema.addEntity("FoodFeature");
        FoodFeature.addIdProperty().primaryKey().autoincrement();
        FoodFeature.addIntProperty("eatingfeatureid");
        FoodFeature.addStringProperty("eatinghabitsdetermine");
        FoodFeature.addStringProperty("EatingHabitAnalysis");
        FoodFeature.addDoubleProperty("averageenergyintake");
        FoodFeature.addStringProperty("energyintakeunit");

        FoodFeature.addDoubleProperty("averagemoistureintake");
        FoodFeature.addStringProperty("moistureintakeunit");

        FoodFeature.addDoubleProperty("averageproteinintake");
        FoodFeature.addStringProperty("proteinintakeunit");

        FoodFeature.addDoubleProperty("averagefatintake");
        FoodFeature.addStringProperty("fatintakeunit");

        FoodFeature.addDoubleProperty("averagefiberintake");
        FoodFeature.addStringProperty("fiberintakeunit");

        FoodFeature.addDoubleProperty("averagecarbohydrateintake");
        FoodFeature.addStringProperty("carbohydrateintakeunit");

        FoodFeature.addDoubleProperty("averagevitaminaintake");
        FoodFeature.addStringProperty("vitaminaintakeunit");

        FoodFeature.addDoubleProperty("averagevitaminb1intake");
        FoodFeature.addStringProperty("vitaminb1intakeunit");

        FoodFeature.addDoubleProperty("averagevitaminb2intake");
        FoodFeature.addStringProperty("vitaminb2intakeunit");

        FoodFeature.addDoubleProperty("averagevitaminb6intake");
        FoodFeature.addStringProperty("vitaminb6intakeunit");

        FoodFeature.addDoubleProperty("averagevitaminb12intake");
        FoodFeature.addStringProperty("vitaminb12intakeunit");

        FoodFeature.addDoubleProperty("averagevitamincintake");
        FoodFeature.addStringProperty("vitamincintakeunit");

        FoodFeature.addDoubleProperty("averagevitamindintake");
        FoodFeature.addStringProperty("vitamindintakeunit");

        FoodFeature.addDoubleProperty("averagevitamineintake");
        FoodFeature.addStringProperty("vitamineintakeunit");

        FoodFeature.addDoubleProperty("averagevitaminkintake");
        FoodFeature.addStringProperty("vitaminkintakeunit");

        FoodFeature.addDoubleProperty("averagenicotinicacidintake");
        FoodFeature.addStringProperty("nicotinicacidintakeunit");

        FoodFeature.addDoubleProperty("averagefolateintake");
        FoodFeature.addStringProperty("folateintakeunit");

        FoodFeature.addDoubleProperty("averagesodiumintake");
        FoodFeature.addStringProperty("sodiumintakeunit");

        FoodFeature.addDoubleProperty("averagecalciumintake");
        FoodFeature.addStringProperty("calciumintakeunit");

        FoodFeature.addDoubleProperty("averageironintake");
        FoodFeature.addStringProperty("ironintakeunit");

        FoodFeature.addDoubleProperty("averagepotassiumintake");
        FoodFeature.addStringProperty("potassiumintakeunit");

        FoodFeature.addDoubleProperty("averagezincintake");
        FoodFeature.addStringProperty("zincintakeunit");

        FoodFeature.addDoubleProperty("averagemagnesiumintake");
        FoodFeature.addStringProperty("magnesiumintakeunit");

        FoodFeature.addDoubleProperty("averagecopperintake");
        FoodFeature.addStringProperty("copperintakeunit");

        FoodFeature.addDoubleProperty("averagechomuimintake");
        FoodFeature.addStringProperty("chomuimintakeunit");

        FoodFeature.addDoubleProperty("averagemangaesiumintake");
        FoodFeature.addStringProperty("mangaesiumintakeunit");

        FoodFeature.addDoubleProperty("averagemolybdenumintake");
        FoodFeature.addStringProperty("molybdenumintakeunit");

        FoodFeature.addDoubleProperty("averageiodineintake");
        FoodFeature.addStringProperty("iodineintakeunit");

        FoodFeature.addDoubleProperty("averagephosphrusintake");
        FoodFeature.addStringProperty("phosphrusintakeunit");

        FoodFeature.addDoubleProperty("averageseleniumintake");
        FoodFeature.addStringProperty("seleniumintakeunit");

        FoodFeature.addDoubleProperty("averagefluorineintake");
        FoodFeature.addStringProperty("fluorineintakeunit");

        FoodFeature.addDoubleProperty("averagecholesterolintake");
        FoodFeature.addStringProperty("cholesterolintakeunit");

        FoodFeature.addDoubleProperty("averagesaturatedintake");
        FoodFeature.addStringProperty("saturatedintakeunit");

        FoodFeature.addDoubleProperty("averageacidregurgitationintake");
        FoodFeature.addStringProperty("acidregurgitationintakeunit");

        FoodFeature.addDoubleProperty("averagebiotinintake");
        FoodFeature.addStringProperty("biotinintakeunit");

        FoodFeature.addDoubleProperty("averagecholineintake");
        FoodFeature.addStringProperty("cholineintakeunit");
        FoodFeature.addIntProperty("temp_amountstdid");
        /**
         * 饮食信息记录表*
         */
        Entity DietaryRecords = schema.addEntity("DietaryRecords");
        DietaryRecords.addIdProperty().primaryKey().autoincrement();
        DietaryRecords.addIntProperty("eatingrecordid");
        DietaryRecords.addStringProperty("foodtypename");
        DietaryRecords.addStringProperty("foodname ");
        DietaryRecords.addDoubleProperty("eatingamount");
        DietaryRecords.addStringProperty("unitname");
        DietaryRecords.addDateProperty("eatingtime");
        DietaryRecords.addDateProperty("eatingrecordsuptime");
        DietaryRecords.addStringProperty("eatinginforemarks");
        DietaryRecords.addIntProperty("temp_userid");
        DietaryRecords.addStringProperty("eatingstateback");
        DietaryRecords.addIntProperty("temp_foodnutritionid");
        DietaryRecords.addIntProperty("temp_locationinfoid");
        DietaryRecords.addIntProperty("temp_intelligentdeviceid");
        DietaryRecords.addIntProperty("intelligentdevicecode");
        DietaryRecords.addIntProperty("eatingupflag");
        DietaryRecords.addIntProperty("setmealcode");
        /**
         * 饮食状况分析表 *
         */
        Entity EatingAnalysis = schema.addEntity("EatingAnalysis");
        EatingAnalysis.addIdProperty().primaryKey().autoincrement();
        EatingAnalysis.addDoubleProperty("energyintake");
        EatingAnalysis.addStringProperty("energyunit");

        EatingAnalysis.addDoubleProperty("moistureintake");
        EatingAnalysis.addStringProperty("moistureunit");

        EatingAnalysis.addDoubleProperty("proteinintake");
        EatingAnalysis.addStringProperty("proteinunit");

        EatingAnalysis.addDoubleProperty("fatintake");
        EatingAnalysis.addStringProperty("fatunit");

        EatingAnalysis.addDoubleProperty("fiberintake");
        EatingAnalysis.addStringProperty("fiberunit");

        EatingAnalysis.addDoubleProperty("carbohydrateintake");
        EatingAnalysis.addStringProperty("carbohydrateunit");

        EatingAnalysis.addDoubleProperty("vitaminaintake");
        EatingAnalysis.addStringProperty("vitaminaunit");

        EatingAnalysis.addDoubleProperty("vitaminb1intake");
        EatingAnalysis.addStringProperty("vitaminb1unit");

        EatingAnalysis.addDoubleProperty("vitaminb2intake");
        EatingAnalysis.addStringProperty("vitaminb2unit");

        EatingAnalysis.addDoubleProperty("vitaminb6intake");
        EatingAnalysis.addStringProperty("vitaminb6unit");

        EatingAnalysis.addDoubleProperty("vitaminb12intake");
        EatingAnalysis.addStringProperty("vitaminb12unit");

        EatingAnalysis.addDoubleProperty("vitamincintake");
        EatingAnalysis.addStringProperty("vitamincunit");

        EatingAnalysis.addDoubleProperty("vitamindintake");
        EatingAnalysis.addStringProperty("vitamindunit");

        EatingAnalysis.addDoubleProperty("vitamineintake");
        EatingAnalysis.addStringProperty("vitaminunit");

        EatingAnalysis.addDoubleProperty("vitaminkintake");
        EatingAnalysis.addStringProperty("vitaminkunit");

        EatingAnalysis.addDoubleProperty("nicotinicacidintake");
        EatingAnalysis.addStringProperty("nicotinicunit");

        EatingAnalysis.addDoubleProperty("folateintake");
        EatingAnalysis.addStringProperty("folateunit");

        EatingAnalysis.addDoubleProperty("sodiumintake");
        EatingAnalysis.addStringProperty("sodiumunit");

        EatingAnalysis.addDoubleProperty("calciumintake");
        EatingAnalysis.addStringProperty("calciumunit");

        EatingAnalysis.addDoubleProperty("ironintake");
        EatingAnalysis.addStringProperty("ironunit");

        EatingAnalysis.addDoubleProperty("potassiumintake");
        EatingAnalysis.addStringProperty("potassiumunit");

        EatingAnalysis.addDoubleProperty("zincintake");
        EatingAnalysis.addStringProperty("zincunit");

        EatingAnalysis.addDoubleProperty("magnesiumintake");
        EatingAnalysis.addStringProperty("magnesiumunit");

        EatingAnalysis.addDoubleProperty("copperintake");
        EatingAnalysis.addStringProperty("copperunit");

        EatingAnalysis.addDoubleProperty("chomuimintake");
        EatingAnalysis.addStringProperty("chomuimunit");

        EatingAnalysis.addDoubleProperty("mangaesiumintake");
        EatingAnalysis.addStringProperty("mangaesiumunit");

        EatingAnalysis.addDoubleProperty("molybdenumintake");
        EatingAnalysis.addStringProperty("molybdenumunit");

        EatingAnalysis.addDoubleProperty("iodineintake");
        EatingAnalysis.addStringProperty("iodineunit");

        EatingAnalysis.addDoubleProperty("phosphrusintake");
        EatingAnalysis.addStringProperty("phosphrusunit");

        EatingAnalysis.addDoubleProperty("seleniumintake");
        EatingAnalysis.addStringProperty("seleniumunit");

        EatingAnalysis.addDoubleProperty("fluorineintake");
        EatingAnalysis.addStringProperty("fluorineunit");

        EatingAnalysis.addDoubleProperty("cholesterolintake");
        EatingAnalysis.addStringProperty("cholesterolunit");

        EatingAnalysis.addDoubleProperty("saturatedintake");
        EatingAnalysis.addStringProperty("saturatedunit");

        EatingAnalysis.addDoubleProperty("acidregurgitationintake");
        EatingAnalysis.addStringProperty("acidregurgitationunit");

        EatingAnalysis.addDoubleProperty("biotinintake");
        EatingAnalysis.addStringProperty("biotinunit");

        EatingAnalysis.addDoubleProperty("cholineintake");
        EatingAnalysis.addStringProperty("cholineunit");

        EatingAnalysis.addIntProperty("temp_eatingrecordid");
        /**
         * 运动信息记录表*
         */
        Entity SportInfoRecords = schema.addEntity("SportInfoRecords");
        SportInfoRecords.addIdProperty().primaryKey().autoincrement();
        SportInfoRecords.addIntProperty("sportrecordid");
        SportInfoRecords.addIntProperty("walkstepnumber");
        SportInfoRecords.addIntProperty("runstepnumber");
        SportInfoRecords.addDoubleProperty("walkdistance");
        SportInfoRecords.addDoubleProperty("rundistance");
        SportInfoRecords.addDoubleProperty("calorieconsumption");
        SportInfoRecords.addDateProperty("sportbegintime");
        SportInfoRecords.addDateProperty("sportovertime");
        SportInfoRecords.addDateProperty("sportrecorduptime");
        SportInfoRecords.addStringProperty("sportinforemarks");
        SportInfoRecords.addStringProperty("sportanalysis");
        SportInfoRecords.addIntProperty("temp_userid");
        SportInfoRecords.addIntProperty("temp_locationinfoid");
        SportInfoRecords.addStringProperty("sport_mode");
        SportInfoRecords.addIntProperty("temp_intelligentdeviceid");
        SportInfoRecords.addIntProperty("intelligentdevicecode");
/**
 * 睡眠信息记录表*
 */
        Entity SleepInfoRecords = schema.addEntity("SleepInfoRecords");
        SleepInfoRecords.addIdProperty().primaryKey().autoincrement();
        SleepInfoRecords.addIntProperty("sleeprecordid");
        SleepInfoRecords.addDoubleProperty("airhumidity");
        SleepInfoRecords.addDoubleProperty("ambienttemperature");
        SleepInfoRecords.addDoubleProperty("ambientnoise");
        SleepInfoRecords.addDateProperty("sleepbegin");
        SleepInfoRecords.addDateProperty("sleepover");
        SleepInfoRecords.addDoubleProperty("deepsleeptime");
        SleepInfoRecords.addDoubleProperty("shallowsleeptime");
        SleepInfoRecords.addStringProperty("sleepremarks");
        SleepInfoRecords.addDateProperty("sleeprecorduptime");
        SleepInfoRecords.addIntProperty("temp_userid");
        SleepInfoRecords.addIntProperty("temp_locationinfoid");
        SleepInfoRecords.addIntProperty("waketimes");
        SleepInfoRecords.addIntProperty("temp_intelligentdeviceid");
        SleepInfoRecords.addIntProperty("intelligentdevicecode");

/**
 * 饮食偏好记录表*
 */
        Entity EatingPreferRecords = schema.addEntity("EatingPreferRecords");
        EatingPreferRecords.addIdProperty().primaryKey().autoincrement();
        EatingPreferRecords.addIntProperty("eatingpreferid");
        EatingPreferRecords.addStringProperty("foodtypename");
        EatingPreferRecords.addStringProperty("foodname");
        EatingPreferRecords.addDoubleProperty("preference");
        EatingPreferRecords.addDoubleProperty("averageintake");
        EatingPreferRecords.addIntProperty("temp_foodnutritionid");
        EatingPreferRecords.addIntProperty("temp_userid");
        EatingPreferRecords.addDateProperty("eatingoftenstarttime");
        EatingPreferRecords.addDateProperty("eatingoftenovertime");
        EatingPreferRecords.addIntProperty("temp_locationinfoid");
        /**
         * 睡眠偏好记录表 *
         */
        Entity SleepPreferRecords = schema.addEntity("SleepPreferRecords");
        SleepPreferRecords.addIdProperty().primaryKey().autoincrement();
        SleepPreferRecords.addIntProperty("sleeppreferid");
        SleepPreferRecords.addDateProperty("prefersleepbeginat");
        SleepPreferRecords.addDateProperty("prefersleepoverat");
        SleepPreferRecords.addIntProperty("temp_locationinfoid");
        SleepPreferRecords.addIntProperty("temp_userid");
        /**
         * 运动偏好记录表*
         */
        Entity ExercisePreferRecords = schema.addEntity("ExercisePreferRecords");
        ExercisePreferRecords.addIdProperty().primaryKey().autoincrement();
        ExercisePreferRecords.addIntProperty("exercisepreferenceid");
        ExercisePreferRecords.addStringProperty("exercisetype");
        ExercisePreferRecords.addStringProperty("exercisename");
        ExercisePreferRecords.addStringProperty("exercisedescribe");
        ExercisePreferRecords.addIntProperty("temp_exerciseid");
        ExercisePreferRecords.addIntProperty("temp_userid");
        ExercisePreferRecords.addIntProperty("temp_locationinfoid");


/**
 *平均量计算标准表 *
 */
        Entity AverageAmountStd = schema.addEntity("AverageAmountStd");
        AverageAmountStd.addIdProperty().primaryKey().autoincrement();
        AverageAmountStd.addIntProperty("amountnumofdays");
        AverageAmountStd.addStringProperty("averageamountremarks");
        AverageAmountStd.addDateProperty("amountstoptime");


    }

    public static void addTcmhealthknowledge(Schema schema) {

/**
 * 养生知识类别表* 
 */
        Entity HealthKnowledgeType = schema.addEntity("HealthKnowledgeType");
        HealthKnowledgeType.addIdProperty().primaryKey().autoincrement();
        HealthKnowledgeType.addIntProperty("healthknowltypeid");
        HealthKnowledgeType.addStringProperty("healthknowltypename");
        HealthKnowledgeType.addStringProperty("classifyexplain");
        HealthKnowledgeType.addIntProperty("healthknowltypecode");
        HealthKnowledgeType.addStringProperty("healthknowltypereamrks");
/**
 * 养生知识信息表*
 */
        Entity TCMHealthKnowledge = schema.addEntity("TCMHealthknowledge");
        TCMHealthKnowledge.addIdProperty().primaryKey().autoincrement();
        TCMHealthKnowledge.addIntProperty("healthknowledgeid");
        TCMHealthKnowledge.addStringProperty("healthknowledgetitle");
        TCMHealthKnowledge.addStringProperty("healthknowledgecontent");
        TCMHealthKnowledge.addDateProperty("exersuggtime");
        TCMHealthKnowledge.addIntProperty("healthknowledgecode");
        TCMHealthKnowledge.addIntProperty("temp_healthknowltypeid");
        TCMHealthKnowledge.addStringProperty("healthknowledgeremarks");
        TCMHealthKnowledge.addIntProperty("healthknowledgeflag");
/**
 * 养生知识限定标准表*
 Entity HealthKnowledgeLimited = schema.addEntity("HealthKnowledgeLimited");
 HealthKnowledgeLimited.addIdProperty().primaryKey().autoincrement();
 HealthKnowledgeLimited.addIntProperty("HealthKnowlLimitedID");
 HealthKnowledgeLimited.addStringProperty("HealthKnowlLimitedAttName");
 HealthKnowledgeLimited.addIntProperty("HealthKnowlLimitedStatus");
 HealthKnowledgeLimited.addIntProperty("HealthKnowlStatusLimitedLevel");
 HealthKnowledgeLimited.addIntProperty("temp_HealthKnowledgeID");
 HealthKnowledgeLimited.addStringProperty("HealthKnowlLimitedExp");
 HealthKnowledgeLimited.addStringProperty("HealthKnowlLimitedRemarks");*/
        /**
         * 用户养生知识映射表*
         */
        Entity UserKnowledgeMapp = schema.addEntity("UserKnowledgeMapp");
        UserKnowledgeMapp.addIdProperty().primaryKey().autoincrement();
        UserKnowledgeMapp.addIntProperty("userknowledgemappid");
        UserKnowledgeMapp.addIntProperty("temp_userid");
        UserKnowledgeMapp.addStringProperty("healthknowledgecontent");
        UserKnowledgeMapp.addIntProperty("temp_healthknowledgeid");
        UserKnowledgeMapp.addStringProperty("healthknowledgereason");
        UserKnowledgeMapp.addDateProperty("healthknowledgetime");
        UserKnowledgeMapp.addStringProperty("healthknowledgeremarks");
/**
 * 用户健康建议映射表*
 */
        Entity HealthSuggestionsMapp = schema.addEntity("HealthSuggestionsMapp");
        HealthSuggestionsMapp.addIdProperty().primaryKey().autoincrement();
        HealthSuggestionsMapp.addIntProperty("healthsuggestmappid");
        HealthSuggestionsMapp.addStringProperty("healthsuggcontent");
        HealthSuggestionsMapp.addIntProperty("temp_userid");
        HealthSuggestionsMapp.addIntProperty("temp_healthsuggestid");
        HealthSuggestionsMapp.addStringProperty("healthsuggestreason");
        HealthSuggestionsMapp.addDateProperty("healthsuggesttime");
        HealthSuggestionsMapp.addStringProperty("healthsuggestremarks");

/**
 * 健康建议类别表*
 */
        Entity HealthSuggType = schema.addEntity("HealthSuggType");
        HealthSuggType.addIdProperty().primaryKey().autoincrement();
        HealthSuggType.addIntProperty("healthsuggtypeid");
        HealthSuggType.addStringProperty("healthsuggtypename");
        HealthSuggType.addIntProperty("healthsuggtypecode");
        HealthSuggType.addStringProperty("suggclassifyexpla");
        HealthSuggType.addStringProperty("healthsuggtyperemarks");

        /**
         * 养生建议表*
         */
        Entity HealthSuggestions = schema.addEntity("HealthSuggestions");
        HealthSuggestions.addIdProperty().primaryKey().autoincrement();
        HealthSuggestions.addIntProperty("healthsuggestid");
        HealthSuggestions.addStringProperty("healthsuggestcontent");
        HealthSuggestions.addStringProperty("healthsuggesttitle");
        HealthSuggestions.addStringProperty("healthsuggestremarks");
        HealthSuggestions.addIntProperty("temp_healthsuggtypeid");
        HealthSuggestions.addIntProperty("healthsuggestflag");
        HealthSuggestions.addIntProperty("healthsuggestcode");


    }

    /**
     * 体质辨识相关表
     *
     * @param schema
     */
    public static void addPhysiqueConsIdenty(Schema schema) {
        /**
         * 体质信息表* 
         */
        Entity PhysiqueInfo = schema.addEntity("PhysiqueInfo");
        PhysiqueInfo.addIdProperty().primaryKey().autoincrement();
        PhysiqueInfo.addIntProperty("physiqueinfoid");
        PhysiqueInfo.addDoubleProperty("tablescoreheight");
        PhysiqueInfo.addDoubleProperty("tablescorelow");
        PhysiqueInfo.addDoubleProperty("switchscoreheight");
        PhysiqueInfo.addDoubleProperty("switchscorelow");
        PhysiqueInfo.addStringProperty("generacharacter");
        PhysiqueInfo.addStringProperty("ShapeFeature");
        PhysiqueInfo.addStringProperty("commonmanifest");
        PhysiqueInfo.addStringProperty("mentalcharacter");
        PhysiqueInfo.addStringProperty("incidencetendency");
        PhysiqueInfo.addStringProperty("adaptivecapacity");
        PhysiqueInfo.addStringProperty("physicaltypename");
        PhysiqueInfo.addIntProperty("physicaltypecode");

/**
 * 体质辨识选项表* 
 */
        Entity IdentifyChoices = schema.addEntity("IdentifyChoices");
        IdentifyChoices.addIdProperty().primaryKey().autoincrement();
        IdentifyChoices.addIntProperty("identifychoiceid");
        IdentifyChoices.addIntProperty("identifychoicevalue");
        IdentifyChoices.addStringProperty("scriptdescribe");
/**
 * 体质辨识问卷问题表*
 */
        Entity IdentificationIssuess = schema.addEntity("IdentificationIssuess");
        IdentificationIssuess.addIdProperty().primaryKey().autoincrement();
        IdentificationIssuess.addIntProperty("identifyissueid");
        IdentificationIssuess.addIntProperty("temp_physiqueinfoid");
        IdentificationIssuess.addStringProperty("identifyissuecontent");
        IdentificationIssuess.addStringProperty("identifyissueremarks");

/**
 * 用户答题记录表*
 */
        Entity UserAnswerRecords = schema.addEntity("UserAnswerRecords");
        UserAnswerRecords.addIdProperty().primaryKey().autoincrement();
        UserAnswerRecords.addIntProperty("issuescoremappid");
        UserAnswerRecords.addIntProperty("temp_identifyissueid");
        UserAnswerRecords.addIntProperty("temp_identifyscoreid");
        UserAnswerRecords.addIntProperty("temp_userid");

        /**
         * 体质辨识结果记录表*
         */
        Entity ConsitituteIdentifyResult = schema.addEntity("ConsitituteIdentifyResult");
        ConsitituteIdentifyResult.addIdProperty().primaryKey().autoincrement();
        ConsitituteIdentifyResult.addIntProperty("identifyresultid");
        ConsitituteIdentifyResult.addIntProperty("temp_userid");
        ConsitituteIdentifyResult.addIntProperty("temp_physiqueinfoid");
        ConsitituteIdentifyResult.addDateProperty("constituteidentifytime");
        ConsitituteIdentifyResult.addStringProperty("constituteidentifyremarks");
        ConsitituteIdentifyResult.addStringProperty("constituteidentifyresult");

    }

    public static void addCollection(Schema schema){
        /**
         * 养生知识信息
         */
        Entity UserHealthknowage = schema.addEntity("UserHealthknowage");
        UserHealthknowage.addIdProperty().primaryKey().autoincrement();
        UserHealthknowage.addIntProperty("userknowledgemappid");
        UserHealthknowage.addIntProperty("temp_picturelistid");
        UserHealthknowage.addDateProperty("healthknowledgetime");
        UserHealthknowage.addIntProperty("userid");
        UserHealthknowage.addStringProperty("username").getProperty();
        UserHealthknowage.addIntProperty("healthknowltypeid");
        UserHealthknowage.addStringProperty("healthknowltypename");
        UserHealthknowage.addStringProperty("healthknowledgetitle");
        UserHealthknowage.addStringProperty("healthknowledgecontent");
        UserHealthknowage.addIntProperty("healthknowledgecode");
        UserHealthknowage.addBooleanProperty("isCollected");

    }

    public static void addHealthSuggestions(Schema schema){
        /**
         * 健康建议
         */
        Entity HealthSuggestionCombileSimplify = schema.addEntity("HealthSuggestionCombileSimplify");
        HealthSuggestionCombileSimplify.addIdProperty().primaryKey().autoincrement();
        HealthSuggestionCombileSimplify.addIntProperty("recommendedconditionsmappid");
        HealthSuggestionCombileSimplify.addStringProperty("temp_healthsuggestlimitedid");
        HealthSuggestionCombileSimplify.addStringProperty("healthsuggestcontent");
        HealthSuggestionCombileSimplify.addStringProperty("healthsuggesttitle");
        HealthSuggestionCombileSimplify.addStringProperty("healthsuggestremarks");
        HealthSuggestionCombileSimplify.addStringProperty("temp_healthsuggtypeid");
        HealthSuggestionCombileSimplify.addStringProperty("healthknowltypeid");
        HealthSuggestionCombileSimplify.addIntProperty("healthsuggestflag");
        HealthSuggestionCombileSimplify.addIntProperty("healthsuggestcode");
        HealthSuggestionCombileSimplify.addStringProperty("originalpicturepath");
        HealthSuggestionCombileSimplify.addStringProperty("picturename");
        HealthSuggestionCombileSimplify.addBooleanProperty("isCollected");
    }
}
