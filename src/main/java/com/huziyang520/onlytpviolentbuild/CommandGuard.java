package com.huziyang520.onlytpviolentbuild;

import com.huziyang520.onlytpviolentbuild.platform.Services;
import net.minecraft.entity.player.EntityPlayerMP;

public final class CommandGuard {
    private CommandGuard() {
    }

    public static boolean shouldBlock(EntityPlayerMP player, String root) {
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

    private static String normalize(String command) {
        String lower = command.toLowerCase();
        return lower.startsWith("minecraft:") ? lower.substring(10) : lower;
    }

    /** 1.7.10：EntityPlayerMP#canCommandSenderUseCommand(int, String) 即 OP 判定。 */
    private static boolean isOperator(EntityPlayerMP player) {
        return player.canCommandSenderUseCommand(2, "gamemode");
    }
}
