package net.minecraft.server.network;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

public record FilteredText<T>(T raw, @Nullable T f_215169_) {
   public static final FilteredText<String> f_215167_ = m_215181_("");

   public static <T> FilteredText<T> m_215181_(T p_215182_) {
      return new FilteredText<>(p_215182_, p_215182_);
   }

   public static <T> FilteredText<T> m_215186_(T p_215187_) {
      return new FilteredText<>(p_215187_, (T)null);
   }

   public <U> FilteredText<U> m_215183_(Function<T, U> p_215184_) {
      return new FilteredText<>(p_215184_.apply(this.raw), Util.mapNullable(this.f_215169_, p_215184_));
   }

   public boolean isFiltered() {
      return !this.raw.equals(this.f_215169_);
   }

   public boolean m_215185_() {
      return this.f_215169_ == null;
   }

   public T m_215189_(T p_215190_) {
      return (T)(this.f_215169_ != null ? this.f_215169_ : p_215190_);
   }

   @Nullable
   public T m_215175_(ServerPlayer p_215176_, ServerPlayer p_215177_) {
      return (T)(p_215176_.shouldFilterMessageTo(p_215177_) ? this.f_215169_ : this.raw);
   }

   @Nullable
   public T m_215178_(CommandSourceStack p_215179_, ServerPlayer p_215180_) {
      ServerPlayer serverplayer = p_215179_.getPlayer();
      return (T)(serverplayer != null ? this.m_215175_(serverplayer, p_215180_) : this.raw);
   }
}