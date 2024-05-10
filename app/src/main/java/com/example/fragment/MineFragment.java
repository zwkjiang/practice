package com.example.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.bean.CarBean;
import com.example.bean.EventBean;
import com.example.broadcase.MyBroadCase;
import com.example.broadcase.OtherBroadCase;
import com.example.common.Contons;
import com.example.common.LogUtils;
import com.example.common.executor.ExecutorManager;
import com.example.common.view.BaseFragment;
import com.example.custom.view.MyTextView;
import com.example.login.LoginUser;
import com.example.service.LoginService;
import com.example.service.NotificationService;
import com.example.textview.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class MineFragment extends BaseFragment implements View.OnTouchListener {

    private VideoView videoView;
    private Button videoStart;
    private Button videoStop;


    private MediaController mediaController;
    private MyTextView videoText;
    private MyTextView videoText2;
    private MyTextView videoText3;

    private MyTextView videoText4;

    private MyTextView startService;

    private MyTextView login;

    private HandlerThread handlerThread;

    private Handler handler;

    private MyTextView videoWebView;

    private MyTextView sendBroadCase;

    private MyBroadCase myBroadCase;

    private OtherBroadCase otherBroadCase;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        sendBroadCase = getView().findViewById(R.id.send_broadCase);
        videoWebView = getView().findViewById(R.id.video_web_view);
        videoText = getView().findViewById(R.id.video_text);
        videoText2 = getView().findViewById(R.id.video_text2);
        videoText3 = getView().findViewById(R.id.video_text3);
        videoView = getView().findViewById(R.id.video_view);
        videoStart = getView().findViewById(R.id.video_start);
        videoStop = getView().findViewById(R.id.video_stop);
        videoText4 = getView().findViewById(R.id.notify);
        startService = getView().findViewById(R.id.startService);
        login = getView().findViewById(R.id.login);
        mediaController = new MediaController(getContext());
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        handlerThread = new HandlerThread("ThreadTest");
        handlerThread.start();
        EventBus.getDefault().register(this);
    }

    private LoginUser loginUser;
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.i("客户端建立成功");

            Messenger messenger = new Messenger(service);
            Message obtain = Message.obtain(null, 1, 0, 0);
            try {
                messenger.send(obtain);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

//            loginUser = LoginUser.Stub.asInterface(service);
//            String result = "";
//            Parcel data = Parcel.obtain();
//            Parcel reply = Parcel.obtain();
//            try {
//                data.writeInterfaceToken("IPCService");
//                data.writeInt(1);
//                service.transact(0x001,data,reply,0);
//                reply.readException();
//                result = reply.readString();
//            } catch (RemoteException e) {
//                throw new RuntimeException(e);
//            } finally {
//                data.recycle();
//                reply.recycle();
//            }
//            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.i("客户端建立失败");
        }
    };

    private void startUserService() {
        Intent intent = new Intent("com.service.login.user");
        intent.setPackage("com.example.loginservice");
        getContext().bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void initData() {
        initBroadCase();
    }

    private void initBroadCase() {
            getContext().sendStickyBroadcast(new Intent("com.test.broadCase"));
    }

    @Override
    public void initListener() {
        videoStart.setOnClickListener(this);
        videoStop.setOnClickListener(this);
        videoText.setOnClickListener(this);
        videoText2.setOnClickListener(this);
        videoText3.setOnClickListener(this);
        videoText4.setOnClickListener(this);
        videoWebView.setOnClickListener(this);
        startService.setOnClickListener(this);
        sendBroadCase.setOnClickListener(this);
        login.setOnClickListener(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean eventBean){
        switch (eventBean.getType()) {
            case "main4":
                videoText3.setText(String.valueOf(eventBean.getObject()));
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_start:
                videoView.start();
                break;
            case R.id.video_stop:
                videoView.stopPlayback();
                break;
            case R.id.video_text:
                ARouter.getInstance().build(Contons.APP_MAIN4).navigation();
                break;
            case R.id.video_text2:
                ExecutorManager.getExecutorManager(getContext()).setFixedThread(new Runnable() {
                    @Override
                    public void run() {
                        saveCarBean("", new CarBean("大卡车", 100000));
                    }
                });
            case R.id.video_text3:
                ExecutorManager.getExecutorManager(getContext()).setFixedThread(new Runnable() {
                    @Override
                    public void run() {
                        getCarBean();
                    }
                });
            case R.id.video_web_view:
                ARouter.getInstance().build(Contons.WEB_VIEW).navigation();
                break;
            case R.id.notify:
                startNotificationService();
                break;
            case R.id.startService:
                startUserService();
                break;
            case R.id.login:
                String zwk = null;
                try {
                    zwk = loginUser.login("zwk", "1232131");
                    LogUtils.i(zwk);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.send_broadCase:
                IntentFilter intentFilter = new IntentFilter();
                IntentFilter intentFilter2 = new IntentFilter();
                myBroadCase = new MyBroadCase();
                otherBroadCase = new OtherBroadCase();
                intentFilter.addAction("com.test.broadCase");
                intentFilter.setPriority(1);
                intentFilter2.addAction("com.test.broadCase");
                intentFilter2.setPriority(3);
                LocalBroadcastManager.getInstance(getContext()).registerReceiver(myBroadCase,intentFilter);
                getContext().registerReceiver(otherBroadCase,intentFilter2);
                break;
            default:break;
        }
    }

    public void startNotificationService() {
        Intent intent = new Intent(getContext(), NotificationService.class);
        Objects.requireNonNull(getContext()).startService(intent);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    private void saveCarBean(String type, CarBean carBean) {
        if (!checkPermission()) {
            return;
        }
//        Log.i("MineFragment", "path == " + Environment.getExternalStorageState().getPath());
        Log.i("MineFragment", "Absolute == ");
        File file = new File(getContext().getExternalFilesDir(null).getPath(), "test");
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        File file1 = new File(file + "/test.txt");
        try {
            if (!file1.exists()) {
                file1.createNewFile();
            }
            fos = new FileOutputStream(file1);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(carBean);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getCarBean() {
        if (!checkPermission()) {
            return;
        }
        File test = new File(getContext().getExternalFilesDir(null), "test");
        File file = null;
        if (!test.exists()) {
            return;
        } else {
            file = new File(test + "/test.txt");
            if (!file.exists()) {
                return;
            }
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            CarBean o = (CarBean) ois.readObject();
            Log.i("MineFragment", o.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, 101);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(myBroadCase);
        EventBus.getDefault().unregister(this);
    }
}
