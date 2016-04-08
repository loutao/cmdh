package com.ratio.deviceService.data;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Mesogene on 2015/5/19.
 */
public class TimeoutCheck {
    private int TIMEOUT = 10000;
    private boolean isConnecting = false;
    private ITimeoutCallback mCallback;
    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message paramAnonymousMessage)
        {
            super.handleMessage(paramAnonymousMessage);
        }
    };
    private Runnable mRunnable = new Runnable()
    {
        public void run()
        {
            TimeoutCheck localTimeoutCheck = TimeoutCheck.this;
            localTimeoutCheck.mTryConnectIndex += 1;
            if (TimeoutCheck.this.isConnecting)
            {
                if (TimeoutCheck.this.mTryConnectIndex > TimeoutCheck.this.mTryConnectCounts << 3)
                {
                    TimeoutCheck.this.mCallback.onConnectFailed(TimeoutCheck.this.mTryConnectIndex);
                    return;
                }
                TimeoutCheck.this.mCallback.onReConnect(TimeoutCheck.this.mTryConnectIndex);
                TimeoutCheck.this.mHandler.postDelayed(TimeoutCheck.this.mRunnable, TimeoutCheck.this.TIMEOUT);
                return;
            }
            if (TimeoutCheck.this.mTryConnectIndex > TimeoutCheck.this.mTryConnectCounts)
            {
                TimeoutCheck.this.mCallback.onReceivedFailed();
                return;
            }
            TimeoutCheck.this.mCallback.onReSend();
            TimeoutCheck.this.mHandler.postDelayed(TimeoutCheck.this.mRunnable, TimeoutCheck.this.TIMEOUT);
        }
    };
    private int mTryConnectCounts = 0;
    private int mTryConnectIndex = 0;

    public TimeoutCheck(ITimeoutCallback paramITimeoutCallback)
    {
        this.mCallback = paramITimeoutCallback;
        this.mTryConnectIndex = 0;
    }

    public void restartChectTime()
    {
        this.mHandler.removeCallbacks(this.mRunnable);
        this.mHandler.postDelayed(this.mRunnable, this.TIMEOUT);
    }

    public void setIsConnection(boolean paramBoolean)
    {
        this.isConnecting = paramBoolean;
    }

    public void setTimeout(int paramInt)
    {
        this.TIMEOUT = paramInt;
    }

    public void setTryConnectCounts(int paramInt)
    {
        this.mTryConnectCounts = paramInt;
    }

    public void startCheckTimeout()
    {
        this.mHandler.removeCallbacks(this.mRunnable);
        this.mTryConnectIndex = 0;
        this.mHandler.postDelayed(this.mRunnable, this.TIMEOUT);
    }

    public void stopCheckTimeout()
    {
        this.mHandler.removeCallbacks(this.mRunnable);
    }

    public static abstract interface ITimeoutCallback
    {
        public abstract void onConnectFailed(int paramInt);

        public abstract void onReConnect(int paramInt);

        public abstract void onReSend();

        public abstract void onReceivedFailed();
    }
}
