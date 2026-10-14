package com.skd.equivalentlegacy.network.packets.to_client;

import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import com.skd.equivalentlegacy.ELCore;
import com.skd.equivalentlegacy.api.ItemInfo;
import com.skd.equivalentlegacy.emc.EMCMappingHandler;
import com.skd.equivalentlegacy.network.packets.IPEPacket;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.connection.ConnectionType;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record SyncEmcPKT(Object2LongMap<ItemInfo> data) implements IPEPacket {

	public static final CustomPacketPayload.Type<SyncEmcPKT> TYPE = new CustomPacketPayload.Type<>(ELCore.rl("sync_emc"));
	private static final StreamCodec<RegistryFriendlyByteBuf, Object2LongMap<ItemInfo>> MAP_STREAM_CODEC = ByteBufCodecs.map(Object2LongOpenHashMap::new,
			ItemInfo.STREAM_CODEC,
			ByteBufCodecs.VAR_LONG
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, SyncEmcPKT> STREAM_CODEC = MAP_STREAM_CODEC.map(SyncEmcPKT::new, SyncEmcPKT::data);

	@NotNull
	@Override
	public CustomPacketPayload.Type<SyncEmcPKT> type() {
		return TYPE;
	}

	@Override
	public void handle(IPayloadContext context) {
		ELCore.debugLog("Receiving EMC data from server.");
		EMCMappingHandler.updateEmcValues(data);
	}

	public static SyncEmcPKT serializeEmcData(RegistryAccess registryAccess) {
		SyncEmcPKT data = EMCMappingHandler.createPacketData();
		//Simulate encoding the EMC packet to get an accurate size
		RegistryFriendlyByteBuf buf = new RegistryFriendlyByteBuf(Unpooled.buffer(), registryAccess, ConnectionType.NEOFORGE);
		try {
			int index = buf.writerIndex();
			SyncEmcPKT.STREAM_CODEC.encode(buf, data);
			ELCore.debugLog("EMC data size: {} bytes", buf.writerIndex() - index);
		} finally {
			buf.release();
		}
		return data;
	}
}