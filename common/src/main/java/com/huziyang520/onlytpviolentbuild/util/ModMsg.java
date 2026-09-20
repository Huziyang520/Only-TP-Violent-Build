package com.huziyang520.onlytpviolentbuild.util;

import com.huziyang520.onlytpviolentbuild.Constants;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;


public final class ModMsg {
    private ModMsg() {
    }

    public static MutableComponent blocked(ServerPlayer player) {
        
        return new TranslatableComponent(Constants.MOD_ID + ".command_blocked").withStyle(ChatFormatting.RED);
    }
}
