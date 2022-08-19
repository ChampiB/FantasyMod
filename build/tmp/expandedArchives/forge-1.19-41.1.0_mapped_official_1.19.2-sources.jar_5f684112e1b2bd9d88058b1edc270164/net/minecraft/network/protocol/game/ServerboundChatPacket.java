package net.minecraft.network.protocol.game;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Crypt;
import net.minecraft.util.StringUtil;

public class ServerboundChatPacket implements Packet<ServerGamePacketListener> {
   public static final Duration f_237949_ = Duration.ofMinutes(5L);
   private final String message;
   private final Instant timeStamp;
   private final Crypt.SaltSignaturePair f_237951_;
   private final boolean signedPreview;

   public ServerboundChatPacket(String p_237955_, MessageSignature p_237956_, boolean p_237957_) {
      this.message = StringUtil.trimChatMessage(p_237955_);
      this.timeStamp = p_237956_.f_237137_();
      this.f_237951_ = p_237956_.f_237138_();
      this.signedPreview = p_237957_;
   }

   public ServerboundChatPacket(FriendlyByteBuf p_179545_) {
      this.message = p_179545_.readUtf(256);
      this.timeStamp = p_179545_.readInstant();
      this.f_237951_ = new Crypt.SaltSignaturePair(p_179545_);
      this.signedPreview = p_179545_.readBoolean();
   }

   public void write(FriendlyByteBuf p_133839_) {
      p_133839_.writeUtf(this.message, 256);
      p_133839_.writeInstant(this.timeStamp);
      Crypt.SaltSignaturePair.write(p_133839_, this.f_237951_);
      p_133839_.writeBoolean(this.signedPreview);
   }

   public void handle(ServerGamePacketListener p_133836_) {
      p_133836_.handleChat(this);
   }

   public String m_133837_() {
      return this.message;
   }

   public MessageSignature m_237958_(UUID p_237959_) {
      return new MessageSignature(p_237959_, this.timeStamp, this.f_237951_);
   }

   public Instant m_237960_() {
      return this.timeStamp;
   }

   public boolean m_237961_() {
      return this.signedPreview;
   }
}