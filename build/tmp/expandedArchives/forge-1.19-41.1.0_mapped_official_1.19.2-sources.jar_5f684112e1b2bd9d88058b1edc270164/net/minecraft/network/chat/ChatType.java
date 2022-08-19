package net.minecraft.network.chat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

public record ChatType(Optional<ChatType.TextDisplay> chat, Optional<ChatType.TextDisplay> f_237012_, Optional<ChatType.Narration> narration) {
   public static final Codec<ChatType> CODEC = RecordCodecBuilder.create((p_237020_) -> {
      return p_237020_.group(ChatType.TextDisplay.f_237076_.optionalFieldOf("chat").forGetter(ChatType::chat), ChatType.TextDisplay.f_237076_.optionalFieldOf("overlay").forGetter(ChatType::f_237012_), ChatType.Narration.f_237031_.optionalFieldOf("narration").forGetter(ChatType::narration)).apply(p_237020_, ChatType::new);
   });
   public static final ResourceKey<ChatType> CHAT = create("chat");
   public static final ResourceKey<ChatType> f_130599_ = create("system");
   public static final ResourceKey<ChatType> f_130600_ = create("game_info");
   public static final ResourceKey<ChatType> SAY_COMMAND = create("say_command");
   public static final ResourceKey<ChatType> f_237007_ = create("msg_command");
   public static final ResourceKey<ChatType> f_237008_ = create("team_msg_command");
   public static final ResourceKey<ChatType> EMOTE_COMMAND = create("emote_command");
   public static final ResourceKey<ChatType> f_237010_ = create("tellraw_command");

   private static ResourceKey<ChatType> create(String p_237024_) {
      return ResourceKey.create(Registry.CHAT_TYPE_REGISTRY, new ResourceLocation(p_237024_));
   }

   public static Holder<ChatType> bootstrap(Registry<ChatType> p_237022_) {
      BuiltinRegistries.register(p_237022_, CHAT, new ChatType(Optional.of(ChatType.TextDisplay.m_237084_(ChatDecoration.m_236896_("chat.type.text"))), Optional.empty(), Optional.of(ChatType.Narration.m_237041_(ChatDecoration.m_236896_("chat.type.text.narrate"), ChatType.Narration.Priority.CHAT))));
      BuiltinRegistries.register(p_237022_, f_130599_, new ChatType(Optional.of(ChatType.TextDisplay.m_237081_()), Optional.empty(), Optional.of(ChatType.Narration.m_237044_(ChatType.Narration.Priority.SYSTEM))));
      BuiltinRegistries.register(p_237022_, f_130600_, new ChatType(Optional.empty(), Optional.of(ChatType.TextDisplay.m_237081_()), Optional.empty()));
      BuiltinRegistries.register(p_237022_, SAY_COMMAND, new ChatType(Optional.of(ChatType.TextDisplay.m_237084_(ChatDecoration.m_236896_("chat.type.announcement"))), Optional.empty(), Optional.of(ChatType.Narration.m_237041_(ChatDecoration.m_236896_("chat.type.text.narrate"), ChatType.Narration.Priority.CHAT))));
      BuiltinRegistries.register(p_237022_, f_237007_, new ChatType(Optional.of(ChatType.TextDisplay.m_237084_(ChatDecoration.m_236902_("commands.message.display.incoming"))), Optional.empty(), Optional.of(ChatType.Narration.m_237041_(ChatDecoration.m_236896_("chat.type.text.narrate"), ChatType.Narration.Priority.CHAT))));
      BuiltinRegistries.register(p_237022_, f_237008_, new ChatType(Optional.of(ChatType.TextDisplay.m_237084_(ChatDecoration.m_236908_("chat.type.team.text"))), Optional.empty(), Optional.of(ChatType.Narration.m_237041_(ChatDecoration.m_236896_("chat.type.text.narrate"), ChatType.Narration.Priority.CHAT))));
      BuiltinRegistries.register(p_237022_, EMOTE_COMMAND, new ChatType(Optional.of(ChatType.TextDisplay.m_237084_(ChatDecoration.m_236896_("chat.type.emote"))), Optional.empty(), Optional.of(ChatType.Narration.m_237041_(ChatDecoration.m_236896_("chat.type.emote"), ChatType.Narration.Priority.CHAT))));
      return BuiltinRegistries.register(p_237022_, f_237010_, new ChatType(Optional.of(ChatType.TextDisplay.m_237081_()), Optional.empty(), Optional.of(ChatType.Narration.m_237044_(ChatType.Narration.Priority.CHAT))));
   }

   public static record Narration(Optional<ChatDecoration> f_237032_, ChatType.Narration.Priority f_237033_) {
      public static final Codec<ChatType.Narration> f_237031_ = RecordCodecBuilder.create((p_237040_) -> {
         return p_237040_.group(ChatDecoration.f_236884_.optionalFieldOf("decoration").forGetter(ChatType.Narration::f_237032_), ChatType.Narration.Priority.f_237060_.fieldOf("priority").forGetter(ChatType.Narration::f_237033_)).apply(p_237040_, ChatType.Narration::new);
      });

      public static ChatType.Narration m_237044_(ChatType.Narration.Priority p_237045_) {
         return new ChatType.Narration(Optional.empty(), p_237045_);
      }

      public static ChatType.Narration m_237041_(ChatDecoration p_237042_, ChatType.Narration.Priority p_237043_) {
         return new ChatType.Narration(Optional.of(p_237042_), p_237043_);
      }

      public Component m_237046_(Component p_237047_, @Nullable ChatSender p_237048_) {
         return this.f_237032_.map((p_237052_) -> {
            return p_237052_.m_236898_(p_237047_, p_237048_);
         }).orElse(p_237047_);
      }

      public static enum Priority implements StringRepresentable {
         CHAT("chat", false),
         SYSTEM("system", true);

         public static final Codec<ChatType.Narration.Priority> f_237060_ = StringRepresentable.fromEnum(ChatType.Narration.Priority::values);
         private final String f_237061_;
         private final boolean f_237062_;

         private Priority(String p_237068_, boolean p_237069_) {
            this.f_237061_ = p_237068_;
            this.f_237062_ = p_237069_;
         }

         public boolean m_237070_() {
            return this.f_237062_;
         }

         public String getSerializedName() {
            return this.f_237061_;
         }
      }
   }

   public static record TextDisplay(Optional<ChatDecoration> f_237077_) {
      public static final Codec<ChatType.TextDisplay> f_237076_ = RecordCodecBuilder.create((p_237083_) -> {
         return p_237083_.group(ChatDecoration.f_236884_.optionalFieldOf("decoration").forGetter(ChatType.TextDisplay::f_237077_)).apply(p_237083_, ChatType.TextDisplay::new);
      });

      public static ChatType.TextDisplay m_237081_() {
         return new ChatType.TextDisplay(Optional.empty());
      }

      public static ChatType.TextDisplay m_237084_(ChatDecoration p_237085_) {
         return new ChatType.TextDisplay(Optional.of(p_237085_));
      }

      public Component m_237086_(Component p_237087_, @Nullable ChatSender p_237088_) {
         return this.f_237077_.map((p_237092_) -> {
            return p_237092_.m_236898_(p_237087_, p_237088_);
         }).orElse(p_237087_);
      }
   }
}