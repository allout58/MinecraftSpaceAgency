package allout58.mods.MSA;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import allout58.mods.MSA.blocks.container.ContainerCommandCenter;
import allout58.mods.MSA.blocks.container.ContainerLaunchControl;
import allout58.mods.MSA.blocks.container.ContainerRocketAssembler;
import allout58.mods.MSA.blocks.logic.AssemblerLogic;
import allout58.mods.MSA.blocks.logic.CommandCenterLogic;
import allout58.mods.MSA.blocks.logic.LaunchControlLogic;
import allout58.mods.MSA.client.gui.GuiCommandCenter;
import allout58.mods.MSA.client.gui.GuiLaunchControl;
import allout58.mods.MSA.client.gui.GuiRocketAssembler;
import allout58.mods.MSA.constants.MSAGuiIDs;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
    // Client stuff
    public void registerRenderers()
    {
        // Nothing here as the server doesn't render graphics or entities!
    }

    public String getCurrentLanguage()
    {
        return null;
    }

    public void addName(Object obj, String s)
    {
    }

    public void addLocalization(String s1, String string)
    {
    }

    public String getItemDisplayName(ItemStack stack)
    {
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == MSAGuiIDs.ROCKET_ASSEMBLER)
        {
            AssemblerLogic logic = (AssemblerLogic) world.getBlockTileEntity(x, y, z);
            return new ContainerRocketAssembler(player.inventory, logic);
        }
        if (ID == MSAGuiIDs.COMMAND_CENTER)
        {
            CommandCenterLogic logic = (CommandCenterLogic) world.getBlockTileEntity(x, y, z);
            return new ContainerCommandCenter(player.inventory, logic);
        }
        if (ID == MSAGuiIDs.LAUNCH_CONTROL)
        {
            LaunchControlLogic logic = (LaunchControlLogic) world.getBlockTileEntity(x, y, z);
            return new ContainerLaunchControl(player.inventory, logic);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == MSAGuiIDs.ROCKET_ASSEMBLER)
        {
            AssemblerLogic logic = (AssemblerLogic) world.getBlockTileEntity(x, y, z);
            return new GuiRocketAssembler(player.inventory, logic);
        }
        if (ID == MSAGuiIDs.COMMAND_CENTER)
        {
            CommandCenterLogic logic = (CommandCenterLogic) world.getBlockTileEntity(x, y, z);
            return new GuiCommandCenter(player.inventory, logic);
        }
        if (ID == MSAGuiIDs.LAUNCH_CONTROL)
        {
            LaunchControlLogic logic = (LaunchControlLogic) world.getBlockTileEntity(x, y, z);
            return new GuiLaunchControl(player.inventory, logic);
        }
        return null;
    }

    public int getCurrentParticleSetting()
    {
        return 0;
    }
}
