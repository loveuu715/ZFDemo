package com.loveuu.vv.app;

/**
 * Created by VV on 2016/9/29.
 */

public class ErrorCodeProcess {

    private static ErrorCodeProcess sInstance;

    private ErrorCodeProcess() {
    }

    public static ErrorCodeProcess getInstance(){
        if (sInstance == null) {
            synchronized(ErrorCodeProcess.class) {
                if (sInstance == null) {
                    sInstance = new ErrorCodeProcess();
                }
            }
        }
        return sInstance;
    }

    public void processErrorCode(int errorCode) {
        switch (errorCode) {
            case 1002:
                break;
            case 2001:
                break;
            case 2002:
                break;
            case 2004:
                break;
            case 4003:
                break;
            case 4004:
                break;
            case 4006:
                break;
            case 4007:
                break;
        }
    }

}
