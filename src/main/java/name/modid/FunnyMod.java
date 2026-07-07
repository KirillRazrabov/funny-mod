package name.modid;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class FunnyMod implements ModInitializer {

	public static final String MOD_ID = "funny-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Funny Mod...");

		ModItems.initialize();

		Registry.register(
				BuiltInRegistries.CREATIVE_MODE_TAB,
				CUSTOM_CREATIVE_TAB_KEY,
				CUSTOM_CREATIVE_TAB
		);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static class ModItems {

		public static final Item SUSPICIOUS_SUBSTANCE =
				register(
						"suspicious_substance",
						Item::new,
						new Item.Properties()
				);

		public static void initialize() {
			LOGGER.info("Registering items...");
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

			Registry.register(
					BuiltInRegistries.ITEM,
					key,
					item
			);

			return item;
		}
	}

	public static final ResourceKey<CreativeModeTab> CUSTOM_CREATIVE_TAB_KEY =
			ResourceKey.create(
					Registries.CREATIVE_MODE_TAB,
					id("creative_tab")
			);

	public static final CreativeModeTab CUSTOM_CREATIVE_TAB =
			FabricCreativeModeTab.builder()
					.title(Component.translatable("itemGroup.funny-mod"))
					.icon(ModItems.SUSPICIOUS_SUBSTANCE::getDefaultInstance)
					.displayItems((_, output) -> output.accept(ModItems.SUSPICIOUS_SUBSTANCE))
					.build();
}