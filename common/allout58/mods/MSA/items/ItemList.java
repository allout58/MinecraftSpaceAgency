package allout58.mods.MSA.items;

import cpw.mods.fml.common.registry.GameRegistry;
import allout58.mods.MSA.MSAConfig;
import allout58.mods.MSA.Blocks.BlockList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ItemList
{
    public static Item ingotStarSteel;

    public static void init()
    {
        /* Initialize item objects */
        ingotStarSteel = new ItemIngotStarSteel(MSAConfig.ingots);

        /* Register items */
        GameRegistry.registerItem(ingotStarSteel, "ingotStarSteel");

    }
    
    public static void addRecipies()
    {
        /* Item recipes */
        FurnaceRecipes.smelting().addSmelting(BlockList.oreStarSteel.blockID, new ItemStack(ingotStarSteel), 0.85F); 
    }
}
