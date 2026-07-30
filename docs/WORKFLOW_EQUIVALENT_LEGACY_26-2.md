# Flujo de trabajo — Equivalent Legacy (NeoForge)

> **Versión del workflow**: 1.12.0
> Este archivo pertenece al proyecto **Equivalent Legacy**. Cada proyecto tiene su propio `WORKFLOW_<MOD_ID>_<MC-VERSION>.md`.
> No es un archivo central ni template compartido. Los cambios aquí solo afectan a este proyecto.
> Para actualizar este workflow, revisar la última versión en `codex-docs/WORKFLOW_GENERIC.md`.

Ver `codex-docs/WORKFLOW_GENERIC.md` para las convenciones completas (nomenclatura, estructura, ramas, versionado, commits, CI/CD, CurseForge, Graphify).

## Notas específicas de este mod

- **Fork de**: [Equivox](https://github.com/Yaskulsky/projecte-26-port) por Yaskulsky (a su vez fork del código MIT de [ProjectE](https://github.com/sinkillerj/ProjectE)). Referencia original: `equivox-1.0.0.jar` (mod id `equivox`, package `com.yaskulsky.equivox`, versión interna 1.5.0 para MC 26.1.2/NeoForge).
- **mod_id**: `equivalent_legacy`
- **package**: `com.skd.equivalentlegacy`
- **Minecraft / NeoForge**: `26.2` / `26.2.0.32-beta` (heredado del esqueleto `codex-docs/mod_template/26.2-26.2.0.32-beta`, no actualizado sin pedirlo explícitamente)
- **Atribución obligatoria**: mantener la mención "fork of Equivox / ProjectE" en `README.md`, `docs/curseforge/project_description.md` y `credits` de `neoforge.mods.toml` durante todo el desarrollo — no eliminar al renombrar clases o paquetes.
- **Rama**: `minecraft/26.2/neoforge-26.2.0.32-beta/production` (+ `main` hermana)
