package com.example.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPServiceSocket extends Service {

    private boolean mIsServiceDestroy = false;
    private String[] mDefinedMessages = new String[]{
            "你好啊,哈哈",
            "很高兴认识你啊",
            "最近怎么样啊",
            "别来无恙你看起来"
    };

    @Override
    public void onCreate() {
        new Thread(new TcpService()).start();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroy = true;
        super.onDestroy();
    }

    private class TcpService implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                System.err.println("establish tcp server failed,port:8688");
                e.printStackTrace();
                return;
            }
            while (!mIsServiceDestroy) {
                try {
                    Socket accept = serverSocket.accept();
                    System.out.println("accept");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseClient(accept);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void responseClient(Socket accept) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(accept.getInputStream()));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(accept.getOutputStream())),true);
        out.println("欢迎来到聊天室");
        while (!mIsServiceDestroy) {
            System.out.println("msg from client:");
            String str = in.readLine();
            System.out.println("msg from client:" + str);
            if (str == null){
                // 客户端断开链接
                break;
            }
            int i = new Random().nextInt(mDefinedMessages.length);
            String message = mDefinedMessages[i];
            out.print(message);
            System.out.println("send:"+message);
        }
        System.out.println("client quit");
        out.close();
        in.close();
        accept.close();
    }
}
