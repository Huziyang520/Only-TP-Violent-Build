package com.huziyang520.onlytpviolentbuild;

import com.huziyang520.onlytpviolentbuild.platform.Services;
import net.minecraft.server.level.ServerPlayer;

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
     * 1.21.6~1.21.8 线的 OP 判定仍是旧的 {@code hasPermissions(int)}；
     * 权限体系重构（{@code permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER)}）发生在 26.1 起。
     */
    private static boolean isOperator(ServerPlayer player) {
        return player.hasPermissions(2);
    }

    private static String normalize(String command) {
        String lower = command.toLowerCase();
        return lower.startsWith("minecraft:") ? lower.substring(10) : lower;
    }
}
