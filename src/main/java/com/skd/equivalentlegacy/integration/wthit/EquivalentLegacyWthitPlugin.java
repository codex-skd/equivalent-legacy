package com.skd.equivalentlegacy.integration.wthit;

import mcp.mobius.waila.api.IClientRegistrar;
import mcp.mobius.waila.api.IWailaClientPlugin;
import net.minecraft.world.level.block.Block;

/**
 * WTHIT entry point for Equivalent Legacy. Registered via {@code wthit_plugins.json} and only loaded
 * when WTHIT is present. WTHIT is an optional dependency: the mod works fine without it.
 */
public class EquivalentLegacyWthitPlugin implements IWailaClientPlugin {
    @Override
    public void register(IClientRegistrar registrar) {
        registrar.body(new TransmutationComponentProvider(), Block.class);
    }
}
