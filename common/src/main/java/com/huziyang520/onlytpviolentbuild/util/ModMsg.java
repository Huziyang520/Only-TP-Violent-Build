package com.huziyang520.onlytpviolentbuild.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.huziyang520.onlytpviolentbuild.Constants;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Single entry point for chat messages sent from the server side.
 *
 * <p><b>Why a fallback is needed</b>: this mod runs on the server, and {@link Component#translatable(String)}
 * is resolved on the <i>receiving</i> side. A client without this mod has no language file, so the raw
 * key would be printed in chat. {@link Component#translatableWithFallback(String, String)} plus a
 * fallback taken from the bundled language file of the player's own client language avoids that.
 *
 * <p><b>Why colour is not in the language file</b>: legacy {@code §} codes are ignored by third-party
 * UI text renderers, so a "red" warning would silently turn white. The style is applied here instead.
 */
public final class ModMsg {
    private static final String FALLBACK_LANG = "en_us";
    /** Last resort when even the bundled language file cannot be read. */
    private static final String HARD_FALLBACK = "That command is forbidden!";

    private static final Map<String, Map<String, String>> CACHE = new HashMap<>();

    private ModMsg() {
    }

    /** Red chat warning: resolved by the receiver's own language, with a same-language fallback. */
    public static MutableComponent blocked(ServerPlayer player) {
        String key = Constants.MOD_ID + ".command_blocked";
        return Component.translatableWithFallback(key, fallback(player, key)).withStyle(ChatFormatting.RED);
    }

    private static String fallback(ServerPlayer player, String key) {
        String language = player.clientInformation().language();
        Map<String, String> table = table(language == null || language.isBlank() ? FALLBACK_LANG : language);
        String value = table.get(key);
        if (value == null) {
            value = table(FALLBACK_LANG).get(key);
        }
        return value != null ? stripLegacyColor(value) : HARD_FALLBACK;
    }

    private static Map<String, String> table(String lang) {
        String code = lang.toLowerCase(Locale.ROOT);
        synchronized (CACHE) {
            Map<String, String> cached = CACHE.get(code);
            if (cached != null) {
                return cached;
            }
            Map<String, String> loaded = load(code);
            if (loaded == null) {
                loaded = FALLBACK_LANG.equals(code) ? Map.of() : table(FALLBACK_LANG);
            }
            CACHE.put(code, loaded);
            return loaded;
        }
    }

    /** Read the language file by exact path; directory enumeration is unreliable on NeoForge. */
    private static Map<String, String> load(String lang) {
        String path = "/assets/" + Constants.MOD_ID + "/lang/" + lang + ".json";
        try (InputStream in = ModMsg.class.getResourceAsStream(path)) {
            if (in == null) {
                return null;
            }
            JsonObject root = JsonParser.parseReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
            Map<String, String> map = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
                if (entry.getValue().isJsonPrimitive()) {
                    map.put(entry.getKey(), entry.getValue().getAsString());
                }
            }
            return map;
        } catch (Exception e) {
            Constants.LOG.warn("Failed to read bundled language file {}", path, e);
            return null;
        }
    }

    /** Fallback strings go to chat verbatim, so strip legacy {@code §} codes. */
    private static String stripLegacyColor(String value) {
        if (value.indexOf('\u00A7') < 0) {
            return value;
        }
        StringBuilder sb = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '\u00A7' && i + 1 < value.length()) {
                i++;
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
