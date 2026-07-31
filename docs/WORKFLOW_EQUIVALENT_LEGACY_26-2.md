# Flujo de trabajo — Equivalent Legacy (NeoForge)

> **Versión del workflow**: 1.13.0 (codex-docs)
> Este archivo pertenece al proyecto **Equivalent Legacy**. Cada proyecto tiene su propio `WORKFLOW_<MOD_ID>_<MC-VERSION>.md`.
> No es un archivo central ni template compartido. Los cambios aquí solo afectan a este proyecto.
> Es una **copia fina**: delega en `codex-docs/WORKFLOW_GENERIC.md` y los `reference/`. No se re-sincroniza copiando contenido — solo se actualiza si cambia la estructura del genérico o los datos específicos del mod.

## Delegación

Todo lo que no sea específico del mod se lee de:
- `codex-docs/WORKFLOW_GENERIC.md` — convenciones, workspace, ramas, versionado, commits, tags, CI/CD, flujo, buenas prácticas, idioma
- `codex-docs/reference/CURSEFORGE.md` — formato HTML de CurseForge (solo al publicar)
- `codex-docs/reference/GRAPHIFY.md` — backend LLM de Graphify (solo al montar `extract`/`label`)
- `codex-docs/reference/REPO_SETUP.md` — setup único de ramas/CI (solo al iniciar el repo)

## Específico del mod

| Dato | Valor |
|---|---|
| Mod ID (`gradle.properties`) | `equivalent_legacy` |
| Clase principal | `EquivalentLegacy` |
| Display name (Title Case) | `Equivalent Legacy` |
| Versiones de Minecraft | `26.2` |

### Notas específicas de este mod

- **Fork de**: [Equivox](https://github.com/Yaskulsky/projecte-26-port) por Yaskulsky (a su vez fork del código MIT de [ProjectE](https://github.com/sinkillerj/ProjectE)). Referencia original: `equivox-1.0.0.jar` (mod id `equivox`, package `com.yaskulsky.equivox`, versión interna 1.5.0 para MC 26.1.2/NeoForge).
- **package**: `com.skd.equivalentlegacy`
- **Minecraft / NeoForge**: `26.2` / `26.2.0.32-beta` (heredado del esqueleto `codex-docs/mod_template/26.2-26.2.0.32-beta`, no actualizar sin pedirlo explícitamente)
- **Atribución obligatoria**: mantener "fork of Equivox / ProjectE" en `README.md`, `docs/curseforge/project_description.md` y `credits` de `neoforge.mods.toml` durante todo el desarrollo — no eliminar al renombrar clases o paquetes.
- **Rama**: `minecraft/26.2/neoforge-26.2.0.32-beta/production` (+ `main` hermana)
