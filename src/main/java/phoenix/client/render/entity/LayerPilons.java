package phoenix.client.render.entity;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import phoenix.Phoenix;
import phoenix.client.models.entity.ModelMTH;
import phoenix.client.models.entity.ModelPilons;
import phoenix.init.ItemRegister;

@SideOnly(Side.CLIENT)
public class LayerPilons implements LayerRenderer<EntityPlayer>
{
    /** The basic Elytra texture. */
    private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation(Phoenix.MOD_ID,"textures/entity/pilons_2.png");
    /** Instance of the player renderer. */
    protected final RenderLivingBase<?> renderPlayer;
    /** The model used by the Elytra. */
    private ModelMTH modelPilons = new ModelMTH();

    public LayerPilons(RenderLivingBase<?> render)
    {
        this.renderPlayer = render;
    }

    @Override
    public void doRenderLayer(EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        modelPilons = new ModelMTH();
        ItemStack itemstack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

        if (itemstack.getItem() == ItemRegister.GODS_CHESTPLATE)
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            /*todo prme skins
            if (player instanceof AbstractClientPlayer)
            {
                AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)player;

                if (abstractclientplayer.isPlayerInfoSet() && abstractclientplayer.getLocationElytra() != null)
                {
                    this.renderPlayer.bindTexture(abstractclientplayer.getLocationElytra());
                }
                else
                {
                    this.renderPlayer.bindTexture(TEXTURE_ELYTRA);
                }
            }
            else
            {*/
                this.renderPlayer.bindTexture(TEXTURE_ELYTRA);
            //}

            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 0.125F);
            this.modelPilons.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, player);
            this.modelPilons.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

            if (itemstack.isItemEnchanted())
            {
                LayerArmorBase.renderEnchantedGlint(this.renderPlayer, player, this.modelPilons, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }
}