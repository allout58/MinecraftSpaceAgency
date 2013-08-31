package allout58.mods.SpaceCraft;

import allout58.mods.SpaceCraft.Blocks.*;
import allout58.mods.SpaceCraft.Blocks.Logic.*;
import allout58.mods.SpaceCraft.Items.*;
import allout58.mods.SpaceCraft.Rockets.Entity.EntityRocket;
import allout58.mods.SpaceCraft.Tools.*;
import allout58.mods.SpaceCraft.WorldGen.*;
import allout58.mods.SpaceCraft.util.*;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { "SpaceCraft" }, packetHandler = PacketHandler.class)
public class SpaceCraft
{
    // not sure if these go in ItemList or ToolList
    // public static Item toolPickaxe, toolSword, toolAxe, toolShovel;
    // public static Item armorBoots, armorLegs, armorChest, armorHelmet;

    public static CreativeTabs creativeTab = new CreativeTabs("SpaceCraft")
    {
        @Override
        @SideOnly(Side.CLIENT)
        public int getTabIconItemIndex()
        {

            return SpaceCraftConfig.ingots;
        }
    };

    @Instance("SpaceCraft")
    public static SpaceCraft instance;

    @SidedProxy(clientSide = "allout58.mods.SpaceCraft.client.ClientProxy", serverSide = "allout58.mods.SpaceCraft.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // load configuration
        SpaceCraftConfig.initConfig(new Configuration(event.getSuggestedConfigurationFile()));

        // load localizations
        Localization.addLocalization("/lang/SpaceCraft/", "en_US");

        // client proxy things
        proxy.registerRenderers();

        // entity registration
        EntityRegistry.registerModEntity(EntityRocket.class, "rocket", 10, this.instance, 60, 1, true);

        // add star steel as a tool material
        EnumHelper.addToolMaterial("Star Steel", 2, 350, 6.5F, 2.1F, 20);
        EnumHelper.addArmorMaterial("Star Steel", 18, new int[] { 3, 6, 5, 3 }, 20);

        // register tile entities
        tileEntityRegistration();

        // Initialize all our blocks, items and tools
        BlockList.init();
        ItemList.init();
        ToolList.init();

        // register our world generators
        GameRegistry.registerWorldGenerator(new GenStarSteelOre());
        GameRegistry.registerWorldGenerator(new GenMeteors());

    }

    @EventHandler
    public void load(FMLInitializationEvent fmlInit)
    {

    }

    private void tileEntityRegistration()
    {
        GameRegistry.registerTileEntity(LaunchControlLogic.class, "LaunchControl");
        GameRegistry.registerTileEntity(AssemblerLogic.class, "RocketAssembler");
    }
}
