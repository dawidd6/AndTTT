package com.github.dawidd6.andttt.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import com.github.dawidd6.andttt.proto.Request;
import com.github.dawidd6.andttt.proto.Response;
import com.github.dawidd6.andttt.proto.Room;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;

public class ClientFragment extends BaseFragment {
    private Socket socket;
    private String host;
    private int port;
    private int bufferSize;
    private Request request;

    private ClientConnectThread connectThread;
    private ClientDisconnectThread disconnectThread;
    private ClientReceiveThread receiveThread;
    private ClientSendThread sendThread;

    private OnConnectSuccessfulListener onConnectSuccessfulListener;
    private OnConnectFailedListener onConnectFailedListener;
    private OnDisconnectListener onDisconnectListener;
    private OnResponseListener onResponseListener;

    public ClientFragment() {
        bufferSize = 4096;
        connectThread = new ClientConnectThread();
        disconnectThread = new ClientDisconnectThread();
        receiveThread = new ClientReceiveThread();
        sendThread = new ClientSendThread();

        setOnConnectFailedListener(null);
        setOnConnectSuccessfulListener(null);
        setOnDisconnectListener(null);
        setOnResponseListener(null);
    }

    public void connect(String host, int port) {
        this.host = host;
        this.port = port;
        connectThread.start();
    }

    public void disconnect() {
        if(socket != null)
            disconnectThread.start();
    }

    public void destroy() {
        setOnConnectFailedListener(null);
        setOnConnectSuccessfulListener(null);
        setOnDisconnectListener(null);
        setOnResponseListener(null);

        receiveThread.interrupt();
        sendThread.interrupt();
        connectThread.interrupt();
        disconnectThread.interrupt();
    }

    public void send(Request request) {
        this.request = request;
        sendThread.start();
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    public String getAddress() {
        return isConnected() ? host + ":" + port : "";
    }

    private class ClientSendThread extends Thread {
        @Override
        public void run() {
            setName("send-thread");

            if(socket == null)
                return;

            try {
                socket.getOutputStream().write(request.toByteArray());
                socket.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientReceiveThread extends Thread {
        @Override
        public void run() {
            setName("receive-thread");

            while(true) {
                try {
                    byte buffer[] = new byte[bufferSize];
                    int length = socket.getInputStream().read(buffer);
                    if(length < 0)
                        throw new IOException();
                    buffer = Arrays.copyOfRange(buffer, 0, length);
                    Response response = Response.parseFrom(buffer);
                    onResponseListener.onResponse(response);
                } catch (IOException e) {
                    disconnect();
                    break;
                }
            }
        }
    }

    private class ClientConnectThread extends Thread {
        @Override
        public void run() {
            setName("connect-thread");

            try {
                socket = new Socket(host, port);
                onConnectSuccessfulListener.onConnectSuccess();
                receiveThread.start();
            } catch (IOException e) {
                e.printStackTrace();
                onConnectFailedListener.onConnectFail();
            }
        }
    }

    private class ClientDisconnectThread extends Thread {
        @Override
        public void run() {
            setName("disconnect-thread");

            if(socket == null)
                return;

            try {
                socket.close();
                socket = null;
                onDisconnectListener.onDisconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setOnConnectSuccessfulListener(OnConnectSuccessfulListener listener) {
        onConnectSuccessfulListener = listener == null ? () -> {} : listener;
    }

    public void setOnConnectFailedListener(OnConnectFailedListener listener) {
        onConnectFailedListener = listener == null ? () -> {} : listener;
    }

    public void setOnDisconnectListener(OnDisconnectListener listener) {
        onDisconnectListener = listener == null ? () -> {} : listener;
    }

    public void setOnResponseListener(OnResponseListener listener) {
        onResponseListener = listener == null ? (msg) -> {} : listener;
    }

    public interface OnConnectSuccessfulListener {
        void onConnectSuccess();
    }

    public interface OnConnectFailedListener {
        void onConnectFail();
    }

    public interface OnDisconnectListener {
        void onDisconnect();
    }

    public interface OnResponseListener {
        void onResponse(Response response);
    }
}
