package com.skd.equivalentlegacy.emc.components.processor;

import com.skd.equivalentlegacy.emc.mapper.EMCMappingHandler;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.WrittenBookContent;

/**
 * Handles written books. The contents do not change the EMC value, but the component is
 * marked as persistent so the book's content is retained through transmutation.
 */
public class WrittenBookProcessor extends PersistentComponentProcessor<WrittenBookContent> {

	@Override
	public String getName() {
		return "WrittenBookProcessor";
	}

	@Override
	protected DataComponentType<WrittenBookContent> getComponentType(ItemStack stack) {
		return DataComponents.WRITTEN_BOOK_CONTENT;
	}

	@Override
	protected boolean shouldPersist(ItemStack stack, WrittenBookContent component) {
		return !component.equals(WrittenBookContent.EMPTY);
	}

	@Override
	protected long calculateComponentEMC(ItemStack stack, WrittenBookContent component, EMCMappingHandler handler) {
		//Contents of the written book do not change the calculated EMC
		return 0;
	}
}
