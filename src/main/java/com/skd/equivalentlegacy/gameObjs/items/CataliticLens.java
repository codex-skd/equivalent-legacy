package com.skd.equivalentlegacy.gameObjs.items;

import com.skd.equivalentlegacy.api.capabilities.item.IProjectileShooter;
import com.skd.equivalentlegacy.gameObjs.registries.PEItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CataliticLens extends DestructionCatalyst implements IProjectileShooter {

	public CataliticLens(Properties props) {
		super(props);
	}

	@Override
	public boolean shootProjectile(@NotNull Player player, @NotNull ItemStack stack, InteractionHand hand) {
		return PEItems.HYPERKINETIC_LENS.get().shootProjectile(player, stack, hand);
	}

	@Override
	public int getNumCharges(@NotNull ItemStack stack) {
		return 7;
	}

	@Override
	protected int calculateDepthFromCharge(ItemStack stack) {
		int charge = getCharge(stack);
		if (charge == 0) {
			return 1;
		}
		return 8 + 8 * charge;
	}
}