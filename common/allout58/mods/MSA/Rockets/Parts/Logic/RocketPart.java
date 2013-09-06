package allout58.mods.MSA.Rockets.Parts.Logic;

import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

import allout58.mods.MSA.Rockets.RocketEnums;
import allout58.mods.MSA.Rockets.RocketEnums.RocketSize;
import allout58.mods.MSA.util.INBTTagable;

public abstract class RocketPart implements INBTTagable
{
    public int Weight = 0;
    protected Random rand = new Random();
    public RocketSize Size = RocketSize.Small;
    //these two may not be needed, probably should go in items
    public static String Name = "";
    public static String Description = "";

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        Size=RocketEnums.RocketSize.values()[nbttagcompound.getByte("PartSize")];
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setByte("PartSize", (byte) Size.ordinal());
    }
}
