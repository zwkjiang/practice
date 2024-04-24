package com.example.textview;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.adapter.TextAdapter;
import com.example.common.Contons;
import com.example.socket.TCPServiceSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

@Route(path = Contons.SOCKET)
public class SocketActivity extends BaseActivity implements View.OnClickListener {
    private ListView list;
    private LinearLayout ly;
    private EditText edit;
    private Button send;

    private TextAdapter textAdapter;

    private ArrayList<String> listData;

    private Socket mClientSocket;
    private PrintWriter printWriter;

    private AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_RECEIVE_NEW_MSG:
                    listData.add((String) msg.obj);
                    textAdapter.setDatas(listData);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    send.setEnabled(true);
                    break;
                default:break;
            }
        }
    };

    @Override
    public void initView() {
        list = (ListView) findViewById(R.id.list);
        ly = (LinearLayout) findViewById(R.id.ly);
        edit = (EditText) findViewById(R.id.edit);
        send = (Button) findViewById(R.id.send);
        send.setEnabled(false);
        listData = new ArrayList<>();
        textAdapter = new TextAdapter(listData, R.layout.item_text, this);
        list.setAdapter(textAdapter);
        Intent intent = new Intent(this, TCPServiceSocket.class);
        startService(intent);
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectTCPServer();
            }
        }).start();
    }

    private void connectTCPServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                printWriter = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream()), true);
                handler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                System.out.println("connect server success");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                System.out.println("connect tcp server failed, retry......");
            }
        }
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!atomicBoolean.get()){
                System.out.println("客户端收到");
                String str = in.readLine();
                if (str!=null){
                    String time = formatDateTime(System.currentTimeMillis());
                    String showMsg = "server "+time+":"+str;
                    System.out.println("客户端收到"+showMsg);
                    handler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG,showMsg).sendToTarget();
                }
            }
            System.out.println("quit");
            in.close();
            printWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatDateTime(long time) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        send.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_socket;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String string = edit.getText().toString();
                        printWriter.println(string);
                        String time = formatDateTime(System.currentTimeMillis());
                        String sendMes = "self "+time+":"+string;
                       handler.post(new Runnable() {
                           @Override
                           public void run() {
                               listData.add(sendMes);
                               textAdapter.setDatas(listData);
                           }
                       });
                    }
                }).start();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        atomicBoolean.set(true);
        if (mClientSocket !=null){
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();

    }
}
