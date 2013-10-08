package allout58.mods.MSA.Rockets.Parts.Items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import allout58.mods.MSA.Rockets.RocketEnums.RocketSize;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPartPayloadSatalite extends ItemPartPayload
{
    public ItemPartPayloadSatalite(int par1)
    {
        super(par1);
        setMaxStackSize(1);
        setUnlocalizedName("payload_satellite");
        super.postInit();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    /** Allows items to add custom lines of information to the mouseover description. */
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List infoList, boolean par4)
    {
    	infoList.add("Weight: 100");
    	infoList.add("Use: A satellite that can be sent to");
    	infoList.add(" space to improve communications.");
    }
}
