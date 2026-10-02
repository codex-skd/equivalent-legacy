package com.skd.equivalentlegacy.gameObjs.entity;

import com.skd.equivalentlegacy.gameObjs.blocks.EquivalentLegacyTNT;
import com.skd.equivalentlegacy.gameObjs.registration.impl.BlockRegistryObject;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlocks;
import com.skd.equivalentlegacy.gameObjs.registries.PEEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EntityNovaCatalystPrimed extends EntityNovaPrimed {

	public EntityNovaCatalystPrimed(EntityType<EntityNovaCatalystPrimed> type, Level level) {
		super(type, level);
	}

	public EntityNovaCatalystPrimed(Level level, double x, double y, double z, LivingEntity placer) {
		super(level, x, y, z, placer);
	}

	@Override
	protected BlockRegistryObject<EquivalentLegacyTNT, ?> getBlock() {
		return PEBlocks.NOVA_CATALYST;
	}

	@NotNull
	@Override
	public EntityType<?> getType() {
		return PEEntityTypes.NOVA_CATALYST_PRIMED.get();
	}
}