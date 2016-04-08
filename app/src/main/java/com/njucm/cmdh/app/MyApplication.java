package com.njucm.cmdh.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;

import com.njucm.cmdh.app.dao.DaoMaster;
import com.njucm.cmdh.app.dao.DaoSession;
import com.njucm.cmdh.app.images.DemoDuiTangImageReSizer;
import com.njucm.cmdh.app.images.PtrImageLoadHandler;
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;

import in.srain.cube.Cube;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.request.RequestCacheManager;
import in.srain.cube.util.CLog;
import in.srain.cube.util.CubeDebug;
import in.srain.cube.views.ptr.PtrFrameLayout;


public class MyApplication extends Application {
    /*
    *公用变量
     */
    public static String myUrl = "http://10.120.56.204:4312";
    public String getMyUrl(){
        return myUrl;
    }
    public void setMyUrl(String myUrl){
        this.myUrl = myUrl;
    }
    /**
     * Multiple custom typefaces support
     */
    private TypefaceCollection mJuiceTypeface;
    /**
     * Multiple custom typefaces support
     */
    private TypefaceCollection mArchRivalTypeface;
    /**
     * Multiple custom typefaces support
     */
    private TypefaceCollection mActionManTypeface;
    /**
     * Multiple custom typefaces support
     */
    private TypefaceCollection mSystemDefaultTypeface;
    /**
     * Multiple custom typefaces support
     */
    private TypefaceCollection mUbuntuTypeface;

    /**
     * android4.0字体
     */
    private TypefaceCollection myRobototypeface;

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    /**
     * 自定义中文楷体字体
     */
    private TypefaceCollection myFontKaiTi;
    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        String environment = "";

        if (environment.equals("production")) {
            CLog.setLogLevel(CLog.LEVEL_ERROR);
        } else if (environment.equals("beta")) {
            CLog.setLogLevel(CLog.LEVEL_WARNING);
        } else {
            // development
            CLog.setLogLevel(CLog.LEVEL_VERBOSE);
        }

        CubeDebug.DEBUG_IMAGE = true;
        PtrFrameLayout.DEBUG = true;
        PtrFrameLayout.DEBUG = false;

        ImageLoaderFactory.setDefaultImageReSizer(DemoDuiTangImageReSizer.getInstance());
        ImageLoaderFactory.setDefaultImageLoadHandler(new PtrImageLoadHandler());
        String dir = "request-cache";
        // ImageLoaderFactory.init(this);
        RequestCacheManager.init(this, dir, 1024 * 10, 1024 * 10);
        Cube.onCreate(this);
        // Load helper with default custom typeface (single custom typeface)
        TypefaceHelper.init(new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "fonts/ubuntu/Ubuntu-R.ttf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(), "fonts/ubuntu/Ubuntu-B.ttf"))
                .set(Typeface.ITALIC, Typeface.createFromAsset(getAssets(), "fonts/ubuntu/Ubuntu-RI.ttf"))
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(), "fonts/ubuntu/Ubuntu-BI.ttf"))
                .create());

        // Multiple custom typefaces support
        mJuiceTypeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "fonts/Juice/JUICE_Regular.ttf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(), "fonts/Juice/JUICE_Bold.ttf"))
                .set(Typeface.ITALIC, Typeface.createFromAsset(getAssets(), "fonts/Juice/JUICE_Italic.ttf"))
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(), "fonts/Juice/JUICE_Bold_Italic.ttf"))
                .create();

        // Multiple custom typefaces support
        mArchRivalTypeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "fonts/arch_rival/SF_Arch_Rival.ttf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(), "fonts/arch_rival/SF_Arch_Rival_Bold.ttf"))
                .set(Typeface.ITALIC, Typeface.createFromAsset(getAssets(), "fonts/arch_rival/SF_Arch_Rival_Italic.ttf"))
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(), "fonts/arch_rival/SF_Arch_Rival_Bold_Italic.ttf"))
                .create();

        // Multiple custom typefaces support
        mActionManTypeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "fonts/Action-Man/Action_Man.ttf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(), "fonts/Action-Man/Action_Man_Bold.ttf"))
                .set(Typeface.ITALIC, Typeface.createFromAsset(getAssets(), "fonts/Action-Man/Action_Man_Italic.ttf"))
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(), "fonts/Action-Man/Action_Man_Bold_Italic.ttf"))
                .create();

        // Multiple custom typefaces support
        mUbuntuTypeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "fonts/ubuntu/Ubuntu-R.ttf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(), "fonts/ubuntu/Ubuntu-B.ttf"))
                .set(Typeface.ITALIC, Typeface.createFromAsset(getAssets(), "fonts/ubuntu/Ubuntu-RI.ttf"))
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(), "fonts/ubuntu/Ubuntu-BI.ttf"))
                .create();

        myRobototypeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-Regular.ttf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-Bold.ttf"))
                .set(Typeface.ITALIC, Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-Italic.ttf"))
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(), "fonts/Roboto/Roboto-BoldItalic.ttf"))
                .create();

        myFontKaiTi = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "fonts/otherfonts/kaiti.ttf"))
                .create();
        // Multiple custom typefaces support
        mSystemDefaultTypeface = TypefaceCollection.createSystemDefault();

    }

    /**
     * Multiple custom typefaces support
     */
    public TypefaceCollection getJuiceTypeface() {
        return mJuiceTypeface;
    }

    /**
     * Multiple custom typefaces support
     */
    public TypefaceCollection getArchRivalTypeface() {
        return mArchRivalTypeface;
    }

    /**
     * Multiple custom typefaces support
     */
    public TypefaceCollection getActionManTypeface() {
        return mActionManTypeface;
    }

    /**
     * Multiple custom typefaces support
     */
    public TypefaceCollection getSystemDefaultTypeface() {
        return mSystemDefaultTypeface;
    }

    /**
     * Multiple custom typefaces support
     */
    public TypefaceCollection getUbuntuTypeface() {
        return mUbuntuTypeface;
    }

    public TypefaceCollection getMyRobototypeface() {
        return myRobototypeface;
    }

    public TypefaceCollection getMyFontKaiTi() {
        return myFontKaiTi;
    }

    public static DaoMaster getDaoMaster(Context context, String databaseName) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, databaseName, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * ??DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context, String databaseName) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context, databaseName);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
    public void restartApplication(){
        final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
