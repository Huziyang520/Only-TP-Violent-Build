package com.huziyang520.onlytpviolentbuild.util;

import com.huziyang520.onlytpviolentbuild.Constants;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;

/**
 * 服务端发出的聊天提示的唯一出口。
 *
 * <p><b>本条分支（1.19.x）为什么只能用 {@code Component.translatable}</b>：
 * 1.19.2 的 {@code Component} 没有 {@code translatableWithFallback}，
 * {@code ServerPlayer} 也没有公开的"客户端语言"读取口。
 * <ul>
 *   <li>用 literal（写死文案）⇒ <b>客户端切任何语言都只显示写死的那一份</b>（实测踩过：中文客户端仍是英文）。</li>
 *   <li>用 {@code translatable} ⇒ 由<b>接收端</b>按自身语言解析，装了本模组的客户端会正确显示中文/英/其它语言；
 *       代价是"没装模组的客户端"会看到原始 key。</li>
 * </ul>
 * 取舍：优先保证"装了模组的客户端语言正确"，与 1.20.2+ 分支的行为一致。
 */
public final class ModMsg {
    private ModMsg() {
    }

    public static MutableComponent blocked(ServerPlayer player) {
        return Component.translatable(Constants.MOD_ID + ".command_blocked").withStyle(ChatFormatting.RED);
    }
}
