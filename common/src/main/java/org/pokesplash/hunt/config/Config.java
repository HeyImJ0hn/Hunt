package org.pokesplash.hunt.config;

import com.google.gson.Gson;
import org.pokesplash.hunt.Hunt;
import org.pokesplash.hunt.util.Utils;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * Config file.
 */
public class Config {
	private int huntDuration; // How long each hunt should last, in minutes.
	private int huntAmount; // How many hunts should there be at once.
	private float commonPokemonRarity; // What rarity is classed as common.
	private float uncommonPokemonRarity; // What rarity is classed as uncommon.
	private float rarePokemonRarity; // What rarity is classed as rare.
	private float commonPokemonPrice; // What price should common pokemon be
	private float uncommonPokemonPrice; // What price should uncommon pokemon be
	private float rarePokemonPrice; // What price should rare pokemon be
	private float ultraRarePokemonPrice; // What price should any pokemon less than rare be
	private Properties matchProperties; // What properties should be checked to complete the hunt.
	private ArrayList<CustomPrice> customPrices; // List of custom prices.

	public Config() {
		huntDuration = 60;
		huntAmount = 7;
		commonPokemonRarity = 7;
		uncommonPokemonRarity = 4;
		rarePokemonRarity = 1;
		commonPokemonPrice = 100;
		uncommonPokemonPrice = 500;
		rarePokemonPrice = 700;
		ultraRarePokemonPrice = 1000;
		matchProperties = new Properties();
		customPrices = new ArrayList<>();
		customPrices.add(new CustomPrice());
	}

	/**
	 * Reads the config or writes one if a config doesn't exist.
	 */
	public void init() {
		CompletableFuture<Boolean> futureRead = Utils.readFileAsync("/config/hunt/", "config.json",
				el -> {
					Gson gson = Utils.newGson();
					Config cfg = gson.fromJson(el, Config.class);
					huntDuration = cfg.getHuntDuration();
					huntAmount = cfg.getHuntAmount();
					commonPokemonRarity = cfg.getCommonPokemonRarity();
					uncommonPokemonRarity = cfg.getUncommonPokemonRarity();
					rarePokemonRarity = cfg.getRarePokemonRarity();
					commonPokemonPrice = cfg.getCommonPokemonPrice();
					uncommonPokemonPrice = cfg.getUncommonPokemonPrice();
					rarePokemonPrice = cfg.getRarePokemonPrice();
					ultraRarePokemonPrice = cfg.getUltraRarePokemonPrice();
					matchProperties = cfg.getMatchProperties();
					customPrices = cfg.getCustomPrices();
				});

		// If the config couldn't be read, write a new one.
		if (!futureRead.join()) {
			Hunt.LOGGER.info("No config.json file found for Hunt. Attempting to generate one.");
			Gson gson = Utils.newGson();
			String data = gson.toJson(this);
			CompletableFuture<Boolean> futureWrite = Utils.writeFileAsync("/config/hunt/", "config.json",
					data);

			// If the write failed, log fatal.
			if (!futureWrite.join()) {
				Hunt.LOGGER.fatal("Could not write config for Hunt.");
			}
			return;
		}
		Hunt.LOGGER.info("Hunt config file read successfully.");
	}

	/**
	 * Bunch of Getters
	 */

	public int getHuntDuration() {
		return huntDuration;
	}

	public int getHuntAmount() {
		return huntAmount;
	}

	public float getCommonPokemonRarity() {
		return commonPokemonRarity;
	}

	public float getUncommonPokemonRarity() {
		return uncommonPokemonRarity;
	}

	public float getRarePokemonRarity() {
		return rarePokemonRarity;
	}

	public float getCommonPokemonPrice() {
		return commonPokemonPrice;
	}

	public float getUncommonPokemonPrice() {
		return uncommonPokemonPrice;
	}

	public float getRarePokemonPrice() {
		return rarePokemonPrice;
	}

	public float getUltraRarePokemonPrice() {
		return ultraRarePokemonPrice;
	}

	public Properties getMatchProperties() {
		return matchProperties;
	}

	public ArrayList<CustomPrice> getCustomPrices() {
		return customPrices;
	}
}