package net.minecraft.network.chat;

import java.security.SignatureException;
import java.time.Instant;
import java.util.UUID;
import net.minecraft.util.Crypt;
import net.minecraft.util.Signer;

public record MessageSigner(UUID f_237169_, Instant timeStamp, long salt) {
   public static MessageSigner create(UUID p_237184_) {
      return new MessageSigner(p_237184_, Instant.now(), Crypt.SaltSupplier.getLong());
   }

   public MessageSignature m_237180_(Signer p_237181_, Component p_237182_) {
      byte[] abyte = p_237181_.sign((p_237187_) -> {
         MessageSignature.m_237144_(p_237187_, p_237182_, this.f_237169_, this.timeStamp, this.salt);
      });
      return new MessageSignature(this.f_237169_, this.timeStamp, new Crypt.SaltSignaturePair(this.salt, abyte));
   }

   public MessageSignature m_237177_(Signer p_237178_, String p_237179_) throws SignatureException {
      return this.m_237180_(p_237178_, Component.literal(p_237179_));
   }
}