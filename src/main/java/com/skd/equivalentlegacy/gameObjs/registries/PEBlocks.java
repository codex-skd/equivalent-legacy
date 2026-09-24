package com.skd.equivalentlegacy.gameObjs.registries;

import java.util.function.Function;
import java.util.function.ToIntFunction;

import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.gameObjs.EnumCollectorTier;
import com.skd.equivalentlegacy.gameObjs.EnumEntropySinkTier;
import com.skd.equivalentlegacy.gameObjs.EnumMatterType;
import com.skd.equivalentlegacy.gameObjs.EnumRelayTier;
import com.skd.equivalentlegacy.gameObjs.blocks.AlchemicalChest;
import com.skd.equivalentlegacy.gameObjs.blocks.Collector;
import com.skd.equivalentlegacy.gameObjs.blocks.Condenser;
import com.skd.equivalentlegacy.gameObjs.blocks.CondenserMK2;
import com.skd.equivalentlegacy.gameObjs.blocks.EntropySink;
import com.skd.equivalentlegacy.gameObjs.blocks.InterdictionTorchEntityBlock.InterdictionTorch;
import com.skd.equivalentlegacy.gameObjs.blocks.InterdictionTorchEntityBlock.InterdictionTorchWall;
import com.skd.equivalentlegacy.gameObjs.blocks.MatterBlock;
import com.skd.equivalentlegacy.gameObjs.blocks.MatterFurnace;
import com.skd.equivalentlegacy.gameObjs.blocks.Pedestal;
import com.skd.equivalentlegacy.gameObjs.blocks.EquivalentLegacyTNT;
import com.skd.equivalentlegacy.gameObjs.blocks.EquivalentLegacyTNT.TNTEntityCreator;
import com.skd.equivalentlegacy.gameObjs.blocks.Relay;
import com.skd.equivalentlegacy.gameObjs.blocks.StellarCondenser;
import com.skd.equivalentlegacy.gameObjs.blocks.TransmutationStone;
import com.skd.equivalentlegacy.gameObjs.entity.EntityNovaCataclysmPrimed;
import com.skd.equivalentlegacy.gameObjs.entity.EntityNovaCatalystPrimed;
import com.skd.equivalentlegacy.gameObjs.registration.impl.BlockDeferredRegister;
import com.skd.equivalentlegacy.gameObjs.registration.impl.BlockRegistryObject;
import com.skd.equivalentlegacy.gameObjs.registration.impl.BlockRegistryObject.WallOrFloorBlockRegistryObject;
import com.skd.equivalentlegacy.gameObjs.items.PEBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class PEBlocks {

	public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(ELCore.MODID);

	public static final BlockRegistryObject<AlchemicalChest, BlockItem> ALCHEMICAL_CHEST = BLOCKS.register("alchemical_chest",
			() -> new AlchemicalChest(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(10, 3_600_000)));

	public static final BlockRegistryObject<Block, BlockItem> ALCHEMICAL_COAL = registerFuelBlock("alchemical_coal_block", MapColor.COLOR_RED);

	public static final BlockRegistryObject<Block, BlockItem> MOBIUS_FUEL = registerFuelBlock("mobius_fuel_block", MapColor.COLOR_RED);

	public static final BlockRegistryObject<Block, BlockItem> AETERNALIS_FUEL = registerFuelBlock("aeternalis_fuel_block", MapColor.COLOR_LIGHT_GRAY);

	public static final BlockRegistryObject<Collector, PEBlockItem> COLLECTOR = registerCollector("collector_mk1", EnumCollectorTier.MK1, state -> 7);

	public static final BlockRegistryObject<Collector, PEBlockItem> COLLECTOR_MK2 = registerCollector("collector_mk2", EnumCollectorTier.MK2, state -> 11);

	public static final BlockRegistryObject<Collector, PEBlockItem> COLLECTOR_MK3 = registerCollector("collector_mk3", EnumCollectorTier.MK3, state -> 15);

	public static final BlockRegistryObject<EntropySink, PEBlockItem> ENTROPY_SINK = registerEntropySink(EnumEntropySinkTier.BASIC, state -> 7);

	public static final BlockRegistryObject<EntropySink, PEBlockItem> ENTROPY_SINK_DARK = registerEntropySink(EnumEntropySinkTier.DARK, state -> 11);

	public static final BlockRegistryObject<EntropySink, PEBlockItem> ENTROPY_SINK_RED = registerEntropySink(EnumEntropySinkTier.RED, state -> 15);

	public static final BlockRegistryObject<StellarCondenser, PEBlockItem> STELLAR_CONDENSER = BLOCKS.register("stellar_condenser",
			() -> new StellarCondenser(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)
					.requiresCorrectToolForDrops().strength(10, 3_600_000)),
			block -> new PEBlockItem(block, new Item.Properties(), (stack, tooltip, flags) -> block.addTooltip(stack, tooltip)));

	public static final BlockRegistryObject<Condenser, BlockItem> CONDENSER = registerCondenser("condenser_mk1", Condenser::new, block -> new BlockItem(block, new Item.Properties()));

	public static final BlockRegistryObject<CondenserMK2, BlockItem> CONDENSER_MK2 = registerCondenser("condenser_mk2", CondenserMK2::new, block -> new BlockItem(block, new Item.Properties().fireResistant()));

	public static final BlockRegistryObject<Pedestal, PEBlockItem> DARK_MATTER_PEDESTAL = BLOCKS.register("dm_pedestal",
			() -> new Pedestal(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1_000_000, 3_000_000).lightLevel(state -> 12)),
			block -> new PEBlockItem(block, new Item.Properties().fireResistant(), (stack, tooltip, flags) -> block.addTooltip(stack, tooltip)));

	public static final BlockRegistryObject<MatterFurnace, BlockItem> DARK_MATTER_FURNACE = registerFurnace("dm_furnace", EnumMatterType.DARK_MATTER, 1_000_000, 3_000_000);

	public static final BlockRegistryObject<MatterFurnace, BlockItem> RED_MATTER_FURNACE = registerFurnace("rm_furnace", EnumMatterType.RED_MATTER, 2_000_000, 6_000_000);

	public static final BlockRegistryObject<MatterBlock, BlockItem> DARK_MATTER = registerMatterBlock("dark_matter_block", EnumMatterType.DARK_MATTER, 1_000_000, 3_000_000);

	public static final BlockRegistryObject<MatterBlock, BlockItem> RED_MATTER = registerMatterBlock("red_matter_block", EnumMatterType.RED_MATTER, 2_000_000, 6_000_000);

	public static final WallOrFloorBlockRegistryObject<InterdictionTorch, InterdictionTorchWall, StandingAndWallBlockItem> INTERDICTION_TORCH = BLOCKS.registerWallOrFloorItem("interdiction_torch", InterdictionTorch::new, InterdictionTorchWall::new,
			BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).noCollission().instabreak().strength(0).lightLevel(state -> 14).randomTicks());

	public static final BlockRegistryObject<EquivalentLegacyTNT, BlockItem> NOVA_CATALYST = registerExplosive("nova_catalyst", EntityNovaCatalystPrimed::new);

	public static final BlockRegistryObject<EquivalentLegacyTNT, BlockItem> NOVA_CATACLYSM = registerExplosive("nova_cataclysm", EntityNovaCataclysmPrimed::new);

	public static final BlockRegistryObject<TransmutationStone, BlockItem> TRANSMUTATION_TABLE = BLOCKS.register("transmutation_table",
			() -> new TransmutationStone(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(10, 30)));

	public static final BlockRegistryObject<Relay, PEBlockItem> RELAY = registerRelay("relay_mk1", EnumRelayTier.MK1, state -> 7);

	public static final BlockRegistryObject<Relay, PEBlockItem> RELAY_MK2 = registerRelay("relay_mk2", EnumRelayTier.MK2, state -> 11);

	public static final BlockRegistryObject<Relay, PEBlockItem> RELAY_MK3 = registerRelay("relay_mk3", EnumRelayTier.MK3, state -> 15);

	private static BlockRegistryObject<Block, BlockItem> registerFuelBlock(String name, MapColor mapColor) {
		return BLOCKS.register(name, () -> new Block(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASEDRUM)
				.requiresCorrectToolForDrops().strength(0.5F, 1.5F)));
	}

	private static BlockRegistryObject<Collector, PEBlockItem> registerCollector(String name, EnumCollectorTier collectorTier, ToIntFunction<BlockState> lightLevel) {
		return BLOCKS.register(name, () -> new Collector(collectorTier, BlockBehaviour.Properties.of().mapColor(MapColor.SAND)
				.instrument(NoteBlockInstrument.PLING).sound(SoundType.GLASS).requiresCorrectToolForDrops().strength(0.3F, 0.9F)
				.lightLevel(lightLevel)), block -> new PEBlockItem(block, new Item.Properties(), (stack, tooltip, flags) -> block.addTooltip(stack, tooltip)));
	}

	private static BlockRegistryObject<EntropySink, PEBlockItem> registerEntropySink(EnumEntropySinkTier tier, ToIntFunction<BlockState> lightLevel) {
		return BLOCKS.register(tier.getSerializedName(), () -> new EntropySink(tier, BlockBehaviour.Properties.of().mapColor(MapColor.SAND)
				.instrument(NoteBlockInstrument.PLING).sound(SoundType.GLASS).requiresCorrectToolForDrops().strength(0.3F, 0.9F)
				.lightLevel(lightLevel)), block -> new PEBlockItem(block, new Item.Properties(), (stack, tooltip, flags) -> block.addTooltip(stack, tooltip)));
	}

	private static <CONDENSER extends Condenser> BlockRegistryObject<CONDENSER, BlockItem> registerCondenser(String name,
			Function<BlockBehaviour.Properties, CONDENSER> condenserFunction, Function<CONDENSER, BlockItem> itemCreator) {
		return BLOCKS.register(name, () -> condenserFunction.apply(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM)
				.requiresCorrectToolForDrops().strength(10, 3_600_000)), itemCreator);
	}

	private static BlockRegistryObject<Relay, PEBlockItem> registerRelay(String name, EnumRelayTier relayTier, ToIntFunction<BlockState> lightLevel) {
		return BLOCKS.register(name, () -> new Relay(relayTier, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK)
				.instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()
				.strength(10, 30).lightLevel(lightLevel)), block -> new PEBlockItem(block, new Item.Properties(), (stack, tooltip, flags) -> block.addTooltip(stack, tooltip)));
	}

	private static BlockRegistryObject<EquivalentLegacyTNT, BlockItem> registerExplosive(String name, TNTEntityCreator tntEntityCreator) {
		return BLOCKS.register(name, () -> new EquivalentLegacyTNT(BlockBehaviour.Properties.of().mapColor(MapColor.FIRE).strength(0).instabreak()
				.sound(SoundType.GRASS).ignitedByLava().isRedstoneConductor((state, getter, pos) -> false), tntEntityCreator));
	}

	private static BlockRegistryObject<MatterFurnace, BlockItem> registerFurnace(String name, EnumMatterType matterType, float hardness, float resistance) {
		return BLOCKS.register(name, () -> new MatterFurnace(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(hardness, resistance)
				.mapColor(matterType.getMapColor()).instrument(NoteBlockInstrument.BASEDRUM).lightLevel(state -> 14), matterType),
				block -> new BlockItem(block, new Item.Properties().fireResistant()));
	}

	private static BlockRegistryObject<MatterBlock, BlockItem> registerMatterBlock(String name, EnumMatterType matterType, float hardness, float resistance) {
		return BLOCKS.register(name, () -> new MatterBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(hardness, resistance)
				.mapColor(matterType.getMapColor()).instrument(NoteBlockInstrument.BASEDRUM).lightLevel(state -> 14), matterType),
				block -> new BlockItem(block, new Item.Properties().fireResistant()));
	}
}
