package com.ratio.deviceService.bean;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mesogene on 2015/5/19.
 */
public class CodoonBluethoothDevice  implements Parcelable
{
    public static final Parcelable.Creator<CodoonBluethoothDevice> CREATOR = new Parcelable.Creator()
    {
        public CodoonBluethoothDevice createFromParcel(Parcel paramAnonymousParcel)
        {
            CodoonBluethoothDevice localCodoonBluethoothDevice = new CodoonBluethoothDevice();
            localCodoonBluethoothDevice.device_name = paramAnonymousParcel.readString();
            localCodoonBluethoothDevice.device = ((BluetoothDevice)paramAnonymousParcel.readParcelable(BluetoothDevice.class.getClassLoader()));
            return localCodoonBluethoothDevice;
        }

        public CodoonBluethoothDevice[] newArray(int paramAnonymousInt)
        {
            return null;
        }
    };
    public BluetoothDevice device;
    public String device_name = "";

    public int describeContents()
    {
        return 0;
    }

    public boolean equals(Object paramObject)
    {
        if (paramObject == null)
            return false;
        if ((paramObject instanceof CodoonBluethoothDevice))
            return ((CodoonBluethoothDevice)paramObject).getDevice().getAddress().equals(this.device.getAddress());
        return super.equals(paramObject);
    }

    public BluetoothDevice getDevice()
    {
        return this.device;
    }

    public String getName()
    {
        return this.device_name;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
        paramParcel.writeString(this.device_name);
        paramParcel.writeParcelable(this.device, 0);
    }
}
