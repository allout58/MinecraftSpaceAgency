package allout58.mods.SpaceCraft.Rockets.Entity;

import org.lwjgl.opengl.GL11;

import allout58.mods.SpaceCraft.Rockets.Model.ModelRocket;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderRocket extends Render
{
    private ModelBase model;
    private ModelRenderer renderer;
    public static final ResourceLocation TEXTURE = new ResourceLocation("spacecraft", "textures/entities/fuselage.png");
//    public static final ResourceLocation TEXTURE = new ResourceLocation("buildcraft", "textures/entities" + "/robot.png");

    public RenderRocket()
    {
         model = new ModelRocket();
//        model = new ModelBase()
//        {
//        };

        renderer = new ModelRenderer(model, 0, 0);
//        renderer.addBox(-4F, -4F, -4F, 8, 8, 8);
//        renderer.rotationPointX = 0;
//        renderer.rotationPointY = 0;
//        renderer.rotationPointZ = 0;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float f, float f1)
    {
        doRender((EntityRocket) entity, x, y, z, f, f1);
    }

    private void doRender(EntityRocket rocket, double x, double y, double z, float f, float f1)
    {

        GL11.glPushMatrix();
        GL11.glDisable(2896 /* GL_LIGHTING */);
        GL11.glTranslated(x, y, z);

        renderManager.renderEngine.func_110577_a(TEXTURE);

        float factor = (float) (1.0 / 16.0);
//        if (rocket.rocketLogic != null)
//        {
//            switch (rocket.rocketLogic.Size)
//            {
//                case Large:
                    factor = (float) (1.0 / 4.0);
//                    break;
//                case Medium:
//                    factor = (float) (1.0 / 8.0);
//                    break;
//                case Small:
//                    factor = (float) (1.0 / 16.0);
//                    break;
//                default:
//                    break;
//
//            }

            renderer.render(factor);
//        }
//        else
//        {
//            System.out.println("Rocket Logic error");
//            new Exception("Rocket Logic Error").printStackTrace();
//        }

        GL11.glEnable(2896 /* GL_LIGHTING */);
        GL11.glPopMatrix();

    }

    @Override
    protected ResourceLocation func_110775_a(Entity entity)
    {
        // TODO Auto-generated method stub
        return TEXTURE;
    }

}
