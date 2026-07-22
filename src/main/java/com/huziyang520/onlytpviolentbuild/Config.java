package com.huziyang520.onlytpviolentbuild;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class Config {
    public static final ModConfigSpec SPEC;
    
    public static final ModConfigSpec.BooleanValue ENABLED;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> BLACKLIST;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("Only TP Violent Build Configuration")
               .push("general");

        ENABLED = builder
                .comment("Whether the mod is enabled")
                .define("enabled", true);

        BLACKLIST = builder
                .comment("Blacklist - players in this list are exempt from restrictions. Format: [\"player1\", \"player2\"]")
                .defineList("blacklist", List.of(), entry -> entry instanceof String);

        builder.pop();

        SPEC = builder.build();
    }

    public static boolean isEnabled() {
        return ENABLED.get();
    }

    public static List<? extends String> getBlacklist() {
        return BLACKLIST.get();
    }
}
