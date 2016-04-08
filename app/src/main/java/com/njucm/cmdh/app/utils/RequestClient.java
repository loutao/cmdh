package com.njucm.cmdh.app.utils;

import com.njucm.cmdh.app.domain.Commonfoodtype;
import com.njucm.cmdh.app.domain.ConsitituteIdentifyResult;
import com.njucm.cmdh.app.domain.DietaryRecords;
import com.njucm.cmdh.app.domain.DoctorView;
import com.njucm.cmdh.app.domain.IdentificationIssuess;
import com.njucm.cmdh.app.domain.MyConstitution;
import com.njucm.cmdh.app.domain.PhysiqueInfo;
import com.njucm.cmdh.app.domain.Recommendedconditionsmapp;
import com.njucm.cmdh.app.domain.SleepInfoRecords;
import com.njucm.cmdh.app.domain.SportInfoRecords;
import com.njucm.cmdh.app.domain.SportTendingAll;
import com.njucm.cmdh.app.domain.TCMHealthknowledge;
import com.njucm.cmdh.app.domain.User;
import com.njucm.cmdh.app.domain.UserAnswerRecord;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Mesogene on 4/2/15.
 */
public interface RequestClient {
    /**
     * 养生知识请求
     * 
     * @param requestGoal
     * @return
     */
    @GET("/{requestGoal}/")
    List<TCMHealthknowledge> getTcmhealthknowledgeList(@Path("requestGoal") String requestGoal);
    
    @GET("/{requestGoal}/")
    List<PhysiqueInfo> getPhysiqueInfoList(@Path("requestGoal") String requestGoal, @Query("physicaltypename") String physicaltypename);

    @GET("/{requestGoal}/ ")
    List<IdentificationIssuess> getTcmidentifyquestionList(@Path("requestGoal") String requestGoal);


    @GET("/{requestGoal}")
    List<IdentificationIssuess> getTcmidentifyquestionList(@Path("requestGoal") String table,@Query("temp_physiqueinfoid") String id);

    @POST("/tbuseranswerrecords/")
    void getUserAnswerRecordList(@Body List<UserAnswerRecord> userAnswerRecord , Callback<Object> cb );

    @POST("/tbconstituteidentifyresult/")
    void getConstituteIdentifyResultList(@Body List<ConsitituteIdentifyResult> consitituteIdentifyResult, Callback<Object> cb );

    @GET("/{requestGoal}")
    List<SportTendingAll> getSportTendingAllInfoList(@Path("requestGoal") String requestGoal, @Query("temp_userid") String temp_userid);

    @GET("/{requestGoal}")
    Object getTendingDataByMonth(@Path("requestGoal") String requestGoal, @Query("temp_userid") String temp_userid, @Query("querytime") String querytime);
    
    @GET("/{requestGoal}")
    List<DoctorView> getDoctorList(@Path("requestGoal") String requestGoal);

    @GET("/{requestGoal}/")
    Object getOneSportData(@Path("requestGoal") String requestGoal, @Query("temp_userid") String temp_userid, @Query("querytime") String querytime);

    @GET("/{requestGoal}")
    Object getTestJsonData(@Path("requestGoal") String requestGoal);

    @GET("/{requestGoal}")
    List<Recommendedconditionsmapp> getHealthSuggestion(@Path("requestGoal") String requestGoal, @Query("temp_healthsuggestid__temp_healthsuggtypeid__healthsuggtypename") String temp_healthsuggestid__temp_healthsuggtypeid__healthsuggtypename, @Query("temp_healthsuggestlimitedid__suggestlimitedvalue") String temp_healthsuggestlimitedid__suggestlimitedvalue);

    @GET("/{requestGoal}/")
    Object getHealthSuggestionObj(@Path("requestGoal") String requestGoal, @Query("temp_healthsuggestid__temp_healthsuggtypeid__healthsuggtypename") String healthsuggtypeid, @Query("temp_healthsuggestlimitedid__suggestlimitedvalue") String healthsuggestlimitedid);

    @GET("/{requestGoal}/")
    Object getPhysiqueInfoObj(@Path("requestGoal") String requestGoal);

    @GET("/{requestGoal}/")
    List<PhysiqueInfo> getPhysiqueInfo(@Path("requestGoal") String requestGoal);

    @GET("/{requestGoal}/")
    List<Recommendedconditionsmapp> getHealthsuggestioncollectionById(@Path("requestGoal") String requestGoal, @Query("id") String id);

    @GET("/{requestGoal}/")
    Object getElementIntakeList(@Path("requestGoal") String requestGoal, @Query("temp_userid") String temp_userid, @Query("querytime") String querytime, @Query("element") String element);

    @GET("/{requestGoal}/")
    Object getOneSleepData(@Path("requestGoal") String requestGoal, @Query("temp_userid") String temp_userid,@Query("querytime") String querytime);

    @GET("/{requestGoal}/")
    Object getOneEatingData(@Path("requestGoal") String requestGoal, @Query("temp_userid") String temp_userid,@Query("querytime") String querytime);

    /**
     * 根据constituteidentifyflag获取所有的体质辨识信息
     
     * @param consitituteIdentifyResult
     * @param consflag
     * @return
     */
    @GET("/{requestGoal}")
    List<ConsitituteIdentifyResult> getConsitituteIdentifyResultList(@Path("requestGoal") String consitituteIdentifyResult, @Query("constituteldentifyflag") int consflag);

    /**
     * 获取每个月平均营养摄入量
     */
    @GET("/{requestGoal}")
    Object getEatingAmmountPerday(@Path("requestGoal") String requestGoal,@Query("temp_userid") int temp_userid, @Query("querytime") String querytime);

    /**
     * 根据flag获取每个人体质测试历史
     */
    @GET("/{requestGoal}")
    List<MyConstitution> getTcmmyconstitutionList(@Path("requestGoal") String requestGoal, @Query("constituteldentifyflag") int constituteldentifyflag);
    /*
   * 睡眠信息录入
   * */
    @POST("/tbsleepinforecords/")
    void postSleepinforecords(@Body List<SleepInfoRecords> sleepInfoRecordses, Callback<Object> cb );
    /*
   * 运动信息录入
   * */
    @POST("/tbsportinforecords/")
    void postSportinforecords(@Body List<SportInfoRecords> sportInfoRecordses,Callback<Object> cb);
    /**
     * 饮食信息录入
     */
    @POST("/tbdietaryrecords/")
    void postDietaryrecords(@Body List<DietaryRecords> dietaryRecordses,Callback<Object> cb);
    @GET("/{requestGoal}")
    List<Commonfoodtype>getCommonfoodtypeList(@Path("requestGoal") String requestGoal);
    /**
     * 登录接口
     */

    @POST("/auth/login")
    void UserLogin(@Body User user, Callback<Object> loginCb);

    @POST("/auth/register")
    void UserRegister(@Body User user, Callback<Object> registerObject);

    @GET("/{me}/")
    void getauthTest(@Path("me") String me, Callback<Object> meObject);
}