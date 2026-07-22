package com.huziyang520.onlytpviolentbuild;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.ParsedCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.CommandEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import java.util.List;
import java.util.Set;

@Mod(OnlyTPViolentBuild.MODID)
public class OnlyTPViolentBuild {
    public static final String MODID = "onlytpviolentbuild";
    private static final Logger LOGGER = LogUtils.getLogger();
    
    // TP 类指令列表
    private static final Set<String> TP_COMMANDS = Set.of(
        "tp", "teleport", "tpa", "tpahere", "tpaccept", "tpdeny", "tpcancel",
        "tpacceptall", "tpdenyall", "tpoffline", "tpr", "tprandom",
        "tpworld", "tpx", "tpy", "tpz", "tpnether", "tpend"
    );
    
    // 游戏模式相关指令
    private static final Set<String> GAMEMODE_COMMANDS = Set.of(
        "gamemode", "gm", "gmc", "gms", "gma", "gmsp"
    );

    public OnlyTPViolentBuild(ModContainer modContainer) {
        // 注册配置
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        
        // 注册事件处理器
        NeoForge.EVENT_BUS.register(this);
        LOGGER.info("Only TP Violent Build loaded successfully!");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Only TP Violent Build - Server starting, config loaded");
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onCommand(CommandEvent event) {
        // 检查功能是否开启
        if (!Config.isEnabled()) {
            return;
        }
        
        ParseResults<CommandSourceStack> parseResults = event.getParseResults();
        CommandSourceStack source = parseResults.getContext().getSource();
        
        // 只检查玩家执行
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        
        // 检查是否在黑名单中（豁免玩家）
        String playerName = player.getName().getString();
        if (Config.getBlacklist().contains(playerName)) {
            return;
        }
        
        // 获取命令的第一个节点（根命令）
        List<ParsedCommandNode<CommandSourceStack>> nodes = parseResults.getContext().getNodes();
        if (nodes.isEmpty()) {
            return;
        }
        
        String commandName = nodes.get(0).getNode().getName().toLowerCase();
        
        LOGGER.info("OP player {} executed command: {}", playerName, commandName);
        
        // 检查是否是游戏模式指令
        if (GAMEMODE_COMMANDS.contains(commandName)) {
            event.setCanceled(true);
            player.sendSystemMessage(Component.translatable("onlytpviolentbuild.command_blocked"));
            LOGGER.info("Blocked gamemode command: {} from player {}", commandName, playerName);
            return;
        }
        
        // 如果不是 TP 类指令，禁止执行
        if (!TP_COMMANDS.contains(commandName)) {
            event.setCanceled(true);
            player.sendSystemMessage(Component.translatable("onlytpviolentbuild.command_blocked"));
            LOGGER.info("Blocked non-TP command: {} from player {}", commandName, playerName);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerChangeGameMode(PlayerEvent.PlayerChangeGameModeEvent event) {
        if (!Config.isEnabled()) {
            return;
        }

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        String playerName = player.getName().getString();
        if (Config.getBlacklist().contains(playerName)) {
            return;
        }

        GameType newGameMode = event.getNewGameMode();
        LOGGER.info("Player {} attempted to change gamemode to: {}", playerName, newGameMode);

        event.setCanceled(true);
        player.sendSystemMessage(Component.translatable("onlytpviolentbuild.command_blocked"));
        LOGGER.info("Blocked gamemode change for player {} to {}", playerName, newGameMode);
    }
}
