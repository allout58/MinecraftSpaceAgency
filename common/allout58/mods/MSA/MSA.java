package allout58.mods.MSA;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import allout58.mods.MSA.blocks.BlockList;
import allout58.mods.MSA.blocks.logic.AssemblerLogic;
import allout58.mods.MSA.blocks.logic.ComSatelliteLogic;
import allout58.mods.MSA.blocks.logic.CommandCenterLogic;
import allout58.mods.MSA.blocks.logic.LaunchControlLogic;
import allout58.mods.MSA.client.gui.GuiOverlay;
import allout58.mods.MSA.constants.MSAEntityIDs;
import allout58.mods.MSA.items.ItemList;
import allout58.mods.MSA.network.PacketHandler;
import allout58.mods.MSA.rockets.Parts.items.RocketPartList;
import allout58.mods.MSA.rockets.entity.EntityRocket;
import allout58.mods.MSA.tools.ToolList;
import allout58.mods.MSA.util.Localization;
import allout58.mods.MSA.util.StringUtils;
import allout58.mods.MSA.worldgen.GenMeteors;
import allout58.mods.MSA.worldgen.GenStarSteelOre;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "MinecraftSpaceAgency", name = "Minecraft Space Agency", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { "MSA" }, packetHandler = PacketHandler.class)
public class MSA
{
    // not sure if these go in ItemList or ToolList
    // public static Item toolPickaxe, toolSword, toolAxe, toolShovel;
    // public static Item armorBoots, armorLegs, armorChest, armorHelmet;

    public static CreativeTabs creativeTab = new CreativeTabs("MinecraftSpaceAgency")
    {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem()
        {
            return ItemList.ingotStarSteel;
        }

        @Override
        public String getTranslatedTabLabel()
        {
            return StringUtils.localize("strings.Title");
        }
    };

    @Instance("MinecraftSpaceAgency")
    public static MSA instance;

    @SidedProxy(clientSide = "allout58.mods.MSA.client.ClientProxy", serverSide = "allout58.mods.MSA.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        FMLLog.getLogger().info("Starting MSA");
        // Load configuration
        MSAConfig.initConfig(new Configuration(event.getSuggestedConfigurationFile()));

        // Load localizations
        Localization.addLocalization("/lang/MSA/", "en_US");

        // Client proxy things
        proxy.registerRenderers();

        // Entity registration
        EntityRegistry.registerModEntity(EntityRocket.class, "rocket", MSAEntityIDs.ROCKET, this.instance, 60, 1, true);

        // Add star steel as a tool material
        EnumHelper.addToolMaterial("Star Steel", 2, 350, 6.5F, 2.1F, 20);
        EnumHelper.addArmorMaterial("Star Steel", 18, new int[] { 3, 6, 5, 3 }, 20);

        // Register tile entities
        tileEntityRegistration();

        // Initialize all our blocks, items, tools and rocket parts
        BlockList.init();
        ItemList.init();
        ToolList.init();
        RocketPartList.init();

        // Add the recipies for our blocks, items, tools and rocket parts
        BlockList.addRecipies();
        ItemList.addRecipies();
        ToolList.addRecipies();
        RocketPartList.addRecipies();

        // Register our world generators
        GameRegistry.registerWorldGenerator(new GenStarSteelOre());
        GameRegistry.registerWorldGenerator(new GenMeteors());

        // Register our Gui Handler
        NetworkRegistry.instance().registerGuiHandler(instance, proxy);

    }

    @EventHandler
    public void load(FMLInitializationEvent fmlInit)
    {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent fmlPost)
    {
        // Register our event handler
        if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT)
            MinecraftForge.EVENT_BUS.register(new GuiOverlay());
    }

    private void tileEntityRegistration()
    {
        GameRegistry.registerTileEntity(LaunchControlLogic.class, "LaunchControl");
        GameRegistry.registerTileEntity(AssemblerLogic.class, "RocketAssembler");
        GameRegistry.registerTileEntity(ComSatelliteLogic.class, "ComSatelliteLogic");
        GameRegistry.registerTileEntity(CommandCenterLogic.class, "CommandCenter");
    }
}
