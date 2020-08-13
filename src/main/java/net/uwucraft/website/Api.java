package net.uwucraft.website;

import java.io.IOException;
import java.util.Map;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.javanet.NetHttpTransport;

public class Api {

    public static void post(Map<String, Object> json) throws IOException {
        GenericUrl url = new GenericUrl("http://localhost/api/game/event");
        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        // Map<String, Object> data = new LinkedHashMap<>();
        // data.put("arg1", true);
        // data.put("arg2", 45);
        // data.put("key", "string");
        HttpContent content = new UrlEncodedContent(json);
        HttpRequest request = requestFactory.buildPostRequest(url, content);
        String rawResponse = request.execute().parseAsString();
        System.out.println(rawResponse);
    }
}