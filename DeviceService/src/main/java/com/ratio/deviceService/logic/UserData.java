package com.ratio.deviceService.logic;

import android.content.Context;
import android.util.Log;

import com.ratio.BTDeviceService.R;
import com.ratio.deviceService.bean.GPSLocation;
import com.ratio.deviceService.bean.MapMode;
import com.ratio.deviceService.bean.MapType;
import com.ratio.deviceService.bean.ProgramItem;
import com.ratio.deviceService.bean.SportsHistory;
import com.ratio.deviceService.bean.UserBaseInfo;
import com.ratio.deviceService.bean.UserConfig;

import java.nio.channels.FileChannel;

/**
 * Created by Mesogene on 2015/5/25.
 */
public class UserData {
    private static UserData mUserChooseData;
    private boolean isAnonymousLogin = true;
    private boolean isSupportGoogleMap = true;
    private String mCityString;
    private Context mContext;
    private GPSLocation mGpsLocation;
    public MapMode mMapMode = MapMode.STREET_MODE;
    private ProgramItem mProgramItem;
    private int mSportsCalorie;
    private float mSportsDistance;
    private SportsHistory mSportsHistory;
//    private SportsMode mSportsMode = SportsMode.Normal;
    private String mSportsModeTextString;
//    private SportsScheme mSportsScheme = SportsScheme.Normal;
    private long mSportsTime;
//    private SportsType mSportsType = SportsType.Walk;
    private UserConfig mUserConfig;
    private UserBaseInfo mUserInfo;

    public UserData(Context paramContext)
    {
        this.mContext = paramContext;
    }

    public static UserData GetInstance(Context paramContext)
    {
        if (mUserChooseData == null)
            mUserChooseData = new UserData(paramContext.getApplicationContext());
        return mUserChooseData;
    }

    public void Close()
    {
        ConfigManager.setIntValue(this.mContext, "UserData_SportsMode", 0);
        ConfigManager.setLongValue(this.mContext, "UserData_SportsTime", 0L);
        ConfigManager.setFloatValue(this.mContext, "UserData_SportsDistance", 0.0F);
        ConfigManager.setIntValue(this.mContext, "UserData_SportsCalorie", 0);
        ConfigManager.setStringValue(this.mContext, "UserData_SportsModeText", "");
        ConfigManager.setIntValue(this.mContext, "UserData_Anonymous", 0);
        ConfigManager.setIntValue(this.mContext, "UserData_SupportGoogleMap", 1);
        mUserChooseData = null;
    }

    public UserBaseInfo GetUserBaseInfo()
    {
       /* if (this.mUserInfo == null)
        {
            String str = UserConfigManager.getInstance(this.mContext).getToken();
            UserInfoDAO localUserInfoDAO = new UserInfoDAO(this.mContext);
            if (str != null)
                this.mUserInfo = localUserInfoDAO.getById(str);*/
          /*  if (this.mUserInfo == null)
            {*/
                this.mUserInfo = new UserBaseInfo();
                this.mUserInfo.id = "anonymous";
                this.mUserInfo.nick = "匿名用户";
                this.mUserInfo.weight = 60.0F;
                this.mUserInfo.height = 170;
                this.mUserInfo.gender = 1;
//            }
            Log.d("user_id", String.valueOf(this.mUserInfo.id));
//        }
        return this.mUserInfo;
    }

    public void SetUserBaseInfo(UserBaseInfo paramUserBaseInfo)
    {
        this.mUserInfo = paramUserBaseInfo;
    }

    public GPSLocation getDisLocation()
    {
        return this.mGpsLocation;
    }

    public boolean getGoogleMapCanUse()
    {
        try
        {
            Class.forName("com.google.android.maps.MapActivity");
            return true;
        }
        catch (Exception localException)
        {
        }
        return false;
    }

    public boolean getIsAnonymousLogin()
    {
        if (ConfigManager.getIntValue(this.mContext, "UserData_Anonymous") == 1);
        for (boolean bool = true; ; bool = false)
        {
            this.isAnonymousLogin = bool;
            return this.isAnonymousLogin;
        }
    }

    public MapMode getMapMode()
    {
        MapMode localMapMode1 = this.mMapMode;
        MapMode localMapMode2 = this.mMapMode;
        if (localMapMode1 == MapMode.STREET_MODE)
            return MapMode.getValue(ConfigManager.getIntValue(this.mContext, "UserData_MapMode"));
        return this.mMapMode;
    }

  /*  public MapType getMapUseType()
    {
        return MapType.getValue(SportsHistoryManager.getInstance(this.mContext.getApplicationContext(), GetInstance(this.mContext.getApplicationContext()).GetUserBaseInfo().id).getSportsHistory().mapType);
    }*/

    public int getSportsCaloire()
    {
        if (this.mSportsCalorie == 0)
            this.mSportsCalorie = ConfigManager.getIntValue(this.mContext, "UserData_SportsCalorie");
        return this.mSportsCalorie;
    }

    public float getSportsDistance()
    {
        if (this.mSportsDistance == 0.0F)
            this.mSportsDistance = ConfigManager.getFloatValue(this.mContext, "UserData_SportsDistance", 0.0F);
        return this.mSportsDistance;
    }

  /*  public SportsMode getSportsMode()
    {
        if (this.mSportsMode.ordinal() == 0)
            this.mSportsMode = SportsMode.getValue(ConfigManager.getIntValue(this.mContext, "UserData_SportsMode"));
        return this.mSportsMode;
    }*/

    public String getSportsModeText()
    {
        if (this.mSportsModeTextString == null)
            this.mSportsModeTextString = ConfigManager.getStringValue(this.mContext, "UserData_SportsModeText");
        if ((this.mSportsModeTextString == null) || (this.mSportsModeTextString.length() == 0));
        for (String str = this.mContext.getString(R.string.action_settings); ; str = this.mSportsModeTextString)
            return str;
    }

   /* public SportsScheme getSportsScheme()
    {
        if (this.mSportsScheme.ordinal() == 0)
            this.mSportsScheme = SportsScheme.getValue(ConfigManager.getIntValue(this.mContext, "UserData_SportsScheme"));
        return this.mSportsScheme;
    }*/

    public long getSportsTime()
    {
        if (this.mSportsTime == 0L)
            this.mSportsTime = ConfigManager.getLongValue(this.mContext, "UserData_SportsTime", 0L).longValue();
        return this.mSportsTime;
    }

   /* public SportsType getSportsType()
    {
        if (this.mSportsType.ordinal() == 0)
            this.mSportsType = SportsType.getValue(ConfigManager.getIntValue(this.mContext, "UserData_SportsType_" + GetInstance(this.mContext).GetUserBaseInfo().id, 1));
        return this.mSportsType;
    }*/

   /* public ProgramItem getTodaySportsProgram()
    {
        if (this.mProgramItem == null)
            this.mProgramItem = new ProgramDetailDAO(this.mContext).getTodayItem(GetUserBaseInfo().id, ConfigManager.getIntValue(this.mContext, "UserData_TodaySportsProgramID"));
        return this.mProgramItem;
    }*/

    public boolean isUserInfoErr()
    {
       /* UserBaseInfo localUserBaseInfo = GetUserBaseInfo();
        int i = 0;
        int j = 0;
        if ((localUserBaseInfo.nick == null) || (localUserBaseInfo.nick.startsWith("cu_")))
            if (this.mContext.getSharedPreferences("MyPrefsFile", 0).getBoolean("SetNick", false))
       for (i = 1; ; i = 0)
        {
            if ((localUserBaseInfo.get_icon_large == null) || (localUserBaseInfo.get_icon_large.contains("backend958cedfacef34ee381cd496d91335e6b")))
                j = 1;
            return i | j;
        }*/
        return false;
    }

    public void setDisLocation(GPSLocation paramGPSLocation)
    {
        this.mGpsLocation = paramGPSLocation;
    }

    public void setIsAnonymousLogin(boolean paramBoolean)
    {
        this.isAnonymousLogin = paramBoolean;
        Context localContext = this.mContext;
        if (this.isAnonymousLogin == true);
        for (int i = 1; ; i = 0)
        {
            ConfigManager.setIntValue(localContext, "UserData_Anonymous", i);
            return;
        }
    }

    public void setIsSupportGoogleMap(boolean paramBoolean)
    {
        this.isSupportGoogleMap = paramBoolean;
        Context localContext = this.mContext;
        if (this.isSupportGoogleMap == true);
        for (int i = 1; ; i = 0)
        {
            ConfigManager.setIntValue(localContext, "UserData_SupportGoogleMap", i);
            return;
        }
    }

    public void setMapMode(MapMode paramMapMode)
    {
        this.mMapMode = paramMapMode;
        ConfigManager.setIntValue(this.mContext, "UserData_MapMode", paramMapMode.ordinal());
    }

    public void setSportsCalorie(int paramInt)
    {
        this.mSportsCalorie = paramInt;
        ConfigManager.setIntValue(this.mContext, "UserData_SportsCalorie", paramInt);
    }

    public void setSportsDistance(float paramFloat)
    {
        this.mSportsDistance = paramFloat;
        ConfigManager.setFloatValue(this.mContext, "UserData_SportsDistance", paramFloat);
    }

   /* public void setSportsMode(SportsMode paramSportsMode)
    {
        this.mSportsMode = paramSportsMode;
        ConfigManager.setIntValue(this.mContext, "UserData_SportsMode", paramSportsMode.ordinal());
    }*/

    public void setSportsModeText(String paramString)
    {
        this.mSportsModeTextString = paramString;
        ConfigManager.setStringValue(this.mContext, "UserData_SportsModeText", paramString);
    }

  /*  public void setSportsScheme(SportsScheme paramSportsScheme)
    {
        this.mSportsScheme = paramSportsScheme;
        ConfigManager.setIntValue(this.mContext, "UserData_SportsScheme", paramSportsScheme.ordinal());
    }*/

    public void setSportsTime(long paramLong)
    {
        this.mSportsTime = paramLong;
        ConfigManager.setLongValue(this.mContext, "UserData_SportsTime", paramLong);
    }

   /* public void setSportsType(SportsType paramSportsType)
    {
        this.mSportsType = paramSportsType;
        ConfigManager.setIntValue(this.mContext, "UserData_SportsType_" + GetInstance(this.mContext).GetUserBaseInfo().id, paramSportsType.ordinal());
    }*/

    public void setTodaySportsProgram(ProgramItem paramProgramItem)
    {
        this.mProgramItem = paramProgramItem;
        ConfigManager.setIntValue(this.mContext, "UserData_TodaySportsProgramID", this.mProgramItem.id);
    }
}
