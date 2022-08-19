package net.minecraft.network.chat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.util.StringRepresentable;

public record ChatDecoration(String f_236885_, List<ChatDecoration.Parameter> f_236886_, Style f_236887_) {
   public static final Codec<ChatDecoration> f_236884_ = RecordCodecBuilder.create((p_236895_) -> {
      return p_236895_.group(Codec.STRING.fieldOf("translation_key").forGetter(ChatDecoration::f_236885_), ChatDecoration.Parameter.f_236917_.listOf().fieldOf("parameters").forGetter(ChatDecoration::f_236886_), Style.FORMATTING_CODEC.fieldOf("style").forGetter(ChatDecoration::f_236887_)).apply(p_236895_, ChatDecoration::new);
   });

   public static ChatDecoration m_236896_(String p_236897_) {
      return new ChatDecoration(p_236897_, List.of(ChatDecoration.Parameter.SENDER, ChatDecoration.Parameter.CONTENT), Style.EMPTY);
   }

   public static ChatDecoration m_236902_(String p_236903_) {
      Style style = Style.EMPTY.withColor(ChatFormatting.GRAY).withItalic(true);
      return new ChatDecoration(p_236903_, List.of(ChatDecoration.Parameter.SENDER, ChatDecoration.Parameter.CONTENT), style);
   }

   public static ChatDecoration m_236908_(String p_236909_) {
      return new ChatDecoration(p_236909_, List.of(ChatDecoration.Parameter.TEAM_NAME, ChatDecoration.Parameter.SENDER, ChatDecoration.Parameter.CONTENT), Style.EMPTY);
   }

   public Component m_236898_(Component p_236899_, @Nullable ChatSender p_236900_) {
      Object[] aobject = this.m_236904_(p_236899_, p_236900_);
      return Component.translatable(this.f_236885_, aobject).withStyle(this.f_236887_);
   }

   private Component[] m_236904_(Component p_236905_, @Nullable ChatSender p_236906_) {
      Component[] acomponent = new Component[this.f_236886_.size()];

      for(int i = 0; i < acomponent.length; ++i) {
         ChatDecoration.Parameter chatdecoration$parameter = this.f_236886_.get(i);
         acomponent[i] = chatdecoration$parameter.m_236928_(p_236905_, p_236906_);
      }

      return acomponent;
   }

   public static enum Parameter implements StringRepresentable {
      SENDER("sender", (p_236939_, p_236940_) -> {
         return p_236940_ != null ? p_236940_.f_236981_() : null;
      }),
      TEAM_NAME("team_name", (p_236936_, p_236937_) -> {
         return p_236937_ != null ? p_236937_.f_236982_() : null;
      }),
      CONTENT("content", (p_236932_, p_236933_) -> {
         return p_236932_;
      });

      public static final Codec<ChatDecoration.Parameter> f_236917_ = StringRepresentable.fromEnum(ChatDecoration.Parameter::values);
      private final String f_236918_;
      private final ChatDecoration.Parameter.Selector f_236919_;

      private Parameter(String p_236925_, ChatDecoration.Parameter.Selector p_236926_) {
         this.f_236918_ = p_236925_;
         this.f_236919_ = p_236926_;
      }

      public Component m_236928_(Component p_236929_, @Nullable ChatSender p_236930_) {
         Component component = this.f_236919_.m_236944_(p_236929_, p_236930_);
         return Objects.requireNonNullElse(component, CommonComponents.EMPTY);
      }

      public String getSerializedName() {
         return this.f_236918_;
      }

      public interface Selector {
         @Nullable
         Component m_236944_(Component p_236945_, @Nullable ChatSender p_236946_);
      }
   }
}