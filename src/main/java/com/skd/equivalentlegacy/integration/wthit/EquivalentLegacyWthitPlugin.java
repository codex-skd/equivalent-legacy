package com.skd.equivalentlegacy.integration.wthit;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import net.minecraft.world.level.block.Block;

/**
 * WTHIT entry point for Equivalent Legacy. Registered via {@code wthit_plugins.json} and only loaded
 * when WTHIT is present. WTHIT is an optional dependency: the mod works fine without it.
 */
public class EquivalentLegacyWthitPlugin implements IWailaPlugin {
    @Override
    public void register(IRegistrar registrar) {
        registrar.addComponent(new TransmutationComponentProvider(), TooltipPosition.BODY, Block.class);
    }
}
