package com.champib.fantasy.entity.render;

import com.champib.fantasy.FantasyMod;
import com.champib.fantasy.entity.custom.PirateKingEntity;
import com.champib.fantasy.entity.model.PirateKingModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PirateKingRenderer extends MobRenderer<PirateKingEntity, PirateKingModel<PirateKingEntity>> {

    public static ModelLayerLocation PIRATE_KING = new ModelLayerLocation(new ResourceLocation(FantasyMod.MOD_ID, "pirate_king"), "pirate_king");

    protected static final ResourceLocation TEXTURE =
            new ResourceLocation(FantasyMod.MOD_ID, "textures/entity/pirate_king.png");

    public PirateKingRenderer(EntityRendererProvider.Context context) {
        super(context, new PirateKingModel(context.bakeLayer(PirateKingRenderer.PIRATE_KING)), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(PirateKingEntity entity) {
        return TEXTURE;
    }

}
