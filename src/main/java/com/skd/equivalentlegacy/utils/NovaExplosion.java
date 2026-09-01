package com.skd.equivalentlegacy.utils;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class NovaExplosion extends Explosion {

	private final Level level;

	public NovaExplosion(Level level, @Nullable Entity entity, double x, double y, double z, float radius, Explosion.BlockInteraction mode) {
		//Nova Explosions don't cause fire
		super(level, entity, x, y, z, radius, false, mode);
		this.level = level;
	}

	@Override
	public void finalizeExplosion(boolean spawnParticles) {
		finalizeExplosion();
	}

	public List<BlockPos> finalizeExplosion() {
		List<BlockPos> particlePositions = new ArrayList<>();
		boolean interactsWithBlocks = interactsWithBlocks();
		List<BlockPos> toBlow = getToBlow();
		if (interactsWithBlocks) {
			this.level.getProfiler().push("explosion_blocks");

			NonNullList<ItemStack> allDrops = NonNullList.create();
			Util.shuffle(toBlow, this.level.random);

			for (BlockPos pos : toBlow) {
				BlockState state = level.getBlockState(pos);
				if (!state.isAir()) {
					particlePositions.add(pos);
				}
				// PE: Collect the drops we can, spawn the stuff we can't
				state.onExplosionHit(this.level, pos, this, (stack, position) -> allDrops.add(stack));
			}

			// PE: Drop all together
			LivingEntity placer = getIndirectSourceEntity();
			WorldHelper.createLootDrop(allDrops, level, placer == null ? center() : placer.position());
			this.level.getProfiler().pop();
		}
		return particlePositions;
	}
}
