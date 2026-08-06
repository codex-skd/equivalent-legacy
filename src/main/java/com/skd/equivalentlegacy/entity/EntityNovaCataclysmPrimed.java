package com.skd.equivalentlegacy.entity;

import com.skd.equivalentlegacy.EquivalentLegacyEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class EntityNovaCataclysmPrimed extends EntityNovaPrimed {
    public EntityNovaCataclysmPrimed(EntityType<? extends EntityNovaPrimed> type, Level level) {
        super(type, level);
        this.blastRadius = 12.0F;
    }

    public EntityNovaCataclysmPrimed(Level level, double x, double y, double z) {
        super(level, x, y, z);
        this.blastRadius = 12.0F;
    }
}