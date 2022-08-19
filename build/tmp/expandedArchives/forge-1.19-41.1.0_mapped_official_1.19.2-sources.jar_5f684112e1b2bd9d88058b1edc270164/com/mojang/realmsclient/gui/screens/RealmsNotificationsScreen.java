package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RealmsDataFetcher;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsNotificationsScreen extends RealmsScreen {
   private static final ResourceLocation INVITE_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/invite_icon.png");
   private static final ResourceLocation TRIAL_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/trial_icon.png");
   private static final ResourceLocation NEWS_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/news_notification_mainscreen.png");
   private static final RealmsDataFetcher f_88824_ = new RealmsDataFetcher(Minecraft.getInstance(), RealmsClient.create());
   private volatile int numberOfPendingInvites;
   static boolean checkedMcoAvailability;
   private static boolean trialAvailable;
   static boolean validClient;
   private static boolean hasUnreadNews;

   public RealmsNotificationsScreen() {
      super(NarratorChatListener.NO_TITLE);
   }

   public void init() {
      this.checkIfMcoEnabled();
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
   }

   public void tick() {
      if ((!this.getRealmsNotificationsEnabled() || !this.inTitleScreen() || !validClient) && !f_88824_.m_87817_()) {
         f_88824_.m_87856_();
      } else if (validClient && this.getRealmsNotificationsEnabled()) {
         f_88824_.m_87847_();
         if (f_88824_.m_87820_(RealmsDataFetcher.Task.PENDING_INVITE)) {
            this.numberOfPendingInvites = f_88824_.m_87851_();
         }

         if (f_88824_.m_87820_(RealmsDataFetcher.Task.TRIAL_AVAILABLE)) {
            trialAvailable = f_88824_.m_87852_();
         }

         if (f_88824_.m_87820_(RealmsDataFetcher.Task.UNREAD_NEWS)) {
            hasUnreadNews = f_88824_.m_87854_();
         }

         f_88824_.m_87848_();
      }
   }

   private boolean getRealmsNotificationsEnabled() {
      return this.minecraft.options.realmsNotifications().get();
   }

   private boolean inTitleScreen() {
      return this.minecraft.screen instanceof TitleScreen;
   }

   private void checkIfMcoEnabled() {
      if (!checkedMcoAvailability) {
         checkedMcoAvailability = true;
         (new Thread("Realms Notification Availability checker #1") {
            public void run() {
               RealmsClient realmsclient = RealmsClient.create();

               try {
                  RealmsClient.CompatibleVersionResponse realmsclient$compatibleversionresponse = realmsclient.clientCompatible();
                  if (realmsclient$compatibleversionresponse != RealmsClient.CompatibleVersionResponse.COMPATIBLE) {
                     return;
                  }
               } catch (RealmsServiceException realmsserviceexception) {
                  if (realmsserviceexception.httpResultCode != 401) {
                     RealmsNotificationsScreen.checkedMcoAvailability = false;
                  }

                  return;
               }

               RealmsNotificationsScreen.validClient = true;
            }
         }).start();
      }

   }

   public void render(PoseStack p_88837_, int p_88838_, int p_88839_, float p_88840_) {
      if (validClient) {
         this.drawIcons(p_88837_, p_88838_, p_88839_);
      }

      super.render(p_88837_, p_88838_, p_88839_, p_88840_);
   }

   private void drawIcons(PoseStack p_88833_, int p_88834_, int p_88835_) {
      int i = this.numberOfPendingInvites;
      int j = 24;
      int k = this.height / 4 + 48;
      int l = this.width / 2 + 80;
      int i1 = k + 48 + 2;
      int j1 = 0;
      if (hasUnreadNews) {
         RenderSystem.setShaderTexture(0, NEWS_ICON_LOCATION);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         p_88833_.pushPose();
         p_88833_.scale(0.4F, 0.4F, 0.4F);
         GuiComponent.blit(p_88833_, (int)((double)(l + 2 - j1) * 2.5D), (int)((double)i1 * 2.5D), 0.0F, 0.0F, 40, 40, 40, 40);
         p_88833_.popPose();
         j1 += 14;
      }

      if (i != 0) {
         RenderSystem.setShaderTexture(0, INVITE_ICON_LOCATION);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         GuiComponent.blit(p_88833_, l - j1, i1 - 6, 0.0F, 0.0F, 15, 25, 31, 25);
         j1 += 16;
      }

      if (trialAvailable) {
         RenderSystem.setShaderTexture(0, TRIAL_ICON_LOCATION);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         int k1 = 0;
         if ((Util.getMillis() / 800L & 1L) == 1L) {
            k1 = 8;
         }

         GuiComponent.blit(p_88833_, l + 4 - j1, i1 + 4, 0.0F, (float)k1, 8, 8, 8, 16);
      }

   }

   public void removed() {
      f_88824_.m_87856_();
   }
}