package allout58.mods.MSA.rockets.Parts.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import allout58.mods.MSA.MSAConfig;

public class RocketPartList
{
    public static Item PayloadSatellite;
    public static Item PayloadRover;
    public static Item LiquidTank;
    public static Item Fuselage;
    public static Item LiquidEngine;
    public static Item SolidEngine;
    public static Item Fins;

    public static void init()
    {
        Fuselage = new ItemPartFuselage(MSAConfig.partFuselage);
        Fins = new ItemPartFin(MSAConfig.partFins);
        PayloadSatellite = new ItemPartPayloadSatalite(MSAConfig.partSatellite);
        PayloadRover = new ItemPartPayloadRover(MSAConfig.partRover);
        LiquidTank = new ItemPartLiquidTank(MSAConfig.partLiquidTank);
        LiquidEngine = new ItemPartLiquidEngine(MSAConfig.partLiquidEngine);
        SolidEngine = new ItemPartSolidEngine(MSAConfig.partSolidEngine);
        
        GameRegistry.registerItem(Fuselage, "fuselage");
        GameRegistry.registerItem(Fins,"fins");
        GameRegistry.registerItem(PayloadSatellite, "payloadSatellite");
        GameRegistry.registerItem(PayloadRover, "payloadRover");
        GameRegistry.registerItem(LiquidTank, "liquidTank");
        GameRegistry.registerItem(LiquidEngine, "liquidEngine");
        GameRegistry.registerItem(SolidEngine, "solidEngine");
    }

    public static void addRecipies()
    {

    }
}
