/*
 * This file is part of TechReborn, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2020 TechReborn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package techreborn.init;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import techreborn.config.TechRebornConfig;
import techreborn.init.TRContent.Ingots;
import techreborn.init.TRContent.Parts;

import java.util.List;
import java.util.stream.StreamSupport;

public class ModLoot {

	public static void init() {

		LootPoolEntry copperIngot = makeEntry(Items.COPPER_INGOT);
		LootPoolEntry tinIngot = makeEntry(Ingots.TIN);
		LootPoolEntry leadIngot = makeEntry(Ingots.LEAD);
		LootPoolEntry silverIngot = makeEntry(Ingots.SILVER);
		LootPoolEntry refinedronIngot = makeEntry(Ingots.REFINED_IRON);
		LootPoolEntry advancedalloyIngot = makeEntry(Ingots.ADVANCED_ALLOY);
		LootPoolEntry basicFrame = makeEntry(TRContent.MachineBlocks.BASIC.frame.asItem());
		LootPoolEntry basicCircuit = makeEntry(Parts.ELECTRONIC_CIRCUIT);
		LootPoolEntry rubberSapling = makeEntry(TRContent.RUBBER_SAPLING, 25);

		LootPoolEntry aluminumIngot = makeEntry(Ingots.ALUMINUM);
		LootPoolEntry electrumIngot = makeEntry(Ingots.ELECTRUM);
		LootPoolEntry invarIngot = makeEntry(Ingots.INVAR);
		LootPoolEntry nickelIngot = makeEntry(Ingots.NICKEL);
		LootPoolEntry steelIngot = makeEntry(Ingots.STEEL);
		LootPoolEntry zincIngot = makeEntry(Ingots.ZINC);
		LootPoolEntry advancedFrame = makeEntry(TRContent.MachineBlocks.ADVANCED.frame.asItem());
		LootPoolEntry advancedCircuit = makeEntry(Parts.ADVANCED_CIRCUIT);
		LootPoolEntry dataStorageChip = makeEntry(Parts.DATA_STORAGE_CHIP);

		LootPoolEntry chromeIngot = makeEntry(Ingots.CHROME);
		LootPoolEntry iridiumIngot = makeEntry(Ingots.IRIDIUM);
		LootPoolEntry platinumIngot = makeEntry(Ingots.PLATINUM);
		LootPoolEntry titaniumIngot = makeEntry(Ingots.TITANIUM);
		LootPoolEntry tungstenIngot = makeEntry(Ingots.TUNGSTEN);
		LootPoolEntry tungstensteelIngot = makeEntry(Ingots.TUNGSTENSTEEL);
		LootPoolEntry industrialFrame = makeEntry(TRContent.MachineBlocks.INDUSTRIAL.frame.asItem());
		LootPoolEntry industrialCircuit = makeEntry(Parts.INDUSTRIAL_CIRCUIT);
		LootPoolEntry energyFlowChip = makeEntry(Parts.ENERGY_FLOW_CHIP);


		LootPool poolBasic = FabricLootPoolBuilder.builder().withEntry(copperIngot).withEntry(tinIngot)
				.withEntry(leadIngot).withEntry(silverIngot).withEntry(refinedronIngot).withEntry(advancedalloyIngot)
				.withEntry(basicFrame).withEntry(basicCircuit).withEntry(rubberSapling).rolls(UniformLootNumberProvider.create(1.0f, 2.0f))
				.build();

		LootPool poolAdvanced = FabricLootPoolBuilder.builder().withEntry(aluminumIngot).withEntry(electrumIngot)
				.withEntry(invarIngot).withEntry(nickelIngot).withEntry(steelIngot).withEntry(zincIngot)
				.withEntry(advancedFrame).withEntry(advancedCircuit).withEntry(dataStorageChip).rolls(UniformLootNumberProvider.create(1.0f, 3.0f))
				.build();

		LootPool poolIndustrial = FabricLootPoolBuilder.builder().withEntry(chromeIngot).withEntry(iridiumIngot)
				.withEntry(platinumIngot).withEntry(titaniumIngot).withEntry(tungstenIngot).withEntry(tungstensteelIngot)
				.withEntry(industrialFrame).withEntry(industrialCircuit).withEntry(energyFlowChip).rolls(UniformLootNumberProvider.create(1.0f, 3.0f))
				.build();

		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, ident, supplier, setter) -> {
			String stringId = ident.toString();
			if (!stringId.contains("chests")) {
				return;
			}
			if (TechRebornConfig.enableOverworldLoot) {
				if (containsAnyOf(stringId,
					List.of("mineshaft",
						"dungeon",
						"shipwreck",
						"ruin_small",
						"weaponsmith",
						"armorer",
						"toolsmith")
					)
				) {
					supplier.withPool(poolBasic);
				}
				else if (containsAnyOf(stringId,
					List.of(
						"ruin_big",
						"outpost",
						"igloo",
						"temple"
						)
						)
				) {
					supplier.withPool(poolAdvanced);
				}
				else if (containsAnyOf(stringId,
					List.of(
						"stronghold",
						"mansion",
						"pyramid"
						)
					)
				) {
					supplier.withPool(poolIndustrial);
				}
			}

			if (TechRebornConfig.enableNetherLoot) {
				if (containsAnyOf(stringId,
					List.of("bridge", "bastion","cities"
					))) {
					supplier.withPool(poolAdvanced);
				}
			}

			if (TechRebornConfig.enableEndLoot) {
				if (containsAnyOf(stringId, List.of("end"))) {
					supplier.withPool(poolAdvanced);
				}
			}

		});
	}

	/**
	 * Makes loot entry from item provided
	 *
	 * @param item Item to include into LootEntry
	 * @return LootEntry for item provided
	 */
	private static LootPoolEntry makeEntry(ItemConvertible item) {
		return makeEntry(item, 5);
	}

	/**
	 * Makes loot entry from item provided with weight provided
	 *
	 * @param item   Item to include into LootEntry
	 * @param weight Weight of that item
	 * @return LootEntry for item and weight provided
	 */
	private static LootPoolEntry makeEntry(ItemConvertible item, int weight) {
		return ItemEntry.builder(item).weight(weight)
				.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f))).build();
	}
	private static boolean containsAnyOf(String checkString, Iterable<String> iterable){
		return StreamSupport.stream(iterable.spliterator(), false).anyMatch(checkString::contains);
	}

}
