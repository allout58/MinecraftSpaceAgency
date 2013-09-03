package allout58.mods.SpaceCraft.Rockets.Parts.Items;

public class ItemPartLiquidEngine extends ItemPartEngine
{
    public ItemPartLiquidEngine(int par1)
    {
        super(par1);
        setMaxStackSize(4);
        setUnlocalizedName("liquid_engine");
        super.postInit();
    }
}
