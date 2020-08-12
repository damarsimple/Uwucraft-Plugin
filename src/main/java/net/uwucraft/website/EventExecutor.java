package net.uwucraft.website;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EventExecutor {
    public Map<String, Object> data = new HashMap<String, Object>();

    public void execute() throws IOException {
      
            Api.post(this.data);
        
    }

    public void join() {

    }

    public void leave() {

    }

    public void death(String killed, String killer, String deathMessage) {
        this.data.put("eventType", "death");
        this.data.put("message", deathMessage);
        this.data.put("killed", killed);
        this.data.put("killer", killer);

    }

    public void advancement() {

    }
}