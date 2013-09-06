package allout58.mods.MSA.Blocks.Container;

import allout58.mods.MSA.Blocks.Container.Slots.*;
import allout58.mods.MSA.Blocks.Logic.AssemblerLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerRocketAssembler extends Container
{
    public static final int drawX = 51;

    public static final int[] draw4ptX = { 19, 40, 61, 82 };

    public ContainerRocketAssembler(InventoryPlayer playerInv, AssemblerLogic logic)
    {
        // add payload slot
        this.addSlotToContainer(new SlotPayload(logic, AssemblerLogic.PAYLOAD_INV_LOC, drawX, 20));

        // add fuel tank slot
        this.addSlotToContainer(new SlotFuelTank(logic, AssemblerLogic.FUELTANK_INV_LOC, drawX, 43));

        // add fuselage slot
        this.addSlotToContainer(new SlotFuselage(logic, AssemblerLogic.FUSELAGE_INV_LOC, drawX, 66));

        // add fin slots
        for (int i = 0; i < AssemblerLogic.FIN_INV_LOCS.length; i++)
        {
            this.addSlotToContainer(new SlotFin(logic, AssemblerLogic.FIN_INV_LOCS[i], draw4ptX[i], 88));
        }

        // add engine slots
        for (int i = 0; i < AssemblerLogic.ENGINE_INV_LOCS.length; i++)
        {
            this.addSlotToContainer(new SlotEngine(logic, AssemblerLogic.ENGINE_INV_LOCS[i], draw4ptX[i], 114));
        }

        // add output slot
        this.addSlotToContainer(new SlotOutput(logic, AssemblerLogic.OUTPUT_INV_LOC, 143, 52));

        // add fuel slot
        this.addSlotToContainer(new SlotFuel(logic, AssemblerLogic.FUEL_INV_LOC, 111, 96));

        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new Slot(playerInv, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 152 + inventoryRowIndex * 18));
            }
        }

        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < 9; ++actionBarSlotIndex)
        {
            this.addSlotToContainer(new Slot(playerInv, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 210));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }

    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex)
    {
        ItemStack itemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {

            ItemStack slotItemStack = slot.getStack();
            itemStack = slotItemStack.copy();

            /**
             * If we are shift-clicking an item out of the Aludel's container,
             * attempt to put it in the first available slot in the player's
             * inventory
             */
            if (slotIndex < AssemblerLogic.INVENTORY_SIZE)
            {

                if (!this.mergeItemStack(slotItemStack, AssemblerLogic.INVENTORY_SIZE, inventorySlots.size(), false))
                {
                    return null;
                }
            }
            else
            {

                /**
                 * If the stack being shift-clicked into the assembler's
                 * container is a fuel, try to put it in the fuel slot.
                 */
                if (TileEntityFurnace.isItemFuel(slotItemStack))
                {
                    if (!this.mergeItemStack(slotItemStack, AssemblerLogic.FUEL_INV_LOC, AssemblerLogic.INVENTORY_SIZE, true))
                    {
                        return null;
                    }
                }
                else
                {
                    for (int i = 0; i < AssemblerLogic.INVENTORY_SIZE; i++)
                    {
                        Slot slotToTry = (Slot) this.inventorySlots.toArray()[i];
                        if (slotToTry.getStack() == null && slotToTry.isItemValid(slotItemStack))
                        {
                            if (!this.mergeItemStack(slotItemStack, slotToTry.slotNumber, AssemblerLogic.INVENTORY_SIZE, false))
                            {
                                return null;
                            }
                        }
                    }
                }
            }

            if (slotItemStack.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemStack;
    }
}
