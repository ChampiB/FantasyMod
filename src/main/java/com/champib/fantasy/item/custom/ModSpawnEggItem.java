package com.champib.fantasy.item.custom;
import com.champib.fantasy.entity.custom.PirateKingEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.core.BlockSource;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModSpawnEggItem extends ForgeSpawnEggItem {

    protected  static final List<ModSpawnEggItem> TO_ADD_EGGS = new ArrayList<>();
    private final Lazy<? extends EntityType<?>> entityTypeSupplier;

    public ModSpawnEggItem(
            final RegistryObject<? extends EntityType<?>> entityTypeSupplier,
            int primaryColorIn, int secondaryColorIn, Properties builder
    ) {
        super(null, primaryColorIn, secondaryColorIn, builder);
        this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
        TO_ADD_EGGS.add(this);
    }

    public static void initSpawnEggs() {
        final Map<EntityType<?>, SpawnEggItem> EGGS = ObfuscationReflectionHelper.getPrivateValue(
                ForgeSpawnEggItem.class, null, "TYPE_MAP"
        );
        DefaultDispenseItemBehavior dispenseItemBehavior = new DefaultDispenseItemBehavior() {
            @Override
            protected @NotNull ItemStack execute(BlockSource source, ItemStack stack) {
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);

                EntityType<?> type = ((ForgeSpawnEggItem) stack.getItem()).getType(stack.getTag());
                type.spawn(
                        source.getLevel(), stack, null, source.getPos(), MobSpawnType.DISPENSER,
                        direction != Direction.UP, false
                );
                return stack;
            }
        };

        for (final SpawnEggItem spawnEgg : TO_ADD_EGGS) {
            EGGS.put(spawnEgg.getType(null), spawnEgg);
            DispenserBlock.registerBehavior(spawnEgg, dispenseItemBehavior);
        }

        TO_ADD_EGGS.clear();
    }

}
