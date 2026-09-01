<h1 align="center">&#128995; Equivalent Legacy</h1>

<p align="center"><strong>Bring back the EMC. Transmute anything.</strong></p>

<p align="center">
<img src="https://img.shields.io/badge/loader-NeoForge-orange?style=plastic&logo=curseforge" alt="NeoForge">
<img src="https://img.shields.io/badge/minecraft-26.2%20%7C%201.21.1-blue?style=plastic" alt="Minecraft 26.2 and 1.21.1">
<img src="https://img.shields.io/badge/side-client%20%2B%20server-brightgreen?style=plastic" alt="Client and Server">
<img src="https://img.shields.io/badge/license-MIT-lightgrey?style=plastic" alt="MIT">
</p>

<br>

---

<br>

<h2>&#10024; Overview</h2>

<table>
<tr>
<td width="65%">
<p>Equivalent Legacy revives the classic Equivalent Exchange gameplay loop for modern Minecraft. Every item and block carries an <strong>EMC</strong> (Energy-Matter-Currency) value, derived automatically from crafting and brewing recipes. Once you know an item, you can transmute practically anything into anything else of equal or lesser value &mdash; no more inventory full of junk you can't use. On top of that sits the full Philosopher's Stone toolchain: Dark Matter and Red Matter tools and armour, the ring and amulet line, Klein Star energy storage, and the automation machines (Collectors, Condensers, Relays, Matter Furnace, Entropy Sink).</p>

<p>A fork of <a href="https://github.com/Yaskulsky/projecte-26-port"><strong>Equivox</strong></a> by <em>Yaskulsky</em>, itself a fork of the MIT-licensed <a href="https://github.com/sinkillerj/ProjectE"><strong>ProjectE</strong></a> codebase (original creators: SinKillerJ, MaPePeR, williewillus, Lilylicious, pupnewfster et al.), which traces back to <strong>Equivalent Exchange 2</strong> by x3n0ph0b3. Item/block retextures based on ProjectE Retexture by Bbublick. Reworked and maintained by <strong>Stalking Dragons</strong>. Not affiliated with or endorsed by the ProjectE or Equivox authors.</p>
</td>
<td width="35%" align="center">
<a href="https://codex.skdragons.com/" target="_blank"><img src="https://node-files.skdragons.com/uploads/MINECRAFT/Codex/logo_codex_stalking_dragons.png" alt="Codex Stalking Dragons" width="160"></a>
</td>
</tr>
</table>

<br>

<h2>&#127919; Features</h2>

<h3>&#128176; EMC System</h3>
<p>Every item and block gets an EMC value, calculated from its recipes. Custom value overrides and conversion-group datapack files give full control over the economy, and a recipe mapper adapts EMC to other mods' crafting and brewing automatically.</p>

<h3>&#128260; Transmutation</h3>
<p>The Philosopher's Stone and Transmutation Tablet convert any known item into any other known item of equal or lesser EMC value, straight from your inventory. Learn items by feeding matter into the transmutation table.</p>

<h3>&#9935;&#65039; Philosopher's Stone Toolchain</h3>
<p>Progress through the classic tool line &mdash; Philosopher's Stone, then Dark Matter and Red Matter tools, weapons and armour &mdash; each tier unlocking wider mining, area effects and combat power.</p>

<h3>&#128141; Rings &amp; Amulets</h3>
<p>Arcana, Black Hole Band, Ignition, Harvest Goddess, Swiftwolf's Rending Gale, Evertide and Volcanite Amulets, the Body / Soul / Life / Mind Stones, Time Watch, Void Ring, Zero Ring and the Gem of Eternal Density &mdash; wearable EMC items with active and passive effects.</p>

<h3>&#128230; Alchemical Bag &amp; Chest</h3>
<p>Portable and stationary EMC-powered storage that can transmute items on the fly, no crafting interface needed.</p>

<h3>&#127756; Collectors, Condensers &amp; Relays</h3>
<p>Automate the economy: Collectors turn light into EMC, Condensers convert stored EMC back into a chosen item, and Relays move and amplify EMC between machines. Three tiers each (MK1&ndash;MK3).</p>

<h3>&#11088; Klein Star &amp; Energy Storage</h3>
<p>Klein Stars store large amounts of EMC for later use &mdash; power transmutations even when far from your main base.</p>

<h3>&#128293; Matter Furnace &amp; Entropy Sink</h3>
<p>The Dark/Red Matter Furnace smelts faster and doubles output; the Entropy Sink safely absorbs and converts explosion energy.</p>

<h3>&#127758; World Transmutation</h3>
<p>Right-click the world with a Philosopher's Stone to transmute blocks in place &mdash; stone to cobblestone, sand to grass, oxidising copper, and more, all datapack-configurable.</p>

<h3>&#129513; Compatibility</h3>
<p>Regalia Slots API slot support for wearable EMC items, so rings and amulets work in curio-style slots.</p>

<br>

<h2>&#129521; Mod Structure</h2>

<table>
<tr><th align="left">Area</th><th align="left">What it provides</th></tr>
<tr><td><code>emc</code></td><td>The EMC map, recipe mappers, custom conversions and world-transmutation loading.</td></tr>
<tr><td><code>gameObjs</code></td><td>Items, blocks, block entities, containers and the registration layer.</td></tr>
<tr><td><code>components</code></td><td>Data components for stored EMC, learned knowledge and item modes.</td></tr>
<tr><td><code>network</code> / <code>events</code></td><td>Packets, the <code>/equivalent_legacy</code> commands and the gameplay event hooks.</td></tr>
<tr><td><code>client</code></td><td>The transmutation GUI, machine screens, key bindings and renderers.</td></tr>
<tr><td><code>integration</code></td><td>JEI, WTHIT and Regalia Slots API support.</td></tr>
</table>

<br>

<h2>&#128203; Requirements</h2>

<table>
<tr><td><strong>Minecraft / NeoForge / Java</strong></td><td>see <em>Available Versions</em> below</td></tr>
<tr><td><strong>Dependencies</strong></td><td><a href="https://www.curseforge.com/minecraft/mc-mods/regalia-slots-api">Regalia Slots API</a> (required)</td></tr>
<tr><td><strong>Side</strong></td><td>Client and Server (required on both)</td></tr>
</table>

<br>

<h2>&#128230; Available Versions</h2>

<table>
<tr><th align="left">Minecraft</th><th align="left">NeoForge</th><th align="left">Java</th><th align="left">Latest build</th><th align="left">Status</th></tr>
<tr><td>26.2</td><td>26.2.0.57+</td><td>25</td><td><code>1.6.4</code></td><td>Stable</td></tr>
<tr><td>1.21.1</td><td>21.1.249+</td><td>21</td><td><code>0.0.0-beta.1</code></td><td>Beta &mdash; port from the 26.2 line, API reverted to 1.21.1</td></tr>
</table>

<p><em>Both versions share this CurseForge project. Pick the file that matches your Minecraft version.</em></p>

<br>

<h2>&#127918; How to Use</h2>

<ol>
<li>Install <strong>Regalia Slots API</strong> and Equivalent Legacy on <strong>both client and server</strong>.</li>
<li>Craft a Philosopher's Stone and start learning items by transmuting known matter.</li>
<li>Use the Transmutation Tablet or the stone's in-inventory interface to convert learned items.</li>
<li>Tune EMC values and conversion overrides via the mod config or custom conversion-group datapack files.</li>
</ol>

<br>

---

<br>

<h2>&#128591; Credits &amp; License</h2>

<p>Equivalent Legacy is a fork of <a href="https://github.com/Yaskulsky/projecte-26-port">Equivox</a> by <strong>Yaskulsky</strong>, itself a fork of the MIT-licensed <a href="https://github.com/sinkillerj/ProjectE">ProjectE</a> codebase (SinKillerJ, MaPePeR, williewillus, Lilylicious, pupnewfster et al.), which traces back to <strong>Equivalent Exchange 2</strong> by x3n0ph0b3. Item/block retextures based on ProjectE Retexture by Bbublick. Reworked and maintained by <strong>Stalking Dragons</strong>. Released under the <strong>MIT</strong> license. Not affiliated with or endorsed by the ProjectE or Equivox authors.</p>

<br>
<br>

<p align="center">
  <a href="https://codex.skdragons.com/" target="_blank">
    <img src="https://node-files.skdragons.com/uploads/MINECRAFT/Codex/logo_codex_stalking_dragons.png" alt="Codex Stalking Dragons" width="200">
  </a>
  <br>
  <a href="https://codex.skdragons.com/">https://codex.skdragons.com/</a>
  <br>
  <em>Codex Stalking Dragons &mdash; Minecraft Modding</em>
</p>
