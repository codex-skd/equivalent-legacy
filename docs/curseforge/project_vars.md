# Project Variables — Equivalent Legacy (1.21.1)

> **Rama 1.21.1**: `game_versions = 9638, 9639, 11779, 10150` (Client, Server, **1.21.1** id `11779`, NeoForge). `release_type = release`. JAR `equivalent_legacy-1.21.1-neoforge-21.1.249-<version>.jar`. Tag `1.21.1-neoforge-<version>`. Proyecto CurseForge compartido con la rama 26.2 (`1632317`).

## Required
project_id = 1632317
api_token = ee776b0a-ee95-4850-b554-06be02a8657f
game_versions = 9638, 9639, 11779, 10150
release_type = release

## Optional
relations =

---

El script lee `project_id`, `api_token` y `game_versions` de este archivo, y `mod_id`, `mod_name`,
`minecraft_version`, `mod_version` de `gradle.properties`. Sube el JAR desde `build/libs/` con el
changelog de `docs/curseforge/versions/<version>.md`.

**game_versions**: `9638` Client · `9639` Server · `11779` Minecraft 1.21.1 · `10150` NeoForge.

## Dependencia requerida

| Mod | Proyecto CurseForge | Archivo | Versión |
|-----|---------------------|---------|---------|
| Regalia Slots API | N/A (fork propio del equipo) | N/A | 0.0.0-beta.11 |

## Rama
minecraft/1.21.1/neoforge-21.1.249/production

## Tag
Formato: `<mc-version>-<framework>-<version>` — Ejemplo: `1.21.1-neoforge-0.0.0-beta.1`

## Repo GitLab
https://gitlab.com/stalking-dragons/minecraft/equivalent-legacy.git

## Historial

- v0.0.0-beta.4: ✅ Subido como BETA (File ID: 8795801). `pe_custom_conversions` ahora respeta condiciones de carga
  de NeoForge. El compat EMC de ATM/Powah que EL trae empaquetado (`atm_powah_compat.json`) se
  cargaba siempre y, sin `allthemodium` ni `powah` instalados, EL escupía decenas de
  `Unable to deserialize key: … allthemodium:…` / `Failed to read conversions: … powah:…` a nivel
  ERROR en cada mapeo de EMC. Ahora el fichero se ha partido en `atm_compat.json` (gate
  `neoforge:mod_loaded → allthemodium`) y `powah_compat.json` (gate `→ powah`), y el original
  queda vacío; `CustomConversionMapper` parsea con `ConditionalOps` +
  `createConditionalCodecWithConditions` (mismo patrón que `WorldTransmutationManager`) y salta en
  silencio los ficheros cuyas condiciones no se cumplen. Valores EMC intactos. Mismo cambio en la
  rama 26.2 (1.6.5). `clean build` OK en ambas ramas. Sin verificar in-game.
- v0.0.0-beta.3: fix de assets de cliente. MC 1.21.1 resuelve los modelos de item desde
  `assets/equivalent_legacy/models/item/<id>.json`; el port los generó para los items sueltos
  pero no para los 19 block-items (bloques de combustible, Collector MK1-3, Entropy Sink, Stellar
  Condenser, hornos de materia, bloques de materia, Nova Catalyst/Cataclysm, Relay MK1-3), que
  salían con la textura ausente en el creativo y en la mano. Añadidos los 19 modelos, cada uno
  heredando su modelo de bloque. Sin tocar Java. `clean build` OK. Sin verificar in-game.
- v0.0.0-beta.2: fix de metadatos. El rango de la dependencia opcional JEI en `neoforge.mods.toml`
  era `[30.15.0,)` (heredado de una plantilla de otra versión de MC); JEI para 1.21.1 va en la
  serie `19.x`, así que NeoForge registraba `Unsupported installed optional dependencies` para
  `jei`. Ahora es `[19.50.0.414,)` (la build de JEI del modpack objetivo, o superior). Mismo
  código que beta.1.
- v0.0.0-beta.1: port a Minecraft 1.21.1 / NeoForge 21.1.249 desde la línea 26.2 (1.6.4).
  Reversión de API 26.2→1.21.1 en 474 clases; fix de pérdida de items al romper 7 tipos de
  bloque (26.2 `preRemoveSideEffects` → 1.21.1 `Block#onRemove`); 151 recetas + 2 tags
  reconvertidos del formato datapack 26.2 al de 1.21.1. `build` + `runServer` verificados
  (Done, 0 FATAL). Sin verificar in-game.
