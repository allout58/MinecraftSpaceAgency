package allout58.mods.SpaceCraft.Rockets.Parts.Items;

public class ItemPartSolidEngine extends ItemPartEngine
{

    public ItemPartSolidEngine(int par1)
    {
        super(par1);
        setMaxStackSize(4);
        setUnlocalizedName("solid_engine");
        super.postInit();
    }

}
