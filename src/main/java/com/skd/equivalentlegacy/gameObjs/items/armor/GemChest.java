package com.skd.equivalentlegacy.gameObjs.items.armor;

import java.util.List;
import com.skd.equivalentlegacy.config.EquivalentLegacyConfig;
import com.skd.equivalentlegacy.gameObjs.items.IFireProtector;
import com.skd.equivalentlegacy.utils.PlayerHelper;
import com.skd.equivalentlegacy.utils.WorldHelper;
import com.skd.equivalentlegacy.utils.text.PELang;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class GemChest extends GemArmorBase implements IFireProtector {

	public GemChest(Properties props) {
		super(ArmorItem.Type.CHESTPLATE, props);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
		super.appendHoverText(stack, context, tooltip, flags);
		tooltip.add(PELang.GEM_LORE_CHEST.translate());
		tooltip.add(PELang.TOOLTIP_GEM_CHESTPLATE.translate());
	}

	@Override
	public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean isHeld) {
		super.inventoryTick(stack, level, entity, slot, isHeld);
		if (isArmorSlot(slot) && !level.isClientSide && entity instanceof Player player && PlayerHelper.checkFeedCooldown(player)) {
			player.getFoodData().eat(2, 10);
			entity.gameEvent(GameEvent.EAT);
		}
	}

	public static void doExplode(Player player) {
		if (EquivalentLegacyConfig.server.difficulty.offensiveAbilities.get()) {
			WorldHelper.createNovaExplosion(player.level(), player, player.getX(), player.getY(), player.getZ(), 9.0F);
		}
	}

	@Override
	public boolean canProtectAgainstFire(ItemStack stack, Player player) {
		return player.getItemBySlot(EquipmentSlot.CHEST) == stack;
	}
}
