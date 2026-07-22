🚫 Only TP Violent Build
Only TP Violent Build 是 Only TP 模组的进一步轻量化版本。

⚠️ 注意：本模组与 Only TP 不可同时安装。

🎯 模组效果
强制所有 OP 玩家 只能使用 TP 类指令 和 游戏模式切换器，其他所有指令均被禁止。

可通过配置文件灵活控制。

⚙️ 配置文件
模组从配置文件中读取以下设置：

ini
#Only TP Violent Build Configuration
[general]
    #Whether the mod is enabled
    enabled = true
    #Blacklist - players in this list are exempt from restrictions. Format: ["player1", "player2"]
    blacklist = []


配置项	说明
enabled	设为 false 可完全禁用本模组（默认 true）。
blacklist	黑名单中的玩家名可豁免所有限制。写法示例：["player1", "player2"]。
🚫 Only TP Violent Build
Only TP Violent Build is a further lightweight version of the Only TP mod.

⚠️ Do not install both mods simultaneously.

🎯 Mod Effect
Restricts all OP players to only TP‑class commands and gamemode switcher — all other commands are blocked.

Fully configurable via the config file.

⚙️ Configuration
The mod reads the following settings from the config file:

ini
#Only TP Violent Build Configuration
[general]
    #Whether the mod is enabled
    enabled = true
    #Blacklist - players in this list are exempt from restrictions. Format: ["player1", "player2"]
    blacklist = []
Setting	Description
enabled	Set to false to disable the mod entirely (default: true).
blacklist	List of player names who are exempt from all restrictions. Write them as ["name1", "name2"].

Installation information
=======

This template repository can be directly cloned to get you started with a new
mod. Simply create a new repository cloned from this one, by following the
instructions provided by [GitHub](https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-repository-from-a-template).

Once you have your clone, simply open the repository in the IDE of your choice. The usual recommendation for an IDE is either IntelliJ IDEA or Eclipse.

If at any point you are missing libraries in your IDE, or you've run into problems you can
run `gradlew --refresh-dependencies` to refresh the local cache. `gradlew clean` to reset everything 
{this does not affect your code} and then start the process again.

Mapping Names:
============
By default, the MDK is configured to use the official mapping names from Mojang for methods and fields 
in the Minecraft codebase. These names are covered by a specific license. All modders should be aware of this
license. For the latest license text, refer to the mapping file itself, or the reference copy here:
https://github.com/NeoForged/NeoForm/blob/main/Mojang.md

Additional Resources: 
==========
Community Documentation: https://docs.neoforged.net/  
NeoForged Discord: https://discord.neoforged.net/
