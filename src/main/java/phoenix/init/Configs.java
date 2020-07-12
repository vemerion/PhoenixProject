package phoenix.init;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import phoenix.Phoenix;

@Config(modid = Phoenix.MOD_ID, category = "")
@Mod.EventBusSubscriber(modid = Phoenix.MOD_ID)
public class Configs 
{
	public static ConfigWorldGen worldgen = new ConfigWorldGen();
	
	public static class ConfigWorldGen 
	{
		@Config.Comment({"Controls size of end biomes. Larger number = larger biomes", "Default: 4"})
		@Config.RequiresWorldRestart
		public int endBiomeSize = 7;
		
		@Config.Comment({"Reduce number of end biomes by percent (range 0-99). e.g. 40 would generate 40% fewer end biomes", "Default: 0"})
		@Config.RequiresWorldRestart
		public int biomeReducer = 0;

		@Config.Comment({"Game mode. \"false\" for normal, true for \"Liahim\"  mode"})
		@Config.RequiresWorldRestart
		public boolean Liahim_Mode = false;

		@Config.Comment({"Graphics quality: true if high, false if off.", " Affects the number of particles"})
		public boolean graphics = true;
	}

	@SubscribeEvent
	public static void onConfigReload(ConfigChangedEvent.OnConfigChangedEvent event) 
	{
		if (Phoenix.MOD_ID.equals(event.getModID()))
			ConfigManager.sync(Phoenix.MOD_ID, Config.Type.INSTANCE);
	}

}
