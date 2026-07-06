package name.modid;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class FunnyMod implements ModInitializer {
	public static final String MOD_ID = "funny-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Hello Fabric world!");
		ModItems.initialize();
	}

	public class ModItems {

		public static final Item SUSPICIOUS_SUBSTANCE =
				register("suspicious_substance", Item::new, new Item.Properties());

		public static void initialize() {
			FunnyMod.LOGGER.info("Registering items...");
			CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS)
					.register((creativeTab) -> creativeTab.accept(ModItems.SUSPICIOUS_SUBSTANCE));
		}

		private static <T extends Item> T register(
				String name,
				Function<Item.Properties, T> factory,
				Item.Properties settings
		) {
			ResourceKey<Item> key = ResourceKey.create(
					Registries.ITEM,
					FunnyMod.id(name)
			);

			T item = factory.apply(settings.setId(key));

			Registry.register(BuiltInRegistries.ITEM, key, item);

			return item;
		}
	}
	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
