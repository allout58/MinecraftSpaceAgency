package allout58.mods.MSA.Rockets.Parts.Items;

public class ItemPartLiquidTank extends ItemRocketPart
{

    public ItemPartLiquidTank(int par1)
    {
        super(par1);
        setMaxStackSize(1);
        setUnlocalizedName("liquid_tank");
        super.postInit();
    }

}
