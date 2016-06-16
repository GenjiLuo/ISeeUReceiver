package net.codersgarage.iseeu.utils;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by s4kib on 6/16/16.
 */

public class Settings {
    private String homeDir = System.getProperty("user.home");
    private String settingsPath = homeDir + File.separator + "config.icu";
    private JsonObject settings;
    private PrintWriter printWriter;

    public Settings() {
        settings = new JsonObject();

        init();
    }

    private void init() {
        File configFile = new File(settingsPath);
        if (!configFile.exists()) {
            System.out.println("File Not Found");

            try {
                configFile.createNewFile();
            } catch (Exception e) {

            }
        }
        try {
            String config = "";
            Scanner sc = new Scanner(configFile);
            while (sc.hasNextLine()) {
                config += sc.nextLine();
            }

            System.out.println(config);
            printWriter = new PrintWriter(configFile);
            settings = Json.parse(config).asObject();
            write();
        } catch (Exception ex) {
            settings = new JsonObject();
        }
    }

    public void setHost(String host) {
        settings.set("host", host);
    }

    public String getHost() {
        return settings.getString("host", "127.0.0.1");
    }

    public void setPort(int port) {
        settings.set("port", port);
    }

    public int getPort() {
        return settings.getInt("port", 1234);
    }

    public void setFullScreen(boolean fullScreen) {
        settings.set("fullScreen", fullScreen);
    }

    public boolean isFullScreen() {
        return settings.getBoolean("fullScreen", false);
    }

    public void write() {
        printWriter.write("");
        printWriter.write(settings.toString());
        printWriter.flush();
    }

}
