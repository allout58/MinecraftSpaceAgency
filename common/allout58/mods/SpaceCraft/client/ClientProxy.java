package allout58.mods.SpaceCraft.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import allout58.mods.SpaceCraft.CommonProxy;
import allout58.mods.SpaceCraft.Rockets.Entity.EntityRocket;
import allout58.mods.SpaceCraft.client.render.RenderRocket;

public class ClientProxy extends CommonProxy
{

    @Override
    public void registerRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderRocket());
    }

    /* LOCALIZATION */
    @Override
    public String getCurrentLanguage()
    {
        return Minecraft.getMinecraft().func_135016_M().func_135041_c().func_135034_a();
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

}
