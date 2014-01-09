package allout58.mods.MSA.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import allout58.mods.MSA.blocks.logic.ComSatelliteLogic;
import allout58.mods.MSA.client.models.ModelComSatellite;
import allout58.mods.MSA.client.models.ModelRocket;
import allout58.mods.MSA.constants.MSATextures;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ComSatelliteLogicRender extends TileEntitySpecialRenderer
{
    private ModelComSatellite modelComSatellite = new ModelComSatellite();
    private ModelRocket model = new ModelRocket();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick)
    {
        if (tileEntity instanceof ComSatelliteLogic)
        {
            boolean bypass = false;
            double dx = 0, dy = 0, dz = 0;
            ComSatelliteLogic logic = (ComSatelliteLogic) tileEntity;

            if (!logic.isControlBlock)
            {
//                dx =  - x;
//                dy =  - y;
//                dz =  - z;
                //bypass = true;
                // GL11.glPushMatrix();
                // GL11.glDisable(GL11.GL_LIGHTING);
                // GL11.glScalef(1.0F, 1.0F, 1.0F);
                // GL11.glTranslatef((float) (x), (float) (y), (float) (z));
                // FMLClientHandler.instance().getClient().renderEngine.bindTexture(MSATextures.MODEL_ROCKET);
                // model.render();
                // GL11.glEnable(GL11.GL_LIGHTING);
                // GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);

                // Scale, Translate, Rotate
                GL11.glScalef(1.0F, 1.0F, 1.0F);
                GL11.glTranslatef((float) (logic.getMasterPos()[0] + 0.5F), (float) (logic.getMasterPos()[1] - 1.005F), (float) (logic.getMasterPos()[2] + 0.5F));

                // Bind texture
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(MSATextures.MODEL_COM_SATELLITE_1);

                // Render
                modelComSatellite.renderPart("Cube_Base");

                GL11.glRotatef(logic.satDishRotZ, 0F, 1F, 0F);

                modelComSatellite.renderPart("Base_DishBase");
                // GL11.glRotatef(logic.satDishRotY, 0F, 0F, 1F);
                // GL11.glTranslatef(logic.satDishPosX, logic.satDishPosZ, 0F);
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(MSATextures.MODEL_COM_SATELLITE_2);
                modelComSatellite.renderPart("Dish");

                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
            }

            if ((logic.isControlBlock && logic.isValidStructure))// || bypass)
            {
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);

                // Scale, Translate, Rotate
                GL11.glScalef(1.0F, 1.0F, 1.0F);
                GL11.glTranslatef((float) (x + dx + 0.5F), (float) (y + dy - 1.005F), (float) (z + dz + 0.5F));

                // Bind texture
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(MSATextures.MODEL_COM_SATELLITE_1);

                // Render
                modelComSatellite.renderPart("Cube_Base");

                GL11.glRotatef(logic.satDishRotZ, 0F, 1F, 0F);

                modelComSatellite.renderPart("Base_DishBase");
                // GL11.glRotatef(logic.satDishRotY, 0F, 0F, 1F);
                // GL11.glTranslatef(logic.satDishPosX, logic.satDishPosZ, 0F);
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(MSATextures.MODEL_COM_SATELLITE_2);
                modelComSatellite.renderPart("Dish");

                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
            }
        }
    }
}
