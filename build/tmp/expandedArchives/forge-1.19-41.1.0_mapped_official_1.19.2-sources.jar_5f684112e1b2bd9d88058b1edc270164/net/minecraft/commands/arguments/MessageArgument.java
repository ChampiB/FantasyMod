package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSigningContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.network.chat.ChatDecorator;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.FilteredText;
import org.slf4j.Logger;

public class MessageArgument implements SignedArgument<MessageArgument.Message> {
   private static final Collection<String> EXAMPLES = Arrays.asList("Hello world!", "foo", "@e", "Hello @p :)");
   static final Logger LOGGER = LogUtils.getLogger();

   public static MessageArgument message() {
      return new MessageArgument();
   }

   public static Component getMessage(CommandContext<CommandSourceStack> p_96836_, String p_96837_) throws CommandSyntaxException {
      MessageArgument.Message messageargument$message = p_96836_.getArgument(p_96837_, MessageArgument.Message.class);
      return messageargument$message.resolveComponent(p_96836_.getSource());
   }

   public static MessageArgument.ChatMessage getChatMessage(CommandContext<CommandSourceStack> p_232164_, String p_232165_) throws CommandSyntaxException {
      MessageArgument.Message messageargument$message = p_232164_.getArgument(p_232165_, MessageArgument.Message.class);
      Component component = messageargument$message.resolveComponent(p_232164_.getSource());
      CommandSigningContext commandsigningcontext = p_232164_.getSource().getSigningContext();
      MessageSignature messagesignature = commandsigningcontext.m_214186_(p_232165_);
      boolean flag = commandsigningcontext.getArgument(p_232165_);
      ChatSender chatsender = p_232164_.getSource().asChatSender();
      return messagesignature.m_238433_(chatsender.f_236980_()) ? new MessageArgument.ChatMessage(messageargument$message.text, component, messagesignature, flag) : new MessageArgument.ChatMessage(messageargument$message.text, component, MessageSignature.m_237143_(), false);
   }

   public MessageArgument.Message parse(StringReader p_96834_) throws CommandSyntaxException {
      return MessageArgument.Message.parseText(p_96834_, true);
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   public Component getSignableText(MessageArgument.Message p_232159_) {
      return Component.literal(p_232159_.getText());
   }

   public CompletableFuture<Component> resolvePreview(CommandSourceStack p_232147_, MessageArgument.Message p_232148_) throws CommandSyntaxException {
      return p_232148_.resolveDecoratedComponent(p_232147_);
   }

   public Class<MessageArgument.Message> getValueType() {
      return MessageArgument.Message.class;
   }

   static void logResolutionFailure(CommandSourceStack p_232156_, CompletableFuture<?> p_232157_) {
      p_232157_.exceptionally((p_232154_) -> {
         LOGGER.error("Encountered unexpected exception while resolving chat message argument from '{}'", p_232156_.getDisplayName().getString(), p_232154_);
         return null;
      });
   }

   public static record ChatMessage(String f_232166_, Component f_238180_, MessageSignature f_232167_, boolean f_232168_) {
      public CompletableFuture<FilteredText<PlayerChatMessage>> m_232174_(CommandSourceStack p_232175_) {
         CompletableFuture<FilteredText<PlayerChatMessage>> completablefuture = this.m_232179_(p_232175_, this.f_238180_).thenComposeAsync((p_232189_) -> {
            ChatDecorator chatdecorator = p_232175_.getServer().getChatDecorator();
            return chatdecorator.m_236964_(p_232175_.getPlayer(), p_232189_, this.f_232167_, this.f_232168_);
         }, p_232175_.getServer()).thenApply((p_238193_) -> {
            PlayerChatMessage playerchatmessage = this.m_238376_(p_238193_);
            if (playerchatmessage != null) {
               this.m_238404_(p_232175_, playerchatmessage);
            }

            return p_238193_;
         });
         MessageArgument.logResolutionFailure(p_232175_, completablefuture);
         return completablefuture;
      }

      @Nullable
      private PlayerChatMessage m_238376_(FilteredText<PlayerChatMessage> p_238377_) {
         if (this.f_232167_.m_237161_()) {
            return this.f_232168_ ? p_238377_.raw() : PlayerChatMessage.m_237232_(this.f_232166_, this.f_232167_);
         } else {
            return null;
         }
      }

      private void m_238404_(CommandSourceStack p_238405_, PlayerChatMessage p_238406_) {
         if (!p_238406_.m_237230_(p_238405_)) {
            MessageArgument.LOGGER.warn("{} sent message with invalid signature: '{}'", p_238405_.getDisplayName().getString(), p_238406_.f_237213_().getString());
         }

      }

      private CompletableFuture<FilteredText<Component>> m_232179_(CommandSourceStack p_232180_, Component p_232181_) {
         ServerPlayer serverplayer = p_232180_.getPlayer();
         return serverplayer != null ? serverplayer.getTextFilter().m_215265_(p_232181_) : CompletableFuture.completedFuture(FilteredText.m_215181_(p_232181_));
      }
   }

   public static class Message {
      final String text;
      private final MessageArgument.Part[] parts;

      public Message(String p_96844_, MessageArgument.Part[] p_96845_) {
         this.text = p_96844_;
         this.parts = p_96845_;
      }

      public String getText() {
         return this.text;
      }

      public MessageArgument.Part[] getParts() {
         return this.parts;
      }

      CompletableFuture<Component> resolveDecoratedComponent(CommandSourceStack p_232195_) throws CommandSyntaxException {
         Component component = this.resolveComponent(p_232195_);
         CompletableFuture<Component> completablefuture = p_232195_.getServer().getChatDecorator().decorate(p_232195_.getPlayer(), component);
         MessageArgument.logResolutionFailure(p_232195_, completablefuture);
         return completablefuture;
      }

      Component resolveComponent(CommandSourceStack p_232197_) throws CommandSyntaxException {
         return this.toComponent(p_232197_, p_232197_.hasPermission(2));
      }

      public Component toComponent(CommandSourceStack p_96850_, boolean p_96851_) throws CommandSyntaxException {
         if (this.parts.length != 0 && p_96851_) {
            MutableComponent mutablecomponent = Component.literal(this.text.substring(0, this.parts[0].getStart()));
            int i = this.parts[0].getStart();

            for(MessageArgument.Part messageargument$part : this.parts) {
               Component component = messageargument$part.toComponent(p_96850_);
               if (i < messageargument$part.getStart()) {
                  mutablecomponent.append(this.text.substring(i, messageargument$part.getStart()));
               }

               if (component != null) {
                  mutablecomponent.append(component);
               }

               i = messageargument$part.getEnd();
            }

            if (i < this.text.length()) {
               mutablecomponent.append(this.text.substring(i));
            }

            return mutablecomponent;
         } else {
            return Component.literal(this.text);
         }
      }

      public static MessageArgument.Message parseText(StringReader p_96847_, boolean p_96848_) throws CommandSyntaxException {
         String s = p_96847_.getString().substring(p_96847_.getCursor(), p_96847_.getTotalLength());
         if (!p_96848_) {
            p_96847_.setCursor(p_96847_.getTotalLength());
            return new MessageArgument.Message(s, new MessageArgument.Part[0]);
         } else {
            List<MessageArgument.Part> list = Lists.newArrayList();
            int i = p_96847_.getCursor();

            while(true) {
               int j;
               EntitySelector entityselector;
               while(true) {
                  if (!p_96847_.canRead()) {
                     return new MessageArgument.Message(s, list.toArray(new MessageArgument.Part[0]));
                  }

                  if (p_96847_.peek() == '@') {
                     j = p_96847_.getCursor();

                     try {
                        EntitySelectorParser entityselectorparser = new EntitySelectorParser(p_96847_);
                        entityselector = entityselectorparser.parse();
                        break;
                     } catch (CommandSyntaxException commandsyntaxexception) {
                        if (commandsyntaxexception.getType() != EntitySelectorParser.ERROR_MISSING_SELECTOR_TYPE && commandsyntaxexception.getType() != EntitySelectorParser.ERROR_UNKNOWN_SELECTOR_TYPE) {
                           throw commandsyntaxexception;
                        }

                        p_96847_.setCursor(j + 1);
                     }
                  } else {
                     p_96847_.skip();
                  }
               }

               list.add(new MessageArgument.Part(j - i, p_96847_.getCursor() - i, entityselector));
            }
         }
      }
   }

   public static class Part {
      private final int start;
      private final int end;
      private final EntitySelector selector;

      public Part(int p_96856_, int p_96857_, EntitySelector p_96858_) {
         this.start = p_96856_;
         this.end = p_96857_;
         this.selector = p_96858_;
      }

      public int getStart() {
         return this.start;
      }

      public int getEnd() {
         return this.end;
      }

      public EntitySelector getSelector() {
         return this.selector;
      }

      @Nullable
      public Component toComponent(CommandSourceStack p_96861_) throws CommandSyntaxException {
         return EntitySelector.joinNames(this.selector.findEntities(p_96861_));
      }
   }
}