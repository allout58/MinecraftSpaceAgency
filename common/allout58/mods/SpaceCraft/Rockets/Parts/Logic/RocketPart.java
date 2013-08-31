package allout58.mods.SpaceCraft.Rockets.Parts.Logic;

import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

import allout58.mods.SpaceCraft.Rockets.RocketEnums;
import allout58.mods.SpaceCraft.Rockets.RocketEnums.RocketSize;
import allout58.mods.SpaceCraft.util.INBTTagable;

public abstract class RocketPart implements INBTTagable
{
    public static int Weight = 0;
    protected Random rand = new Random();
    public RocketSize Size = RocketSize.Small;
    public static String Name = "";
    public static String Description = "";

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setByte("PartSize", (byte) Size.ordinal());
    }
}
