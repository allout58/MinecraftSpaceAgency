package allout58.mods.MSA.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import allout58.mods.MSA.MSAConfig;
import allout58.mods.MSA.blocks.BlockList;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemList
{
    //@formatter:off
    /*
     * To Do list for making a new item:
     * 
     * 1)Item class (duh!)
     * 2)Add itemID int to config
     * 3)Set itemID int in config
     * 4)Create new Item var below
     * 5)Initialize that item
     * 6)GameRegistry.registerItem
     * 7)Add unlocalized name to lang file(s)
     * Optional steps:
     * 8)Register recipe
     */
    //@formatter:on
    public static Item ingotStarSteel;
    public static Item linkCard;
    public static Item rocketFinalized;

    public static void init()
    {
        /* Initialize item objects */
        ingotStarSteel = new ItemIngotStarSteel(MSAConfig.ingots);
        linkCard = new ItemLinkCard(MSAConfig.linkCard);
        rocketFinalized = new ItemRocketFinal(MSAConfig.rocket);

        /* Register items */
        GameRegistry.registerItem(ingotStarSteel, "ingotStarSteel");
        GameRegistry.registerItem(linkCard, "linkCard");
        GameRegistry.registerItem(rocketFinalized, "rocketFinalized");
    }

    public static void addRecipies()
    {
        /* Item recipes */
        FurnaceRecipes.smelting().addSmelting(BlockList.oreStarSteel.blockID, new ItemStack(ingotStarSteel), 0.85F);
    }
}
