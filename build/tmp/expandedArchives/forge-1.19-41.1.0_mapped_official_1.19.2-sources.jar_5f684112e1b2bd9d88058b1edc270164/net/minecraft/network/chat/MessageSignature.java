package net.minecraft.network.chat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.time.Instant;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.util.Crypt;
import net.minecraft.util.SignatureUpdater;
import net.minecraft.util.SignatureValidator;

public record MessageSignature(UUID f_237136_, Instant f_237137_, Crypt.SaltSignaturePair f_237138_) {
   public static MessageSignature m_237143_() {
      return new MessageSignature(Util.NIL_UUID, Instant.now(), Crypt.SaltSignaturePair.EMPTY);
   }

   public boolean m_237153_(SignatureValidator p_237154_, Component p_237155_) {
      return this.m_237161_() ? p_237154_.validate((p_237160_) -> {
         m_237144_(p_237160_, p_237155_, this.f_237136_, this.f_237137_, this.f_237138_.salt());
      }, this.f_237138_.signature()) : false;
   }

   public boolean m_237150_(SignatureValidator p_237151_, String p_237152_) throws SignatureException {
      return this.m_237153_(p_237151_, Component.literal(p_237152_));
   }

   public static void m_237144_(SignatureUpdater.Output p_237145_, Component p_237146_, UUID p_237147_, Instant p_237148_, long p_237149_) throws SignatureException {
      byte[] abyte = new byte[32];
      ByteBuffer bytebuffer = ByteBuffer.wrap(abyte).order(ByteOrder.BIG_ENDIAN);
      bytebuffer.putLong(p_237149_);
      bytebuffer.putLong(p_237147_.getMostSignificantBits()).putLong(p_237147_.getLeastSignificantBits());
      bytebuffer.putLong(p_237148_.getEpochSecond());
      p_237145_.update(abyte);
      p_237145_.update(m_237156_(p_237146_));
   }

   private static byte[] m_237156_(Component p_237157_) {
      String s = Component.Serializer.toStableJson(p_237157_);
      return s.getBytes(StandardCharsets.UTF_8);
   }

   public boolean m_237161_() {
      return this.f_237136_ != Util.NIL_UUID && this.f_237138_.isValid();
   }

   public boolean m_238433_(UUID p_238434_) {
      return this.m_237161_() && p_238434_.equals(this.f_237136_);
   }
}