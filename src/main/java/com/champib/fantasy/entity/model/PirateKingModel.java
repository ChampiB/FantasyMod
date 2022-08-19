package com.champib.fantasy.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class PirateKingModel <T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "fantasy:pirate_king"), "main");
	private final ModelPart h_head;
	private final ModelPart sword;
	private final ModelPart body;

	public PirateKingModel(ModelPart root) {
		this.h_head = root.getChild("h_head");
		this.sword = root.getChild("sword");
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition h_head = partdefinition.addOrReplaceChild("h_head", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -12.0F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(14, 0).addBox(-1.75F, -12.5F, 0.749F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(18, 11).addBox(-1.0F, -13.0F, 0.75F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition jaw = h_head.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition jaw_r1 = jaw.addOrReplaceChild("jaw_r1", CubeListBuilder.create().texOffs(1, 0).addBox(-1.0F, -0.1F, 0.0F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, 1.8F, -0.3491F, 0.0F, 0.0F));

		PartDefinition sword = partdefinition.addOrReplaceChild("sword", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0F, 21.0F, 0.0F, -0.7418F, 0.0F, 0.3054F));

		PartDefinition blade_top_r1 = sword.addOrReplaceChild("blade_top_r1", CubeListBuilder.create().texOffs(4, 5).addBox(-8.4021F, -8.5F, -3.6112F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(10, 20).addBox(-8.4021F, -8.0F, -3.8612F, 0.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 19).addBox(-8.6521F, -3.4992F, -4.3612F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(20, 2).addBox(-8.4021F, -3.0F, -3.6112F, 0.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 2.0F, 0.0F, -0.829F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 19).mirror().addBox(-0.5F, -9.4F, 0.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(8, 0).addBox(-2.0F, -8.5F, -1.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(12, 6).addBox(-1.5F, -6.5F, -1.25F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(2, 17).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(14, 14).addBox(-3.0F, -9.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(2, 16).mirror().addBox(1.0F, -9.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 23.0F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition hand_r1 = left_arm.addOrReplaceChild("hand_r1", CubeListBuilder.create().texOffs(16, 16).addBox(-5.0F, -2.0F, -0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition left_upper_hand_r1 = left_arm.addOrReplaceChild("left_upper_hand_r1", CubeListBuilder.create().texOffs(0, 19).addBox(0.8F, -1.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition hand_r2 = right_arm.addOrReplaceChild("hand_r2", CubeListBuilder.create().texOffs(16, 2).addBox(-5.9021F, -4.8612F, -0.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition left_upper_hand_r2 = right_arm.addOrReplaceChild("left_upper_hand_r2", CubeListBuilder.create().texOffs(4, 10).addBox(-0.1021F, -3.8612F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -5.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		h_head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		sword.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}