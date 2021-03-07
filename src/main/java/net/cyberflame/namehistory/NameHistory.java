package net.cyberflame.namehistory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class NameHistory extends Plugin {

    String mojangApi = "https://api.mojang.com/user/profiles/UUID/names";
    private JsonParser nhParser = new JsonParser();

    public void onEnable() {
        getProxy().getPluginManager().registerCommand(this, new NameHistoryCommand(this));
    }

    public void doRequest(String uuid, ProxiedPlayer executorPlayer) {
        URL url = null;
        try {
            url = new URL(mojangApi.replace("UUID", uuid.replace("-", "")));
            System.out.println("Requesting: " + url.toString());
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            parseAndSendData("name", urlConnection.getInputStream(), executorPlayer);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void parseAndSendData(String just, InputStream theTip, ProxiedPlayer executorPlayer) throws Exception {
        final Reader streamReader = new InputStreamReader(theTip);
        final BufferedReader readerBuffered = new BufferedReader(streamReader);
        JsonArray mojangArray = (JsonArray) nhParser.parse(readerBuffered.readLine());
        executorPlayer.sendMessage(ChatColor.AQUA + "<-- Printing History -->");
        for (Object nhParser : mojangArray) {
            JsonObject objToStr = (JsonObject) nhParser;
            executorPlayer.sendMessage(ChatColor.RED + "> " +ChatColor.GREEN + objToStr.get(just).toString());
        }
        executorPlayer.sendMessage(ChatColor.AQUA + "<-- Done Printing -->");
    }

    public static void main(String[] args) throws Exception {
        //doRequest("a59d0569-ea82-4efc-a6a6-b42508b2c547");
    }

}
