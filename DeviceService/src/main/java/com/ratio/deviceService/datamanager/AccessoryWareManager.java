package com.ratio.deviceService.datamanager;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ratio.deviceService.accessory.Accessory;
import com.ratio.deviceService.accessory.AccessoryConfig;
import com.ratio.deviceService.data.CLog;
import com.ratio.deviceService.logic.UserData;
import com.ratio.util.FilePathConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mesogene on 2015/5/25.
 */
public class AccessoryWareManager {
    private static final int CONNECTTIMEOUT = 30000;
    private static final int DOWNLOAD_BUFFER_SIZE = 1024;
    private static final int MAX_PROGRESS = 100;
    private static final int READTIMEOUT = 30000;
    private Runnable downLoadRunable;
    private boolean isNeedUpload = false;
    private AccessoryManager mAccessoryManager = null;
    private DownLoadCallBack mCallBack;
    private Context mContext;
    private AccessoryInfo mTargetAccessory;

    public AccessoryWareManager(Context paramContext)
    {
        this.mContext = paramContext.getApplicationContext();
        this.mAccessoryManager = new AccessoryManager(this.mContext);
        this.isNeedUpload = AccessoryConfig.getBooleanValue(this.mContext, "com.codoon.gps.key_accessory_needup", false);
    }

    private boolean checkNeedUpgrade(List<AccessoryInfo> paramList)
    {
        Accessory localAccessory = this.mAccessoryManager.getCurAccessory();
        Iterator localIterator = paramList.iterator();
        if (localIterator.hasNext())
        {
            AccessoryInfo localAccessoryInfo = (AccessoryInfo)localIterator.next();
            boolean bool2 = checkIsNeedUpload(localAccessoryInfo.device_name, localAccessoryInfo.version_name, localAccessory);
            if (localAccessory.mDeviceType.startsWith(localAccessoryInfo.device_name))
            {
                setTargetAccessory(localAccessoryInfo);
                StringBuilder localStringBuilder = new StringBuilder();
                CLog.d("enlong", " target url:" + localAccessoryInfo.binary_url);
            }
            if (bool2)
                setNeedUpload(true);
        }
        for (boolean bool1 = true; ; bool1 = false)
        {
            return bool1;
        }
    }

    public static boolean isFirstShowBlueFriend(Context paramContext)
    {
        String str = UserData.GetInstance(paramContext).GetUserBaseInfo().id;
        return AccessoryConfig.getBooleanValue(paramContext, "com.codoon.gps.bluetooth_friends_warning_show_" + str, true);
    }

    public static void setIsFirstShowBlueFriend(Context paramContext, boolean paramBoolean)
    {
        String str = UserData.GetInstance(paramContext).GetUserBaseInfo().id;
        AccessoryConfig.setBooleanValue(paramContext, "com.codoon.gps.bluetooth_friends_warning_show_" + str, paramBoolean);
    }

    private void setTargetAccessory(AccessoryInfo paramAccessoryInfo)
    {
        this.mTargetAccessory = paramAccessoryInfo;
        Gson localGson = new Gson();
        Type localType = new TypeToken()
        {
        }
                .getType();
        AccessoryConfig.setStringValue(this.mContext, "com.codoon.gps.key_accessory_upjson", localGson.toJson(paramAccessoryInfo, localType));
    }

    public boolean checkHasDownLoad(String paramString)
    {
        Object localObject = AccessoryConfig.getStringValue(this.mContext, "com.codoon.gps.key_accessory_upfile");
        if (TextUtils.isEmpty((CharSequence)localObject))
            return false;
        localObject = new File((String)localObject);
        return (((File)localObject).exists()) && (((File)localObject).getName().equals(paramString));
    }

    public boolean checkIsNeedUpload(String paramString1, String paramString2, Accessory paramAccessory)
    {
        if ((paramString1 == null) || (paramString2 == null))
            return false;
        CLog.d("enlong", "target deviec" + paramString1 + " targetVersion:" + paramString2 + " and curdevice:" + paramAccessory.mDeviceType + " curVersion:" + paramAccessory.version);
        return (paramAccessory.mDeviceType.startsWith(paramString1)) && (paramString2.compareTo(paramAccessory.version) > 0);
    }

    public void checkServiceVersion()
    {
       /* if (!new AccessoryManager(this.mContext).isBindAccessory())
        {
            setNeedUpload(false);
            return;
        }
        AccessoryVersionHttp localAccessoryVersionHttp = new AccessoryVersionHttp(this.mContext);
        NetUtil.DoHttpTask(this.mContext, localAccessoryVersionHttp, new IHttpHandler()
        {
            public void Respose(Object paramAnonymousObject)
            {
                if ((paramAnonymousObject != null) && ((paramAnonymousObject instanceof List)))
                {
                    paramAnonymousObject = (List)paramAnonymousObject;
                    if (paramAnonymousObject.size() > 0)
                        AccessoryWareManager.access$002(AccessoryWareManager.this, AccessoryWareManager.this.checkNeedUpgrade(paramAnonymousObject));
                }
            }
        });*/
    }

    public boolean downLoadFromService(final AccessoryInfo paramAccessoryInfo, final DownLoadCallBack paramDownLoadCallBack)
    {
      /*  if ((paramAccessoryInfo == null) || (paramAccessoryInfo.binary_url == null))
            return false;
        final String str1 = paramAccessoryInfo.binary_url.substring(paramAccessoryInfo.binary_url.lastIndexOf("/") + 1);
        CLog.d("enlong", "fileName：" + str1 + " target name:" + paramAccessoryInfo.device_name);
        final String str2 = paramAccessoryInfo.binary_url;
        this.mCallBack = paramDownLoadCallBack;
        if ((checkHasDownLoad(str1)) && (paramDownLoadCallBack != null))
        {
            File localFile = new File(FilePathConstants.getAccessoryDownLoadPath() + File.separator + str1);
            String str3 = MD5Uitls.getFileMD5String(localFile);
            if ((paramAccessoryInfo.checksum != null) && (paramAccessoryInfo.checksum.equalsIgnoreCase(str3)))
            {
                if (paramDownLoadCallBack != null)
                    paramDownLoadCallBack.downLoadResult(0, localFile.getAbsolutePath());
                return true;
            }
            localFile.delete();
        }
        if (this.downLoadRunable == null)
            this.downLoadRunable = new Runnable()
            {
                public void run()
                {
                    Object localObject3 = null;
                    Object localObject2 = null;
                    int j = 0;
                    File localFile2 = null;
                    FileOutputStream localFileOutputStream = null;
                    Object localObject5 = null;
                    File localFile1 = localFile2;
                    Object localObject4 = localFileOutputStream;
                    try
                    {
                        RequestResult localRequestResult = new DownLoadHttp(AccessoryWareManager.this.mContext).requestByGet(AccessoryWareManager.this.mContext, str2);
                        int i = j;
                        localObject2 = localRequestResult;
                        localFile1 = localFile2;
                        localObject3 = localRequestResult;
                        localObject4 = localFileOutputStream;
                        if (localRequestResult.getStatusCode() == 200)
                        {
                            localObject2 = localRequestResult;
                            localFile1 = localFile2;
                            localObject3 = localRequestResult;
                            localObject4 = localFileOutputStream;
                            long l3 = paramAccessoryInfo.size;
                            localObject2 = localRequestResult;
                            localFile1 = localFile2;
                            localObject3 = localRequestResult;
                            localObject4 = localFileOutputStream;
                            localFile2 = new File(FilePathConstants.getAccessoryDownLoadPath() + File.separator + str1);
                            localObject2 = localRequestResult;
                            localFile1 = localFile2;
                            localObject3 = localRequestResult;
                            localObject4 = localFile2;
                            if (!localFile2.exists())
                            {
                                localObject2 = localRequestResult;
                                localFile1 = localFile2;
                                localObject3 = localRequestResult;
                                localObject4 = localFile2;
                                localFile2.createNewFile();
                            }
                            localObject2 = localRequestResult;
                            localFile1 = localFile2;
                            localObject3 = localRequestResult;
                            localObject4 = localFile2;
                            localObject5 = new byte[1024];
                            long l1 = 0L;
                            localObject2 = localRequestResult;
                            localFile1 = localFile2;
                            localObject3 = localRequestResult;
                            localObject4 = localFile2;
                            localFileOutputStream = new FileOutputStream(localFile2);
                            localObject2 = localRequestResult;
                            localFile1 = localFile2;
                            localObject3 = localRequestResult;
                            localObject4 = localFile2;
                            InputStream localInputStream = localRequestResult.asStream();
                            while (true)
                            {
                                localObject2 = localRequestResult;
                                localFile1 = localFile2;
                                localObject3 = localRequestResult;
                                localObject4 = localFile2;
                                i = localInputStream.read((byte[])localObject5);
                                if (i <= 0)
                                    break;
                                localObject2 = localRequestResult;
                                localFile1 = localFile2;
                                localObject3 = localRequestResult;
                                localObject4 = localFile2;
                                localFileOutputStream.write((byte[])localObject5, 0, i);
                                long l2 = l1 + i;
                                l1 = l2;
                                localObject2 = localRequestResult;
                                localFile1 = localFile2;
                                localObject3 = localRequestResult;
                                localObject4 = localFile2;
                                if (paramDownLoadCallBack != null)
                                {
                                    localObject2 = localRequestResult;
                                    localFile1 = localFile2;
                                    localObject3 = localRequestResult;
                                    localObject4 = localFile2;
                                    paramDownLoadCallBack.downLoadProgress((int)(l2 * 100L / l3));
                                    l1 = l2;
                                }
                            }
                            localObject2 = localRequestResult;
                            localFile1 = localFile2;
                            localObject3 = localRequestResult;
                            localObject4 = localFile2;
                            AccessoryConfig.setStringValue(AccessoryWareManager.this.mContext, "com.codoon.gps.key_accessory_upfile", localFile2.getAbsolutePath());
                            localObject2 = localRequestResult;
                            localFile1 = localFile2;
                            localObject3 = localRequestResult;
                            localObject4 = localFile2;
                            localFileOutputStream.close();
                            localObject2 = localRequestResult;
                            localFile1 = localFile2;
                            localObject3 = localRequestResult;
                            localObject4 = localFile2;
                            localInputStream.close();
                            i = j;
                            localObject5 = localFile2;
                            if (l1 == l3)
                            {
                                i = 1;
                                localObject5 = localFile2;
                            }
                        }
                        if (localRequestResult != null)
                            localRequestResult.DisConnect();
                        localObject2 = localObject5;
                        if (i == 0)
                        {
                            paramDownLoadCallBack.downLoadResult(-1, ((File)localObject5).getAbsolutePath());
                            return;
                        }
                    }
                    catch (Exception localException)
                    {
                        localObject3 = localObject2;
                        localObject4 = localFile1;
                        CLog.e("enlong", localException.toString());
                        if (localObject2 != null)
                            localObject2.DisConnect();
                        localObject2 = localFile1;
                        if (j == 0)
                        {
                            paramDownLoadCallBack.downLoadResult(-1, localFile1.getAbsolutePath());
                            return;
                        }
                    }
                    finally
                    {
                        if (localObject3 != null)
                            localObject3.DisConnect();
                        if (j == 0)
                        {
                            paramDownLoadCallBack.downLoadResult(-1, ((File)localObject4).getAbsolutePath());
                            return;
                        }
                    }
                    String str;
                    if ((localObject2 != null) && (localObject2.exists()))
                    {
                        str = MD5Uitls.getFileMD5String(localObject2);
                        if ((paramAccessoryInfo.checksum == null) || (!paramAccessoryInfo.checksum.equalsIgnoreCase(str)))
                            break label720;
                        CLog.d("enlong", "rigth md5:" + str);
                        if (paramDownLoadCallBack != null)
                            paramDownLoadCallBack.downLoadResult(0, localObject2.getAbsolutePath());
                    }
                    return;
                    label720: CLog.e("enlong", "my MD5:" + str + " cloud:" + paramAccessoryInfo.checksum);
                    paramDownLoadCallBack.downLoadResult(-2, localObject2.getAbsolutePath());
                }
            };
        new Thread(this.downLoadRunable).start();*/
        return true;
    }

    public Accessory getCurAccessory()
    {
        return this.mAccessoryManager.getCurAccessory();
    }

    public DownLoadCallBack getDownLoadCallBack()
    {
        return this.mCallBack;
    }

    public AccessoryInfo getTargetAccessory()
    {
        if (this.mTargetAccessory == null)
        {
            Gson localGson = new Gson();
            Type localType = new TypeToken()
            {
            }
                    .getType();
            String str = AccessoryConfig.getStringValue(this.mContext, "com.codoon.gps.key_accessory_upjson");
            if (!TextUtils.isEmpty(str))
                this.mTargetAccessory = ((AccessoryInfo)localGson.fromJson(str, localType));
        }
        return this.mTargetAccessory;
    }

    public boolean isBootMode()
    {
        return AccessoryConfig.getBooleanValue(this.mContext, "com.codoon.gps.accessory_bootmode", false);
    }

    public boolean isFirstFuncationShow()
    {
        String str = UserData.GetInstance(this.mContext).GetUserBaseInfo().id;
        str = "com.codoon.gps.accessory_fucation_show_" + str;
        return AccessoryConfig.getBooleanValue(this.mContext, str, true);
    }

    public boolean isNeedPop()
    {
        AccessoryInfo localAccessoryInfo = getTargetAccessory();
        return (localAccessoryInfo != null) && (localAccessoryInfo.popup == 1);
    }

    public boolean isNeedUpload()
    {
        return this.isNeedUpload;
    }

    public boolean isShowAlertUpgrade()
    {
        Object localObject = getTargetAccessory();
        if ((localObject != null) && (((AccessoryInfo)localObject).version_name != null))
        {
            localObject = "com.codoon.gps.accessory_upgrade_alert" + ((AccessoryInfo)localObject).version_name;
            return AccessoryConfig.getBooleanValue(this.mContext, (String)localObject, true);
        }
        return false;
    }

    public boolean isShowRedViewUpgrade()
    {
        Object localObject = getTargetAccessory();
        if ((localObject != null) && (((AccessoryInfo)localObject).version_name != null))
        {
            localObject = "com.codoon.gps.accessory_upgrade_redview_alert" + ((AccessoryInfo)localObject).version_name;
            return AccessoryConfig.getBooleanValue(this.mContext, (String)localObject, true);
        }
        return false;
    }

    public void removeDownUpgradeState()
    {
        setNeedUpload(false);
        Object localObject = AccessoryConfig.getStringValue(this.mContext, "com.codoon.gps.key_accessory_upfile");
        if (TextUtils.isEmpty((CharSequence) localObject))
            return;
        localObject = new File((String)localObject);
        if (((File)localObject).exists())
            ((File)localObject).delete();
    }

    public void saveVersion(String paramString)
    {
        this.mAccessoryManager.saveVersion(paramString);
    }

    public void saveVersion(String paramString1, String paramString2)
    {
        this.mAccessoryManager.saveVersion(paramString1, paramString2);
    }

    public void setAlertUpgrade(boolean paramBoolean)
    {
        Object localObject = getTargetAccessory();
        if ((localObject != null) && (((AccessoryInfo)localObject).version_name != null))
        {
            localObject = "com.codoon.gps.accessory_upgrade_alert" + this.mTargetAccessory.version_name;
            AccessoryConfig.setBooleanValue(this.mContext, (String)localObject, paramBoolean);
        }
    }

    public void setDownLoadCallBack(DownLoadCallBack paramDownLoadCallBack)
    {
        this.mCallBack = paramDownLoadCallBack;
    }

    public void setFuncationShow(boolean paramBoolean)
    {
        String str = UserData.GetInstance(this.mContext).GetUserBaseInfo().id;
        str = "com.codoon.gps.accessory_fucation_show_" + str;
        AccessoryConfig.setBooleanValue(this.mContext, str, paramBoolean);
    }

    public void setIsBootMode(boolean paramBoolean)
    {
        AccessoryConfig.setBooleanValue(this.mContext, "com.codoon.gps.accessory_bootmode", paramBoolean);
    }

    public void setNeedUpload(boolean paramBoolean)
    {
        this.isNeedUpload = paramBoolean;
        AccessoryConfig.setBooleanValue(this.mContext, "com.codoon.gps.key_accessory_needup", paramBoolean);
    }

    public void setShowRedViewUpgrade(boolean paramBoolean)
    {
        Object localObject = getTargetAccessory();
        if ((localObject != null) && (((AccessoryInfo)localObject).version_name != null))
        {
            localObject = "com.codoon.gps.accessory_upgrade_redview_alert" + this.mTargetAccessory.version_name;
            AccessoryConfig.setBooleanValue(this.mContext, (String)localObject, paramBoolean);
        }
    }

    public static abstract interface DownLoadCallBack
    {
        public static final int RESULT_CHECK_FAILED = -2;
        public static final int RESULT_NET_FAILED = -1;
        public static final int RESULT_SUCCESS = 0;

        public abstract void downLoadProgress(int paramInt);

        public abstract void downLoadResult(int paramInt, String paramString);
    }
}
