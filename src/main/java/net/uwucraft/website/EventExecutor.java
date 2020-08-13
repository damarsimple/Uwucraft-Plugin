package net.uwucraft.website;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EventExecutor {
    public Map<String, Object> data = new HashMap<String, Object>();

    public void execute() throws IOException, InterruptedException {

        Thread.sleep(2500);
        Api.post(this.data);
        Webhooks webhook = new Webhooks(
                "https://discordapp.com/api/webhooks/742273177702629387/xEzGIEvYAwHD4rz_Cx85zH0hLUSK_DYKDmnQusS9rIrZstdMl7J3W7H3LugHPQN3RC16");
        webhook.setTts(false);
        webhook.setAvatarUrl("https://minotar.net/avatar/" + this.data.get("player").toString());
        webhook.setContent(this.data.get("message").toString());
        webhook.setUsername(this.data.get("player").toString());
        webhook.execute(); // Handle exception
    }

    public static void executeWebhooks(String message) throws IOException {
        Webhooks webhook = new Webhooks(
                "https://discordapp.com/api/webhooks/742273177702629387/xEzGIEvYAwHD4rz_Cx85zH0hLUSK_DYKDmnQusS9rIrZstdMl7J3W7H3LugHPQN3RC16");
        webhook.setTts(false);
        webhook.setAvatarUrl("https://minotar.net/avatar/notch");
        webhook.setContent(message);
        webhook.setUsername("admin");
        webhook.execute(); // Handle exception
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