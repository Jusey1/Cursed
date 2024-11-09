package net.salju.curse.item.component;

import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import io.netty.buffer.ByteBuf;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record CursedItem(boolean curse) {
	public static final CursedItem EMPTY = new CursedItem(false);
	public static final Codec<CursedItem> CODEC = RecordCodecBuilder.create(codec -> codec.group(Codec.BOOL.fieldOf("curse").forGetter(CursedItem::curse)).apply(codec, CursedItem::new));
	public static final StreamCodec<ByteBuf, CursedItem> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.BOOL, CursedItem::curse, CursedItem::new);

	public CursedItem(boolean curse) {
		this.curse = curse;
	}

	public boolean getCursedStatus() {
		return this.curse;
	}
}