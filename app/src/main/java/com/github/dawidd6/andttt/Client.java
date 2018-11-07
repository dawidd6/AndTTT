package com.github.dawidd6.andttt;

import android.util.Log;
import com.github.dawidd6.andttt.proto.ERROR;
import com.github.dawidd6.andttt.proto.Message;
import com.github.dawidd6.andttt.proto.Room;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;

public class Client {
    private Socket socket;
    private String host;
    private int port;
    private int bufferSize;
    private int timeout;
    private Message message;

    private ClientConnectThread connectThread;
    private ClientDisconnectThread disconnectThread;
    private ClientReceiveThread receiveThread;
    private ClientSendThread sendThread;

    private OnConnectSuccessfulListener onConnectSuccessfulListener;
    private OnConnectFailedListener onConnectFailedListener;
    private OnDisconnectListener onDisconnectListener;
    private OnMessageReceivedListener onMessageReceivedListener;

    public Client() {
        timeout = 5000;
        bufferSize = 4096;
        connectThread = new ClientConnectThread();
        disconnectThread = new ClientDisconnectThread();
        receiveThread = new ClientReceiveThread();
        sendThread = new ClientSendThread();

        setOnConnectFailedListener(null);
        setOnConnectSuccessfulListener(null);
        setOnDisconnectListener(null);
        setOnMessageReceivedListener(null);
    }

    public void connect(String host, int port) {
        this.host = host;
        this.port = port;
        connectThread.start();
    }

    public void disconnect() {
        disconnectThread.start();
    }

    public void destroy() {
        receiveThread.interrupt();
        sendThread.interrupt();
        connectThread.interrupt();
        disconnectThread.interrupt();
    }

    public void send(Message message) {
        this.message = message;
        sendThread.start();
    }

    private class ClientSendThread extends Thread {
        @Override
        public void run() {
            try {
                socket.getOutputStream().write(message.toByteArray());
                socket.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientReceiveThread extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    byte buffer[] = new byte[bufferSize];
                    int length = socket.getInputStream().read(buffer);
                    if(length < 0)
                        throw new IOException();
                    buffer = Arrays.copyOfRange(buffer, 0, length);
                    Message message = Message.parseFrom(buffer);
                    onMessageReceivedListener.onMessageReceive(message);
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
            try {
                socket = new Socket(host, port);
                //socket.setSoTimeout(timeout);
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
            try {
                socket.close();
                onDisconnectListener.onDisconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // CONNECTION LISTENERS
    public void setOnConnectSuccessfulListener(OnConnectSuccessfulListener listener) {
        onConnectSuccessfulListener = listener == null ? () -> {} : listener;
    }

    public void setOnConnectFailedListener(OnConnectFailedListener listener) {
        onConnectFailedListener = listener == null ? () -> {} : listener;
    }

    public void setOnDisconnectListener(OnDisconnectListener listener) {
        onDisconnectListener = listener == null ? () -> {} : listener;
    }

    public void setOnMessageReceivedListener(OnMessageReceivedListener listener) {
        onMessageReceivedListener = listener == null ? (msg) -> {} : listener;
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

    public interface OnMessageReceivedListener {
        void onMessageReceive(Message message);
    }
}
