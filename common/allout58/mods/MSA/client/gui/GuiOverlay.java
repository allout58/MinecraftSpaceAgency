package allout58.mods.MSA.client.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import allout58.mods.MSA.blocks.logic.LaunchControlLogic;
import cpw.mods.fml.client.FMLClientHandler;

public class GuiOverlay extends Gui
{
    public static final int START_X = 5;
    public static final int START_Y = 5;
    public static final int SEARCH_RADIUS = 5;

    public GuiOverlay()
    {
        super();
    }

    // event
    @ForgeSubscribe(priority = EventPriority.NORMAL)
    public void onRenderExperienceBar(RenderGameOverlayEvent event)
    {
        EntityClientPlayerMP player = FMLClientHandler.instance().getClient().thePlayer;
        // int playerX=(int) player.posX;
        // int playerY=(int) player.posY;
        // int playerZ=(int) player.posZ;
        // int halfSize= (int)(SEARCH_RADIUS/2);
        // AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(playerX-halfSize,
        // playerY-halfSize, playerZ-halfSize, playerX+halfSize,
        // playerY+halfSize, playerZ+halfSize);
        // player.worldObj.selectEntitiesWithinAABB(par1Class, bb, new IEntity)
        List tes = player.worldObj.loadedTileEntityList;
        LaunchControlLogic logic = null;
        for (int i = 0; i < tes.size(); i++)
        {
            if (tes.get(i) instanceof LaunchControlLogic)
            {
                if (((LaunchControlLogic) tes.get(i)).RocketLogic != null)
                {
                    logic = ((LaunchControlLogic) tes.get(i));
                    break;
                }
            }
        }
        if (event.isCancelable() || event.type != ElementType.EXPERIENCE || logic == null) return;
        // GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        // GuiOverlay.GL11.glDisable(GL11.GL_LIGHTING);
        FontRenderer fRender = Minecraft.getMinecraft().fontRenderer;
        fRender.drawString("Rocket Stats:", START_X, START_Y, 0);
        fRender.drawString("VelY: " + logic.RocketLogic.velY, START_X + 3, START_Y + fRender.FONT_HEIGHT + 2, 0);
        fRender.drawString("Current Power: " + logic.RocketLogic.currentPower, START_X + 3, START_Y + (fRender.FONT_HEIGHT + 2) * 2, 0);
        fRender.drawString("Weight: " + logic.RocketLogic.totalWeight, START_X + 3, START_Y + (fRender.FONT_HEIGHT + 2) * 3, 0);
        // GL11.glEnable(GL11.GL_LIGHTING);
    }
}
