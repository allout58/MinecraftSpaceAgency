package allout58.mods.SpaceCraft;

import allout58.mods.SpaceCraft.Core.*;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.Locale;
import net.minecraft.creativetab.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

@Mod(
    modid              = "SpaceCraft",
    name               = "SpaceCraft",
    version            = "0.0.1"
)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class SpaceCraft
{
	public static Block oreStarSteel,storageStarSteel;
	public static Item ingotStarSteel;
	
	public static int starSteelGenAmount = 1;
	
	public static CreativeTabs creativeTab;	
	
    @Instance("SpaceCraft")
    public static SpaceCraft  instance;

    @SidedProxy(clientSide = "allout58.mods.SpaceCraft.client.ClientProxy", serverSide = "allout58.mods.SpaceCraft.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	creativeTab = new CreativeTabs("SpaceCraft").setBackgroundImageName("spacecraft:blocks/oreStarSteel");
    	
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();
        loadBlocks(config);
        loadOres(config);
        loadItems(config);
        config.save();
        
        addNames();
        blockRegistration();
        oreRegistration();
        itemRegistration();
        forgeOreDict();
        addRecipes();
        
        proxy.registerRenderers();
        
        GameRegistry.registerWorldGenerator(new SpaceCraftWorldGen());
    }

    private void forgeOreDict() {
		OreDictionary.registerOre("ingotStarSteel", ingotStarSteel);
		OreDictionary.registerOre("oreStarSteel", oreStarSteel);
		//OreDictionary.registerOre("", ore)
		
	}

	private void addRecipes()
    {
    	FurnaceRecipes.smelting().addSmelting(oreStarSteel.blockID, new ItemStack(ingotStarSteel), 0.85F);
    	GameRegistry.addShapedRecipe(new ItemStack(storageStarSteel), "III","III","III",'I',new ItemStack(ingotStarSteel));
    }

    private void itemRegistration() {
    	GameRegistry.registerItem(ingotStarSteel, "ingotStarSteel");
    }

    private void oreRegistration() {
    	GameRegistry.registerBlock(oreStarSteel, "oreStarSteel");
    	GameRegistry.registerBlock(storageStarSteel,"storageStarSteel");
    	MinecraftForge.setBlockHarvestLevel(oreStarSteel, "pickaxe", 2);
    	MinecraftForge.setBlockHarvestLevel(storageStarSteel, "pickaxe", 2);
    }

    private void blockRegistration() {

        // TODO Auto-generated method stub
    }

    private void addNames()
    {
        addItemNames();
        addBlockNames();
        addOreNames();
    }

    private void addItemNames()
    {
    	LanguageRegistry.addName(ingotStarSteel, "Star Steel Ingot");
    }
    
    private void addBlockNames()
    {
    	
    }
      
    private void addOreNames()
    {
    	LanguageRegistry.addName(oreStarSteel, "Star Steel Ore");
    	LanguageRegistry.addName(storageStarSteel, "Block of Star Steel");
    }
    
    private void loadItems(Configuration config)
    {
    	ingotStarSteel = new ItemIngotStarSteel(config.getItem("ingotStarSteel.ID", 2400).getInt());
    }

    private void loadOres(Configuration config) {

    	oreStarSteel = new BlockOreStarSteel(config.getBlock("oreStarSteel.ID", 400).getInt(), Material.rock);
    	storageStarSteel = new BlockStorageStarSteel(config.getBlock("blockStarSteel", 401).getInt(), Material.iron);
        // TODO Auto-generated method stub
    }

    private void loadBlocks(Configuration config) {

        // TODO Auto-generated method stub
    }
}
