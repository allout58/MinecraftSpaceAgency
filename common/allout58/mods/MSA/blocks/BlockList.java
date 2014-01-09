package allout58.mods.MSA.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import allout58.mods.MSA.MSAConfig;
import allout58.mods.MSA.items.ItemList;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockList
{
    /*
     * To Do list for making a new block:
     * 
     * 1)Block class (duh!) 2)Add blockID int to config 3)Set blockID int in
     * config 4)Create new Block var below 5)Initialize that block
     * 6)GameRegistry.registerBlock 7)Add unlocalized name to lang file(s) 8)If
     * a TileEntity, register it in MSA.java Optional steps:
     * 9)MinecraftForge.setBlockHarvestLevel 10)Register recipe
     */
    public static Block oreStarSteel;
    public static Block storageStarSteel;
    public static Block launchTower;
    public static Block launchTowerController;
    public static Block rocketAssembler;
    public static Block comSatellite;
    public static Block ssBuilding;
    public static Block commandCenter;

    public static void init()
    {
        /* Initialize block objects */
        // ore blocks
        oreStarSteel = new BlockOreStarSteel(MSAConfig.ores, Material.rock);
        storageStarSteel = new BlockStorageStarSteel(MSAConfig.storageBlock, Material.iron);
        // tech blocks
        launchTower = new BlockLaunchTower(MSAConfig.launchTower, Material.iron);
        launchTowerController = new BlockLaunchControl(MSAConfig.launchController, Material.iron);
        rocketAssembler = new BlockRocketAssembler(MSAConfig.rocketAssembler, Material.iron);
        comSatellite = new BlockComSatellite(MSAConfig.comSatellite, Material.iron);
        ssBuilding = new BlockSSBuilding(MSAConfig.ssBuilding, Material.rock);
        commandCenter = new BlockCommandCenter(MSAConfig.commandCenter, Material.iron);

        /* Register Blocks */
        // ore blocks
        GameRegistry.registerBlock(oreStarSteel, "oreStarSteel");
        GameRegistry.registerBlock(storageStarSteel, "storageStarSteel");
        // tech blocks
        GameRegistry.registerBlock(launchTower, "launchTower");
        GameRegistry.registerBlock(launchTowerController, "launchTowerController");
        GameRegistry.registerBlock(rocketAssembler, "rocketAssembler");
        GameRegistry.registerBlock(comSatellite, "comSatellite");
        GameRegistry.registerBlock(ssBuilding, "ssBuilding");
        GameRegistry.registerBlock(commandCenter, "commandCenter");

        /* Set block harvest level */
        // ore blocks
        MinecraftForge.setBlockHarvestLevel(oreStarSteel, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(storageStarSteel, "pickaxe", 2);
        // building blocks
        MinecraftForge.setBlockHarvestLevel(ssBuilding, "pickaxe", 2);
        // tech blocks
        MinecraftForge.setBlockHarvestLevel(launchTower, "pickaxe", 1);
    }

    public static void addRecipies()
    {
        /* Block recipes */
        GameRegistry.addShapedRecipe(new ItemStack(storageStarSteel), "III", "III", "III", 'I', new ItemStack(ItemList.ingotStarSteel));
        GameRegistry.addShapedRecipe(new ItemStack(ssBuilding, 7), "SSS", "ISI", "SSS", 'S', new ItemStack(Block.stoneBrick), 'I', new ItemStack(ItemList.ingotStarSteel));
    }
}
