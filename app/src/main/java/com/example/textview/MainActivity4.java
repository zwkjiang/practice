package com.example.textview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.example.aidl.CarBean;
import com.example.bean.EventBean;
import com.example.bean.GoodBean;
import com.example.bean.GoodClick;
import com.example.common.Contons;
import com.example.common.MyArrayQueue;
import com.example.common.MyArrayQueueRing;
import com.example.common.MyLinkedList;
import com.example.common.MySortUtils;
import com.example.common.MyStack;
import com.example.common.executor.ExecutorManager;
import com.example.common.lock.MyLockService;
import com.example.common.lock.MyLockThread;
import com.example.textview.databinding.ActivityMain4Binding;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
@Route(path = Contons.APP_MAIN4)
public class MainActivity4 extends BaseActivity {
    private Button mian4Text;
    private Button mian4Text2;

    private MyLinkedList<Integer> myLinkedList;

    private MyArrayQueue myArrayQueue;

    private MyStack myStack;

    private MyArrayQueueRing myArrayQueueRing;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mian4_text:
//                reentrantLock();
                MyLockService myLockService = new MyLockService();
                myLockService.moreThread();
                EventBus.getDefault().post(new EventBean("main4", "成功"));
                break;
            case R.id.mian4_text2:
                finish();
                break;
        }
    }

    public void reentrantLock() {
        ExecutorManager.getExecutorManager(this).setFixedThread(new Runnable() {
            @Override
            public void run() {
                MyLockService myLockService = new MyLockService();
                MyLockThread myLockThread = new MyLockThread(myLockService);
                myLockThread.start();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                myLockService.signal();
            }
        });
    }

    @Override
    public void initView() {
        ActivityMain4Binding activityMain4Binding = DataBindingUtil.setContentView(this, R.layout.activity_main4);
        activityMain4Binding.setGood(new GoodBean("火龙果", 30000, false));
        activityMain4Binding.setClick(new GoodClick());
        mian4Text = findViewById(R.id.mian4_text);
        mian4Text2 = findViewById(R.id.mian4_text2);
        myLinkedList = new MyLinkedList<>();
        myLinkedList.tailInsert(1);
        myLinkedList.tailInsert(2);
        myLinkedList.tailInsert(3);
        myLinkedList.tailInsert(4);
        myLinkedList.tailInsert(5);
        myArrayQueue = new MyArrayQueue(5);
        myArrayQueue.enqueue("1");
        myArrayQueue.enqueue("2");
        myArrayQueue.enqueue("3");
        myArrayQueue.enqueue("4");
        myArrayQueue.enqueue("5");
        myStack = new MyStack(5);
        myStack.push("1");
        myStack.push("2");
        myStack.push("3");
        myStack.push("4");
        myStack.push("5");
        myArrayQueueRing = new MyArrayQueueRing(5);
        myArrayQueueRing.enqueue("1");
        myArrayQueueRing.enqueue("2");
        myArrayQueueRing.enqueue("3");
        myArrayQueueRing.enqueue("4");
        myArrayQueueRing.enqueue("5");
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        Set<Object> objects = objectObjectHashMap.keySet();
        for (Object key : objects) {
            Object o = objectObjectHashMap.get(key);
        }
//        myLinkedList.headInsert(6);
    }

    @Override
    public void initListener() {
        mian4Text.setOnClickListener(this);
        mian4Text2.setOnClickListener(this);
    }

    @Override
    public void initData() {
    }

    private Socket mSocket;

    public void connect() {
        try {
            mSocket = new Socket();
            mSocket.connect(new InetSocketAddress("test/zwk", 8888), 5000);
            mSocket.setKeepAlive(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void performAction() {
        Toast.makeText(this, "已经有权限了", Toast.LENGTH_SHORT).show();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void doOnPermissionDenied() {
        Toast.makeText(this, "你拒绝了权限", Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void doOnShowRationale(PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setTitle("存储权限申请")
                .setMessage("存储照片需要访问权限")
                .setPositiveButton("我知道了继续吧", ((dialog, which) -> request.proceed()))
                .setNegativeButton("我不需要了,不要再请求了", ((dialog, which) -> request.cancel()))
                .create()
                .show();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void doOnNeverAskAgain() {
        Toast.makeText(this, "你拒绝了权限", Toast.LENGTH_SHORT).show();
    }

    public void requestPermissions() {
        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        int i = ContextCompat.checkSelfPermission(this, permission[0]);
        if (i != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission[0])) {
                new AlertDialog.Builder(this)
                        .setTitle("为什么要使用权限")
                        .setMessage("请求SD卡权限,需要保存照片")
                        .setPositiveButton("好的", (dialog, which) -> ActivityCompat.requestPermissions(this, permission, 3))
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, permission, 3);
            }
        }
    }

    public void queueRingTest() {
        String dequeue = myArrayQueueRing.dequeue();
        myArrayQueueRing.enqueue("6");
        String dequeue1 = myArrayQueueRing.dequeue();
        Log.i("ZWK", "queueRingTest = " + dequeue1);
        String dequeue2 = myArrayQueueRing.dequeue();
        Log.i("ZWK", "queueRingTest = " + dequeue2);
        String dequeue3 = myArrayQueueRing.dequeue();
        Log.i("ZWK", "queueRingTest = " + dequeue3);
        String dequeue4 = myArrayQueueRing.dequeue();
        Log.i("ZWK", "queueRingTest = " + dequeue4);
        String dequeue5 = myArrayQueueRing.dequeue();
        Log.i("ZWK", "queueRingTest = " + dequeue5);
    }

    public void stackTest() {
        String pop = myStack.pop();
        Log.i("ZWK", "pop = " + pop);
        String pop1 = myStack.pop();
        Log.i("ZWK", "pop = " + pop1);
        String pop2 = myStack.pop();
        Log.i("ZWK", "pop = " + pop2);
        String pop3 = myStack.pop();
        Log.i("ZWK", "pop = " + pop3);
        String pop4 = myStack.pop();
        Log.i("ZWK", "pop = " + pop4);
    }

    public void queueTest() {
        String dequeue = myArrayQueue.dequeue();
        Log.i("ZWK", "dequeue = " + dequeue);
        String dequeue2 = myArrayQueue.dequeue();
        Log.i("ZWK", "dequeue = " + dequeue2);
        String dequeue3 = myArrayQueue.dequeue();
        Log.i("ZWK", "dequeue = " + dequeue3);
        String dequeue4 = myArrayQueue.dequeue();
        Log.i("ZWK", "dequeue = " + dequeue4);
        String dequeue5 = myArrayQueue.dequeue();
        Log.i("ZWK", "dequeue = " + dequeue5);
        myArrayQueue.enqueue("6");
        myArrayQueue.enqueue("7");
        myArrayQueue.enqueue("8");
        myArrayQueue.enqueue("9");
        myArrayQueue.enqueue("10");
        myArrayQueue.enqueue("11");
        String dequeue6 = myArrayQueue.dequeue();
        Log.i("ZWK", "dequeue = " + dequeue6);
        String dequeue7 = myArrayQueue.dequeue();
        Log.i("ZWK", "dequeue = " + dequeue7);
        String dequeue8 = myArrayQueue.dequeue();
        Log.i("ZWK", "dequeue = " + dequeue8);
        String dequeue9 = myArrayQueue.dequeue();
        Log.i("ZWK", "dequeue = " + dequeue9);
        String dequeue10 = myArrayQueue.dequeue();
        Log.i("ZWK", "dequeue = " + dequeue10);
        String dequeue11 = myArrayQueue.dequeue();
        Log.i("ZWK", "dequeue = " + dequeue11);
    }

    private void linkedTest() {
        MyLinkedList.Node<Integer> node1 = myLinkedList.getValue(1);
//                Log.i("ZWK","getNode = "+node1.getValue());
//                collectGlobalSettings();
//                createObserver();
//                int valueIndex = myLinkedList.getValueIndex(3);
//                Log.i("ZWK","getValueIndex = "+valueIndex);
        int size = myLinkedList.linkSize();
//                Log.i("ZWK","size = " + size);
//                int delete = myLinkedList.deleteIndex(3);
        myLinkedList.deleteValue(5);
        MyLinkedList.Node<Integer> node = myLinkedList.getHead().getNext();
        while (node != null) {
//                    Log.i("ZWK","value = " + node.getValue());
            node = node.getNext();
        }
        int size2 = myLinkedList.linkSize();
//                Log.i("ZWK","size = " + size2);
    }

    public void collectSystemSettings() {
        StringBuilder result = new StringBuilder();
        Field[] fields = Settings.System.class.getFields();
        for (Field key : fields) {
            if (!key.isAnnotationPresent(Deprecated.class) && key.getType() == String.class) {
                try {
                    String string = Settings.System.getString(this.getContentResolver(), (String) key.get(null));
                    if (string != null) {
                        result.append(key.getName()).append("=").append(string).append("\n");
                    }

                } catch (IllegalAccessException e) {
                    Log.w("MainActivity4", String.valueOf(e));
                }
            }
        }
        Log.i("ZWKKK", result.toString());
    }

    @SuppressLint("ObsoleteSdkInt")
    public void collectGlobalSettings() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return;
        }
        StringBuilder result = new StringBuilder();
        try {
            Class<?> global = Class.forName("android.provider.Settings$Global");
            Field[] fields = global.getFields();
            Method getString = global.getMethod("getString", ContentResolver.class, String.class);

            for (Field key : fields) {
                if (!key.isAnnotationPresent(Deprecated.class) && key.getType() == String.class && isAuthorized(key)) {
                    Object invoke = getString.invoke(null, this.getContentResolver(), key.get(null));
                    result.append(key).append("=").append(invoke).append("\n");
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        Log.i("ZWKKK", result.toString());
    }

    private boolean isAuthorized(Field key) {
        return key != null && !key.getName().startsWith("WIFI_AP");
    }

    public void createObserver() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                ExecutorManager.getExecutorManager(MainActivity4.this).setFixedThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        emitter.onNext("fff");
                    }
                });
            }
        });

        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.i("ZWKKKk", s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void setBinding() {
        super.setBinding();
        isBindingView = true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity4444444", "onDestroy");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MainActivity4444444", "onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainActivity4444444", "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity4444444", "onStart");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MainActivity4444444", "onRestart");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity4444444", "onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity4444444", "onStop");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivity4PermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    /**
     * 实现求取两个字符串中最长重复字符串长度，并给出该重复字符串
     *
     * @param strA 字符串A
     * @param strB 字符串B
     * @return 最长重复字符串
     */
    public String lastComStr(String strA, String strB) {
        // 重复字符串最大长度
        int maxLen = 0;
        // 重复字符串最后结束的位置
        int endLen = 0;

        int strAlen = strA.length();
        int strBlen = strB.length();

        // 创建二维数组 A为行 B为列
        int[][] arry = new int[strAlen][strBlen];
        for (int i = 0; i < strAlen; i++) {
            for (int j = 0; j < strBlen; j++) {
                if (strA.charAt(i) == strB.charAt(j)) {
                    if (0 == i || j == 0) {
                        arry[i][j] = 1;
                    } else {
                        // 获取上一列一行对角值 arry[i - 1][j - 1]如果不是0 说明重复字符长度+1
                        arry[i][j] = 1 + arry[i - 1][j - 1];
                    }
                }

                if (maxLen < arry[i][j]) {
                    // 取最重复字符串长度
                    maxLen = arry[i][j];
                    endLen = i;
                }
            }
        }

        return strA.substring(endLen - maxLen + 1, endLen + 1);
    }


}
