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

    private OnConnectSuccessfulListener onConnectSuccessfulListener;
    private OnConnectFailedListener onConnectFailedListener;
    private OnDisconnectListener onDisconnectListener;
    private OnResponseListener onResponseListener;

    public ClientFragment() {
        setOnConnectFailedListener(null);
        setOnConnectSuccessfulListener(null);
        setOnDisconnectListener(null);
        setOnResponseListener(null);
    }

    public void connect(String host, int port) {
        new ClientConnectThread(host, port)
                .start();
    }

    public void disconnect() {
        new ClientDisconnectThread()
                .start();
    }

    public void send(Request request) {
        new ClientSendThread(request)
                .start();
    }

    public void destroy() {
        setOnConnectFailedListener(null);
        setOnConnectSuccessfulListener(null);
        setOnDisconnectListener(null);
        setOnResponseListener(null);
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    public String getAddress() {
        if(!isConnected())
            return "";

        return socket.getRemoteSocketAddress().toString();
    }

    private class ClientSendThread extends Thread {
        private Request request;

        ClientSendThread(Request request) {
            this.request = request;
        }

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
        private final int BUFFER_SIZE = 4096;

        @Override
        public void run() {
            setName("receive-thread");

            while(true) {
                try {
                    byte buffer[] = new byte[BUFFER_SIZE];
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
        private String host;
        private int port;

        ClientConnectThread(String host, int port) {
            this.host = host;
            this.port = port;
        }

        @Override
        public void run() {
            setName("connect-thread");

            try {
                socket = new Socket(this.host, this.port);
                onConnectSuccessfulListener.onConnectSuccess();
                new ClientReceiveThread().start();
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
