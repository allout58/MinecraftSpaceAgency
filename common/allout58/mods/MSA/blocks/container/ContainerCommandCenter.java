package allout58.mods.MSA.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import allout58.mods.MSA.blocks.container.slots.SlotLinkCard;
import allout58.mods.MSA.blocks.logic.AssemblerLogic;
import allout58.mods.MSA.blocks.logic.CommandCenterLogic;

public class ContainerCommandCenter extends Container
{
    public ContainerCommandCenter(InventoryPlayer playerInv, CommandCenterLogic logic)
    {
        this.addSlotToContainer(new SlotLinkCard(logic, CommandCenterLogic.LAUNCH_LINK_INV_LOC, 230, 109));
        this.addSlotToContainer(new SlotLinkCard(logic, CommandCenterLogic.SAT_LINK_INV_LOC, 230, 140));

        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new Slot(playerInv, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 48 + inventoryColumnIndex * 18, 174 + inventoryRowIndex * 18));
            }
        }

        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < 9; ++actionBarSlotIndex)
        {
            this.addSlotToContainer(new Slot(playerInv, actionBarSlotIndex, 48 + actionBarSlotIndex * 18, 232));
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
             * If we are shift-clicking an item out of the Command Center's
             * container, attempt to put it in the first available slot in the
             * player's inventory
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
