package com.huziyang520.onlytpviolentbuild;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public final class Constants {
    public static final String MOD_ID = "onlytpviolentbuild";
    /** 对外展示版本；构建脚本里的 version 也必须是同一个值。 */
    public static final String VERSION = "1.2.0";
    public static final Logger LOG = LogManager.getLogger(MOD_ID);
    public static final List<String> DEFAULT_WHITELIST = Arrays.asList(
            "tp", "tpa", "tpaccept", "tpdeny", "tphere", "tpr",
            "spawn", "back", "home", "sethome", "warp", "warps",
            "help", "list", "me", "msg", "w", "tell", "teammsg", "trigger", "seed"
    );

    private Constants() {
    }
}
