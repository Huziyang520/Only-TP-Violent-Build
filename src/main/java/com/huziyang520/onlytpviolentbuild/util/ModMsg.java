package com.huziyang520.onlytpviolentbuild.util;

import com.huziyang520.onlytpviolentbuild.Constants;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

/** 服务端聊天提示的唯一出口：可翻译组件，客户端按自身语言解析。 */
public final class ModMsg {
    private ModMsg() {
    }

    public static ITextComponent blocked(ServerPlayerEntity player) {
        return new TranslationTextComponent(Constants.MOD_ID + ".command_blocked")
                .withStyle(TextFormatting.RED);
    }
}
