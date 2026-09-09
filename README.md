# Equivalent Legacy

Equivalent Legacy is an EMC/transmutation mod for Minecraft 1.21.1 (NeoForge), bringing the classic Equivalent Exchange-style gameplay to modern Minecraft.

> This mod is a fork of [Equivox](https://github.com/Yaskulsky/projecte-26-port) by Yaskulsky, itself a fork of the MIT-licensed [ProjectE](https://github.com/sinkillerj/ProjectE) codebase (original creators: SinKillerJ, MaPePeR, williewillus, Lilylicious, pupnewfster et al.), which traces back to Equivalent Exchange 2 by x3n0ph0b3. Item/block retextures based on [ProjectE Retexture](https://github.com/Bbublick) by Bbublick. Not affiliated with or endorsed by the ProjectE or Equivox authors.

## Status

Stable (`1.0.0`). API port of the stable 26.2 line (1.6.4) to the 1.21.1 API — the fork's own adaptations are preserved; only 26.2-only Minecraft / NeoForge API was reverted to its 1.21.1 form. `./gradlew build` and `./gradlew runServer` verified: `Done`, 0 FATAL, 0 recipe/tag errors, EMC map and world-transmutation files register cleanly. This build has been running in a full modded-server pack.

## Requirements

| Component | Version |
|---|---|
| Minecraft | 1.21.1 |
| NeoForge | 21.1.249+ |
| Java | 21+ |
| [Regalia Slots API](https://gitlab.com/stalking-dragons/minecraft/regalia-slots-api) | 0.0.0-beta.11+ (required — the team's own fork of the Curios API) |

## Installation

1. Install [NeoForge](https://neoforged.net/) for Minecraft 1.21.1.
2. Install Regalia Slots API.
3. Download the mod jar and place it in your `mods/` folder (client and server).

## License

MIT — see [LICENSE](LICENSE).
