package net.minecraft.network.protocol.game;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Crypt;

public record ClientboundPlayerChatPacket(Component f_237732_, Optional<Component> f_237733_, int f_237734_, ChatSender f_237735_, Instant f_237736_, Crypt.SaltSignaturePair f_237737_) implements Packet<ClientGamePacketListener> {
   private static final Duration f_237738_ = ServerboundChatPacket.f_237949_.plus(Duration.ofMinutes(2L));

   public ClientboundPlayerChatPacket(FriendlyByteBuf p_237741_) {
      this(p_237741_.readComponent(), p_237741_.readOptional(FriendlyByteBuf::readComponent), p_237741_.readVarInt(), new ChatSender(p_237741_), p_237741_.readInstant(), new Crypt.SaltSignaturePair(p_237741_));
   }

   public void write(FriendlyByteBuf p_237755_) {
      p_237755_.writeComponent(this.f_237732_);
      p_237755_.writeOptional(this.f_237733_, FriendlyByteBuf::writeComponent);
      p_237755_.writeVarInt(this.f_237734_);
      this.f_237735_.m_236993_(p_237755_);
      p_237755_.writeInstant(this.f_237736_);
      Crypt.SaltSignaturePair.write(p_237755_, this.f_237737_);
   }

   public void handle(ClientGamePacketListener p_237759_) {
      p_237759_.handlePlayerChat(this);
   }

   public boolean isSkippable() {
      return true;
   }

   public PlayerChatMessage m_237760_() {
      MessageSignature messagesignature = new MessageSignature(this.f_237735_.f_236980_(), this.f_237736_, this.f_237737_);
      return new PlayerChatMessage(this.f_237732_, messagesignature, this.f_237733_);
   }

   private Instant m_237770_() {
      return this.f_237736_.plus(f_237738_);
   }

   public boolean m_237752_(Instant p_237753_) {
      return p_237753_.isAfter(this.m_237770_());
   }

   public ChatType m_237750_(Registry<ChatType> p_237751_) {
      return Objects.requireNonNull(p_237751_.byId(this.f_237734_), "Invalid chat type");
   }
}