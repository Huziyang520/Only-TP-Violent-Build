package com.huziyang520.onlytpviolentbuild.util;

import com.huziyang520.onlytpviolentbuild.Constants;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

/** 服务端聊天提示的唯一出口：可翻译组件，客户端按自身语言解析。 */
public final class ModMsg {
    private ModMsg() {
    }

    public static IChatComponent blocked() {
        ChatComponentTranslation msg =
                new ChatComponentTranslation(Constants.MOD_ID + ".command_blocked");
        msg.getChatStyle().setColor(EnumChatFormatting.RED);
        return msg;
    }
}
