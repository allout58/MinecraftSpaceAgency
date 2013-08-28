package allout58.mods.SpaceCraft;

import allout58.mods.SpaceCraft.Blocks.*;
import allout58.mods.SpaceCraft.Blocks.Logic.*;
import allout58.mods.SpaceCraft.Items.*;
import allout58.mods.SpaceCraft.Rockets.Entity.EntityRocket;
import allout58.mods.SpaceCraft.Tools.*;
import allout58.mods.SpaceCraft.WorldGen.*;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.Locale;
import net.minecraft.creativetab.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = "SpaceCraft", name = "SpaceCraft", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels={"SpaceCraft"}, packetHandler = PacketHandler.class)
public class SpaceCraft
{
    public static Block oreStarSteel, storageStarSteel;
    public static Block launchTower, launchTowerController;
    public static Item ingotStarSteel;
    public static Item toolPickaxe, toolSword, toolAxe, toolShovel;
    public static Item armorBoots, armorLegs, armorChest, armorHelmet;
    public static Item toolWrench, toolDebug;

    public static int starSteelGenAmount = 1;

    public static CreativeTabs creativeTab;

    @Instance("SpaceCraft")
    public static SpaceCraft instance;

    @SidedProxy(clientSide = "allout58.mods.SpaceCraft.client.ClientProxy", serverSide = "allout58.mods.SpaceCraft.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.registerRenderers();
        
        EntityRegistry.registerModEntity(EntityRocket.class, "rocket", 10, this.instance, 50, 1, true);
        
        creativeTab = new CreativeTabs("SpaceCraft");

        EnumHelper.addToolMaterial("Star Steel", 2, 350, 6.5F, 2.1F, 20);
        EnumHelper.addArmorMaterial("Star Steel", 18, new int[] { 3, 6, 5, 3 }, 20);

        SpaceCraftConfig.initConfig(new Configuration(event.getSuggestedConfigurationFile()));
        
        allout58.mods.SpaceCraft.util.Localization.addLocalization("/lang/SpaceCraft/", "en_US");

        tileEntityRegistration();
        
        loadBlocks();
        loadOres();
        loadItems();
        loadTools();

        blockRegistration();
        oreRegistration();
        itemRegistration();

        forgeOreDict();
        addRecipes();



        GameRegistry.registerWorldGenerator(new GenStarSteelOre());
        GameRegistry.registerWorldGenerator(new GenMeteors());

    }



    private void forgeOreDict()
    {
        OreDictionary.registerOre("ingotStarSteel", ingotStarSteel);
        OreDictionary.registerOre("oreStarSteel", oreStarSteel);
        // OreDictionary.registerOre("", ore)

    }

    private void addRecipes()
    {
        FurnaceRecipes.smelting().addSmelting(oreStarSteel.blockID, new ItemStack(ingotStarSteel), 0.85F);
        GameRegistry.addShapedRecipe(new ItemStack(storageStarSteel), "III", "III", "III", 'I', new ItemStack(ingotStarSteel));
    }

    private void itemRegistration()
    {
        GameRegistry.registerItem(ingotStarSteel, "ingotStarSteel");
        GameRegistry.registerItem(toolWrench, "toolWrench");
        // GameRegistry.registerItem(toolDebug, "toolDebug");
        // GameRegistry.registerItem(item, name)
    }

    private void tileEntityRegistration()
    {
        GameRegistry.registerTileEntity(LaunchControlLogic.class, "LaunchControl");
    }

    private void oreRegistration()
    {
        GameRegistry.registerBlock(oreStarSteel, "oreStarSteel");
        GameRegistry.registerBlock(storageStarSteel, "storageStarSteel");
        MinecraftForge.setBlockHarvestLevel(oreStarSteel, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(storageStarSteel, "pickaxe", 2);
    }

    private void blockRegistration()
    {
        GameRegistry.registerBlock(launchTower, "launchTower");
        MinecraftForge.setBlockHarvestLevel(launchTower, "pickaxe", 1);
        GameRegistry.registerBlock(launchTowerController, "launchTowerController");
    }

    private void addNames()
    {
        String langDir = "/lang/spacecraft/resources/lang/";
        String[] langFiles = { "en_US.xml" };
    }

    private void addItemNames()
    {
        LanguageRegistry.addName(ingotStarSteel, "Star Steel Ingot");
        LanguageRegistry.addName(toolWrench, "Wrench");
        // LanguageRegistry.addName(toolDebug, "Debug Tool");
    }

    private void addBlockNames()
    {
        LanguageRegistry.addName(launchTower, "Launch Tower");
        LanguageRegistry.addName(launchTowerController, "Launch Tower Controller");
    }

    private void addOreNames()
    {
        LanguageRegistry.addName(oreStarSteel, "Star Steel Ore");
        LanguageRegistry.addName(storageStarSteel, "Block of Star Steel");
    }

    private void loadItems()
    {
        ingotStarSteel = new ItemIngotStarSteel(SpaceCraftConfig.ingots);
        toolWrench = new ToolWrench(SpaceCraftConfig.wrench);
        // toolDebug =
    }

    private void loadOres()
    {

        oreStarSteel = new BlockOreStarSteel(SpaceCraftConfig.ores, Material.rock);
        storageStarSteel = new BlockStorageStarSteel(SpaceCraftConfig.storageBlock, Material.iron);
    }

    private void loadBlocks()
    {
        launchTower = new BlockLaunchTower(SpaceCraftConfig.launchTower, Material.iron);
        launchTowerController = new BlockLaunchControl(SpaceCraftConfig.launchController, Material.iron);
    }

    private void loadTools()
    {

    }
}
