package allout58.mods.MSA.client;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import allout58.mods.MSA.CommonProxy;
import allout58.mods.MSA.blocks.logic.ComSatelliteLogic;
import allout58.mods.MSA.client.render.ComSatelliteLogicRender;
import allout58.mods.MSA.client.render.RenderRocket;
import allout58.mods.MSA.rockets.entity.EntityRocket;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ClientProxy extends CommonProxy
{

    @Override
    public void registerRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderRocket());

        ClientRegistry.bindTileEntitySpecialRenderer(ComSatelliteLogic.class, new ComSatelliteLogicRender());
    }

    /* LOCALIZATION */
    @Override
    public String getCurrentLanguage()
    {
        return Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
    }

    @Override
    public void addName(Object obj, String s)
    {
        LanguageRegistry.addName(obj, s);
    }

    @Override
    public void addLocalization(String s1, String string)
    {
        LanguageRegistry.instance().addStringLocalization(s1, string);
    }

    @Override
    public String getItemDisplayName(ItemStack stack)
    {
        if (Item.itemsList[stack.itemID] == null) return "";

        return Item.itemsList[stack.itemID].getItemDisplayName(stack);
    }

    @Override
    public int getCurrentParticleSetting()
    {
        return Minecraft.getMinecraft().gameSettings.particleSetting;
    }

}
