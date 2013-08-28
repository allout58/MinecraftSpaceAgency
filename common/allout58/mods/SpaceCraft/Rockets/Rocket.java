package allout58.mods.SpaceCraft.Rockets;

import net.minecraft.entity.EntityFlying;
import allout58.mods.SpaceCraft.Rockets.Parts.Logic.EngineBase;
import allout58.mods.SpaceCraft.Rockets.Parts.Logic.Fuselage;
import allout58.mods.SpaceCraft.Rockets.Parts.Logic.PayloadBase;
import allout58.mods.SpaceCraft.Rockets.RocketEnums.*;

public class Rocket
{    
    private EngineBase[] Engines;
    private Fuselage Fuselage;
    private PayloadBase Payload;
    public int totalWeight=0;
    public int motionX=0;
    public int motionY=0;
    public int motionZ=0;
    public RocketSize Size;
    
    
    public Rocket(EngineBase[] engines,Fuselage fuselage, PayloadBase payload)
    {
        Engines=engines;
        Fuselage=fuselage;
        Payload=payload;
        Size=Fuselage.Size;
        for(int i=0;i<Engines.length;i++)
        {
            totalWeight+=Engines[i].Weight;
        }
        totalWeight+=Fuselage.Weight;
        //totalWeight+=Payload.Weight;
    }
    
    public void Launch()
    {
        
    }
    
    public void tick()
    {
        int power=0;
        for(int i=0;i<Engines.length;i++)
        {
            power+=Engines[i].engineTick();
        }
        motionY=power-totalWeight;
    }

}
