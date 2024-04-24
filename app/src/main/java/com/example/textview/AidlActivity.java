package com.example.textview;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.aidl.BinderPool;
import com.example.aidl.ComputeImpl;
import com.example.aidl.SecurityCenterImpl;
import com.example.common.Contons;

@Route(path = Contons.AIDL)
public class AidlActivity extends BaseActivity{
    private Button bt1;


    @Override
    public void initView() {
        bt1 = (Button) findViewById(R.id.bt1);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BinderPool binderPool = BinderPool.getInstance(AidlActivity.this);
                        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
                        ISecurityCenter iSecurityCenter = SecurityCenterImpl.asInterface(securityBinder);
                        Log.i("AidlActivity","visit ISecurityCenter");
                        String msg = "helloworld-安卓";
                        System.out.println("content:"+msg);
                        try {
                            String password = iSecurityCenter.encrypt(msg);
                            System.out.println("encrypt :"+password);
                            System.out.println("decrypt :"+iSecurityCenter.decrypt(password));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
                        ICompute compute = ComputeImpl.asInterface(computeBinder);
                        try {
                            System.out.println("3+5= "+compute.add(3,5));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_aidl;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onClick(View v) {

    }
}
