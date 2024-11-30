package org.lime.resourcepack.clear;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.player.ResourcePackInfo;
import org.lime.resourcepack.clear.config.ResourcePackConfig;
import org.lime.resourcepack.clear.utils.Regex;

import java.util.Map;

public record ServerSwitchListener(ResourcePackClear plugin) {
    @Subscribe
    public void onServerSwitch(ServerConnectedEvent event) {
        String fromServer = event.getPreviousServer().map(v -> v.getServerInfo().getName()).orElse("NEW");
        String toServer = event.getServer().getServerInfo().getName();

        ResourcePackConfig config = plugin.config.config();
        for (Map.Entry<String, String> kv : config.servers.entrySet()) {
            if (Regex.compareRegex(fromServer, kv.getKey()) && Regex.compareRegex(toServer, kv.getValue())) {
                Player player = event.getPlayer();
                if (player.getProtocolVersion().noLessThan(ProtocolVersion.MINECRAFT_1_20_3)) {
                    player.clearResourcePacks();
                } else {
                    ResourcePackInfo.Builder builder = plugin.proxy.createResourcePackBuilder(config.url);
                    if (config.hash.length == 20) builder = builder.setHash(config.hash);
                    player.sendResourcePackOffer(builder.build());
                }
                return;
            }
        }
    }
}
