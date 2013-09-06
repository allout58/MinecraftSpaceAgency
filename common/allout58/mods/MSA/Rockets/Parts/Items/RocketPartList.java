package allout58.mods.MSA.Rockets.Parts.Items;

import allout58.mods.MSA.MSAConfig;
import net.minecraft.item.Item;

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
        Fuselage=new ItemPartFuselage(MSAConfig.partFuselage);
        Fins = new ItemPartFin(MSAConfig.partFins);
        PayloadSatellite = new ItemPartPayloadSatalite(MSAConfig.partSatellite);
        PayloadRover = new ItemPartPayloadRover(MSAConfig.partRover);
        LiquidTank = new ItemPartLiquidTank(MSAConfig.partLiquidTank);
        LiquidEngine = new ItemPartLiquidEngine(MSAConfig.partLiquidEngine);
        SolidEngine = new ItemPartSolidEngine(MSAConfig.partSolidEngine);
    }
    
    public static void addRecipies()
    {
        
    }
}
