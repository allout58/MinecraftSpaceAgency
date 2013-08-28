package allout58.mods.SpaceCraft.Rockets.Model;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelRocket extends ModelBase
{
    private IModelCustom fuselage;
    private IModelCustom[] externalRockets=new IModelCustom[4];
    private IModelCustom[] engines=new IModelCustom[5];
    
    public ModelRocket()
    {
        fuselage=AdvancedModelLoader.loadModel("/assets/spacecraft/models/Fuselage_broke.obj");
        
    }
    
    private void render()
    {
        this.fuselage.renderAll();
    }
    
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
            // Push a blank matrix onto the stack
            GL11.glPushMatrix();
         
            // Move the object into the correct position on the block (because the OBJ's origin is the center of the object)
            //GL11.glTranslatef((float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f);
         
            // Scale our object to about half-size in all directions (the OBJ file is a little large)
            //GL11.glScalef(0.5f, 0.5f, 0.5f);
         
            // Bind the texture, so that OpenGL properly textures our block.
            FMLClientHandler.instance().getClient().renderEngine.func_110577_a(new ResourceLocation("SpaceCraft:assets/spacecraft/objects/Fuselage.png"));
         
            // Render the object, using modelTutBox.renderAll();
            this.render();
         
            // Pop this matrix from the stack.
            GL11.glPopMatrix();
    }

}
