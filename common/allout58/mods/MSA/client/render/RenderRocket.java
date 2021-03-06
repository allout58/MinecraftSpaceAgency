package allout58.mods.MSA.client.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import allout58.mods.MSA.client.models.ModelRocket;
import allout58.mods.MSA.constants.MSATextures;
import allout58.mods.MSA.rockets.entity.EntityRocket;
import cpw.mods.fml.client.FMLClientHandler;

public class RenderRocket extends Render
{
    private ModelRocket model;

    public RenderRocket()
    {
        model = new ModelRocket();
        // model = new ModelBase()
        // {
        // };

        // renderer = new ModelRenderer(model, 0, 0);
        // renderer.addBox(-4F, -4F, -4F, 8, 8, 8);
        // renderer.rotationPointX = 0;
        // renderer.rotationPointY = 0;
        // renderer.rotationPointZ = 0;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float f, float f1)
    {
        doRender((EntityRocket) entity, x, y, z, f, f1);
    }

    private void doRender(EntityRocket rocket, double x, double y, double z, float f, float f1)
    {

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(x, y, z);

        // Scale, Translate, Rotate
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.0F, (float) z + 1.2F);
        // GL11.glRotatef(45F, 0F, 1F, 0F);
        // GL11.glRotatef(-90F, 1F, 0F, 0F);

        // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(MSATextures.MODEL_ROCKET);

        model.render();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();

    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return MSATextures.MODEL_ROCKET;
    }

}
