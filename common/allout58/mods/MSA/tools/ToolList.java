package allout58.mods.MSA.tools;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import allout58.mods.MSA.MSAConfig;
import allout58.mods.MSA.items.ItemList;
import cpw.mods.fml.common.registry.GameRegistry;

public class ToolList
{
    public static Item toolWrench;

    // public static Item toolDebug;

    public static void init()
    {
        /* Initialize tool objects */
        toolWrench = new ToolWrench(MSAConfig.wrench);

        /* Register tool items */
        GameRegistry.registerItem(toolWrench, "toolWrench");
    }

    public static void addRecipies()
    {
        /* Tool recipes */
        GameRegistry.addShapedRecipe(new ItemStack(toolWrench), "S S", " I ", " S ", 'S', new ItemStack(ItemList.ingotStarSteel), 'I', new ItemStack(Item.ingotIron));

    }
}
