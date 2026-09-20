package com.huziyang520.onlytpviolentbuild;

import com.huziyang520.onlytpviolentbuild.platform.Services;
import net.minecraft.entity.player.ServerPlayerEntity;

public final class CommandGuard {
    private CommandGuard() {
    }

    public static boolean shouldBlock(ServerPlayerEntity player, String root) {
        return isOperator(player)
                && Services.PLATFORM.isModEnabled()
                && !Services.PLATFORM.isExempt(player)
                && !isWhitelisted(root);
    }

    public static boolean isWhitelisted(String root) {
        if (root == null) {
            return false;
        }
        String name = normalize(root);
        for (String entry : Services.PLATFORM.getCommandWhitelist()) {
            if (normalize(entry).equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isOperator(ServerPlayerEntity player) {
        return player.hasPermissions(2);
    }

    private static String normalize(String command) {
        String lower = command.toLowerCase();
        return lower.startsWith("minecraft:") ? lower.substring(10) : lower;
    }
}
