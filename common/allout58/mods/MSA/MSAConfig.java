package allout58.mods.MSA;

import net.minecraftforge.common.Configuration;

public class MSAConfig
{
    // Blocks
    public static int ores;
    public static int storageBlock;
    public static int launchTower;
    public static int launchController;
    public static int rocketAssembler;
    public static int comSatellite;
    public static int ssBuilding;
    // Items
    public static int ingots;
    public static int rocket;
    // Rocket Parts
    public static int partSatellite;
    public static int partRover;
    public static int partLiquidTank;
    public static int partFuselage;
    public static int partLiquidEngine;
    public static int partSolidEngine;
    public static int partFins;
    // Tools
    public static int wrench;
    public static int debugTool;

    // World Gen settings
    public static boolean generateCopper;
    public static int starsteelgenMinY_Lower;
    public static int starsteelgenMaxY_Lower;
    public static int starsteelgenMinY_Upper;
    public static int starsteelgenMaxY_Upper;
    public static int starsteelgenRarity;
    public static int starsteelgenAmount;

    // Rocket settings
    public static int chanceMissFire;

    public static void initConfig(Configuration config)
    {
        config.load();
        // Blocks
        ores = config.getBlock("Blocks", "ores", 1200).getInt();
        storageBlock = config.getBlock("Blocks", "storageBlocks", 1201).getInt();
        launchTower = config.getBlock("Blocks", "launchTower", 1202).getInt();
        launchController = config.getBlock("Blocks", "launchController", 1203).getInt();
        rocketAssembler = config.getBlock("Blocks", "rocketAssembler", 1204).getInt();
        comSatellite=config.getBlock("Blocks", "comSatellite", 1205).getInt();
        ssBuilding=config.getBlock("Blocks","starSteelBuilding",1206).getInt();
        // Items
        ingots = config.getItem("Items", "ingots", 5400).getInt();
        rocket = config.getItem("Items","rocket", 5403).getInt();
        // Rocket parts
        partSatellite = config.getItem("RocketParts", "partSatellite", 5405).getInt();
        partRover=config.getItem("RocketParts", "partRover", 5406).getInt();
        partLiquidTank=config.getItem("RocketParts", "partLiquidTank", 5407).getInt();
        partFuselage = config.getItem("RocketParts", "partFuselage", 5408).getInt();
        partLiquidEngine = config.getItem("RocketParts", "partLiquidEngine", 5409).getInt();
        partSolidEngine = config.getItem("RocketParts", "partSolidEngine", 5410).getInt();
        partFins = config.getItem("RocketParts", "partFin", 5411).getInt();
                // Tools
        wrench = config.getItem("Tools", "wrench", 5401).getInt();
        debugTool = config.getItem("Tools", "debugTool", 5402).getInt();

        // World Generation settings
        starsteelgenMinY_Lower = config.get("WorldGen", "starSteelMinY_Lower", 0).getInt();
        starsteelgenMaxY_Lower = config.get("WorldGen", "starSteelMaxY_Lower", 18).getInt();
        starsteelgenMinY_Upper = config.get("WorldGen", "starSteelMinY_Upper", 50).getInt();
        starsteelgenMaxY_Upper = config.get("WorldGen", "starSteelMaxY_Upper", 65).getInt();
        starsteelgenRarity = config.get("WorldGen", "starSteelRarity", 2).getInt();
        starsteelgenAmount = config.get("WorldGen", "starSteelAmount", 3).getInt();
        // rocket settings
        chanceMissFire = config.get("Rocket", "chanceMissFireEngine", 1000000).getInt();
        config.save();
    }

}
