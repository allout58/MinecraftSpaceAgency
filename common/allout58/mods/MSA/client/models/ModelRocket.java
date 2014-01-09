package allout58.mods.MSA.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import allout58.mods.MSA.constants.MSAModels;

public class ModelRocket extends ModelBase
{
    private IModelCustom fuselage;
    private IModelCustom[] externalRockets = new IModelCustom[4];
    private IModelCustom[] engines = new IModelCustom[5];

    public ModelRocket()
    {
        fuselage = AdvancedModelLoader.loadModel(MSAModels.ROCKET);

    }

    public void render()
    {
        fuselage.renderAll();
    }

    public void renderPart(String partName)
    {
        fuselage.renderPart(partName);
    }

}
