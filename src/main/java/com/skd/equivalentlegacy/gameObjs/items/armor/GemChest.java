package com.skd.equivalentlegacy.gameObjs.items.armor;

import java.util.function.Consumer;
import com.skd.equivalentlegacy.config.EquivalentLegacyConfig;
import com.skd.equivalentlegacy.gameObjs.items.IFireProtector;
import com.skd.equivalentlegacy.gameObjs.items.ItemPE;
import com.skd.equivalentlegacy.utils.BalanceHelper;
import com.skd.equivalentlegacy.utils.ClientKeyHelper;
import com.skd.equivalentlegacy.utils.PEKeybind;
import com.skd.equivalentlegacy.utils.PlayerHelper;
import com.skd.equivalentlegacy.utils.WorldHelper;
import com.skd.equivalentlegacy.utils.text.PELang;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GemChest extends GemArmorBase implements IFireProtector {

	public GemChest(Properties props) {
		super(ArmorType.CHESTPLATE, props);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull TooltipDisplay display, @NotNull Consumer<Component> tooltip, @NotNull TooltipFlag flags) {
		super.appendHoverText(stack, context, display, tooltip, flags);
		tooltip.accept(PELang.GEM_LORE_CHEST.translate());
		tooltip.accept(PELang.TOOLTIP_GEM_CHESTPLATE.translate());
		tooltip.accept(PELang.TOOLTIP_GEM_CHESTPLATE_2.translate());
		tooltip.accept(PELang.TOOLTIP_GEM_CHESTPLATE_3.translate(ClientKeyHelper.getKeyName(PEKeybind.EXTRA_FUNCTION)));
		if (!EquivalentLegacyConfig.server.difficulty.offensiveAbilities.getOrDefault()) {
			tooltip.accept(PELang.TOOLTIP_GEM_OFFENSIVE_DISABLED.translateColored(ChatFormatting.GRAY));
		}
	}

	@Override
	public void inventoryTick(@NotNull ItemStack stack, @NotNull ServerLevel level, @NotNull Entity entity, @Nullable EquipmentSlot slot) {
		super.inventoryTick(stack, level, entity, slot);
		if (isArmorSlot(slot) && !level.isClientSide() && entity instanceof Player player && PlayerHelper.checkFeedCooldown(player)) {
			if (BalanceHelper.enabled()) {
				if (BalanceHelper.autoSurvivalEnabled() && BalanceHelper.isAbilityAllowed(level)) {
					long cost = BalanceHelper.scaleEmc(BalanceHelper.emcPerFeed());
					if (cost <= 0 || ItemPE.consumeFuel(player, stack, cost, false)) {
						player.getFoodData().eat(BalanceHelper.feedAmount(), BalanceHelper.feedAmount() * 5.0F);
						entity.gameEvent(GameEvent.EAT);
						if (cost > 0) {
							ItemPE.removeEmc(stack, cost);
						}
					}
				}
			} else {
				player.getFoodData().eat(2, 10);
				entity.gameEvent(GameEvent.EAT);
			}
		}
	}

	public static void doExplode(Player player) {
		if (EquivalentLegacyConfig.server.difficulty.offensiveAbilities.get() && BalanceHelper.gemArmorEnabled() && BalanceHelper.isAbilityAllowed(player.level())) {
			WorldHelper.createNovaExplosion(player.level(), player, player.getX(), player.getY(), player.getZ(), 9.0F);
		}
	}

	@Override
	public boolean canProtectAgainstFire(ItemStack stack, Player player) {
		return player.getItemBySlot(EquipmentSlot.CHEST) == stack;
	}
}