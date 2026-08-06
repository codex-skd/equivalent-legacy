package com.skd.equivalentlegacy.block;

import com.skd.equivalentlegacy.block.entity.EquivalentLegacyBlockEntities;
import java.util.function.Supplier;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class RMPedestal extends Pedestal {
    public RMPedestal(Properties properties) {
        super(properties, () -> (BlockEntityType<? extends com.skd.equivalentlegacy.block.entity.PedestalBlockEntity>) (BlockEntityType<?>) EquivalentLegacyBlockEntities.RM_PEDESTAL.get());
    }
}