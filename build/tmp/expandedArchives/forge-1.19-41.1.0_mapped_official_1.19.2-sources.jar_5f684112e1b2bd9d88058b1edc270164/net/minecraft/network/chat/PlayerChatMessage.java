package net.minecraft.network.chat;

import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.FilteredText;
import net.minecraft.world.entity.player.ProfilePublicKey;

public record PlayerChatMessage(Component f_237213_, MessageSignature f_237214_, Optional<Component> unsignedContent) {
   public static PlayerChatMessage m_237242_(Component p_237243_, MessageSignature p_237244_) {
      return new PlayerChatMessage(p_237243_, p_237244_, Optional.empty());
   }

   public static PlayerChatMessage m_237232_(String p_237233_, MessageSignature p_237234_) {
      return m_237242_(Component.literal(p_237233_), p_237234_);
   }

   public static PlayerChatMessage m_237237_(Component p_237238_, Component p_237239_, MessageSignature p_237240_, boolean p_237241_) {
      if (p_237238_.equals(p_237239_)) {
         return m_237242_(p_237238_, p_237240_);
      } else {
         return !p_237241_ ? m_237242_(p_237238_, p_237240_).m_237246_(p_237239_) : m_237242_(p_237239_, p_237240_);
      }
   }

   // Forge: Bouncer for the version with player context
   public static FilteredText<PlayerChatMessage> m_237223_(FilteredText<Component> p_237224_, FilteredText<Component> p_237225_, MessageSignature p_237226_, boolean p_237227_) {
      return filteredSigned(p_237224_, p_237225_, p_237226_, p_237227_, null);
   }

   public static FilteredText<PlayerChatMessage> filteredSigned(FilteredText<Component> p_237224_, FilteredText<Component> p_237225_, MessageSignature p_237226_, boolean p_237227_, @org.jetbrains.annotations.Nullable ServerPlayer player) {
      Component component = p_237224_.raw();
      Component component1 = p_237225_.raw();
      Component forgeComponent = net.minecraftforge.common.ForgeHooks.onServerChatEvent(player, p_237224_.raw().getString(), component);
      // TODO: this overrides the signed ("normal") component from the player, but ignores the unsigned component. What do we do here? This is a mess. -C
      PlayerChatMessage playerchatmessage = m_237237_(forgeComponent, component1, p_237226_, p_237227_);
      if (p_237225_.isFiltered()) {
         PlayerChatMessage playerchatmessage1 = Util.mapNullable(p_237225_.f_215169_(), PlayerChatMessage::m_237235_);
         return new FilteredText<>(playerchatmessage, playerchatmessage1);
      } else {
         return FilteredText.m_215181_(playerchatmessage);
      }
   }

   public static PlayerChatMessage m_237235_(Component p_237236_) {
      return new PlayerChatMessage(p_237236_, MessageSignature.m_237143_(), Optional.empty());
   }

   public PlayerChatMessage m_237246_(Component p_237247_) {
      return new PlayerChatMessage(this.f_237213_, this.f_237214_, Optional.of(p_237247_));
   }

   public boolean verify(ProfilePublicKey p_237229_) {
      return this.f_237214_.m_237153_(p_237229_.createSignatureValidator(), this.f_237213_);
   }

   public boolean m_237221_(ServerPlayer p_237222_) {
      ProfilePublicKey profilepublickey = p_237222_.getProfilePublicKey();
      return profilepublickey == null || this.verify(profilepublickey);
   }

   public boolean m_237230_(CommandSourceStack p_237231_) {
      ServerPlayer serverplayer = p_237231_.getPlayer();
      return serverplayer == null || this.m_237221_(serverplayer);
   }

   public Component serverContent() {
      return this.unsignedContent.orElse(this.f_237213_);
   }
}
