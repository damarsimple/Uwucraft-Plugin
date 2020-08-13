package net.uwucraft.website;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EventExecutor {
    public Map<String, Object> data = new HashMap<String, Object>();

    public void execute() throws IOException {

        Api.post(this.data);

    }

    public void join(String playerName) {
        this.data.put("type", "join");
        this.data.put("message", playerName + " Has Join the server");
        this.data.put("player", playerName);
    }

    public void leave(String playerName) {
        this.data.put("type", "leave");
        this.data.put("message", playerName + " Has Leave the server");
        this.data.put("player", playerName);

    }

    public void death(String playerName, String killer, String message) {
        this.data.put("type", "death");
        this.data.put("message", message);
        this.data.put("player", playerName);
        this.data.put("killer", killer);

    }

    public void advancement(String advancementName, String playerName, String message) {
        this.data.put("type", "advancement");
        this.data.put("message", message);
        this.data.put("player", playerName);
    }
}