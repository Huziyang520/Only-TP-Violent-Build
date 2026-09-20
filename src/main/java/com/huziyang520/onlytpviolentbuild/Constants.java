package com.huziyang520.onlytpviolentbuild;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public final class Constants {
    public static final String MOD_ID = "onlytpviolentbuild";
    // 1.16.5 没有 com.mojang.logging.LogUtils（1.17 才有）
    public static final Logger LOG = LogManager.getLogger(MOD_ID);
    public static final List<String> DEFAULT_WHITELIST = Arrays.asList(
            "tp", "tpa", "tpaccept", "tpdeny", "tphere", "tpr",
            "spawn", "back", "home", "sethome", "warp", "warps",
            "help", "list", "me", "msg", "w", "tell", "teammsg", "trigger", "seed"
    );

    private Constants() {
    }
}
