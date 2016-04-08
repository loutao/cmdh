package com.ratio.deviceService.communication.mancherster;

/**
 * Created by Mesogene on 2015/5/20.
 */
public abstract interface CstCode {
    public static final int ARR_LEN_SIMPLES = 32;
    public static final int AUDIORCORD_SOURCE = 1;
    public static final int AUDIORECORD_CONFIG = 1;
    public static final int AUDIORECORD_SIMPLE = 44100;
    public static final int AUDIOTRACK_CONFIG = 3;
    public static final int AUDIOTRACK_MODE = 1;
    public static final int AUDIOTRACK_SIMPLES = 44100;
    public static final int AUDIOTRACK_STREAM_TYPE = 3;
    public static final int AUDIO_ENCODING = 2;
    public static final short NUM_HIGH = 32767;
    public static final short NUM_LOW = -32767;
    public static final String ONE = "01";
    public static final String START_BIT = "10";
    public static final String STOP_BIT = "01";
    public static final String VERIFY_BIT_EVEN = "10";
    public static final String VERIFY_BIT_UNEVEN = "01";
    public static final String ZERO = "10";
}