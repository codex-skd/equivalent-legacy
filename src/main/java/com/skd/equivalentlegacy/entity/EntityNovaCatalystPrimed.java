package com.skd.equivalentlegacy.entity;

import com.skd.equivalentlegacy.EquivalentLegacyEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class EntityNovaCatalystPrimed extends EntityNovaPrimed {
    public EntityNovaCatalystPrimed(EntityType<? extends EntityNovaPrimed> type, Level level) {
        super(type, level);
        this.blastRadius = 6.0F;
    }

    public EntityNovaCatalystPrimed(Level level, double x, double y, double z) {
        super(level, x, y, z);
        this.blastRadius = 6.0F;
    }
}