package allout58.mods.SpaceCraft.Rockets.Parts.Logic;

import net.minecraft.nbt.NBTTagCompound;
import allout58.mods.SpaceCraft.Rockets.RocketEnums;
import allout58.mods.SpaceCraft.Rockets.RocketEnums.FuelType;
import allout58.mods.SpaceCraft.Rockets.RocketEnums.RocketSize;

public class SolidFueledEngine extends EngineBase
{

    public SolidFueledEngine(RocketSize size)
    {
        Fuel = FuelType.Solid;
        Size = size;
        switch (Size)
        {
            case Large:
                TotalFuel = 1800;
                break;
            case Medium:
                TotalFuel = 900;
                break;
            case Small:
                TotalFuel = 450;
                break;
            default:
                break;
        }
    }

    @Override
    protected int powerPerFuel()
    {
        return 1;
    }

    @Override
    protected int fuelBurnPerTick()
    {
        switch (Size)
        {
            case Large:
                return 8;
            case Medium:
                return 4;
            case Small:
                return 2;
            default:
                return 0;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setString("Type", "SolidFueled");
        super.writeToNBT(nbttagcompound);
    }

}
