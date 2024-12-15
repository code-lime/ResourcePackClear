package org.lime.resourcepack.clear;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.lime.resourcepack.clear.config.RuntimeConfig;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "resourcepackclear",
        name = "ResourcePackClear",
        version = BuildConstants.VERSION,
        authors = "Lime"
)
public class ResourcePackClear {
    public final ProxyServer proxy;
    public final RuntimeConfig config;
    public final Logger logger;

    @Inject public ResourcePackClear(ProxyServer proxy, Logger logger, @DataDirectory Path dataFolder) {
        this.proxy = proxy;
        this.logger = logger;
        this.config = new RuntimeConfig(this, dataFolder.resolve("config.json").toFile());

        logger.info("Loading ResourcePackClear");
    }

    @Subscribe public void onProxyInitialization(ProxyInitializeEvent event) {
        proxy.getEventManager().register(this, new ServerSwitchListener(this));
    }
}
