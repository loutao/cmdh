package com.ratio.deviceService.datamanager;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.ratio.deviceService.bean.BluetoothUser;
import com.ratio.deviceService.data.CLog;

/**
 * Created by Mesogene on 2015/5/24.
 */
public class AccessoryDeviceHelper {
    private static final String TAG = "AccessoryDeviceHelper";

    public static boolean isProductId(String paramString) {
        if (paramString != null) {
            String[] arryOfString = paramString.split("-");
            if ((arryOfString != null) && (arryOfString.length == 8))
                return true;
        }
        return false;
    }

    public static BluetoothUser parseDevice(BluetoothDevice paramBluetoothDevice, byte[] paramArrayOfByte) {
        Log.d("device ID:",paramBluetoothDevice.getAddress());
        if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 32)) {
            BluetoothUser localBluetoothUser = new BluetoothUser();
            int i = paramArrayOfByte[3] & 0xFF;
            int j = 3 + 1;
            byte[] localObject2;
            Object localObject1;
            for (int k = paramArrayOfByte[j]; k != 9; k = paramArrayOfByte[j]) {
                if (i >= 32)
                    return null;
                CLog.d("AccessoryDeviceHelper", "type is" + k + " and lengh is:" + i);
                int m = i - 1;
                localObject2 = new byte[m];
                k = 0;
                while (k < m) {
                    localObject2[k] = paramArrayOfByte[(j + m - k)];
                    k += 1;
                }
                localObject1 = "";
                k = 0;
                String str = "";
                if (k < localObject2.length) {

                    if ((localObject2[k] & 0xFF) > 15) ;
                    for (str = (String) localObject1 + Integer.toHexString(localObject2[k] & 0xFF);k<m ; str = (String) localObject1 + "0" + Integer.toHexString(localObject2[k] & 0xFF)) {
                        if ((k != 3) && (k != 5) && (k != 7)) {
                            localObject1 = str;
                            if (k != 9) k += 1;
                            if (k==m)break;

                        } else {
                            localObject1 = str + "-";
                            break;
                        }

                    }
                }
                if (localObject2.length == 16) {
                    CLog.d("AccessoryDeviceHelper", "user_id: " + (String) localObject1 + "   lenth:" + localObject2.length);
                    localBluetoothUser.user_id = ((String) localObject1);
                }
                j += i;
                i = paramArrayOfByte[j];
                j += 1;
            }
            j += 1;
            String str = new String(paramArrayOfByte, j, i - 1);
            CLog.d("AccessoryDeviceHelper", "parse name:" + str);
            if (AccessoryManager.isThirdBleDevice(str))
                return null;
            if (localBluetoothUser.user_id != null) {
                localBluetoothUser.device_name = str;
                return localBluetoothUser;
            }
            i = j + (i - 1);
            j = paramArrayOfByte[i];
            i += 1;
            int k;
            for (k = paramArrayOfByte[i]; (k & 0xFF) != 255; k = paramArrayOfByte[i]) {
                i += j;
                j = paramArrayOfByte[i];
                i += 1;
            }
            if ((k & 0xFF) == 255) {
                k = paramArrayOfByte[(i + 1)];
                CLog.d("AccessoryDeviceHelper", "parse type:255 length is:" + j + " index=" + i);
                localObject1 = "" + k;
                localObject1 = (String) localObject1 + "-" + (((paramArrayOfByte[(i + 2)] & 0xFF) << 8) + (paramArrayOfByte[(i + 3)] & 0xFF));
                localObject1 = (String) localObject1 + "-" + (((paramArrayOfByte[(i + 4)] & 0xFF) << 8) + (paramArrayOfByte[(i + 5)] & 0xFF));
                localObject1 = (String) localObject1 + "-" + (((paramArrayOfByte[(i + 6)] & 0xFF) << 8) + (paramArrayOfByte[(i + 7)] & 0xFF));
                localObject1 = (String) localObject1 + "-" + (paramArrayOfByte[(i + 8)] & 0xFF);
                localObject1 = (String) localObject1 + "-" + (((paramArrayOfByte[(i + 9)] & 0xFF) << 8) + (paramArrayOfByte[(i + 10)] & 0xFF));
                localObject1 = (String) localObject1 + "-" + (((paramArrayOfByte[(i + 11)] & 0xFF) << 8) + (paramArrayOfByte[(i + 12)] & 0xFF));
                localObject1 = (String) localObject1 + "-" + (paramArrayOfByte[(i + 13)] & 0xFF);
                String str10 = new String(paramArrayOfByte, i + 2, 12);
                CLog.d("AccessoryDeviceHelper", "----------------------id=" + str10);
                localBluetoothUser.product_id = ((String) localObject1);
                localBluetoothUser.device = paramBluetoothDevice;
                if (j == 21) {
                    localBluetoothUser.isRomBand = true;
                    i = paramArrayOfByte[(i + 14)] & 0x1;
                    CLog.d("AccessoryDeviceHelper", "romdevice:" + str + " product_id: " + (String) localObject1 + " isTurnFriends = " + i + " address:" + paramBluetoothDevice.getAddress());
                    if ((i == 1) && (AccessoryManager.isRomDevice(str)))
                        localBluetoothUser.iscanFriend = true;
                }
                localBluetoothUser.device_name = str;
            }
            return localBluetoothUser;
        }
        return null;
    }
}