package allout58.mods.SpaceCraft.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import allout58.mods.SpaceCraft.SpaceCraftConfig;
import allout58.mods.SpaceCraft.Blocks.BlockList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ItemList
{
    public static Item ingotStarSteel;

    public static void init()
    {
        /* Initialize item objects */
        ingotStarSteel = new ItemIngotStarSteel(SpaceCraftConfig.ingots);

        /* Register items */
        GameRegistry.registerItem(ingotStarSteel, "ingotStarSteel");

        /* Item recipes */
        FurnaceRecipes.smelting().addSmelting(BlockList.oreStarSteel.blockID, new ItemStack(ingotStarSteel), 0.85F);
    }
}
