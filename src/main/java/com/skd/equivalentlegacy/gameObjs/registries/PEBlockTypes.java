package com.skd.equivalentlegacy.gameObjs.registries;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.gameObjs.EnumMatterType;
import com.skd.equivalentlegacy.gameObjs.blocks.MatterFurnace;
import com.skd.equivalentlegacy.gameObjs.blocks.TransmutationStone;
import com.skd.equivalentlegacy.gameObjs.registration.PEDeferredHolder;
import com.skd.equivalentlegacy.gameObjs.registration.impl.BlockTypeDeferredRegister;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class PEBlockTypes {

	private PEBlockTypes() {
	}

	public static final BlockTypeDeferredRegister BLOCK_TYPES = new BlockTypeDeferredRegister(ELCore.MODID);

	public static final PEDeferredHolder<MapCodec<? extends Block>, MapCodec<TransmutationStone>> TRANSMUTATION_TABLE = BLOCK_TYPES.registerSimple("transmutation_table", TransmutationStone::new);
	public static final PEDeferredHolder<MapCodec<? extends Block>, MapCodec<MatterFurnace>> MATTER_FURNACE = BLOCK_TYPES.register("matter_furnace", () -> RecordCodecBuilder.mapCodec(instance -> instance.group(
			BlockBehaviour.propertiesCodec(),
			EnumMatterType.CODEC.fieldOf("type").forGetter(MatterFurnace::getMatterType)
	).apply(instance, MatterFurnace::new)));
}