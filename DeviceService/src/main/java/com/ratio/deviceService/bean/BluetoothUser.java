package com.ratio.deviceService.bean;

import android.bluetooth.BluetoothDevice;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mesogene on 2015/5/19.
 */
public class BluetoothUser  implements Parcelable
{
    public static final Parcelable.Creator<BluetoothUser> CREATOR = new Parcelable.Creator()
    {
        public BluetoothUser createFromParcel(Parcel paramAnonymousParcel)
        {
            BluetoothUser localBluetoothUser = new BluetoothUser();
            localBluetoothUser.user_id = paramAnonymousParcel.readString();
            localBluetoothUser.product_id = paramAnonymousParcel.readString();
            localBluetoothUser.device_name = paramAnonymousParcel.readString();
            localBluetoothUser.device = ((BluetoothDevice)paramAnonymousParcel.readParcelable(BluetoothDevice.class.getClassLoader()));
            return localBluetoothUser;
        }

        public BluetoothUser[] newArray(int paramAnonymousInt)
        {
            return null;
        }
    };
    public BluetoothDevice device;
    public String device_name;
    public boolean hasLoad;
    public boolean isRomBand;
    public boolean iscanFriend;
    public Location mLoacation;
    public SurroundPersonJSON person_info;
    public String product_id;
    public String user_id;

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
        paramParcel.writeString(this.user_id);
        paramParcel.writeString(this.product_id);
        paramParcel.writeString(this.device_name);
        paramParcel.writeParcelable(this.device, 0);
    }
}