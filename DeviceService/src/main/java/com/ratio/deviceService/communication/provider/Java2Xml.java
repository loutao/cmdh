package com.ratio.deviceService.communication.provider;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Mesogene on 2015/5/20.
 */
public class Java2Xml {
    private Context mContext;
    private SimpleDateFormat mDateTimeFormat;
    private StringBuilder sleepXML;
    private StringBuilder sportXML;

    public Java2Xml(Context paramContext)
    {
        this.mContext = paramContext;
        this.mDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.mDateTimeFormat.setTimeZone(TimeZone.getDefault());
        this.sportXML = new StringBuilder("<sportsdata>");
        this.sleepXML = new StringBuilder("<sleepdata>");
    }

    public void addSleepNode(int paramInt, long paramLong)
    {
        this.sleepXML.append("<item level=\"" + paramInt + "\" time=\"" + this.mDateTimeFormat.format(new Date(paramLong)) + "\"/>");
    }

    public void addSportNode(int paramInt1, int paramInt2, int paramInt3, long paramLong)
    {
        this.sportXML.append("<item steps=\"" + paramInt1 + "\"  distance=\"" + paramInt3 + "\" kcal=\"" + paramInt2 + "\" time=\"" + this.mDateTimeFormat.format(new Date(paramLong)) + "\"/>");
    }

    public String getXmlContent()
    {
        this.sportXML.append("</sportsdata>");
        this.sleepXML.append("</sleepdata>");
        return "<xml><root>" + this.sportXML.toString() + this.sleepXML.toString() + "</root></xml>";
    }

   /* public void save()
    {
        new FileManager().saveXmlToSD(this.mContext, "sportdata.xml", getXmlContent());
    }*/
}
