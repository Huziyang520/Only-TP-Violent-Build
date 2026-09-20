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
 * key would be printed in chat. {@link Component#translatableWithFallback(String, String)} avoids that.
 *
 * <p><b>与 1.2.0 主线的差异（仅本条分支）</b>：1.20.1 的 {@code ServerPlayer} 没有公开的
 * "客户端语言"读取口（{@code clientInformation()} 是 1.20.2 才引入的），因此这里无法按玩家语言
 * 取兜底文案，固定使用 {@code en_us} 兜底。装了本模组的客户端仍由自身语言文件正常翻译，
 * 只有"没装模组的客户端"会看到英文兜底文案。
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

    /** Red chat warning: resolved by the receiver's own language, with an en_us fallback. */
    public static MutableComponent blocked(ServerPlayer player) {
        String key = Constants.MOD_ID + ".command_blocked";
        return Component.translatableWithFallback(key, fallback(key)).withStyle(ChatFormatting.RED);
    }

    private static String fallback(String key) {
        Map<String, String> table = table(FALLBACK_LANG);
        String value = table.get(key);
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
