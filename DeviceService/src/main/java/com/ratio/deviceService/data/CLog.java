package com.ratio.deviceService.data;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CLog {
    public static boolean isDebug = true;
    private static File logFile;

    private static boolean creatLogFile()
    {
        if (Environment.getExternalStorageState().equals("mounted"))
        {
            File localFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codoon_log");
            if (!localFile.exists())
                localFile.mkdir();
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MM-dd-HH-mm");
            logFile = new File(localFile.getAbsolutePath() + File.separator + "log_" + localSimpleDateFormat.format(new Date(System.currentTimeMillis())) + ".txt");
            if (logFile.exists())
                return false;
            try
            {
                logFile.createNewFile();
                return true;
            }
            catch (IOException localIOException)
            {
                localIOException.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean createLogFile(String paramString)
    {
        if (Environment.getExternalStorageState().equals("mounted"))
        {
            File localFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codoon_log");
            if (!localFile.exists())
                localFile.mkdir();
            new SimpleDateFormat("MM-dd-HH-mm");
            logFile = new File(localFile.getAbsolutePath() + File.separator + paramString + ".txt");
            if (!logFile.exists());
            try
            {
                logFile.createNewFile();
                return true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static void d(String paramString1, String paramString2)
    {
        try
        {
            boolean bool = isDebug;
            if (!bool);
            while (true)
            {

                Log.d(paramString1, paramString2);
                println(paramString1, paramString2);
                return;
            }
        }
        finally
        {
        }
    }

    public static void e(String paramString1, String paramString2)
    {
        try
        {
            boolean bool = isDebug;
            if (!bool);
            while (true)
            {

                Log.e(paramString1, paramString2);
                println(paramString1, paramString2);
                return;
            }
        }
        finally
        {
        }
    }

    public static void i(String paramString1, String paramString2)
    {
        try
        {
            boolean bool = isDebug;
            if (!bool);
            while (true)
            {

                Log.i(paramString1, paramString2);
                println(paramString1, paramString2);
                return;
            }
        }
        finally
        {
        }
    }

    private static void println(String paramString1, String paramString2)
    {
        String str = paramString2;
        if (TextUtils.isEmpty(paramString2))
            str = "null";
        System.out.println(paramString1+"-------"+paramString2);
       /* if (!Environment.getExternalStorageState().equals("mounted"))
            return;
        if ((logFile == null) && (!creatLogFile()))
            return;
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(logFile, true);
            fileOutputStream.write((new SimpleDateFormat("MM-dd-HH-mm--SS").format(new Date(System.currentTimeMillis())) + " " + paramString1 + ": " + str + "\n").getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            return;
        }
        catch (FileNotFoundException fe)
        {
            fe.printStackTrace();
            return;
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }*/
    }

    public static void v(String paramString1, String paramString2)
    {
        try
        {
            boolean bool = isDebug;
            if (!bool);
            while (true)
            {

                Log.v(paramString1, paramString2);
                println(paramString1, paramString2);
                return;
            }
        }
        finally
        {
        }
    }
}