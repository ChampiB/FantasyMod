package net.minecraft.network.chat;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;

public record ChatSender(UUID f_236980_, Component f_236981_, @Nullable Component f_236982_) {
   public ChatSender(UUID p_236984_, Component p_236985_) {
      this(p_236984_, p_236985_, (Component)null);
   }

   public ChatSender(FriendlyByteBuf p_236991_) {
      this(p_236991_.readUUID(), p_236991_.readComponent(), p_236991_.readNullable(FriendlyByteBuf::readComponent));
   }

   public static ChatSender m_236995_(Component p_236996_) {
      return new ChatSender(Util.NIL_UUID, p_236996_);
   }

   public void m_236993_(FriendlyByteBuf p_236994_) {
      p_236994_.writeUUID(this.f_236980_);
      p_236994_.writeComponent(this.f_236981_);
      p_236994_.writeNullable(this.f_236982_, FriendlyByteBuf::writeComponent);
   }

   public ChatSender m_236998_(Component p_236999_) {
      return new ChatSender(this.f_236980_, this.f_236981_, p_236999_);
   }
}