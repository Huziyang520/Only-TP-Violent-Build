# 🚫 Only TP Violent Build

[CN] Only TP 模组的进一步轻量化版本：强制服务端 OP 玩家只能使用 TP 类指令，游戏模式切换同样受限，其余指令一律拦截。
[EN] A further lightweight variant of Only TP: OP players on the server may only run TP-class commands; gamemode switching is restricted too, every other command is blocked.

⚠️ 本模组与 Only TP 功能重叠（同样拦截非 TP 指令），不建议玩家同时安装；两者可同时加载，但效果会叠加。
[EN] Overlaps with Only TP — both can load, but the restriction is applied twice; installing one of them is recommended.

## 版本 / Versions

| 加载器 | Minecraft |
|---|---|
| Fabric（需 Fabric API） | 1.20 ~ 1.20.1 |
| Forge（NeoForge 1.20.1 通用同一文件） | 1.20 ~ 1.20.1 |

本分支版本号 / Version: `1.2.0`
**纯服务端模组 / Server-side only**（不需要装到客户端）。

## 构建产物 / Artifacts

- `onlytpviolentbuild-1.2.0-fabric-1.20-1.20.1.jar`
- `onlytpviolentbuild-1.2.0-forge-1.20-1.20.1.jar`

## 配置文件 / Configuration

首次启动时在 `config/otpvb.json` 生成默认配置，改动后由模组自动热重载（无需重启）：

```json
{
  "enabled": true,
  "command_whitelist": [ "tp", "tpa", "tpaccept", "home", "spawn" ],
  "exempt_players": [ "player1", "player2" ],
  "show_notification": true
}
```

| 配置项 | 说明 |
|---|---|
| `enabled` | 设为 `false` 可完全禁用本模组（默认 `true`）。 |
| `command_whitelist` | 允许 OP 玩家使用的指令根名列表，可省略 `minecraft:` 前缀。 |
| `exempt_players` | 名单内的玩家名豁免所有限制。 |
| `show_notification` | 进服提示开关（默认 `true`）。 |

The same keys apply in English: `enabled` toggles the mod, `command_whitelist` lists allowed command roots (`minecraft:` prefix optional), `exempt_players` exempts players by name, `show_notification` toggles the join message.

## 反馈 / Issues

- GitHub Issues: https://github.com/Huziyang520/Only-TP-Violent-Build/issues
- 备用反馈站: https://issue.mengcai.online/
- CurseForge: https://www.curseforge.com/minecraft/mc-mods/only-tp-violent-build

## 许可 / License

MIT © Huziyang520