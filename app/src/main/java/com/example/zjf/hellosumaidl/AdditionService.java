package com.example.zjf.hellosumaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

public class AdditionService extends Service {
    public AdditionService(){

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IAdditionService.Stub() {
            @Override
            public int add(int value1, int value2) throws RemoteException {
                return value1 + value2;
            }
        };
    }
}
