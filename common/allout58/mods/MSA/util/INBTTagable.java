package allout58.mods.MSA.util;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTTagable
{

    void readFromNBT(NBTTagCompound nbttagcompound);

    void writeToNBT(NBTTagCompound nbttagcompound);
}
