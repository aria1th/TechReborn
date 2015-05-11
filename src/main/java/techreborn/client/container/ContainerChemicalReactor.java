package techreborn.client.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import techreborn.client.SlotOutput;
import techreborn.tiles.TileAlloySmelter;
import techreborn.tiles.TileBlastFurnace;
import techreborn.tiles.TileChemicalReactor;

public class ContainerChemicalReactor extends TechRebornContainer {

	EntityPlayer player;

	TileChemicalReactor tile;

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}

	public int tickTime;

	public ContainerChemicalReactor(TileChemicalReactor tilechemicalReactor,
			EntityPlayer player)
	{
		tile = tilechemicalReactor;
		this.player = player;

		// input
		this.addSlotToContainer(new Slot(tilechemicalReactor.inventory, 0, 70, 21));
		this.addSlotToContainer(new Slot(tilechemicalReactor.inventory, 1, 90, 21));
		// outputs
		this.addSlotToContainer(new SlotOutput(tilechemicalReactor.inventory, 2, 80, 51));
		//TODO battery Slot

		int i;

		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(player.inventory, j + i * 9
						+ 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18,
					142));
		}
	}

}
