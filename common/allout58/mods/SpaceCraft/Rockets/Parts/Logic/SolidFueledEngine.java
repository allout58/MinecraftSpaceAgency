package allout58.mods.SpaceCraft.Rockets.Parts.Logic;

import allout58.mods.SpaceCraft.Rockets.RocketEnums;
import allout58.mods.SpaceCraft.Rockets.RocketEnums.RocketSize;

public class SolidFueledEngine extends EngineBase
{
    public SolidFueledEngine(RocketSize size)
    {
        Size=size;
        switch(Size)
        {
            case Large:
                TotalFuel=1800;
                break;
            case Medium:
                TotalFuel=900;
                break;
            case Small:
                TotalFuel=450;
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
        switch(Size)
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

}
