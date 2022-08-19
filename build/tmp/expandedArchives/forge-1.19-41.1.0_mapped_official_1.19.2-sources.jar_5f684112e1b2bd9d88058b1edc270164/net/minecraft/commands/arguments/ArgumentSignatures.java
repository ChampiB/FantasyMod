package net.minecraft.commands.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Crypt;

public record ArgumentSignatures(long f_231044_, Map<String, byte[]> f_231045_) {
   private static final int MAX_ARGUMENT_COUNT = 8;
   private static final int MAX_ARGUMENT_NAME_LENGTH = 16;

   public ArgumentSignatures(FriendlyByteBuf p_231052_) {
      this(p_231052_.readLong(), p_231052_.readMap(FriendlyByteBuf.limitValue(HashMap::new, 8), (p_231068_) -> {
         return p_231068_.readUtf(16);
      }, FriendlyByteBuf::readByteArray));
   }

   public static ArgumentSignatures m_231053_() {
      return new ArgumentSignatures(0L, Map.of());
   }

   @Nullable
   public Crypt.SaltSignaturePair m_231059_(String p_231060_) {
      byte[] abyte = this.f_231045_.get(p_231060_);
      return abyte != null ? new Crypt.SaltSignaturePair(this.f_231044_, abyte) : null;
   }

   public void write(FriendlyByteBuf p_231062_) {
      p_231062_.writeLong(this.f_231044_);
      p_231062_.writeMap(this.f_231045_, (p_231064_, p_231065_) -> {
         p_231064_.writeUtf(p_231065_, 16);
      }, FriendlyByteBuf::writeByteArray);
   }

   public static Map<String, Component> m_231054_(CommandContextBuilder<?> p_231055_) {
      CommandContextBuilder<?> commandcontextbuilder = p_231055_.getLastChild();
      Map<String, Component> map = new Object2ObjectArrayMap<>();

      for(ParsedCommandNode<?> parsedcommandnode : commandcontextbuilder.getNodes()) {
         CommandNode $$6 = parsedcommandnode.getNode();
         if ($$6 instanceof ArgumentCommandNode<?, ?> argumentcommandnode) {
            ArgumentType argumenttype = argumentcommandnode.getType();
            if (argumenttype instanceof SignedArgument<?> signedargument) {
               ParsedArgument<?, ?> parsedargument = commandcontextbuilder.getArguments().get(argumentcommandnode.getName());
               if (parsedargument != null) {
                  map.put(argumentcommandnode.getName(), m_231056_(signedargument, parsedargument));
               }
            }
         }
      }

      return map;
   }

   private static <T> Component m_231056_(SignedArgument<T> p_231057_, ParsedArgument<?, ?> p_231058_) {
      return p_231057_.getSignableText((T)p_231058_.getResult());
   }
}