package net.codersgarage.iseeu.networks;

import net.codersgarage.iseeu.listeners.StreamListener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by s4kib on 6/14/16.
 */

public class ISeeUServer {
    private int PORT = 5670;
    private byte[] data = new byte[70000];
    private DatagramPacket packet = new DatagramPacket(data, data.length);
    private DatagramSocket socket;

    private StreamListener streamListener;

    public void init() {
        try {
            socket = new DatagramSocket(PORT);
            System.out.println("Server Started...");
            listen();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("Waiting for packet...");
                        socket.receive(packet);
                        streamListener.onStream(packet.getData());
                        System.out.println("Packet Delivered : " + packet.getLength());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    public void addStreamListener(StreamListener streamListener) {
        this.streamListener = streamListener;
    }
}
