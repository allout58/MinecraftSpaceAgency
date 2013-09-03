package allout58.mods.SpaceCraft.Rockets.Parts.Items;

import allout58.mods.SpaceCraft.SpaceCraftConfig;
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
        Fuselage=new ItemPartFuselage(SpaceCraftConfig.partFuselage);
        Fins = new ItemPartFin(SpaceCraftConfig.partFins);
        PayloadSatellite = new ItemPartPayloadSatalite(SpaceCraftConfig.partSatellite);
        PayloadRover = new ItemPartPayloadRover(SpaceCraftConfig.partRover);
        LiquidTank = new ItemPartLiquidTank(SpaceCraftConfig.partLiquidTank);
        LiquidEngine = new ItemPartLiquidEngine(SpaceCraftConfig.partLiquidEngine);
        SolidEngine = new ItemPartSolidEngine(SpaceCraftConfig.partSolidEngine);
    }
    
    public static void addRecipies()
    {
        
    }
}
