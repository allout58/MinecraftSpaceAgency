package allout58.mods.MSA.blocks.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import allout58.mods.MSA.blocks.container.slots.SlotFinalRocket;
import allout58.mods.MSA.blocks.logic.LaunchControlLogic;

public class ContainerLaunchControl extends Container
{
    public ContainerLaunchControl(InventoryPlayer playerInv, LaunchControlLogic logic)
    {
        this.addSlotToContainer(new SlotFinalRocket(logic, LaunchControlLogic.ROCKET_INV_LOC, 80, 18));

        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new Slot(playerInv, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 84 + inventoryRowIndex * 18));
            }
        }

        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < 9; ++actionBarSlotIndex)
        {
            this.addSlotToContainer(new Slot(playerInv, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 142));
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
            if (slotIndex < LaunchControlLogic.INVENTORY_SIZE)
            {

                if (!this.mergeItemStack(slotItemStack, LaunchControlLogic.INVENTORY_SIZE, inventorySlots.size(), false))
                {
                    return null;
                }
            }
            else
            {
                for (int i = 0; i < LaunchControlLogic.INVENTORY_SIZE; i++)
                {
                    Slot slotToTry = (Slot) this.inventorySlots.toArray()[i];
                    if (slotToTry.getStack() == null && slotToTry.isItemValid(slotItemStack))
                    {
                        if (!this.mergeItemStack(slotItemStack, slotToTry.slotNumber, LaunchControlLogic.INVENTORY_SIZE, false))
                        {
                            return null;
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
