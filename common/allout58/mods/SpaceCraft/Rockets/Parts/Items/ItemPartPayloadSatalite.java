package allout58.mods.SpaceCraft.Rockets.Parts.Items;

public class ItemPartPayloadSatalite extends ItemPartPayload
{
    public ItemPartPayloadSatalite(int par1)
    {
        super(par1);
        setMaxStackSize(1);
        setUnlocalizedName("payload_satellite");
        super.postInit();
    }
}
