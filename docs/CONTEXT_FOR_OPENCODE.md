# Contexto — Equivalent Legacy (para usar directamente con OpenCode/Codex)

> Generado 2026-08-01. Pégalo tal cual como prompt (o adjúntalo con `-f`) al lanzar OpenCode manualmente.

## Qué es este mod

Mod NeoForge que revive el sistema EMC (Equivalent Exchange 2 / ProjectE): asignar valor EMC a items, transmutarlos entre sí vía el Philosopher's Stone.

- **mod_id**: `equivalent_legacy`
- **package**: `com.skd.equivalentlegacy`
- **Minecraft / NeoForge**: `26.2` / `26.2.0.32-beta` — **no subir de versión sin que se pida explícitamente**
- **mod_version actual**: `0.0.0-beta.6`
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

## Lo ya implementado (fases 1-5, todo commiteado y compilando)

| Fase | Qué | Paquetes |
|---|---|---|
| 1 | Núcleo EMC: config (`CommonConfig`/`ClientConfig`/`ServerConfig`/`MappingConfig` vía `ModConfigSpec`), estructura NSS (`NormalizedSimpleStack`, `NSSItem`, `NSSTag`...), mapper EMC (`MappingCollector`, `SimpleGraphMapper`, `LongArithmetic`), `FixedValues`/`CustomConversion`, `EMCHelper` | `config/`, `emc/` |
| 2 | Registro base de items/bloques (`DeferredRegister`), creative tab propio, primer item: **Philosopher's Stone** | `item/`, `block/` |
| 3 | Datos persistentes de jugador vía NeoForge Data Attachments: EMC del jugador + set de items conocidos (`PlayerKnowledgeAttachment`, `PlayerKnowledge` API), sync server↔client (login/respawn/cambio de dimensión) | `player/`, `network/`, `events/` |
| 4 | GUI de transmutación real: right-click con Philosopher's Stone abre `TransmutationScreen`/`TransmutationContainer` — aprender items metiéndolos en el slot de input, lista scrolleable de conocidos, transmutar gastando EMC (bug de doble cobro ya arreglado) | `gui/` |
| 5 | **Dark Matter** (item de alto EMC, 139,264, intermedio de crafteo) y **Klein Star Ein** (batería EMC portátil con `DataComponentType<Long>`, carga/descarga por click, barra de durabilidad, tooltip) | `item/` |

Estructura de paquetes actual (todo bajo `src/main/java/com/skd/equivalentlegacy/`):
```
EquivalentLegacy.java, EquivalentLegacyClient.java
config/       — CommonConfig, ClientConfig, ServerConfig, MappingConfig, EquivalentLegacyConfig
emc/          — ConversionGroup, CustomConversion, EMCHelper, FixedValues, IHasConversions
emc/mapper/   — IMappingCollector, IExtendedMappingCollector, IValueArithmetic, IValueGenerator,
                LongArithmetic, MappingCollector, SimpleGraphMapper
emc/nss/      — AbstractNSSTag, AbstractDataComponentHolderNSSTag, NormalizedSimpleStack,
                NSSDataComponentHolder, NSSItem, NSSTag
item/         — DarkMatter, EquivalentLegacyCreativeTab, EquivalentLegacyDataComponents,
                EquivalentLegacyItems, KleinStar, KleinStarTier, PhilosophersStone
block/        — EquivalentLegacyBlocks (registro vacío, preparado)
gui/          — ModMenuTypes, TransmutationContainer, TransmutationScreen
network/      — PacketHandler
network/payload/ — KnowledgeDataPayload, KnowledgeSyncChangePayload, KnowledgeSyncEmcPayload,
                   KnowledgeSyncPayload, TransmuteRequestPayload
command/      — ModCommands (comandos /equivalent_legacy emc)
player/       — EquivalentLegacyAttachments, PlayerKnowledge, PlayerKnowledgeAttachment
events/       — PlayerEvents
```

## Lo que falta (fase 6 en adelante — quedó a medias, no arrancó)

**Fase 6 (completada — recetas + comando EMC):**
1. Recetas de crafteo vanilla JSON (`data/equivalent_legacy/recipe/`) para Philosopher's Stone, Dark Matter y Klein Star (Ein). Adaptadas del jar de referencia: los ingredientes de otros mods que no existen aún (`aeternalis_fuel`, `mobius_fuel`) se sustituyeron por vanilla — Dark Matter usa 8× `#c:storage_blocks/gold` + `#c:storage_blocks/diamond`, Klein Star Ein usa 8× `minecraft:glowstone` + `#c:gems/diamond`. Cuando se implementen los combustibles (fases futuras) hay que restaurar las recetas originales.
2. Comando `/equivalent_legacy emc` (mostrar tu EMC) y `/equivalent_legacy emc give <jugador> <cantidad>` (solo OP, nivel gamemaster), vía `RegisterCommandsEvent`/Brigadier en `command/ModCommands`.

**Fases futuras (no empezadas):**
- Collectors, Condensers, Relays (block entities, automatización de EMC).
- Red Matter y el resto de herramientas/armadura del toolchain.
- Tiers superiores de Klein Star (Zwei, Drei, Vier, Sphere, Omega — el enum `KleinStarTier` ya los define, solo falta registrarlos).
- Alchemical Bag / Alchemical Chest.
- Soporte Curios para items EMC equipables.

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
