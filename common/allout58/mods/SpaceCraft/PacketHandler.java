package allout58.mods.SpaceCraft;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player playerEntity)
    {
        if (packet.channel.equals("SpaceCraft"))
        {
            updateClient(packet);
        }

    }

    private void updateClient(Packet250CustomPayload packet)
    {
        // TODO Auto-generated method stub
        System.out.println("Updating Client - fake!");
    }

}