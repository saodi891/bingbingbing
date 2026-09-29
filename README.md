# Bingbingbing（冰冰冰）— Minecraft 1.20.1 Forge 模组

从蓝冰 + 浮冰 + 冰无序合成一本「冰冰冰之书」，在铁砧上**免费、无惩罚**地把「冰冰冰」附魔敲给工具 / 武器 / 盔甲。带附魔的装备会把相关音效替换为 `bingbingbing.ogg`。

## 环境要求
- JDK **17**（Forge 1.20.1 必须用 17）
- Forge `1.20.1-47.2.0`（build.gradle 已写死，可改）
- 编译由你执行；本仓库不含 `gradle-wrapper.jar`（二进制无法生成）

## 编译步骤
1. 放入素材（见下）。
2. 生成 wrapper（二选一）：
   - 本机已装 Gradle 8.x：在项目根目录执行
     ```bash
     gradle wrapper --gradle-version 8.1.1
     ```
   - 或从官方 [1.20.1 Forge MDK](https://files.minecraftforge.net/) 拷贝 `gradlew`、`gradlew.bat`、`gradle/wrapper/gradle-wrapper.jar` 到本项目。
3. 编译：
   ```bash
   ./gradlew build
   ```
   产物在 `build/libs/bingbingbing-1.0.0.jar`。
4. 首次导入 IDE 前可选执行 `./gradlew genIntellijRuns`（或 `genEclipseRuns`）。

> 编译若报错，把完整报错发我，我来改。Mixin 的注入点（见下方“置信度”）最可能需要按你的实际环境微调。

## 需要你提供的素材
| 素材 | 路径 | 说明 |
|---|---|---|
| 音频 | `src/main/resources/assets/bingbingbing/sounds/bingbingbing.ogg` | Ogg Vorbis，建议单声道 |
| 纹理 | `src/main/resources/assets/bingbingbing/textures/item/bingbingbing_book.png` | 16x16 PNG |

两个目录里各有一个占位说明 txt，放好素材后可删。缺素材也能编译运行（音效不响 / 纹理显示为缺失方块）。

## 已实现（本期 v1）
- 合成：蓝冰 + 浮冰 + 冰 → 冰冰冰之书（无序合成）。
- 附魔应用：铁砧右槽放书 + 左槽放工具/武器/盔甲 → 免经验、免等级、无“前置修理惩罚”。
- 不进附魔台（`isDiscoverable=false`，同时也不会出现在战利品/交易/钓鱼）。
- 音效替换：
  - **武器近战攻击** → 服务端 Mixin，所有人都能听到。
  - **盔甲破碎**（物品损坏）→ Mixin。
  - **盔甲抵消伤害时的受伤声** → Mixin。
  - **荆棘触发** → 事件叠加播放（1.20.1 荆棘本身无独立音效，故为“额外播放”）。
  - **工具挖掘/破坏方块、锄头耕地/斧头剥皮/铲子铺路** → 客户端 Mixin-free 的 `PlaySoundEvent` 拦截。
- 音符盒音色：**音符盒放在冰块上**（正下方是 冰 / 浮冰 / 蓝冰）时，演奏音色替换为 `bingbingbing`（沿用同一音效 id，音高/音符粒子/音量均保持原版）——[NoteBlockMixin.java](src/main/java/com/bingbingbing/mixin/NoteBlockMixin.java)

## 本期限制 / 下一版
- **弓、弩**：本期不兼容（下一版做）。
- **工作方块**（铁砧/潜影盒/切石机等）：本期不做（方块不携带附魔，需要单独机制）。
- **工具类音效是“客户端本地替换”**：只有手持该工具的玩家自己听到 bingbingbing；旁观的其他玩家听到的仍是原版方块声。近战/盔甲音效是服务端替换，所有人可听。
- 距离阈值 6 格用于判断“是不是你造成的方块声”，极端情况下可能误伤附近方块声。

## Mixin 置信度（编译/联机测试重点验证）
- `PlayerMixin#attack` 重定向 `Level.playSound`：高。
- `LivingEntityMixin#breakItem` HEAD 注入：高。
- `LivingEntityMixin#playHurtSound` HEAD 注入：中高（玩家的受伤声路径需实测确认）。
