package com.im_hero.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.im_hero.aidldemo.Employee;
import com.im_hero.aidldemo.EmployeeManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MainActivity";

    private boolean mBound;
    private EmployeeManager mEmployeeManager;

    private List<Employee> mEmployees;
    private Button btnAddIn;
    private Button btnAddOut;
    private Button btnAddInout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onDestroy() {
        unbindService();
        super.onDestroy();
    }

    private void bindService() {
        Intent intent = new Intent("com.im_hero.aidldemo.service.EmployeeService");
        intent.setPackage("com.im_hero.aidldemo");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private void unbindService() {
        unbindService(conn);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "service connected binder:" + service);
            mEmployeeManager = EmployeeManager.Stub.asInterface(service);
            mBound = true;
            if (mEmployeeManager != null) {
                try {
                    mEmployees = mEmployeeManager.getEmployees();
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "onServiceConnected() called with: name = [" + name + "], mEmployees = [" + mEmployees + "]");
                } catch (RemoteException e) {
                    if (BuildConfig.DEBUG) Log.e(TAG, "onServiceConnected: ", e);
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "service disconnected");
            mBound = false;
        }
    };

    private void initView() {
        btnAddIn = findViewById(R.id.btnAddIn);
        btnAddOut = findViewById(R.id.btnAddOut);
        btnAddInout = findViewById(R.id.btnAddInout);
        btnAddIn.setOnClickListener(this);
        btnAddOut.setOnClickListener(this);
        btnAddInout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onClick EmployeeManager:" + mEmployeeManager);

        if (!mBound) {
            bindService();
        }

        if (mEmployeeManager != null) {
            try {
                Employee e = null;
                switch (v.getId()) {
                    case R.id.btnAddIn: {
                        e = new Employee("Client in", (short) 1);
                        Log.i(TAG, "onClick: " + mEmployeeManager.addEmployeeIn(e));
                    }
                    break;
                    case R.id.btnAddOut: {
                        e = new Employee("Client out", (short) 22);
                        Log.i(TAG, "onClick: " + mEmployeeManager.addEmployeeOut(e));
                    }
                    break;
                    case R.id.btnAddInout: {
                        e = new Employee("Client inout", (short) 333);
                        Log.i(TAG, "onClick: " + mEmployeeManager.addEmployeeInout(e));
                    }
                    break;
                }
                Log.i(TAG, "onClick: Client e = " + e);
            } catch (RemoteException e) {
                Log.e(TAG, "onClick: ", e);
            }
        }
    }
}
