package allout58.mods.SpaceCraft.Rockets.Parts.Items;

public class ItemPartFuselage extends ItemRocketPart
{
    public ItemPartFuselage(int par1)
    {
        super(par1);
        setMaxStackSize(1);
        setUnlocalizedName("fuselage");
        super.postInit();
    }
}
