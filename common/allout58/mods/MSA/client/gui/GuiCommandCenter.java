package allout58.mods.MSA.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import allout58.mods.MSA.blocks.container.ContainerCommandCenter;
import allout58.mods.MSA.blocks.logic.CommandCenterLogic;
import allout58.mods.MSA.constants.MSATextures;
import allout58.mods.MSA.util.StringUtils;

public class GuiCommandCenter extends GuiContainer
{
    CommandCenterLogic logic;

    public GuiCommandCenter(InventoryPlayer playerInv, CommandCenterLogic logic)
    {
        super(new ContainerCommandCenter(playerInv, logic));
        ySize = 255;
        xSize = 255;
        this.logic = logic;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {

        String containerName = logic.isInvNameLocalized() ? logic.getInvName() : StringUtils.localize(logic.getInvName());
        fontRenderer.drawString(containerName, xSize / 2 - fontRenderer.getStringWidth(containerName) / 2, 6, 4210752);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 45, ySize - 96 + 2, 4210752);
        fontRenderer.drawSplitString(StringUtils.localize("tile.launchControl.name"), 215, 87, 40, 4210752);
        // fontRenderer.drawString(StringUtils.localize("tile.launchControl.name"),
        // 227, 99, 4210752);
        fontRenderer.drawSplitString(StringUtils.localize("tile.comSatellite.name"), 185, 125, 80, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y)
    {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(MSATextures.GUI_COMMAND_CENTER);

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }

}
