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

    /**
     * 实测：1.21.9 起 {@code ServerPlayer#hasPermissions(int)} 已被移除，
     * 权限体系重构为 {@code permissions().hasPermission(Permissions.*)}，与 26.x 一致。
     */
    private static boolean isOperator(ServerPlayer player) {
        return player.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER);
    }

    private static String normalize(String command) {
        String lower = command.toLowerCase();
        return lower.startsWith("minecraft:") ? lower.substring(10) : lower;
    }
}
