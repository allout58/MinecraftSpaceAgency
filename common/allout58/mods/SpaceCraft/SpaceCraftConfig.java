package allout58.mods.SpaceCraft;

import net.minecraftforge.common.Configuration;

public class SpaceCraftConfig
{

    // blocks
    public static int ores;
    public static int storageBlock;
    public static int launchTower;
    public static int launchController;
    // items
    public static int ingots;
    public static int wrench;
    public static int debugTool;

    // settings
    public static boolean generateCopper;
    public static int starsteelgenMinY_Lower;
    public static int starsteelgenMaxY_Lower;
    public static int starsteelgenMinY_Upper;
    public static int starsteelgenMaxY_Upper;
    public static int starsteelgenRarity;
    public static int starsteelgenAmount;
    
    //rocket settings
    public static int chanceMissFire;

    public static void initConfig(Configuration config)
    {
        config.load();
        ores = config.getBlock("Blocks", "ores", 400).getInt();
        storageBlock = config.getBlock("Blocks", "storageBlocks", 401).getInt();
        launchTower = config.getBlock("Blocks", "launchTower", 402).getInt();
        launchController = config.getBlock("Blocks", "launchController", 403).getInt();
        ingots = config.getItem("Items", "ingots", 2400).getInt();
        wrench = config.getItem("Tools", "wrench", 2401).getInt();
        debugTool = config.getItem("Tools", "debugTool", 2402).getInt();
        starsteelgenMinY_Lower = config.get("WorldGen", "starSteelMinY_Lower", 0).getInt();
        starsteelgenMaxY_Lower = config.get("WorldGen", "starSteelMaxY_Lower", 18).getInt();
        starsteelgenMinY_Upper = config.get("WorldGen", "starSteelMinY_Upper", 50).getInt();
        starsteelgenMaxY_Upper = config.get("WorldGen", "starSteelMaxY_Upper", 65).getInt();
        starsteelgenRarity = config.get("WorldGen", "starSteelRarity", 2).getInt();
        starsteelgenAmount = config.get("WorldGen", "starSteelAmount", 3).getInt();
        //rocket settings
        chanceMissFire = config.get("Rocket", "chanceMissFireEngine", 1000000).getInt();
        config.save();
    }

}
