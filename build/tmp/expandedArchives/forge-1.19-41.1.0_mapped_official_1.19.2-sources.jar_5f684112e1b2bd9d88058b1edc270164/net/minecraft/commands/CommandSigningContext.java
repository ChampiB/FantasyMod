package net.minecraft.commands;

import java.time.Instant;
import java.util.UUID;
import net.minecraft.commands.arguments.ArgumentSignatures;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.util.Crypt;

public interface CommandSigningContext {
   CommandSigningContext f_230578_ = (p_230582_) -> {
      return MessageSignature.m_237143_();
   };

   MessageSignature m_214186_(String p_230583_);

   default boolean getArgument(String p_230580_) {
      return false;
   }

   public static record SignedArguments(UUID f_230584_, Instant f_230585_, ArgumentSignatures f_230586_, boolean f_230587_) implements CommandSigningContext {
      public MessageSignature m_214186_(String p_230602_) {
         Crypt.SaltSignaturePair crypt$saltsignaturepair = this.f_230586_.m_231059_(p_230602_);
         return crypt$saltsignaturepair != null ? new MessageSignature(this.f_230584_, this.f_230585_, crypt$saltsignaturepair) : MessageSignature.m_237143_();
      }

      public boolean getArgument(String p_230595_) {
         return this.f_230587_;
      }
   }
}