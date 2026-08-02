# Contexto — Equivalent Legacy (para usar directamente con OpenCode/Codex)

> Generado 2026-08-01. Pégalo tal cual como prompt (o adjúntalo con `-f`) al lanzar OpenCode manualmente.

## Qué es este mod

Mod NeoForge que revive el sistema EMC (Equivalent Exchange 2 / ProjectE): asignar valor EMC a items, transmutarlos entre sí vía el Philosopher's Stone.

- **mod_id**: `equivalent_legacy`
- **package**: `com.skd.equivalentlegacy`
- **Minecraft / NeoForge**: `26.2` / `26.2.0.32-beta` — **no subir de versión sin que se pida explícitamente**
- **mod_version actual**: `1.0.0`
- **Rama**: `minecraft/26.2/neoforge-26.2.0.32-beta/production`
- **Repo**: `G:\Proyectos\Mods_Minecraft\equivalent_legacy\26.2`

## Origen / atribución (obligatorio mantener)

Fork de [Equivox](https://github.com/Yaskulsky/projecte-26-port) por Yaskulsky (mod id `equivox`, package `com.yaskulsky.equivox`, v1.5.0, MC 26.1.2/NeoForge, MIT), que a su vez es fork de [ProjectE](https://github.com/sinkillerj/ProjectE) (SinKillerJ, MaPePeR, williewillus, Lilylicious, pupnewfster et al.), que viene de Equivalent Exchange 2 (x3n0ph0b3).

La atribución ya está puesta en `README.md`, `neoforge.mods.toml` (`credits`) y `docs/curseforge/project_description.md`. **No borrarla** al renombrar clases/paquetes. El jar original de referencia (para decompilar y ver recetas/lógica reales) hay que copiarlo a `temp/equivox-1.0.0.jar` desde `~/Downloads/equivox-1.0.0.jar` — no está versionado, `temp/` se vacía tras cada tarea.

## Reglas de naming y estilo (obligatorias)

- snake_case para `mod_id`/assets, PascalCase para clases, camelCase para variables/config keys, Title Case para display names.
- **Sin residuos del mod original** (`equivox`, `yaskulsky`, `projecte`) en paquetes/clases/JSON — salvo comentarios de atribución ya presentes.
- Commits en inglés, formato Conventional Commits (`feat:`, `fix:`, `chore:`...), con footer `v<version>` en el mensaje.
- Todo el código/comentarios en inglés.
- `gradlew.bat build` debe pasar antes de dar nada por terminado.
- **Hacer `git add` + `git commit` del trabajo terminado antes de reportar como acabado** — esto se ha olvidado alguna vez, insiste en verificarlo con `git status --short` (debe salir vacío).
- No cambiar `mod_version` en `gradle.properties` (eso lo hace el operador al cerrar cada fase).

## Lo ya implementado (fases 1-13, todo commiteado, compilando y publicado en CurseForge)

| Fase | Qué | Paquetes |
|---|---|---|
| 1 | Núcleo EMC: config (`CommonConfig`/`ClientConfig`/`ServerConfig`/`MappingConfig` vía `ModConfigSpec`), estructura NSS (`NormalizedSimpleStack`, `NSSItem`, `NSSTag`...), mapper EMC (`MappingCollector`, `SimpleGraphMapper`, `LongArithmetic`), `FixedValues`/`CustomConversion`, `EMCHelper` | `config/`, `emc/` |
| 2 | Registro base de items/bloques (`DeferredRegister`), creative tab propio, primer item: **Philosopher's Stone** | `item/`, `block/` |
| 3 | Datos persistentes de jugador vía NeoForge Data Attachments: EMC del jugador + set de items conocidos (`PlayerKnowledgeAttachment`, `PlayerKnowledge` API), sync server↔client (login/respawn/cambio de dimensión) | `player/`, `network/`, `events/` |
| 4 | GUI de transmutación real: right-click con Philosopher's Stone abre `TransmutationScreen`/`TransmutationContainer` — aprender items metiéndolos en el slot de input, lista scrolleable de conocidos, transmutar gastando EMC (bug de doble cobro ya arreglado) | `gui/` |
| 5 | **Dark Matter** (139,264 EMC) y **Klein Star Ein** (batería EMC portátil) | `item/` |
| 6 | Recetas de crafteo vanilla (Philosopher's Stone, Dark Matter, Klein Star) + comandos `/equivalent_legacy emc` y `emc give` (OP) | `command/` |
| 7 | **Klein Stars Zwei–Omega** (línea completa de baterías, 1M–16M) | `item/` |
| 8 | **Combustibles alquímicos** (Alchemical Coal/Mobius/Aeternalis Fuel) + **Red Matter**; recetas originales restauradas | `item/` |
| 9 | **Bloques de almacenamiento** (fuel + matter blocks) + fix de render: bindings `items/<id>.json` de MC 26.2 | `block/`, `item/` |
| 10 | **Toolchain completo**: tools (sword/pick/axe/shovel/hoe/shears/hammer, +katar/morning star en Red Matter) y armadura de ambas materias; martillo con minado 3×3; EquipmentAssets para la armadura | `item/` |
| 11 | **Máquinas EMC**: Collector MK1-3, Relay MK1-3, Condenser MK1-2 (block entities con tick, menús/screens con DataSlots, red vecinal simplificada, Klein Stars) | `block/`, `block/entity/`, `gui/` |
| 12 | **Alchemical Bag** (inventario 13 slots persistido en el item vía `DataComponents.CONTAINER`) y **Alchemical Chest** (bloque 13 slots con BE) | `item/`, `block/`, `block/entity/`, `gui/` |
| 13 | **Curios support** para Klein Stars (dependencia blanda por datos: `data/curios/tags`, slot custom `klein_star`) | `data/` |
| 14 | **Covalence Dusts** (low/medium/high) + **Tome of Knowledge**; recetas de bolsa/cofre restauradas | `item/` |
| 15 | **16 Alchemical Bags** de color | `item/` |
| 16 | **Set Arcana**: anillos/bandas/amuletos/piedras/talismán/reloj con efectos pasivos (tick handler) + tags Curios | `item/`, `events/` |
| 17 | **Misc tools**: nova catalyst/cataclysm, divining rods, destruction catalyst, lenses, mercurial eye, archangel smite | `item/` |
| 18 | **Transmutation Table/Tablet** (abren la GUI de transmutación) + **Interdiction Torch** | `block/`, `block/entity/`, `item/` |
| 19 | **Sistema de carga/AOE**: `TOOL_CHARGE` component, shift-RMB cicla 0-3; martillo/pico minan AOE; espadas atacan en área | `item/` |
| 20 | **Red EMC por dimensión** (`EMCNetwork` pool compartido; las máquinas ya no dependen de bloques adyacentes) | `emc/`, `block/entity/` |
| 21 | **Gem Armor**: set definitivo (helmet/chestplate/leggings/boots) | `item/` |
| 22 | **Dark/Red Matter Furnaces**: hornos 4x/8x con recetas vanilla y GUI estilo horno (`AbstractFurnaceMenu`/`AbstractFurnaceScreen`); completa el set de bloques del original | `block/`, `block/entity/`, `gui/` |
| 23 | **Persistencia del pool EMC** (`EMCNetworkData` SavedData, carga al iniciar / guarda al parar) | `emc/`, `events/` |
| 24 | **Curios como dependencia requerida** (compileOnly+localRuntime desde `lib_ext/`, dependency en toml) + efectos pasivos desde slots de Curios + **fix crítico de empaquetado** (`META-INF/neoforge.mods.toml` ahora sí entra al jar) | `build.gradle`, `templates/`, `events/` |
| 25 | **Full Star recipes**: ingrediente custom (`ingredient_serializer`) que matchea un Klein Star Omega al máximo (16M); recetas alternativas de Gem Armor y Tome (endgame) | `item/crafting/`, `data/` |

Estructura de paquetes actual (todo bajo `src/main/java/com/skd/equivalentlegacy/`):
```
EquivalentLegacy.java, EquivalentLegacyClient.java
config/       — CommonConfig, ClientConfig, ServerConfig, MappingConfig, EquivalentLegacyConfig
emc/          — ConversionGroup, CustomConversion, EMCHelper, FixedValues, IHasConversions
emc/mapper/   — IMappingCollector, IExtendedMappingCollector, IValueArithmetic, IValueGenerator,
                LongArithmetic, MappingCollector, SimpleGraphMapper
emc/nss/      — AbstractNSSTag, AbstractDataComponentHolderNSSTag, NormalizedSimpleStack,
                NSSDataComponentHolder, NSSItem, NSSTag
item/         — AeternalisFuel, AlchemicalBag, AlchemicalCoal, DarkMatter, EquivalentLegacyCreativeTab,
                EquivalentLegacyDataComponents, EquivalentLegacyItems, EquivalentLegacyTags,
                HammerItem, KleinStar, KleinStarTier, MatterMaterials, MobiusFuel,
                PhilosophersStone, RedMatter
block/        — BaseMachineBlock, EquivalentLegacyBlocks (storage + machines + alchemical chest)
block/entity/ — AlchemicalChestBlockEntity, BaseMachineBlockEntity, CollectorBlockEntity,
                CondenserBlockEntity, EquivalentLegacyBlockEntities, MachineTiers, RelayBlockEntity
gui/          — BagMenu, BagScreen, ChestMenu, ChestScreen, CollectorMenu, CollectorScreen,
                CondenserMenu, CondenserScreen, MachineMenu, MachineScreen, ModMenuTypes,
                RelayMenu, RelayScreen, StorageScreen, TransmutationContainer, TransmutationScreen
network/      — PacketHandler
network/payload/ — KnowledgeDataPayload, KnowledgeSyncChangePayload, KnowledgeSyncEmcPayload,
                   KnowledgeSyncPayload, TransmuteRequestPayload
command/      — ModCommands (comandos /equivalent_legacy emc)
player/       — EquivalentLegacyAttachments, PlayerKnowledge, PlayerKnowledgeAttachment
events/       — PlayerEvents
```

## Lo que falta (siguientes betas)

**Estado: port completo** — todos los items, bloques y sistemas del mod original (ProjectE/Equivox) implementados, incluidas las variantes endgame. Única dependencia externa: **Curios** (requerida, jar en `lib_ext/curios-neoforge-15.0.0-beta.2+26.2.jar`, LGPL-3.0, no versionado).

## Notas sobre el entorno (por qué esto existe)

Varios intentos de lanzar la fase 6 vía OpenCode (`opencode-go/deepseek-v4-flash` y `opencode-go/deepseek-v4-pro --variant high`) se quedaron colgados sin producir ni una línea de output durante horas, incluso tras matar procesos zombie y confirmar que no había contención de sesiones concurrentes. No se identificó la causa raíz. Por eso este documento existe: para que puedas lanzar tú mismo el comando `opencode run` con este contexto pegado, sin depender de que Claude lo orqueste.

Comando de referencia usado hasta ahora (ajusta el modelo si hace falta):

```bash
cd "G:/Proyectos/Mods_Minecraft/equivalent_legacy/26.2"
OPENCODE_PERMISSION='{"edit":"allow","bash":"allow","webfetch":"allow"}' \
  opencode run -m opencode-go/deepseek-v4-flash --auto \
  --title "Phase 6: crafting recipes + EMC command" \
  --file=docs/CONTEXT_FOR_OPENCODE.md \
  -- "Lee el contexto adjunto y ejecuta la fase 6 descrita en 'Lo que falta'. Compila con gradlew.bat build y haz commit (Conventional Commits, footer v0.0.0-beta.6) antes de terminar."
```
