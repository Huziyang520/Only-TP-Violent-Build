package com.huziyang520.onlytpviolentbuild.util;

import com.huziyang520.onlytpviolentbuild.Constants;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

/** 服务端聊天提示的唯一出口：可翻译组件，客户端按自身语言解析。 */
public final class ModMsg {
    private ModMsg() {
    }

    public static ITextComponent blocked() {
        TextComponentTranslation msg =
                new TextComponentTranslation(Constants.MOD_ID + ".command_blocked");
        msg.setStyle(new Style().setColor(TextFormatting.RED));
        return msg;
    }
}
