package org.lime.resourcepack.clear.config;

import com.google.gson.JsonParser;
import org.lime.resourcepack.clear.ResourcePackClear;
import org.lime.resourcepack.clear.utils.JsonFormatter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class RuntimeConfig {
    private final File file;
    private final ResourcePackClear plugin;

    private long lastModified = 0;
    private ResourcePackConfig config = ResourcePackConfig.EMPTY;

    public RuntimeConfig(ResourcePackClear plugin, File file) {
        this.plugin = plugin;
        this.file = file;
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (!parent.exists()) parent.mkdirs();
            try {
                Files.writeString(file.toPath(), JsonFormatter.format(ResourcePackConfig.DEFAULT.save()));
            }
            catch (IOException ex) {
                plugin.logger.error("Could not create default config: " + file.getAbsoluteFile(), ex);
            }
        }
    }

    public ResourcePackConfig config() {
        long lastModified = file.lastModified();
        if (lastModified != this.lastModified) {
            try {
                this.config = new ResourcePackConfig(JsonParser.parseString(Files.readString(file.toPath())).getAsJsonObject());
                this.lastModified = lastModified;
            }
            catch (IOException ex) {
                plugin.logger.error("Could not read config: " + file.getAbsoluteFile(), ex);
            }
        }
        return this.config;
    }
}
