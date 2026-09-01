package com.skd.equivalentlegacy.gameObjs.block_entities;

import com.mojang.serialization.DataResult;
import java.util.UUID;
import com.skd.equivalentlegacy.api.capabilities.block_entity.IEmcStorage.EmcAction;
import com.skd.equivalentlegacy.gameObjs.StellarCondenserBalance;
import com.skd.equivalentlegacy.gameObjs.registration.impl.BlockEntityTypeRegistryObject;
import com.skd.equivalentlegacy.gameObjs.registries.PEBlockEntityTypes;
import com.skd.equivalentlegacy.utils.EmcDepositHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Grants death-echo EMC (not from item drops) to the owning player when living entities die nearby.
 * Offline owners buffer EMC in this block until they return or pipes/Klein extract it.
 */
public class StellarCondenserBlockEntity extends EmcBlockEntity {

	public static final long MAX_STORAGE = 100_000;

	@Nullable
	private UUID owner;
	private long emcThisMinute;
	private long minuteWindowStartTick = -1;
	private long lastKillTick = Long.MIN_VALUE / 4;
	private long bossReadyAtTick;

	public StellarCondenserBlockEntity(BlockPos pos, BlockState state) {
		this(PEBlockEntityTypes.STELLAR_CONDENSER, pos, state);
	}

	protected StellarCondenserBlockEntity(BlockEntityTypeRegistryObject<? extends StellarCondenserBlockEntity> type,
			BlockPos pos, BlockState state) {
		super(type, pos, state, MAX_STORAGE);
	}

	public void setOwner(@Nullable UUID owner) {
		this.owner = owner;
		setChanged();
	}

	@Nullable
	public UUID getOwner() {
		return owner;
	}

	@Override
	protected boolean canAcceptEmc() {
		return false;
	}

	@Override
	protected boolean canProvideEmc() {
		return true;
	}

	@Override
	protected boolean emcAffectsComparators() {
		return true;
	}

	public static void tickServer(Level level, BlockPos pos, BlockState state, StellarCondenserBlockEntity condenser) {
		condenser.tryFlushToOwner(level);
		long stored = condenser.getStoredEmc();
		if (stored > 0) {
			condenser.sendToAllAcceptors(level, pos, stored);
		}
		condenser.updateComparators(level, pos);
	}

	private void tryFlushToOwner(Level level) {
		if (owner == null || getStoredEmc() <= 0 || level.getServer() == null) {
			return;
		}
		ServerPlayer player = EmcDepositHelper.findOnlineOwner(level.getServer(), owner);
		if (player == null) {
			return;
		}
		long stored = getStoredEmc();
		long extracted = forceExtractEmc(stored, EmcAction.EXECUTE);
		long deposited = EmcDepositHelper.depositToPlayer(player, extracted);
		if (deposited < extracted) {
			forceInsertEmc(extracted - deposited, EmcAction.EXECUTE);
		}
	}

	/**
	 * Called from {@link com.skd.equivalentlegacy.events.StellarCondenserEvents} on living death.
	 *
	 * @return true if this condenser claimed the kill echo (prevents multi-condenser double-dip)
	 */
	public boolean tryGrantDeathEcho(Level level, LivingEntity dead, long gameTime) {
		if (owner == null || level.isClientSide) {
			return false;
		}
		if (gameTime - lastKillTick < StellarCondenserBalance.KILL_COOLDOWN_TICKS) {
			return false;
		}

		boolean boss = isBoss(dead);
		if (boss) {
			if (gameTime < bossReadyAtTick) {
				return false;
			}
		} else {
			resetMinuteWindowIfNeeded(gameTime);
			if (emcThisMinute >= StellarCondenserBalance.SOFT_CAP_PER_MINUTE) {
				return false;
			}
		}

		long echo = echoEmcFor(dead);
		if (echo <= 0) {
			return false;
		}
		if (!boss) {
			long remaining = StellarCondenserBalance.SOFT_CAP_PER_MINUTE - emcThisMinute;
			echo = Math.min(echo, remaining);
			if (echo <= 0) {
				return false;
			}
		}

		ServerPlayer player = EmcDepositHelper.findOnlineOwner(level.getServer(), owner);
		long granted;
		if (player != null) {
			granted = EmcDepositHelper.depositToPlayer(player, echo);
		} else {
			granted = forceInsertEmc(echo, EmcAction.EXECUTE);
		}
		if (granted <= 0) {
			return false;
		}

		lastKillTick = gameTime;
		if (boss) {
			bossReadyAtTick = gameTime + StellarCondenserBalance.BOSS_COOLDOWN_TICKS;
		} else {
			emcThisMinute += granted;
		}
		setChanged();
		return true;
	}

	private void resetMinuteWindowIfNeeded(long gameTime) {
		if (minuteWindowStartTick < 0 || gameTime - minuteWindowStartTick >= 1_200) {
			minuteWindowStartTick = gameTime;
			emcThisMinute = 0;
		}
	}

	public static boolean isBoss(LivingEntity entity) {
		EntityType<?> type = entity.getType();
		return type == EntityType.WITHER || type == EntityType.ENDER_DRAGON;
	}

	public static long echoEmcFor(LivingEntity entity) {
		if (isBoss(entity)) {
			return StellarCondenserBalance.BOSS_EMC;
		}
		EntityType<?> type = entity.getType();
		if (type == EntityType.ZOMBIE || type == EntityType.HUSK || type == EntityType.DROWNED || type == EntityType.ZOMBIE_VILLAGER) {
			return StellarCondenserBalance.EMC_ZOMBIE;
		}
		if (type == EntityType.WITHER_SKELETON) {
			return StellarCondenserBalance.EMC_WITHER_SKELETON;
		}
		if (type == EntityType.SKELETON || type == EntityType.STRAY || type == EntityType.BOGGED) {
			return StellarCondenserBalance.EMC_SKELETON;
		}
		if (type == EntityType.CREEPER) {
			return StellarCondenserBalance.EMC_CREEPER;
		}
		if (type == EntityType.ENDERMAN) {
			return StellarCondenserBalance.EMC_ENDERMAN;
		}
		if (type == EntityType.PIGLIN || type == EntityType.PIGLIN_BRUTE) {
			return StellarCondenserBalance.EMC_PIGLIN;
		}
		if (entity instanceof Animal) {
			return StellarCondenserBalance.EMC_DEFAULT_ANIMAL;
		}
		if (entity instanceof Enemy) {
			return StellarCondenserBalance.EMC_DEFAULT_MONSTER;
		}
		return StellarCondenserBalance.EMC_DEFAULT_ANIMAL;
	}

	@Override
	public void loadAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		owner = UUIDUtil.CODEC.parse(registries.createSerializationContext(NbtOps.INSTANCE), tag.get("owner")).result().orElse(null);
		emcThisMinute = tag.getLong("emc_this_minute");
		minuteWindowStartTick = tag.contains("minute_window_start") ? tag.getLong("minute_window_start") : -1;
		lastKillTick = tag.contains("last_kill_tick") ? tag.getLong("last_kill_tick") : Long.MIN_VALUE / 4;
		bossReadyAtTick = tag.getLong("boss_ready_at");
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (owner != null) {
			DataResult<Tag> result = UUIDUtil.CODEC.encodeStart(registries.createSerializationContext(NbtOps.INSTANCE), owner);
			if (result.isSuccess()) {
				tag.put("owner", result.getOrThrow());
			}
		}
		tag.putLong("emc_this_minute", emcThisMinute);
		tag.putLong("minute_window_start", minuteWindowStartTick);
		tag.putLong("last_kill_tick", lastKillTick);
		tag.putLong("boss_ready_at", bossReadyAtTick);
	}
}
