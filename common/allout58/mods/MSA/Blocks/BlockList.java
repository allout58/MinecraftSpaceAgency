package allout58.mods.MSA.Blocks;

import allout58.mods.MSA.MSAConfig;
import allout58.mods.MSA.items.ItemList;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class BlockList
{
    public static Block oreStarSteel;
    public static Block storageStarSteel;
    public static Block launchTower;
    public static Block launchTowerController;
    public static Block rocketAssembler;
    public static Block comSatellite;

    public static void init()
    {
        /* Initialize block objects */
        // ore blocks
        oreStarSteel = new BlockOreStarSteel(MSAConfig.ores, Material.rock);
        storageStarSteel = new BlockStorageStarSteel(MSAConfig.storageBlock, Material.iron);
        // tech blocks
        launchTower = new BlockLaunchTower(MSAConfig.launchTower, Material.iron);
        launchTowerController = new BlockLaunchControl(MSAConfig.launchController, Material.iron);
        rocketAssembler= new BlockRocketAssembler(MSAConfig.rocketAssembler, Material.iron);
        comSatellite = new BlockComSatellite(MSAConfig.comSatellite,Material.iron);

        /* Register Blocks */
        // ore blocks
        GameRegistry.registerBlock(oreStarSteel, "oreStarSteel");
        GameRegistry.registerBlock(storageStarSteel, "storageStarSteel");
        // tech blocks
        GameRegistry.registerBlock(launchTower, "launchTower");
        GameRegistry.registerBlock(launchTowerController, "launchTowerController");
        GameRegistry.registerBlock(rocketAssembler,"rocketAssembler");
        GameRegistry.registerBlock(comSatellite,"comSatellite");

        /* Set block harvest level */
        // ore blocks
        MinecraftForge.setBlockHarvestLevel(oreStarSteel, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(storageStarSteel, "pickaxe", 2);
        // tech blocks
        MinecraftForge.setBlockHarvestLevel(launchTower, "pickaxe", 1);
    }
    
    public static void addRecipies()
    {
        /* Block recipes */
        GameRegistry.addShapedRecipe(new ItemStack(storageStarSteel), "III", "III", "III", 'I', new ItemStack(ItemList.ingotStarSteel));
    }
}
