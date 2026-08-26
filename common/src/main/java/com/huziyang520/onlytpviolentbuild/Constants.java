package com.huziyang520.onlytpviolentbuild;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.util.List;

public final class Constants {
    public static final String MOD_ID = "onlytpviolentbuild";
    public static final Logger LOG = LogUtils.getLogger();
    public static final List<String> DEFAULT_WHITELIST = List.of(
            "tp", "tpa", "tpaccept", "tpdeny", "tphere", "tpr",
            "spawn", "back", "home", "sethome", "warp", "warps",
            "help", "list", "me", "msg", "w", "tell", "teammsg", "trigger", "seed"
    );

    private Constants() {
    }
}