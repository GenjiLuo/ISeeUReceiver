package net.codersgarage.iseeu.networks;

import net.codersgarage.iseeu.listeners.StreamListener;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.WebSocketConnection;
import org.webbitserver.WebSocketHandler;

/**
 * Created by s4kib on 6/14/16.
 */

public class ReceiverServer implements WebSocketHandler {
    private WebServer receiverServer;
    private StreamListener streamListener;

    public void init() {
        receiverServer = WebServers.createWebServer(8900);
        receiverServer.add("/connect", this);
        receiverServer.start();
        System.out.println("Server Started...");
    }

    @Override
    public void onOpen(WebSocketConnection webSocketConnection) throws Throwable {
        System.out.println("Client Connected " + webSocketConnection.httpRequest().remoteAddress());
    }

    @Override
    public void onClose(WebSocketConnection webSocketConnection) throws Throwable {

    }

    @Override
    public void onMessage(WebSocketConnection webSocketConnection, String s) throws Throwable {

    }

    @Override
    public void onMessage(WebSocketConnection webSocketConnection, byte[] bytes) throws Throwable {
        System.out.println("Received " + bytes.length);
        streamListener.onStream(bytes);
    }

    @Override
    public void onPing(WebSocketConnection webSocketConnection, byte[] bytes) throws Throwable {

    }

    @Override
    public void onPong(WebSocketConnection webSocketConnection, byte[] bytes) throws Throwable {

    }

    public void addStreamListener(StreamListener streamListener) {
        this.streamListener = streamListener;
    }
}
