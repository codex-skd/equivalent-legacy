package com.skd.equivalentlegacy.events;

import com.skd.equivalentlegacy.ELCore;
import com.skd.vellumli.api.VellumliAPI;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = ELCore.MODID)
public class GuideBookEvents {

	// The Vellumli book this module hands out. It matches data/equivalent_legacy/vellumli_books/guide/book.json.
	public static final Identifier GUIDE_BOOK_ID = Identifier.fromNamespaceAndPath(ELCore.MODID, "guide");

	// Flag stored in the player's persisted NBT (survives death and dimension changes) so the book is only
	// given once per player. Stored under Player.PERSISTED_NBT_TAG.
	private static final String GUIDE_GIVEN_TAG = ELCore.MODID + ":guide_given";

	@SubscribeEvent
	public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (!(event.getEntity() instanceof ServerPlayer player)) {
			return;
		}

		CompoundTag persistedData = player.getPersistentData().getCompoundOrEmpty(Player.PERSISTED_NBT_TAG);
		if (persistedData.getBooleanOr(GUIDE_GIVEN_TAG, false)) {
			return;
		}

		ItemStack book = VellumliAPI.get().getBookStack(GUIDE_BOOK_ID);
		if (book.isEmpty()) {
			// Vellumli did not know about the book (e.g. the book resources failed to load). Do not
			// mark it as given so the player still gets it on a later login.
			ELCore.LOGGER.error("Could not build the guide book stack for {}", GUIDE_BOOK_ID);
			return;
		}

		if (!player.getInventory().add(book)) {
			// Inventory full: drop the book at the player's feet instead of silently losing it.
			player.drop(book, false);
		}

		persistedData.putBoolean(GUIDE_GIVEN_TAG, true);
		player.getPersistentData().put(Player.PERSISTED_NBT_TAG, persistedData);
		player.sendSystemMessage(Component.translatable("chat." + ELCore.MODID + ".guide_book.given"), false);
		ELCore.debugLog("Gave the guide book to {}", player.getName());
	}
}