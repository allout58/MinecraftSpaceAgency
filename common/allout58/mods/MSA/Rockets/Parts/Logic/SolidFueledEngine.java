package allout58.mods.MSA.Rockets.Parts.Logic;

import net.minecraft.nbt.NBTTagCompound;
import allout58.mods.MSA.Rockets.RocketEnums;
import allout58.mods.MSA.Rockets.RocketEnums.FuelType;
import allout58.mods.MSA.Rockets.RocketEnums.RocketSize;

public class SolidFueledEngine extends EngineBase
{

    public SolidFueledEngine(RocketSize size)
    {
        Fuel = FuelType.Solid;
        Size = size;
        switch (Size)
        {
            case Large:
                TotalFuel = 2000;
                Weight = 500;
                break;
            case Medium:
                TotalFuel = 1000;
                Weight = 250;
                break;
            case Small:
                TotalFuel = 450;
                Weight = 100;
                break;
            default:
                break;
        }
        
    }

    @Override
    protected int powerPerFuel()
    {
        return (int)(2*Weight);
    }

    @Override
    protected int fuelBurnPerTick()
    {
        switch (Size)
        {
            case Large:
                return 4;
            case Medium:
                return 2;
            case Small:
                return 1;
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
        //nbttagcompound.setString("Type", "SolidFueled");
        super.writeToNBT(nbttagcompound);
    }

}
