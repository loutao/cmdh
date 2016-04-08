package com.ratio.deviceService.datamanager;

/**
 * Created by Mesogene on 2015/5/19.
 */
public abstract interface AccessoryConstOrder {
    public static final int ACTION_CONNECT = 1;
    public static final int ACTION_DISS_CONNECT = -1;
    public static final int ACTION_FRIENDS_TURN = 11;
    public static final int ACTION_FRIENDS_WARNING = 10;
    public static final int ACTION_GET_BATTERY = 7;
    public static final int ACTION_GET_DEVICE_INFO = 8;
    public static final int ACTION_GET_USERINFO = 6;
    public static final int ACTION_SYNC_DATA = 2;
    public static final int ACTION_UPATE_ALARM = 5;
    public static final int ACTION_UPDATE_CLOCK = 3;
    public static final int ACTION_UPDATE_USERINFO = 4;
    public static final int ACTION_UPGRADE = 101;
    public static final int CHANGE_CONNECT_DELAY = 3000;
    public static final int CHANGE_CONNECT_METHOD = 240;
    public static final int CONNECT_BLE = 241;
    public static final int FOUND_BLE_DEVICE = 33;
    public static final int MSG_CLEAR_DATA_OVER = 12;
    public static final int MSG_FRIENDS_SET_OVER = 50;
    public static final int MSG_FRIENDS_WARING_OVER = 49;
    public static final int MSG_SERTCH_NEXT_DEVICE = 51;
    public static final int SEARTCH_BLE = 2;
    public static final int SEARTCH_FSK = 1;
    public static final int SEARTCH_MAN = 0;
    public static final int SERATCH_TIME_OUT = 34;
    public static final int SYNCDATA_DEVICE_BEGIN = 61680;
    public static final int SYNCDATA_DEVICE_BINDSUCESS = 18;
    public static final int SYNCDATA_DEVICE_BUTTERY = 8;
    public static final int SYNCDATA_DEVICE_CLOCKINFO = 11;
    public static final int SYNCDATA_DEVICE_CONENCT_INTERRUPT = 252;
    public static final int SYNCDATA_DEVICE_CONNECTED = 2;
    public static final int SYNCDATA_DEVICE_CURDAY = 13;
    public static final int SYNCDATA_DEVICE_DATA_NULL = 240;
    public static final int SYNCDATA_DEVICE_ID = 3;
    public static final int SYNCDATA_DEVICE_ING = 1;
    public static final int SYNCDATA_DEVICE_NOT_MATCH = 251;
    public static final int SYNCDATA_DEVICE_NO_DEVICE = 254;
    public static final int SYNCDATA_DEVICE_NO_SUPPORT = 253;
    public static final int SYNCDATA_DEVICE_OVER = 5;
    public static final int SYNCDATA_DEVICE_TIMEOUT = 255;
    public static final int SYNCDATA_DEVICE_TIME_TODAY = 16;
    public static final int SYNCDATA_DEVICE_TIME_TOTAL = 17;
    public static final int SYNCDATA_DEVICE_TOTAL = 28;
    public static final int SYNCDATA_DEVICE_USERINFO = 10;
    public static final int SYNCDATA_DEVICE_VERSION = 4;
    public static final int SYNCDATA_GET_WEIGHT = 48;
    public static final int SYNCDATA_SERVICE_FAILED = 6;
    public static final int SYNCDATA_SERVICE_SUCCED = 7;
    public static final int SYNC_TO_SERVICE = 9;
    public static final int UPGRADE_BOOT_VERSION = 229;
    public static final int UPGRADE_CHANGE_BOOT_MODE = 227;
    public static final int UPGRADE_CONNECT_BOOT_MODE = 228;
    public static final int UPGRADE_DEVICE_ING = 225;
    public static final int UPGRADE_DEVICE_RESULT = 226;
}
