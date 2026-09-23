package com.huziyang520.onlytpviolentbuild;

import com.huziyang520.onlytpviolentbuild.platform.Services;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;

public final class CommandGuard {
    private CommandGuard() {
    }

    public static boolean shouldBlock(ServerPlayer player, String root) {
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

    private static boolean isOperator(ServerPlayer player) {
        return player.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER);
    }

    private static String normalize(String command) {
        String lower = command.toLowerCase();
        return lower.startsWith("minecraft:") ? lower.substring(10) : lower;
    }
}
