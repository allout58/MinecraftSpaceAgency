package allout58.mods.MSA.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import allout58.mods.MSA.blocks.container.ContainerLaunchControl;
import allout58.mods.MSA.blocks.logic.LaunchControlLogic;
import allout58.mods.MSA.constants.MSATextures;
import allout58.mods.MSA.util.StringUtils;

public class GuiLaunchControl extends GuiContainer
{
    LaunchControlLogic logic;

    public GuiLaunchControl(InventoryPlayer playerInv, LaunchControlLogic logic)
    {
        super(new ContainerLaunchControl(playerInv, logic));
        ySize = 165;
        xSize = 175;
        this.logic = logic;
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

        this.mc.getTextureManager().bindTexture(MSATextures.GUI_LAUNCH_CONTROL);

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }

}
