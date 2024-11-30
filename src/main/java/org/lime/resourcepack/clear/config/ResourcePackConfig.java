package org.lime.resourcepack.clear.config;

import com.google.common.io.BaseEncoding;
import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class ResourcePackConfig {
    public final String url;
    public final byte[] hash;

    public final Map<String, String> servers;

    public static ResourcePackConfig DEFAULT = new ResourcePackConfig(true);
    public static ResourcePackConfig EMPTY = new ResourcePackConfig(false);

    public ResourcePackConfig(JsonObject json) {
        url = json.get("url").getAsString();
        hash = BaseEncoding.base16().lowerCase().decode(json.get("hash").getAsString().toLowerCase(Locale.ROOT));

        servers = json.get("servers")
                .getAsJsonObject()
                .entrySet()
                .stream()
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, kv -> kv.getValue().getAsString()));
    }
    private ResourcePackConfig(boolean isDefault) {
        url = "URL";
        hash = new byte[0];
        servers = isDefault
            ? Map.of("server1", "hub1", "(.*)-1", "(.*)-2")
            : Collections.emptyMap();
    }
    public JsonObject save() {
        JsonObject json = new JsonObject();
        json.addProperty("url", this.url);
        json.addProperty("hash", BaseEncoding.base16().lowerCase().encode(this.hash));
        JsonObject servers = new JsonObject();
        this.servers.forEach(servers::addProperty);
        json.add("servers", servers);
        return json;
    }
}
