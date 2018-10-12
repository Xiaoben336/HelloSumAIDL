package com.example.zjf.hellosumaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HelloSumAIDLActivity extends AppCompatActivity {
    IAdditionService service;
    AdditionServiceConnection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_sum_aidl);
        initService();
        Button buttonCalc = (Button)findViewById(R.id.buttonCalc);
        buttonCalc.setOnClickListener(new View.OnClickListener() {
            EditText value1 = (EditText)findViewById(R.id.value1);
            EditText value2= (EditText)findViewById(R.id.value2);
            TextView result = (TextView)findViewById(R.id.result);
            @Override
            public void onClick(View v) {
                int v1, v2, res = -1;
                v1 = Integer.parseInt(value1.getText().toString());
                v2 = Integer.parseInt(value2.getText().toString());

                try {
                    res = service.add(v1, v2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                result.setText(Integer.valueOf(res).toString());
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseService();
    }

    private void releaseService() {
        unbindService(connection);
        connection = null;
    }

    private void initService() {
        connection = new AdditionServiceConnection();
        Intent intent = new Intent();
        intent.setClassName("com.example.zjf.hellosumaidl",AdditionService.class.getName());
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    class AdditionServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = IAdditionService.Stub.asInterface(boundService);
            Toast.makeText(HelloSumAIDLActivity.this, "Service connected", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            service = null;
            Toast.makeText(HelloSumAIDLActivity.this, "Service disconnected", Toast.LENGTH_LONG).show();
        }
    }
}
