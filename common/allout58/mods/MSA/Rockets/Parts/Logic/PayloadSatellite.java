package allout58.mods.MSA.Rockets.Parts.Logic;

import net.minecraft.nbt.NBTTagCompound;

public class PayloadSatellite extends PayloadBase
{
    public PayloadSatellite()
    {
        Weight=100;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tags)
    {
        super.writeToNBT(tags);
        tags.setInteger("Type", 0);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tags)
    {
        super.readFromNBT(tags);
    }
}
