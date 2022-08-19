package net.minecraft.client.gui.chat;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OverlayChatListener implements ChatListener {
   private final Minecraft f_93350_;

   public OverlayChatListener(Minecraft p_93352_) {
      this.f_93350_ = p_93352_;
   }

   public void m_213924_(ChatType p_232454_, Component p_232455_, @Nullable ChatSender p_232456_) {
      p_232454_.f_237012_().ifPresent((p_232460_) -> {
         Component component = p_232460_.m_237086_(p_232455_, p_232456_);
         this.f_93350_.gui.setOverlayMessage(component, false);
      });
   }
}