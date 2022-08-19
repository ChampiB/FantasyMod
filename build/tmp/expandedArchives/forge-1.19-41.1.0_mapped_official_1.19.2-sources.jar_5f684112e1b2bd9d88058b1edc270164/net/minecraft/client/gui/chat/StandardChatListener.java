package net.minecraft.client.gui.chat;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StandardChatListener implements ChatListener {
   private final Minecraft f_93357_;

   public StandardChatListener(Minecraft p_93359_) {
      this.f_93357_ = p_93359_;
   }

   public void m_213924_(ChatType p_232462_, Component p_232463_, @Nullable ChatSender p_232464_) {
      p_232462_.chat().ifPresent((p_232468_) -> {
         Component component = p_232468_.m_237086_(p_232463_, p_232464_);
         if (p_232464_ == null) {
            this.f_93357_.gui.getChat().addMessage(component);
         } else {
            this.f_93357_.gui.getChat().m_93808_(component);
         }

      });
   }
}