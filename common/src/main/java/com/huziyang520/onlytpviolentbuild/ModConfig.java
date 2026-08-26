package com.huziyang520.onlytpviolentbuild;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class ModConfig {
    private static final String FILE = "otpvb.json";
    private static final Gson GSON = new Gson();
    private static final Gson PRETTY = new GsonBuilder().setPrettyPrinting().create();

    private final AtomicReference<Boolean> enabled = new AtomicReference<>(true);
    private final AtomicReference<List<String>> whitelist =
            new AtomicReference<>(new ArrayList<>(Constants.DEFAULT_WHITELIST));
    private final AtomicReference<List<String>> exempt = new AtomicReference<>(new ArrayList<>());
    private final AtomicReference<Boolean> showNotification = new AtomicReference<>(true);
    private final Path configPath;

    public ModConfig(Path configDir) {
        this.configPath = configDir.resolve(FILE);
        create();
        load();
        watch();
    }

    public boolean isModEnabled() {
        return enabled.get();
    }

    public boolean isExempt(String playerName) {
        return exempt.get().contains(playerName);
    }

    public List<String> getCommandWhitelist() {
        return new ArrayList<>(whitelist.get());
    }

    public boolean isNotificationEnabled() {
        return showNotification.get();
    }

    private void create() {
        if (Files.exists(configPath)) {
            return;
        }
        try {
            Files.createDirectories(configPath.getParent());
            write(PRETTY.toJson(defaults()));
            Constants.LOG.info("Generated default config at {}", configPath);
        } catch (IOException e) {
            Constants.LOG.warn("Failed to write default config at {}", configPath, e);
        }
    }

    private JsonObject defaults() {
        JsonObject root = new JsonObject();
        root.addProperty("enabled", true);
        JsonArray whitelist = new JsonArray();
        for (String entry : Constants.DEFAULT_WHITELIST) {
            whitelist.add(entry);
        }
        root.add("command_whitelist", whitelist);
        root.add("exempt_players", new JsonArray());
        root.addProperty("show_notification", true);
        return root;
    }

    private void write(String json) throws IOException {
        try (Writer writer = Files.newBufferedWriter(configPath)) {
            writer.write(json);
        }
    }

    private void load() {
        if (!Files.exists(configPath)) {
            create();
            return;
        }
        JsonObject root;
        try (Reader reader = Files.newBufferedReader(configPath)) {
            JsonElement el = GSON.fromJson(reader, JsonElement.class);
            root = el != null && el.isJsonObject() ? el.getAsJsonObject() : null;
        } catch (Exception e) {
            Constants.LOG.warn("Failed to parse config at {}, regenerating defaults", configPath, e);
            reset();
            return;
        }
        if (root == null) {
            reset();
            return;
        }
        enabled.set(bool(root, "enabled", true));
        whitelist.set(strings(root, "command_whitelist", Constants.DEFAULT_WHITELIST));
        exempt.set(strings(root, "exempt_players", List.of()));
        showNotification.set(bool(root, "show_notification", true));
    }

    private void reset() {
        try {
            Files.deleteIfExists(configPath);
        } catch (IOException ignored) {
        }
        create();
        enabled.set(true);
        whitelist.set(new ArrayList<>(Constants.DEFAULT_WHITELIST));
        exempt.set(new ArrayList<>());
        showNotification.set(true);
    }

    private boolean bool(JsonObject root, String key, boolean fallback) {
        if (!root.has(key) || root.get(key).isJsonNull()) {
            return fallback;
        }
        try {
            return root.get(key).getAsBoolean();
        } catch (Exception e) {
            return fallback;
        }
    }

    private List<String> strings(JsonObject root, String key, List<String> fallback) {
        if (!root.has(key) || root.get(key).isJsonNull()) {
            return new ArrayList<>(fallback);
        }
        try {
            JsonArray array = root.getAsJsonArray(key);
            List<String> result = new ArrayList<>();
            for (JsonElement element : array) {
                if (element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
                    result.add(element.getAsString());
                }
            }
            return result;
        } catch (Exception e) {
            return new ArrayList<>(fallback);
        }
    }

    private void watch() {
        Path dir = configPath.getParent();
        if (dir == null) {
            return;
        }
        WatchService watch;
        try {
            watch = dir.getFileSystem().newWatchService();
        } catch (IOException e) {
            return;
        }
        try {
            dir.register(watch, StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
        } catch (IOException e) {
            return;
        }
        Path file = configPath.getFileName();
        Thread thread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    WatchKey key = watch.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        boolean overflow = event.kind() == StandardWatchEventKinds.OVERFLOW;
                        boolean isFile = event.context() instanceof Path p && file.equals(p.getFileName());
                        if (overflow || isFile) {
                            load();
                        }
                    }
                    if (!key.reset()) {
                        break;
                    }
                }
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }, "otpvb-watcher");
        thread.setDaemon(true);
        thread.start();
    }
}