package allout58.mods.MSA.client.models;

import allout58.mods.MSA.constants.MSAModels;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

@SideOnly(Side.CLIENT)
public class ModelComSatellite
{
    private IModelCustom modelBase;
    
    public ModelComSatellite()
    {
        modelBase=AdvancedModelLoader.loadModel(MSAModels.COM_SATELLITE);
    }
    
    public void render()
    {
        modelBase.renderAll();
    }
    public void renderPart(String partName)
    {
        modelBase.renderPart(partName);
    }
}
