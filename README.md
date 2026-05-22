# Electric Fisher / 新能源渔业

[![NeoForge](https://img.shields.io/badge/NeoForge-21.1.230-blue)](https://neoforged.net/)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.1-green)](https://www.minecraft.net/)

一款 Minecraft NeoForge 模组，灵感来源于星露谷物语 Nexus Mods 作品 [#45923](https://www.nexusmods.com/stardewvalley/mods/45923)。

---

## 物品

| 物品 | 合成方式 | 功能 |
|------|---------|------|
| **新能源渔网** | 8 根线 + 1 钓鱼竿（环绕） | 手持靠近水域时，自动以原版钓鱼战利品表生成掉落物。支持整合包中其他模组通过数据包或 Global Loot Modifier 添加的钓鱼产出。 |
| **新能源鱼竿** | 钻石（上）、红石（左）、金锭（下）、铁锭（右）、钓鱼竿（中） | 手持靠近水域时，持续在周围水域生成活体鱼类实体。鱼在水中短暂存活后跃出水面并自然死亡，掉落对应鱼肉。 |

---

## 配置

配置文件位于 `config/fisher_eletric-server.toml`，服务端生效：

| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| `waterSearchRadius` | 5 | 水域搜索半径（格） |
| `netCooldownTicks` | 20 | 渔网每次钓鱼间隔（tick，20 = 1 秒） |
| `rodSpawnInterval` | 3 | 鱼竿生成间隔（tick，越小越快，当前默认约同时存活 10 条） |
| `rodLifetimeTicks` | 40 | 鱼的存活时间（tick，20 = 1 秒） |

---

## 依赖

- **Minecraft** 1.21.1
- **NeoForge** 21.1.230 或更高

---

## 致谢

- 本模组的设计理念受到星露谷物语模组 [Nexus Mods #45923](https://www.nexusmods.com/stardewvalley/mods/45923) 的启发，该模组通过手持特定物品靠近水体的方式实现了批量钓鱼事件的自动化。

## License

MIT

---

## Items

| Item | Recipe | Function |
|------|--------|----------|
| **New Energy Fishing Net** | 8 String + 1 Fishing Rod (surrounding) | Hold near water to automatically generate fishing loot via the vanilla fishing loot table. Compatible with other mods' fishing drops added through datapacks or Global Loot Modifiers. |
| **New Energy Fishing Rod** | Diamond (top), Redstone (left), Gold Ingot (bottom), Iron Ingot (right), Fishing Rod (center) | Hold near water to continuously spawn live fish entities around the player. Fish briefly survive in water before leaping out and dying naturally, dropping their corresponding fish items. |

## Configuration

Configuration file located at `config/fisher_eletric-server.toml` (server-side):

| Key | Default | Description |
|-----|---------|-------------|
| `waterSearchRadius` | 5 | Water search radius in blocks |
| `netCooldownTicks` | 20 | Ticks between each net fishing attempt (20 = 1 second) |
| `rodSpawnInterval` | 3 | Ticks between fish spawns for the rod (lower = faster; default keeps ~10 fish alive concurrently) |
| `rodLifetimeTicks` | 40 | Lifespan of spawned fish in ticks (20 = 1 second) |

## Dependencies

- **Minecraft** 1.21.1
- **NeoForge** 21.1.230 or later

## Credits

- This mod's design is inspired by the Stardew Valley mod [Nexus Mods #45923](https://www.nexusmods.com/stardewvalley/mods/45923), which automates batch fishing events by holding a specific item near bodies of water.
