package allout58.mods.MSA.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLLog;

import allout58.mods.MSA.blocks.container.ContainerRocketAssembler;
import allout58.mods.MSA.blocks.logic.AssemblerLogic;
import allout58.mods.MSA.constants.MSATextures;
import allout58.mods.MSA.util.StringUtils;

public class GuiRocketAssembler extends GuiContainer
{
    AssemblerLogic logic;

    public GuiRocketAssembler(InventoryPlayer playerInv, AssemblerLogic logic)
    {
        super(new ContainerRocketAssembler(playerInv, logic));
        ySize = 232;
        this.logic = logic;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        // id, x, y, width, height, text
        buttonList.add(new GuiButton(1, 240, 122, 48, 20, "Create!"));
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        // id is the id you give your button
        switch (guibutton.id)
        {
            case 1:
                System.out.println("Button 1 Clicked");
                break;
            default:
                FMLLog.severe("Unknown button clicked!");
        }
        // Packet code here
        // PacketDispatcher.sendPacketToServer(packet); //send packet
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {

        String containerName = logic.isInvNameLocalized() ? logic.getInvName() : StringUtils.localize(logic.getInvName());
        fontRenderer.drawString(containerName, xSize / 2 - fontRenderer.getStringWidth(containerName) / 2, 6, 4210752);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y)
    {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(MSATextures.GUI_ROCKET_ASSEMBLER);

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }

}
