package allout58.mods.MSA.client.gui;

import org.lwjgl.opengl.GL11;

import allout58.mods.MSA.Blocks.Container.ContainerRocketAssembler;
import allout58.mods.MSA.Blocks.Logic.AssemblerLogic;
import allout58.mods.MSA.constants.MSATextures;
import allout58.mods.MSA.util.StringUtils;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.StatCollector;

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

        // this.mc.getTextureManager().bindTexture(...)
        this.mc.func_110434_K().func_110577_a(MSATextures.GUI_ROCKET_ASSEMBLER);

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }

}
