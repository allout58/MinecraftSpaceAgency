package allout58.mods.MSA.client.render;

import org.lwjgl.opengl.GL11;

import allout58.mods.MSA.Blocks.Logic.ComSatelliteLogic;
import allout58.mods.MSA.client.models.ModelComSatellite;
import allout58.mods.MSA.constants.MSATextures;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

@SideOnly(Side.CLIENT)
public class ComSatelliteLogicRender extends TileEntitySpecialRenderer
{
    private ModelComSatellite modelComSatellite = new ModelComSatellite();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick)
    {

        if (tileEntity instanceof ComSatelliteLogic)
        {
            boolean bypass=false;
            double dx=0,dy=0,dz=0;
            ComSatelliteLogic logic = (ComSatelliteLogic) tileEntity;

            if(!logic.isControlBlock)
            {   
                dx=logic.getMasterPos()[0]-x;
                dy=logic.getMasterPos()[1]-y;
                dz=logic.getMasterPos()[2]-z;
                bypass=true;
                System.out.println("Rendering non-control block");
            }
            
            if ((logic.isControlBlock && logic.isValidStructure)||bypass)
            {
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);

                // Scale, Translate, Rotate
                GL11.glScalef(1.0F, 1.0F, 1.0F);
                GL11.glTranslatef((float) (x + dx + 0.5F), (float)( y + dy - 1.005F), (float)( z + dz + 0.5F));

                // Bind texture
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(MSATextures.MODEL_COM_SATELLITE);

                // Render
                modelComSatellite.render();

                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
            }
        }
    }
}
