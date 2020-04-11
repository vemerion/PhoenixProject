package ru.googletan.projectend.world;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import ru.googletan.projectend.world.biomes.BiomeEndJungle;
import ru.googletan.projectend.world.biomes.BiomeEndUnder;
import ru.googletan.projectend.world.biomes.BiomeEndVolcano;

public class BiomeRegistrar 
{
	
	public static final Biome END_JUNGLE = new BiomeEndJungle();
	public static final Biome END_VOLCANO = new BiomeEndVolcano();
	public static final Biome END_UNDER = new BiomeEndUnder();
	
	public static void registerBiomes()
	{
		initBiome(END_JUNGLE, "stygian_growth", Type.END);
		initBiome(END_VOLCANO, "acidic_plains", Type.END);
	}
	
	private static void initBiome(Biome biome, String name, Type... types)
	{
		biome.setRegistryName(name);
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, types);
	}
}
