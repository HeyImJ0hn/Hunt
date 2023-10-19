package org.pokesplash.hunt.automation;

import org.pokesplash.hunt.Hunt;

public class TrackerPokemon {
	private String pokemon;
	private double multiplier;
	private int tracker;
	private int count;

	public TrackerPokemon(String pokemon) {
		this.pokemon = pokemon;
		this.multiplier = 1;
		this.tracker = 0;
		this.count = 0;
	}

	public double getMultiplier() {
		return multiplier;
	}

	public String getPokemon() {
		return pokemon;
	}

	private void addCount() {
		if (!Hunt.config.isEnableTracker()) {
			return;
		}

		count ++;
		if (count >= Hunt.trackerConfig.getFrequency()) {

			if (tracker > 0) {
				if (multiplier < Hunt.trackerConfig.getMaximum()) {
					multiplier += Hunt.trackerConfig.getMultiplier();
				}
			} else if (tracker < 0) {
				if (multiplier > Hunt.trackerConfig.getMinimum()) {
					multiplier -= Hunt.trackerConfig.getMultiplier();
				}
			}

			count = 0;
			tracker = 0;
		}
	}

	public void addTracker() {
		this.tracker ++; // TODO this adds to the fucking count?!?!
		addCount();
	}

	public void subtractTracker() {
		this.tracker --;
		addCount();
	}
}
